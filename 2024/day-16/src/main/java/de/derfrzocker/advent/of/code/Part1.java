package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Note: This solution might not necessarily work for all inputs, for a solution, which should solve all possible inputs see {@link Part2}.
 */
public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    static int width;
    static int height;
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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = lines.get(i).charAt(j);

                if (c == '.') {
                    maze[i][j] = Integer.MAX_VALUE;
                } else if (c == '#') {
                    maze[i][j] = WALL;
                } else if (c == 'S') {
                    maze[i][j] = 0;
                    startX = j;
                    startY = i;
                } else if (c == 'E') {
                    maze[i][j] = Integer.MAX_VALUE;
                    endX = j;
                    endY = i;
                }
            }
        }

        List<PositionDirection> toCheck = new ArrayList<>();
        List<Integer> goals = new ArrayList<>();
        toCheck.add(new PositionDirection(startX, startY, Direction.RIGHT));
        while (!toCheck.isEmpty()) {
            PositionDirection checking = toCheck.removeLast();
            int currentValue = maze[checking.y][checking.x];
            for (Direction direction : Direction.values()) {
                int nextValue = maze[checking.y + direction.getYOffset()][checking.x + direction.getXOffset()];
                if (nextValue == WALL) {
                    continue;
                }

                int newValue = currentValue + 1;
                if (direction != checking.direction) {
                    newValue += 1000;
                }

                if (newValue < nextValue) {
                    maze[checking.y + direction.getYOffset()][checking.x + direction.getXOffset()] = newValue;

                    toCheck.add(new PositionDirection(checking.x + direction.getXOffset(), checking.y + direction.getYOffset(), direction));
                }

                if (checking.y + direction.getYOffset() == endY && checking.x + direction.getXOffset() == endX) {
                    System.out.println("Finish: " + newValue);
                    goals.add(newValue);
                }
            }
        }

        goals.sort(Integer::compareTo);
        System.out.println(goals.get(0));
    }

    public record PositionDirection(int x, int y, Direction direction) {

    }
}
