package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Set<Position> map = new HashSet<>();

        int offestX = lines.getFirst().length() / 2;
        int offestY = lines.size() / 2;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    map.add(new Position(j - offestX, i - offestY));
                }
            }
        }

        int infections = 0;
        Direction direction = Direction.UP;
        Position position = new Position(0, 0);
        for (int i = 0; i < 10000; i++) {

            if (map.contains(position)) {
                direction = switch (direction) {
                    case UP -> Direction.RIGHT;
                    case RIGHT -> Direction.DOWN;
                    case DOWN -> Direction.LEFT;
                    case LEFT -> Direction.UP;
                };
                map.remove(position);
            } else {
                direction = switch (direction) {
                    case UP -> Direction.LEFT;
                    case RIGHT -> Direction.UP;
                    case DOWN -> Direction.RIGHT;
                    case LEFT -> Direction.DOWN;
                };
                infections++;
                map.add(position);
            }

            position = new Position(position.x() + direction.getXOffset(), position.y() + direction.getYOffset());
        }

        System.out.println(infections);
    }
}
