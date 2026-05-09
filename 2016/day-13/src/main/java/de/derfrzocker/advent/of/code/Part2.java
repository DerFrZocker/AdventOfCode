package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int GOAL_X = 31;
    private static final int GOAL_Y = 39;
    private static int FAVORITE_NUMBER = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        FAVORITE_NUMBER = Integer.parseInt(lines.getFirst());

        Set<Position> visited = new HashSet<>();
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(1, 1));
        int steps = 0;
        while (true) {
            steps++;

            Set<Position> newPositions = new HashSet<>();
            for (Position current : positions) {
                for (Direction direction : Direction.values()) {
                    int x = current.x() + direction.getXOffset();
                    int y = current.y() + direction.getYOffset();

                    if (isWall(x, y)) {
                        continue;
                    }

                    Position position = new Position(x, y);
                    if (!visited.contains(position)) {
                        visited.add(position);
                        newPositions.add(position);
                    }
                }
            }
            if (steps == 50) {
                break;
            }

            positions = newPositions;
        }

        System.out.println(visited.size());
    }

    private static boolean isWall(int x, int y) {
        if (x < 0 || y < 0) {
            return true;
        }
        int value = (x * x) + (3 * x) + (2 * x * y) + y + (y * y);

        int count = Integer.bitCount(value + FAVORITE_NUMBER);

        return count % 2 != 0;
    }
}
