package com.game.herobattle.engine;

import com.game.herobattle.observer.IObserver;
import java.util.ArrayList;
import java.util.List;

public class EventLogger implements IObserver {
    private static final EventLogger instance = new EventLogger();
    private final List<String> log = new ArrayList<>();

    private EventLogger() {}

    public static EventLogger getInstance() {
        return instance;
    }

    @Override
    public void onEvent(GameEvent event) {
        String line = event.toString();
        log.add(line);
        System.out.println("[MESSAGE] " + line);
    }

    public List<String> getLog() { return log; }
}
