package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        char state = 'Z';
        int iterations = 0;

        Map<Integer, Boolean> tape = new HashMap<>();
        Map<Character, Step> steps = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                state = lines.get(i).charAt(15);
                continue;
            }

            if (i == 1) {
                iterations = Integer.parseInt(lines.get(i).split(" ")[5]);
                continue;
            }

            if (i % 10 == 3) {
                char blockState = lines.get(i).charAt(9);
                boolean zeroNewValue = lines.get(i + 2).endsWith("1.");
                Direction zeroDirection = lines.get(i + 3).endsWith("right.") ? Direction.RIGHT : Direction.LEFT;
                char zeroNewState = lines.get(i + 4).charAt(26);
                boolean oneNewValue = lines.get(i + 6).endsWith("1.");
                Direction oneDirection = lines.get(i + 7).endsWith("right.") ? Direction.RIGHT : Direction.LEFT;
                char oneNewState = lines.get(i + 8).charAt(26);

                Step step = new Step(
                        blockState,
                        zeroNewValue,
                        zeroDirection,
                        zeroNewState,
                        oneNewValue,
                        oneDirection,
                        oneNewState);

                steps.put(blockState, step);
            }
        }


        int location = 0;
        for (int i = 0; i < iterations; i++) {
            Step step = steps.get(state);
            boolean value = tape.getOrDefault(location, false);

            if (value) {
                tape.put(location, step.oneNewValue);
                location += step.oneDirection.getXOffset();
                state = step.oneNewState;
            } else {
                tape.put(location, step.zeroNewValue);
                location += step.zeroDirection.getXOffset();
                state = step.zeroNewState;
            }
        }

        int count = 0;
        for (boolean value : tape.values()) {
            if (value) {
                count++;
            }
        }

        System.out.println(count);
    }

    private record Step(
            char blockState,
            boolean zeroNewValue,
            Direction zeroDirection,
            char zeroNewState,
            boolean oneNewValue,
            Direction oneDirection,
            char oneNewState) {

    }
}
