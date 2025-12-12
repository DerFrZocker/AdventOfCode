package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    // Magic number example and data always have 6 shapes
    private final static Shape[] shapes = new Shape[6];

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Input> inputs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.charAt(1) == ':') {
                int index = Integer.parseInt(String.valueOf(line.charAt(0)));
                boolean[][] area = new boolean[3][3];
                int size = 0;
                for (int j = 0; j < 3; j++) {
                    String l = lines.get(i + 1 + j);
                    for (int x = 0; x < 3; x++) {
                        if (l.charAt(x) == '#') {
                            area[x][j] = true;
                            size++;
                        }
                    }
                }
                List<boolean[][]> variations = variations(area);
                shapes[index] = new Shape(index, size, variations);
                i = i + 4;
            } else {
                int yHeight = 0;
                int xWidth = 0;
                int[] shapesLeft = new int[6];
                String[] split = line.split(": ");
                if (split.length != 2) {
                    throw new RuntimeException();
                }

                String[] cords = split[0].split("x");
                yHeight = Integer.parseInt(cords[0]);
                xWidth = Integer.parseInt(cords[1]);
                String[] numbers = split[1].split(" ");
                if (numbers.length != 6) {
                    throw new RuntimeException();
                }
                for (int j = 0; j < numbers.length; j++) {
                    shapesLeft[j] = Integer.parseInt(numbers[j]);
                }

                inputs.add(new Input(xWidth, yHeight, shapesLeft));
            }
        }

        int result = 0;
        for (int i = 0; i < inputs.size(); i++) {
            Input input = inputs.get(i);
            int requiredSize = 0;
            int amountOfShapes = 0;

            for (int j = 0; j < 6; j++) {
                requiredSize = Math.addExact(requiredSize, Math.multiplyExact(input.shapesLeft[j], shapes[j].size));
                amountOfShapes = Math.addExact(amountOfShapes, input.shapesLeft[j]);
            }

            int size = input.xWidth * input.yHeight;
            int possible = (input.xWidth / 3) * (input.yHeight / 3);
            if (size < requiredSize) {
                continue;
            }

            if (possible >= amountOfShapes) {
                result++;
                continue;
            }
            System.out.println("Is complex");

            boolean[][] grid = new boolean[input.yHeight][input.xWidth];
            cache.clear();
            boolean solve = solve(grid, input.yHeight, input.xWidth, input.shapesLeft);
            if (solve) {
                result++;
            }
        }

        System.out.println(result);
    }

    // All the work for nothing
    private static boolean solve(boolean[][] grid, int yHeight, int xWidth, int[] shapesLeft) {
        if (empty(shapesLeft)) {
            return true;
        }

        if (cached(grid, shapesLeft)) {
            return false;
        }

        for (int i = 0; i < shapesLeft.length; i++) {
            int left = shapesLeft[i];
            if (left <= 0) {
                continue;
            }
            for (boolean[][] variation : shapes[i].variations) {
                for (int y = 0; y < yHeight - 2; y++) {
                    for (int x = 0; x < xWidth - 2; x++) {
                        if (fits(x, y, grid, variation)) {
                            boolean[][] clone = clone(grid);
                            int[] newShapeLeft = shapesLeft.clone();
                            newShapeLeft[i] = newShapeLeft[i] - 1;
                            place(x, y, clone, variation);
                            boolean solved = solve(clone, yHeight, xWidth, newShapeLeft);
                            if (solved) {
                                return true;
                            }
                        }
                    }
                }
            }

            cache(grid, shapesLeft);
            return false;
        }

        cache(grid, shapesLeft);
        return false;
    }

    private static final Map<int[], List<boolean[][]>> cache = new HashMap<>();

    private static void cache(boolean[][] grid, int[] shapesLeft) {
        for (Map.Entry<int[], List<boolean[][]>> entry : cache.entrySet()) {
            if (!Arrays.equals(entry.getKey(), shapesLeft)) {
                continue;
            }

            entry.getValue().add(grid);
            return;
        }

        List<boolean[][]> list = new ArrayList<>();
        list.add(grid);
        cache.put(shapesLeft, list);
    }

    private static boolean cached(boolean[][] grid, int[] shapesLeft) {
        for (Map.Entry<int[], List<boolean[][]>> entry : cache.entrySet()) {
            if (!Arrays.equals(entry.getKey(), shapesLeft)) {
                continue;
            }

            for (boolean[][] cached : entry.getValue()) {
                if (same(cached, grid)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean same(boolean[][] first, boolean[][] second) {
        for (int i = 0; i < first.length; i++) {
            if (!Arrays.equals(first[i], second[i])) {
                return false;
            }
        }

        return true;
    }

    private static boolean[][] clone(boolean[][] grid) {
        boolean[][] clone = new boolean[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            clone[i] = grid[i].clone();
        }

        return clone;
    }

    private static boolean fits(int x, int y, boolean[][] grid, boolean[][] shape) {
        // Magic number shapes are always 3 x 3
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[y + i][x + j] && shape[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void place(int x, int y, boolean[][] grid, boolean[][] shape) {
        // Magic number shapes are always 3 x 3
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (shape[i][j]) {
                    grid[y + i][x + j] = true;
                }
            }
        }
    }

    private static boolean empty(int[] shapesLeft) {
        for (int j : shapesLeft) {
            if (j > 0) {
                return false;
            }
            if (j < 0) {
                throw new RuntimeException();
            }
        }

        return true;
    }

    private static List<boolean[][]> variations(boolean[][] input) {
        List<boolean[][]> variations = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            input = rotateRight(input);
            if (!contains(variations, input)) {
                variations.add(input);
            }

            boolean[][] flipX = flipX(input);
            boolean[][] flipY = flipY(input);
            if (!contains(variations, flipX)) {
                variations.add(flipX);
            }
            if (!contains(variations, flipY)) {
                variations.add(flipY);
            }
        }

        return variations;
    }

    private static boolean contains(List<boolean[][]> list, boolean[][] input) {
        for (boolean[][] va : list) {
            if (same(va, input)) {
                return true;
            }
        }

        return false;
    }

    private static boolean[][] rotateRight(boolean[][] input) {
        boolean[][] clone = clone(input);

        clone[0][0] = input[2][0];
        clone[2][0] = input[2][2];
        clone[2][2] = input[0][2];
        clone[0][2] = input[0][0];

        clone[0][1] = input[1][0];
        clone[1][0] = input[2][1];
        clone[2][1] = input[1][2];
        clone[1][2] = input[0][1];

        return clone;
    }

    private static boolean[][] flipX(boolean[][] input) {
        boolean[][] clone = clone(input);

        clone[0][0] = input[2][0];
        clone[0][1] = input[2][1];
        clone[0][2] = input[2][2];
        clone[2][0] = input[0][0];
        clone[2][1] = input[0][1];
        clone[2][2] = input[0][2];

        return clone;
    }

    private static boolean[][] flipY(boolean[][] input) {
        boolean[][] clone = clone(input);

        clone[0][0] = input[0][2];
        clone[1][0] = input[1][2];
        clone[2][0] = input[2][2];
        clone[0][2] = input[0][0];
        clone[1][2] = input[1][0];
        clone[2][2] = input[2][0];

        return clone;
    }

    private record Shape(int index, int size, List<boolean[][]> variations) {

    }

    private record Input(int xWidth, int yHeight, int[] shapesLeft) {

    }
}
