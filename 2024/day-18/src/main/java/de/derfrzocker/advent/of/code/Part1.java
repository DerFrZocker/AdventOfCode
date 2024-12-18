package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    static int height = 70 + 1;
    static int width = 70 + 1;
    static int[][] maze = new int[height][width];

    static int startX = 0;
    static int startY = 0;

    static int endX = width - 1;
    static int endY = height - 1;

    private static final int WALL = -1;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                maze[y][x] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < 1024; i++) {
            String[] parts = lines.get(i).split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            maze[y][x] = WALL;
        }

        maze[startY][startX] = 0;

        List<Position> toCheck = new ArrayList<>();
        toCheck.add(new Position(startX, startY));
        while (!toCheck.isEmpty()) {
            Position checking = toCheck.removeFirst();

            for (Direction direction : Direction.values()) {
                int newX = checking.x() + direction.getXOffset();
                int newY = checking.y() + direction.getYOffset();

                if (!inBound(newX, newY) || maze[newY][newX] == WALL) {
                    continue;
                }

                int cost = maze[checking.y()][checking.x()] + 1;

                if (cost < maze[newY][newX]) {
                    maze[newY][newX] = cost;
                    toCheck.add(new Position(newX, newY));
                }

                if (newX == endX && newY == endY) {
                    System.out.println(cost);
                    return;
                }
            }
        }
    }

    private static boolean inBound(Position postion) {
        return inBound(postion.x(), postion.y());
    }

    private static boolean inBound(int x, int y) {
        if (y < 0) {
            return false;
        }

        if (x < 0) {
            return false;
        }

        if (x >= width) {
            return false;
        }

        if (y >= height) {
            return false;
        }

        return true;
    }
}
