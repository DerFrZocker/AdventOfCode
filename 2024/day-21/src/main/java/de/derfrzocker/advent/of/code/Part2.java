package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    static Map<Character, Position> numericKeypad = Map.ofEntries(Map.entry('7', Position.of(0, 0)), Map.entry('8', Position.of(1, 0)), Map.entry('9', Position.of(2, 0)), Map.entry('4', Position.of(0, 1)), Map.entry('5', Position.of(1, 1)), Map.entry('6', Position.of(2, 1)), Map.entry('1', Position.of(0, 2)), Map.entry('2', Position.of(1, 2)), Map.entry('3', Position.of(2, 2)), Map.entry('0', Position.of(1, 3)), Map.entry('A', Position.of(2, 3)));

    static Map<Character, Position> directionalKeypad = Map.ofEntries(Map.entry('^', Position.of(1, 0)), Map.entry('A', Position.of(2, 0)), Map.entry('<', Position.of(0, 1)), Map.entry('v', Position.of(1, 1)), Map.entry('>', Position.of(2, 1)));

    static Position posKeypad = numericKeypad.get('A');

    static Map<CacheKey, CacheResult> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long count = 0;
        for (String line : lines) {
            posKeypad = numericKeypad.get('A');
            List<Character> numericMoves = new ArrayList<>();
            for (char c : line.toCharArray()) {
                numericMoves.addAll(moveNumeric(c));
            }

            long size = 0;
            Position initialPosition = directionalKeypad.get('A');
            for (char c : numericMoves) {
                AdvanceResult advanceResult = advance(c, 0, initialPosition);
                initialPosition = advanceResult.nextPostion();
                size = Math.addExact(advanceResult.length, size);
            }

            long tmp = Math.multiplyExact(size, Integer.parseInt(line.substring(0, line.length() - 1)));
            count = Math.addExact(count, tmp);
        }

        System.out.println("Result: " + count);
    }

    private static AdvanceResult advance(char c, int index, Position initialState) {
        if (index >= 25) {
            return new AdvanceResult(1, initialState);
        }
        Position nextInitialState = null;
        long amount = 0;
        Position position = initialState;
        CacheResult cacheResult = cache.get(new CacheKey(c, position, index));
        if (cacheResult != null) {
            nextInitialState = cacheResult.nextPostion();
            amount = Math.addExact(amount, cacheResult.length);
        } else {
            Result result = pressDirectional(c, position);
            nextInitialState = result.newPos();

            position = result.newPos;
            Position po = directionalKeypad.get('A');
            long tmp = 0;
            for (char c2 : result.characters()) {
                AdvanceResult advanceResult = advance(c2, index + 1, po);
                po = advanceResult.nextPostion();
                tmp = Math.addExact(advanceResult.length, tmp);
            }

            cache.put(new CacheKey(c, initialState, index), new CacheResult(tmp, nextInitialState));
            amount = Math.addExact(amount, tmp);
        }

        return new AdvanceResult(amount, nextInitialState);
    }

    private static String toString(List<Character> characters) {
        StringBuilder result = new StringBuilder();
        for (Character character : characters) {
            result.append(character);
        }

        return result.toString();
    }

    private static Result pressDirectional(char c, Position lastPosition) {
        Position position = directionalKeypad.get(c);
        if (position.equals(lastPosition)) {
            return new Result(position, List.of('A'));
        }

        int xDif = position.x() - lastPosition.x();
        int yDif = position.y() - lastPosition.y();
        Direction[] directions;

        if (lastPosition.x() == 0 && position.y() == 0) {
            // posKeypad == 1 4 7
            directions = new Direction[]{Direction.RIGHT, Direction.UP};
        } else if (position.x() == 0 && lastPosition.y() == 0) {
            directions = new Direction[]{Direction.DOWN, Direction.LEFT};
        } else {
            if (xDif == 0) {
                if (yDif < 0) {
                    directions = new Direction[]{Direction.UP};
                } else {
                    directions = new Direction[]{Direction.DOWN};
                }
            } else if (yDif == 0) {
                if (xDif < 0) {
                    directions = new Direction[]{Direction.LEFT};
                } else {
                    directions = new Direction[]{Direction.RIGHT};
                }
            } else if (xDif < 0) {
                if (yDif < 0) {
                    directions = new Direction[]{Direction.LEFT, Direction.UP}; // Better
                } else {
                    directions = new Direction[]{Direction.LEFT, Direction.DOWN}; // Better
                }
            } else {
                if (yDif < 0) {
                    directions = new Direction[]{Direction.UP, Direction.RIGHT}; // Better
                } else {
                    directions = new Direction[]{Direction.DOWN, Direction.RIGHT}; // Better
                }
            }
        }

        xDif = Math.abs(xDif);
        yDif = Math.abs(yDif);

        List<Character> characters = new ArrayList<>();
        for (Direction direction : directions) {
            switch (direction) {
                case UP -> {
                    for (int i = 0; i < yDif; i++) {
                        characters.add('^');
                    }
                }
                case DOWN -> {
                    for (int i = 0; i < yDif; i++) {
                        characters.add('v');
                    }
                }
                case LEFT -> {
                    for (int i = 0; i < xDif; i++) {
                        characters.add('<');
                    }
                }
                case RIGHT -> {
                    for (int i = 0; i < xDif; i++) {
                        characters.add('>');
                    }
                }
            }
        }

        characters.add('A');

        return new Result(position, characters);
    }

    private record Result(Position newPos, List<Character> characters) {
    }

    private static List<Character> moveNumeric(char c) {
        Position position = numericKeypad.get(c);
        if (position.equals(posKeypad)) {
            return List.of('A');
        }

        int xDif = position.x() - posKeypad.x();
        int yDif = position.y() - posKeypad.y();
        Direction[] directions;

        if (posKeypad.x() == 0 && position.y() == 3) {
            // posKeypad == 1 4 7
            directions = new Direction[]{Direction.RIGHT, Direction.DOWN};
        } else if (position.x() == 0 && posKeypad.y() == 3) {
            directions = new Direction[]{Direction.UP, Direction.LEFT};
        } else {
            if (xDif == 0) {
                if (yDif < 0) {
                    directions = new Direction[]{Direction.UP};
                } else {
                    directions = new Direction[]{Direction.DOWN};
                }
            } else if (yDif == 0) {
                if (xDif < 0) {
                    directions = new Direction[]{Direction.LEFT};
                } else {
                    directions = new Direction[]{Direction.RIGHT};
                }
            } else if (xDif < 0) {
                if (yDif < 0) {
                    directions = new Direction[]{Direction.LEFT, Direction.UP}; // Better
                } else {
                    directions = new Direction[]{Direction.LEFT, Direction.DOWN};
                }
            } else {
                if (yDif < 0) {
                    directions = new Direction[]{Direction.UP, Direction.RIGHT}; // Better
                } else {
                    directions = new Direction[]{Direction.DOWN, Direction.RIGHT}; // Better
                }
            }
        }

        xDif = Math.abs(xDif);
        yDif = Math.abs(yDif);

        List<Character> characters = new ArrayList<>();
        for (Direction direction : directions) {
            switch (direction) {
                case UP -> {
                    for (int i = 0; i < yDif; i++) {
                        characters.add('^');
                    }
                }
                case DOWN -> {
                    for (int i = 0; i < yDif; i++) {
                        characters.add('v');
                    }
                }
                case LEFT -> {
                    for (int i = 0; i < xDif; i++) {
                        characters.add('<');
                    }
                }
                case RIGHT -> {
                    for (int i = 0; i < xDif; i++) {
                        characters.add('>');
                    }
                }
            }
        }

        posKeypad = position;

        characters.add('A');

        return characters;
    }

    private record CacheKey(char current, Position previous, int layer) {

    }

    private record CacheResult(long length, Position nextPostion) {

    }

    private record AdvanceResult(long length, Position nextPostion) {

    }
}
