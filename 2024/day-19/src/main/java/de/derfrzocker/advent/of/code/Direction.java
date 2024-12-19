package de.derfrzocker.advent.of.code;

public enum Direction {

    UP('^', 0, -1),
    DOWN('v', 0, 1),
    LEFT('<', -1, 0),
    RIGHT('>', 1, 0);

    public static Direction fromChar(char c) {
        for (Direction direction : Direction.values()) {
            if (c == direction.getSymbol()) {
                return direction;
            }
        }

        return null;
    }

    private final char symbol;
    private final int xOffset;
    private final int yOffset;

    Direction(char symbol, int xOffset, int yOffset) {
        this.symbol = symbol;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public int getYOffset() {
        return this.yOffset;
    }
}
