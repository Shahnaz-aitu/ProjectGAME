import com.game.herobattle.*;
import com.game.herobattle.decorators.BuffDecorator;
import com.game.herobattle.decorators.ShieldDecorator;
import com.game.herobattle.observer.GameEngine;
import com.game.herobattle.heroes.Hero;
import com.game.herobattle.heroes.HeroCreator;

import java.util.Scanner;

public class Demo {

    public void Start() {
        Scanner sc = new Scanner(System.in);
        UserManager um = new UserManager(sc);
        GameEngine engine = GameEngine.getInstance();
        engine.setScanner(sc);

        String currentUser = null;
        Hero chosen = null;

        mainLoop:
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1) registration");
            System.out.println("2) log in");
            System.out.println("3) choose player");
            System.out.println("4) start game");
            System.out.println("5) exit");
            System.out.print("> ");
            String cmd = sc.nextLine().trim();

            switch (cmd) {
                case "1":
                case "registration":
                    um.register();
                    break;
                case "2":
                case "log in":
                    boolean ok = um.login();
                    if (ok) {
                        System.out.print("Confirm name again to set session: ");
                        currentUser = sc.nextLine().trim();
                    }
                    break;
                case "3":
                case "choose player":
                    if (currentUser == null) {
                        System.out.println("You must log in first.");
                        break;
                    }
                    System.out.println("Choose hero: mage / warrior / archer / princess");
                    System.out.print("> ");
                    String type = sc.nextLine().toLowerCase();
                    try {
                        chosen = HeroCreator.create(type, currentUser);
                        System.out.println("You chose: " + chosen.getName());
                        System.out.println("Add extra: shield / buff / none ?");
                        String extra = sc.nextLine().toLowerCase();
                        if (extra.equals("shield")) {
                            chosen = new ShieldDecorator(chosen, 30);
                        } else if (extra.equals("buff")) {
                            chosen = new BuffDecorator(chosen, 1.3);
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unknown hero type.");
                    }
                    break;
                case "4":
                case "start game":
                    if (currentUser == null) { System.out.println("Log in first."); break; }
                    if (chosen == null) { System.out.println("Choose player first."); break; }
                    engine.runCampaignInteractive(sc, chosen);
                    chosen = null;
                    break;
                case "5":
                case "exit":
                    System.out.println("Bye!");
                    break mainLoop;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}