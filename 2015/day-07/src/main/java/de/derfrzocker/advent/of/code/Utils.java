package de.derfrzocker.advent.of.code;

public final class Utils {

    public static Direction getDirection(char c) {
        return Direction.fromChar(c);
    }

    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Utils() {

    }
}
