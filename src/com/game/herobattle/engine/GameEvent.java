package com.game.herobattle.engine;

public class GameEvent {
    private final String source;
    private final String message;

    public GameEvent(String source, String message) {
        this.source = source;
        this.message = message;
    }
    public String getSource() {
        return source; }
    public String getMessage() {
        return message; }

    @Override
    public String toString() {
        return "[" + source + "] " + message;
    }
}
