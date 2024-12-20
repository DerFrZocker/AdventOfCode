package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Part1 {

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
        int[][] mazeCopy = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mazeCopy[y][x] = maze[y][x];
            }
        }

        int firstBest = solve(Integer.MAX_VALUE);
        System.out.println("Base line: " + firstBest);
        int result = 0;
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (mazeCopy[y][x] != WALL) {
                    continue;
                }

                for (int y1 = 0; y1 < height; y1++) {
                    for (int x1 = 0; x1 < width; x1++) {
                        maze[y1][x1] = mazeCopy[y1][x1];
                    }
                }

                maze[y][x] = Integer.MAX_VALUE;

                int solve = solve(firstBest);
                int div = firstBest - solve;
                System.out.println("C: " + solve + " div " + (firstBest - solve));

                if (div >= 100) {
                    result++;
                }
            }
        }

        System.out.println("Result: " + result);
    }

    private static int solve(int max) {
        List<Position> toCheck = new ArrayList<>();
        toCheck.add(new Position(startX, startY));
        while (!toCheck.isEmpty()) {
            Position checking = toCheck.removeFirst();

            for (Direction direction : Direction.values()) {
                int newX = checking.x() + direction.getXOffset();
                int newY = checking.y() + direction.getYOffset();

                if (maze[newY][newX] == WALL) {
                    continue;
                }

                int cost = maze[checking.y()][checking.x()] + 1;

                if (cost < maze[newY][newX]) {
                    maze[newY][newX] = cost;
                    toCheck.add(new Position(newX, newY));
                }

                if (newX == endX && newY == endY) {
//                    System.out.println(cost);
                    return cost;
                }
            }
        }

        return Integer.MIN_VALUE;
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
