package com.game.herobattle.singleton;

import com.game.herobattle.attacks.*;
import com.game.herobattle.decorators.*;
import com.game.herobattle.enemies.*;
import com.game.herobattle.heroes.*;
import com.game.herobattle.observer.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public abstract class GameEngine implements Subject {
    private static final GameEngine instance = new GameEngine() {
        @Override
        public void notifyObservers(GameEvent event) {
        }};
    public static GameEngine getInstance() { return instance; }

    private final List<Observer> observers = new ArrayList<>();
    private final List<Effect> heroEffects = new ArrayList<>();
    private final List<Hero> allies = new ArrayList<>();
    private Hero player;
    private Scanner scanner;
    Scanner sc=new Scanner(System.in);

    public void setScanner(Scanner scanner) {
        this.scanner = scanner; }
    public Scanner getScanner() {
        return scanner; }

    public void setPlayer(Hero h) {
        this.player = h;
        player.registerObserver(EventLogger.getInstance());
        player.registerObserver(new Announcer());
    }

    public void registerObserver(Observer o) {
        if (!observers.contains(o)) observers.add(o); }
    public void unregisterObserver(Observer o) {
        observers.remove(o); }
    public void broadcast(GameEvent event) {
        for (Observer o : new ArrayList<>(observers)) o.onEvent(event);
    }

    public void applyEffectToHero(Hero hero, Effect effect) {
        heroEffects.add(effect);
        broadcast(new GameEvent("Effect", hero.getName() + " gets " + effect.name + " for " + effect.turns + " turns"));
    }

    public boolean heroHasEffect(Hero hero, String name) {
        for (Effect e : new ArrayList<>(heroEffects)) {
            if (e.name.equals(name) && e.turns > 0) return true;
        }
        return false;
    }

    public void addAlly(Hero h) {
        allies.add(h);
        broadcast(new GameEvent("Ally", h.getName() + " joined as ally."));
    }

    public Hero getFirstAliveEnemy() {
          return null;
    }

    private void tickEffects() {
        Iterator<Effect> it = heroEffects.iterator();
        while (it.hasNext()) {
            Effect e = it.next();
            e.turns--;
            if (e.turns <= 0) {
                broadcast(new GameEvent("Effect", "Effect " + e.name + " expired"));
                it.remove();
            }
        }
    }

    public void runCampaignInteractive(Scanner sc, Hero player) {
        this.scanner = sc;
        setPlayer(player);
        registerObserver(EventLogger.getInstance());
        registerObserver(new Announcer());

        broadcast(new GameEvent("Engine", "Campaign started!"));
        List<Enemy> goblins = new ArrayList<>();
        goblins.add(new Goblin());
        goblins.add(new Goblin());
        goblins.add(new Goblin());
        battleLoop(goblins, sc);
        if (player.getHp() <= 0) { broadcast(new GameEvent("Game", "Player died in goblins wave.")); return; }

        List<Enemy> floods = new ArrayList<>();
        floods.add(new Flood());
        battleLoop(floods, sc);
        if (player.getHp() <= 0) { broadcast(new GameEvent("Game", "Player drowned in flood.")); return; }

        System.out.println("Choose pre-boss reward: 1) Full heal 2) Small buff 3) Nothing");
        String pick = sc.nextLine().trim();
        if (pick.equals("1")) player.heal(player.getMaxHp());
        else if (pick.equals("2")) {
            Hero buffed = new BuffDecorator(player, 1.25);
            this.player = buffed;
            broadcast(new GameEvent("Engine", "You received a damage buff."));
        }
        List<Enemy> boss = new ArrayList<>();
        boss.add(new Dragon());
        battleLoop(boss, sc);

        if (player.getHp() > 0) broadcast(new GameEvent("Game", "You won the campaign!"));
        else broadcast(new GameEvent("Game", "You lost the campaign..."));
    }

    private void battleLoop(List<Enemy> enemies, Scanner sc) {
        while (true) {
            enemies.removeIf(e -> !e.isAlive());
            if (enemies.isEmpty()) { broadcast(new GameEvent("Battle", "Wave cleared.")); break; }
            if (player.getHp() <= 0) { broadcast(new GameEvent("Battle", "Player died.")); break; }

            System.out.println("\nPlayer: " + player.getName() + " HP=" + player.getHp() + "/" + player.getMaxHp());
            System.out.println("Enemies:");
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println((i+1) + ") " + enemies.get(i).getName() + " HP=" + enemies.get(i).getHp());
            }
            System.out.println("Commands: attack [index], special, change [melee/ranged/magic], status, help");
            System.out.print("> ");
            String line = sc.nextLine().trim();
            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();

            switch (cmd) {
                case "attack":
                    if (parts.length < 2) {
                        System.out.println("Specify index."); continue; }
                    int idx;
                    try { idx = Integer.parseInt(parts[1]) - 1; } catch (Exception e) {
                        System.out.println("Bad index."); continue; }
                    if (idx < 0 || idx >= enemies.size()) {
                        System.out.println("No such enemy."); continue; }
                    Enemy target = enemies.get(idx);
                    GameCommand a = new AttackCommand(player, target);
                    a.execute();
                    break;
                case "special":
                    player.useSpecial(this);
                    break;
                case "change":
                    if (parts.length < 2) { System.out.println("Specify melee/ranged/magic.");continue; }
                    AttackStrategy s;
                    switch (parts[1].toLowerCase()) {
                        case "melee": s = new MeleeAttack(); break;
                        case "ranged": s = new RangedAttack(); break;
                        case "magic": s = new MagicAttack(); break;
                        default: System.out.println("Unknown strategy."); continue;
                    }
                    player.changeStrategy(s);
                    break;
                case "status":
                    System.out.println(player.getName() + " HP=" + player.getHp() + "/" + player.getMaxHp() + ", Strategy=" + player.strategy.getName());
                    continue;
                case "help":
                    System.out.println("attack [index], special, change [melee/ranged/magic], status");
                    continue;
                default:
                    System.out.println("Unknown command.");
                    continue;
            }
            tickEffects();

            for (Enemy e : new ArrayList<>(enemies)) {
                if (player.getHp() <= 0) break;
                e.performAction(player);
            }

            for (Hero ally : new ArrayList<>(allies)) {
                if (enemies.isEmpty()) break;
                if (ally.getHp() <= 0) continue;
                Enemy t = enemies.get(0);
                GameCommand ac = new AttackCommand(ally, t);
                ac.execute();
            }
        }
    }
}
