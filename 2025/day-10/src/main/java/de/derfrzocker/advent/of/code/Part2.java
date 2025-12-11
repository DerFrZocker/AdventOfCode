package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Row> rows = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            int[] targetState = null;
            List<Button> buttons = new ArrayList<>();
            for (String entry : split) {
                char firstChar = entry.charAt(0);
                if (firstChar == '[') {
                    /*
                    targetState = new boolean[entry.length() - 2];
                    for (int i = 0; i < entry.length() - 2; i++) {
                        if (entry.charAt(i + 1) == '#') {
                            targetState[i] = true;
                        }
                    }
                     */
                } else if (firstChar == '(') {
                    String[] subSplit = entry.substring(1, entry.length() - 1).split(",");
                    List<Integer> lamps = new ArrayList<>();
                    for (String sub : subSplit) {
                        lamps.add(Integer.parseInt(sub));
                    }
                    buttons.add(new Button(lamps));
                } else if (firstChar == '{') {
                    String[] subSplit = entry.substring(1, entry.length() - 1).split(",");
                    targetState = new int[subSplit.length];
                    for (int i = 0; i < subSplit.length; i++) {
                        targetState[i] = Integer.parseInt(subSplit[i]);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal character " + entry);
                }
            }

            rows.add(new Row(targetState, buttons));
        }

        long result = 0;
        for (Row row : rows) {
            result = Math.addExact(result, solveRow(row));
        }

        System.out.println(result);
    }

    private static long solveRow(Row row) {
        Number[][] matrix = new Number[row.buttons().size()][row.targetState.length];
        Number[] result = new Number[row.targetState.length];

        for (int x = 0; x < row.buttons().size(); x++) {
            for (int y = 0; y < row.targetState.length; y++) {
                matrix[x][y] = Number.ZERO;
            }
        }

        for (int i = 0; i < row.buttons().size(); i++) {
            Button button = row.buttons().get(i);
            for (Integer lamp : button.lamps) {
                matrix[i][lamp] = Number.ONE;
            }
        }

        for (int i = 0; i < row.targetState.length; i++) {
            result[i] = Number.of(row.targetState[i]);
        }

        List<Integer> nullIndexes = new ArrayList<>();
        transformDownLeft(0, 0, row.buttons.size(), row.targetState.length, matrix, result, nullIndexes);
        transformUpRight(0, 0, row.buttons.size(), row.targetState.length, matrix, result);

        if (nullIndexes.size() > 0) {
            int[] counters = new int[nullIndexes.size()];
            int[] maxes = new int[nullIndexes.size()];
            for (int i = 0; i < nullIndexes.size(); i++) {
                int nullIndex = nullIndexes.get(i);
                int min = Integer.MAX_VALUE;
                for (Integer lamp : row.buttons.get(nullIndex).lamps) {
                    if (min > row.targetState[lamp]) {
                        min = row.targetState[lamp];
                    }
                }

                if (min == Integer.MAX_VALUE) {
                    throw new RuntimeException();
                }

                maxes[i] = min;
            }
            int best = Integer.MAX_VALUE;
            dance:
            do {
                Number[] copy = result.clone();
                int amount = 0;
                for (int i = 0; i < counters.length; i++) {
                    int count = counters[i];
                    if (count == 0) {
                        continue;
                    }
                    int index = nullIndexes.get(i);
                    for (int r = 0; r < row.targetState.length; r++) {
                        if (matrix[index][r].isZero()) {
                            continue;
                        }
                        copy[r] = copy[r].subtract(matrix[index][r].multiply(Number.of(count)));
                    }
                    amount = amount + count;
                }

                for (Number res : copy) {
                    if (res.isSmallerZero()) {
                        continue dance;
                    }
                    if (!res.isWholeNumber()) {
                        continue dance;
                    }
                    amount = amount + res.wholeNumber();
                }

                if (amount < best) {
                    best = amount;
                }
            } while (increment(0, counters, maxes));

            return best;
        } else {
            int number = 0;
            for (Number res : result) {
                if (res.isSmallerZero()) {
                    throw new RuntimeException();
                }
                if (!res.isWholeNumber()) {
                    throw new RuntimeException();
                }
                number = number + res.wholeNumber();
            }
            return number;
        }
    }

    private static boolean increment(int index, int[] counter, int[] maxes) {
        if (index >= counter.length) {
            return false;
        }
        int current = counter[index];
        int max = maxes[index];
        if (current < max) {
            counter[index]++;
            return true;
        }
        counter[index] = 0;
        return increment(index + 1, counter, maxes);
    }

    private static void transformDownLeft(int buttonIndex, int rowIndex, int xWidth, int yHeight, Number[][] matrix, Number[] result, List<Integer> nullIndexes) {
        if (buttonIndex >= xWidth) {
            return;
        }
        if (rowIndex >= yHeight) {
            for (int i = buttonIndex; i < xWidth; i++) {
                nullIndexes.add(i);
            }
            return;
        }
        // Step one find none 0 entry
        int noneNull = -1;
        for (int i = rowIndex; i < yHeight; i++) {
            if (!matrix[buttonIndex][i].isZero()) {
                noneNull = i;
                break;
            }
        }

        if (noneNull == -1) {
            nullIndexes.add(buttonIndex);
            transformDownLeft(buttonIndex + 1, rowIndex, xWidth, yHeight, matrix, result, nullIndexes);
            return;
        }

        if (noneNull != rowIndex) {
            // It is not on the correct row switch it
            for (int i = buttonIndex; i < xWidth; i++) {
                Number tmp = matrix[i][rowIndex];
                matrix[i][rowIndex] = matrix[i][noneNull];
                matrix[i][noneNull] = tmp;
            }

            Number tmp = result[rowIndex];
            result[rowIndex] = result[noneNull];
            result[noneNull] = tmp;
        }

        // Check Scale and if not correct rescale
        if (!matrix[buttonIndex][rowIndex].isOne()) {
            Number scale = Number.ONE.divide(matrix[buttonIndex][rowIndex]);
            for (int i = buttonIndex; i < xWidth; i++) {
                matrix[i][rowIndex] = matrix[i][rowIndex].multiply(scale);
            }

            result[rowIndex] = result[rowIndex].multiply(scale);
        }

        // Check and set rest to 0
        for (int row = rowIndex + 1; row < yHeight; row++) {
            if (matrix[buttonIndex][row].isZero()) {
                continue;
            }

            Number scale = matrix[buttonIndex][row];
            for (int i = buttonIndex; i < xWidth; i++) {
                matrix[i][row] = matrix[i][row].subtract(matrix[i][rowIndex].multiply(scale));
            }

            result[row] = result[row].subtract(result[rowIndex].multiply(scale));
        }

        transformDownLeft(buttonIndex + 1, rowIndex + 1, xWidth, yHeight, matrix, result, nullIndexes);
    }

    private static void transformUpRight(int buttonIndex, int rowIndex, int xWidth, int yHeight, Number[][] matrix, Number[] result) {
        if (buttonIndex >= xWidth) {
            return;
        }
        if (rowIndex >= yHeight) {
            return;
        }

        // If there is not a 1 then it is a flexible row and we can ignore it
        if (!matrix[buttonIndex][rowIndex].isOne()) {
            transformUpRight(buttonIndex + 1, rowIndex, xWidth, yHeight, matrix, result);
            return;
        }

        // Check if a row does not have 0
        for (int row = 0; row < rowIndex; row++) {
            if (matrix[buttonIndex][row].isZero()) {
                continue;
            }

            Number scale = matrix[buttonIndex][row];
            for (int i = buttonIndex; i < xWidth; i++) {
                matrix[i][row] = matrix[i][row].subtract(matrix[i][rowIndex].multiply(scale));
            }

            result[row] = result[row].subtract(result[rowIndex].multiply(scale));
        }

        transformUpRight(buttonIndex + 1, rowIndex + 1, xWidth, yHeight, matrix, result);
    }

    public record Button(List<Integer> lamps) {

    }

    public record Row(int[] targetState, List<Button> buttons) {

    }
}
