package de.derfrzocker.advent.of.code;

public record Position(int x, int y) {

    public static Position of(String x, String y) {
        return new Position(Integer.parseInt(x), Integer.parseInt(y));
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }
}
