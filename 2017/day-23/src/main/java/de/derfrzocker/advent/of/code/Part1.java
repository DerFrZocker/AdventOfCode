package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final Map<Character, Long> REGISTER = new HashMap<>();

    private static long mulExecuted = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            String start = line.substring(0, 3);
            Instruction instruction = switch (start) {
                case "set" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new SetInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "sub" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new SubInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "mul" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new MulInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "jnz" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(x)) {
                        isRegistry = false;
                        value = Integer.parseInt(x);
                    } else {
                        isRegistry = true;
                        registry = x.charAt(0);
                    }

                    boolean offseetIsRegistry;
                    char offsetRegistry = Character.MAX_VALUE;
                    int offsetValue = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        offseetIsRegistry = false;
                        offsetValue = Integer.parseInt(y);
                    } else {
                        offseetIsRegistry = true;
                        offsetRegistry = y.charAt(0);
                    }

                    yield new JnzInstruction(
                            offseetIsRegistry,
                            offsetRegistry,
                            offsetValue,
                            isRegistry,
                            registry,
                            value);
                }
                default -> throw new IllegalArgumentException("Unknown command: " + line);
            };

            instructions.add(instruction);
        }

        int counter = 0;
        while (counter >= 0 && counter < instructions.size()) {
            Instruction instruction = instructions.get(counter);
            counter += instruction.execute();
        }

        System.out.println(mulExecuted);
    }

    private interface Instruction {

        int execute();
    }

    private record SetInstruction(
            char setRegistry, boolean isRegistry, char registry,
            int value) implements Instruction {

        @Override
        public int execute() {
            long v = value;
            if (isRegistry) {
                v = REGISTER.getOrDefault(registry, 0L);
            }

            REGISTER.put(setRegistry, v);

            return 1;
        }
    }

    private record SubInstruction(
            char setRegistry, boolean isRegistry, char registry,
            int value) implements Instruction {

        @Override
        public int execute() {
            long v = value;
            if (isRegistry) {
                v = REGISTER.getOrDefault(registry, 0L);
            }

            v = REGISTER.getOrDefault(setRegistry, 0L) - v;
            REGISTER.put(setRegistry, v);

            return 1;
        }
    }

    private record MulInstruction(
            char setRegistry, boolean isRegistry, char registry,
            int value) implements Instruction {

        @Override
        public int execute() {
            long v = value;
            if (isRegistry) {
                v = REGISTER.getOrDefault(registry, 0L);
            }

            v = Math.multiplyExact(REGISTER.getOrDefault(setRegistry, 0L), v);
            REGISTER.put(setRegistry, v);

            mulExecuted++;

            return 1;
        }
    }

    private record JnzInstruction(
            boolean isOffestRegistry, char offsetRegistry, int offsetValue, boolean isRegistry,
            char registry, int value) implements Instruction {

        @Override
        public int execute() {
            long v = value;
            if (isRegistry) {
                v = REGISTER.getOrDefault(registry, 0L);
            }

            if (v != 0) {
                if (isOffestRegistry) {
                    return Math.toIntExact(REGISTER.getOrDefault(offsetRegistry, 0L));
                }

                return offsetValue;
            }

            return 1;
        }
    }

    private static boolean isNumber(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
