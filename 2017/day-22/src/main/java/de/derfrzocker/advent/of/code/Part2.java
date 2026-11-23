package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Map<Position, State> map = new HashMap<>();

        int offestX = lines.getFirst().length() / 2;
        int offestY = lines.size() / 2;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    map.put(new Position(j - offestX, i - offestY), State.INFECTED);
                }
            }
        }

        int infections = 0;
        Direction direction = Direction.UP;
        Position position = new Position(0, 0);
        for (int i = 0; i < 10000000; i++) {

            State state = map.getOrDefault(position, State.CLEAN);
            switch (state) {
                case CLEAN -> {
                    direction = switch (direction) {
                        case UP -> Direction.LEFT;
                        case RIGHT -> Direction.UP;
                        case DOWN -> Direction.RIGHT;
                        case LEFT -> Direction.DOWN;
                    };
                    map.put(position, State.WEAKENED);
                }
                case WEAKENED -> {
                    map.put(position, State.INFECTED);
                    infections++;
                }
                case INFECTED -> {
                    direction = switch (direction) {
                        case UP -> Direction.RIGHT;
                        case RIGHT -> Direction.DOWN;
                        case DOWN -> Direction.LEFT;
                        case LEFT -> Direction.UP;
                    };
                    map.put(position, State.FLAGGED);
                }
                case FLAGGED -> {
                    direction = switch (direction) {
                        case UP -> Direction.DOWN;
                        case RIGHT -> Direction.LEFT;
                        case DOWN -> Direction.UP;
                        case LEFT -> Direction.RIGHT;
                    };
                    map.remove(position);
                }
            }

            position = new Position(position.x() + direction.getXOffset(), position.y() + direction.getYOffset());
        }

        System.out.println(infections);
    }

    private enum State {
        CLEAN,
        WEAKENED,
        INFECTED,
        FLAGGED
    }
}
