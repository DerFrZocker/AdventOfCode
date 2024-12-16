package de.derfrzocker.advent.of.code;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File DATA2 = new File("data2.txt");
    private static final File EXAMPLE = new File("example.txt");
    private static final File EXAMPLE2 = new File("example2.txt");

    static int width;
    static int height;
    static MazeData[][] maze;

    static int startX;
    static int startY;

    static int endX;
    static int endY;

    private static final MazeData WALL = new MazeData(-1, -1, null, null);

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        height = lines.size();
        width = lines.get(0).length();

        maze = new MazeData[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = lines.get(i).charAt(j);

                if (c == '.') {
                    maze[i][j] = new MazeData(Integer.MAX_VALUE, Integer.MAX_VALUE, new HashSet<>(), new HashSet<>());
                } else if (c == '#') {
                    maze[i][j] = WALL;
                } else if (c == 'S') {
                    maze[i][j] = new MazeData(0, 1000, new HashSet<>(), new HashSet<>());
                    startX = j;
                    startY = i;
                    maze[i][j].leftRightPos.add(new Position(startX, startY));
                    maze[i][j].upDownPos.add(new Position(startX, startY));
                } else if (c == 'E') {
                    maze[i][j] = new MazeData(Integer.MAX_VALUE, Integer.MAX_VALUE, new HashSet<>(), new HashSet<>());
                    endX = j;
                    endY = i;
                }
            }
        }

        MazeData data2 = solve();

        System.out.println("Pos leftRight: " + data2.leftRightPos.size());
        System.out.println("Score leftRight: " + data2.leftRightScore);

        System.out.println("Pos upDown: " + data2.upDownPos.size());
        System.out.println("Score upDown: " + data2.upDownScore);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (maze[h][w] == WALL) {
                    image.setRGB(w, h, Color.BLACK.getRGB());
                } else {
                    image.setRGB(w, h, Color.WHITE.getRGB());
                }
            }
        }

        for (Position position : data2.upDownPos) {
            image.setRGB(position.x(), position.y(), Color.CYAN.getRGB());
        }

        ImageIO.write(image, "png", new File("output.png"));
    }

    public static MazeData solve() {

        NavigableSet<PositionDirection> toCheck = new TreeSet<>((a, b) -> {
            if (a.lastValue != b.lastValue) {
                return Integer.compare(a.lastValue, b.lastValue);
            }

            if (a.x != b.x) {
                return Integer.compare(a.x, b.x);
            }
            if (a.y != b.y) {
                return Integer.compare(a.y, b.y);
            }

            return a.direction.compareTo(b.direction);
        });

        toCheck.add(new PositionDirection(0, startX, startY, Direction.RIGHT));
        while (!toCheck.isEmpty()) {
            PositionDirection checking = toCheck.removeFirst();
            MazeData currentValue = maze[checking.y][checking.x];

            for (Direction direction : Direction.values()) {
                int nextX = checking.x + direction.getXOffset();
                int nextY = checking.y + direction.getYOffset();
                MazeData nextValue = maze[nextY][nextX];
                if (nextValue == WALL) {
                    continue;
                }

                int newLeftRight = 0;
                int newUpDown = 0;
                Set<Position> positions = new HashSet<>();
                if (direction == Direction.RIGHT || direction == Direction.LEFT) {
                    newLeftRight = currentValue.leftRightScore + 1;
                    newUpDown = currentValue.leftRightScore + 1 + 1000;
                    positions = currentValue.leftRightPos;
                } else {
                    newLeftRight = currentValue.upDownScore + 1 + 1000;
                    newUpDown = currentValue.upDownScore + 1;
                    positions = currentValue.upDownPos;
                }

                if (newLeftRight == nextValue.leftRightScore) {
                    nextValue.leftRightPos.addAll(positions);
                }

                if (newUpDown == nextValue.upDownScore) {
                    nextValue.upDownPos.addAll(positions);
                }

                if (newLeftRight < nextValue.leftRightScore) {
                    Set<Position> newPositions = new HashSet<>(positions);
                    newPositions.add(new Position(nextX, nextY));

                    maze[nextY][nextX] = new MazeData(newLeftRight, nextValue.upDownScore, newPositions, nextValue.upDownPos);
                    nextValue = maze[nextY][nextX];
                    toCheck.add(new PositionDirection(newLeftRight, nextX, nextY, direction));
                }

                if (newUpDown < nextValue.upDownScore) {
                    Set<Position> newPositions = new HashSet<>(positions);
                    newPositions.add(new Position(nextX, nextY));

                    maze[nextY][nextX] = new MazeData(nextValue.leftRightScore, newUpDown, nextValue.leftRightPos, newPositions);
                    toCheck.add(new PositionDirection(newUpDown, nextX, nextY, direction));
                }

                if (nextY == endY && nextX == endX) {
                    System.out.println("Finish: " + newLeftRight + " " + newUpDown);
                    return maze[nextY][nextX];
                }
            }
        }

        throw new RuntimeException("Error");
    }

    public record PositionDirection(int lastValue, int x, int y, Direction direction) {

    }

    public record MazeData(int leftRightScore, int upDownScore, Set<Position> leftRightPos, Set<Position> upDownPos) {

    }
}
