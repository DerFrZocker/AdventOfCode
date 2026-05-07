package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final Map<Integer, Instruction> botInstruction = new HashMap<>();
    private static final Map<Integer, State> states = new HashMap<>();
    private static final Map<Integer, List<Integer>> outputs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Value> values = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("value ")) {
                String[] split = line.split(" ");
                int bot = Integer.parseInt(split[5]);
                int amount = Integer.parseInt(split[1]);

                values.add(new Value(bot, amount));
            } else if (line.startsWith("bot ")) {
                String[] split = line.split(" ");
                int bot = Integer.parseInt(split[1]);
                boolean lowOutput = split[5].equals("output");
                int lowTarget = Integer.parseInt(split[6]);
                boolean highOutput = split[10].equals("output");
                int highTarget = Integer.parseInt(split[11]);

                Instruction old = botInstruction.put(bot, new Instruction(bot, lowOutput, lowTarget, highOutput, highTarget));
                if (old != null) {
                    throw new RuntimeException("Multiple bots with the same id");
                }
            } else {
                throw new RuntimeException("Unknown line");
            }
        }

        for (Value value : values) {
            State state = states.computeIfAbsent(value.bot, bot -> new State(null, null));

            if (state.left == null) {
                states.put(value.bot, new State(value.amount, null));
            } else {
                sendValue(value.bot, new State(state.left, value.amount));
            }
        }

        int result = 1;
        for (int i = 0; i < 3; i++) {
            for (int number : outputs.computeIfAbsent(i, a -> new ArrayList<>())) {
                result *= number;
            }
        }

        System.out.println(result);
    }

    private static void sendValue(int bot, State state) {
        if ((state.left == 17 && state.right == 61) || (state.left == 61 && state.right == 17)) {
            System.out.println("Found: " + bot);
        }
        Instruction instruction = botInstruction.get(bot);

        if (instruction == null) {
            System.out.println("No instruction found " + bot);
            return;
        }

        states.remove(bot);

        if (!instruction.lowOutput) {
            int lower = Math.min(state.left, state.right);
            State lowerState = states.computeIfAbsent(instruction.lowTarget, i -> new State(null, null));
            if (lowerState.left == null) {
                states.put(instruction.lowTarget, new State(lower, null));
            } else {
                sendValue(instruction.lowTarget, new State(lowerState.left, lower));
            }
        } else {
            int lower = Math.min(state.left, state.right);
            outputs.computeIfAbsent(instruction.lowTarget, i -> new ArrayList<>()).add(lower);
        }

        if (!instruction.highOutput) {
            int higher = Math.max(state.left, state.right);
            State higherState = states.computeIfAbsent(instruction.highTarget, i -> new State(null, null));
            if (higherState.left == null) {
                states.put(instruction.highTarget, new State(higher, null));
            } else {
                sendValue(instruction.highTarget, new State(higherState.left, higher));
            }
        } else {
            int higher = Math.max(state.left, state.right);
            outputs.computeIfAbsent(instruction.highTarget, i -> new ArrayList<>()).add(higher);
        }
    }

    private record Value(int bot, int amount) {

    }

    private record Instruction(int bot, boolean lowOutput, int lowTarget, boolean highOutput, int highTarget) {

    }

    private record State(Integer left, Integer right) {

    }
}
