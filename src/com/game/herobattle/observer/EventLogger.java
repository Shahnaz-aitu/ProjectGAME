package com.game.herobattle.observer;

import java.util.ArrayList;
import java.util.List;

public class EventLogger implements Observer {
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
}
