package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Row> rows = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            boolean[] targetState = null;
            List<Button> buttons = new ArrayList<>();
            for (String entry : split) {
                char firstChar = entry.charAt(0);
                if (firstChar == '[') {
                    targetState = new boolean[entry.length() - 2];
                    for (int i = 0; i < entry.length() - 2; i++) {
                        if (entry.charAt(i + 1) == '#') {
                            targetState[i] = true;
                        }
                    }
                } else if (firstChar == '(') {
                    String[] subSplit = entry.substring(1, entry.length() - 1).split(",");
                    List<Integer> lamps = new ArrayList<>();
                    for (String sub : subSplit) {
                        lamps.add(Integer.parseInt(sub));
                    }
                    buttons.add(new Button(lamps));
                } else if (firstChar == '{') {
                    String[] subSplit = entry.substring(1, entry.length() - 1).split(",");

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
        return solveRow(row, new boolean[row.targetState.length]);
    }

    private static long solveRow(Row row, boolean[] currentState) {
        TreeSet<Entry> nextStates = new TreeSet<>((a, b) -> {
            int result = Long.compare(a.steps, b.steps);
            if (result == 0) {
                if (Arrays.equals(a.currentState, b.currentState)) {
                    return 0;
                }

                return 1;
            } else {
                return result;
            }
        });
        nextStates.add(new Entry(currentState, 0));

        while (true) {
            Entry current = nextStates.removeFirst();
            for (Button button : row.buttons) {
                boolean[] copy = current.currentState.clone();
                for (Integer lamp : button.lamps) {
                    copy[lamp] = !copy[lamp];
                }

                if (Arrays.equals(row.targetState, copy)) {
                    return Math.addExact(current.steps, 1);
                }

                nextStates.add(new Entry(copy, Math.addExact(current.steps, 1)));
            }
        }
    }

    public record Entry(boolean[] currentState, long steps) {

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry other) {
                if (steps != other.steps) {
                    return false;
                }
                return Arrays.equals(currentState, other.currentState);
            }
            return false;
        }
    }

    public record Button(List<Integer> lamps) {

    }

    public record Row(boolean[] targetState, List<Button> buttons) {

    }
}
