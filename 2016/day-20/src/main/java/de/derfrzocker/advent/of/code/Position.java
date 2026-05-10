package de.derfrzocker.advent.of.code;

public record Position(int x, int y) {

    public static Position of(String x, String y) {
        return new Position(Integer.parseInt(x), Integer.parseInt(y));
    }
}
