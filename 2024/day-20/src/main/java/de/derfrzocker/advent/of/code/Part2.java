package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    static int height;
    static int width;
    static int[][] maze;

    static int startX;
    static int startY;

    static int endX;
    static int endY;

    private static final int WALL = -1;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        height = lines.size();
        width = lines.get(0).length();
        maze = new int[height][width];

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                char c = line.charAt(x);
                if (c == '#') {
                    maze[y][x] = WALL;
                } else if (c == '.') {
                    maze[y][x] = Integer.MAX_VALUE;
                } else if (c == 'S') {
                    startX = x;
                    startY = y;
                    maze[y][x] = 0;
                } else if (c == 'E') {
                    endX = x;
                    endY = y;
                    maze[y][x] = Integer.MAX_VALUE;
                }
            }
        }

        maze[startY][startX] = 0;

        List<Position> firstBest = solve();
        int baseLine = firstBest.size() - 1;

        int result = getResult(firstBest);

        System.out.println(result);
    }

    private static int getResult(List<Position> firstBest) {
        int result = 0;

        for (int i = 0; i < firstBest.size(); i++) {
            for (int j = i + 1; j < firstBest.size(); j++) {
                Position start = firstBest.get(i);
                Position end = firstBest.get(j);

                int distance = Math.abs(start.x() - end.x()) + Math.abs(start.y() - end.y());
                if (distance > 20) {
                    continue;
                }

                int skipped = j - i - distance;
                if (skipped <= 0) {
                    continue;
                }

                if (skipped >= 100) {
//                    System.out.println("Start: (%d|%d) End: (%d|%d); Distance: %d".formatted(start.x(), start.y(), end.x(), end.y(), distance));
                    result = Math.addExact(result, 1);
                }
            }
        }
        return result;
    }

    private static List<Position> solve() {
        List<Position> path = new ArrayList<>();
        Position nextPosition = new Position(startX, startY);
        Position end = new Position(endX, endY);
        Direction lastDirection = null;
        path.add(nextPosition);
        do {
            boolean found = false;
            Direction foundDirection = null;
            Position nextPos = null;
            for (Direction direction : Direction.values()) {
                if (lastDirection != null) {
                    switch (lastDirection) {
                        case UP -> {
                            if (direction == Direction.DOWN) {
                                continue;
                            }
                        }
                        case DOWN -> {
                            if (direction == Direction.UP) {
                                continue;
                            }
                        }
                        case LEFT -> {
                            if (direction == Direction.RIGHT) {
                                continue;
                            }
                        }
                        case RIGHT -> {
                            if (direction == Direction.LEFT) {
                                continue;
                            }
                        }
                    }
                }

                int newX = nextPosition.x() + direction.getXOffset();
                int newY = nextPosition.y() + direction.getYOffset();
                if (maze[newY][newX] == WALL) {
                    continue;
                }

                if (found) {
                    throw new RuntimeException("Error: " + newX + ", " + newY);
                }

                foundDirection = direction;
                nextPos = new Position(newX, newY);
                found = true;
            }

            lastDirection = foundDirection;
            nextPosition = nextPos;

            path.add(nextPosition);
            if (!found) {
                throw new RuntimeException("Error");
            }
        } while (!nextPosition.equals(end));

        return path;
    }
}
