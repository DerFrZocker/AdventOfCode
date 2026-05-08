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

    private static final Map<Character, Integer> REGISTRY = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("cpy ")) {
                String value = line.split(" ")[1];
                String target = line.split(" ")[2];

                boolean isRegistry = false;
                char registry = 'X';
                int value2 = 0;
                if (value.equals("a") || value.equals("b") || value.equals("c") || value.equals("d")) {
                    isRegistry = true;
                    registry = value.charAt(0);
                } else {
                    value2 = Integer.parseInt(value);
                }

                instructions.add(new CpyInstruction(isRegistry, registry, value2, target.charAt(0)));
            } else if (line.startsWith("inc ")) {
                instructions.add(new IncInstruction(line.charAt(4)));
            } else if (line.startsWith("dec ")) {
                instructions.add(new DecInstruction(line.charAt(4)));
            } else if (line.startsWith("jnz ")) {
                String value = line.split(" ")[1];
                String target = line.split(" ")[2];

                boolean isRegistry = false;
                char registry = 'X';
                int value2 = 0;
                if (value.equals("a") || value.equals("b") || value.equals("c") || value.equals("d")) {
                    isRegistry = true;
                    registry = value.charAt(0);
                } else {
                    value2 = Integer.parseInt(value);
                }

                instructions.add(new JnzInstruction(isRegistry, registry, value2, Integer.parseInt(target)));
            } else {
                throw new RuntimeException("Unknown instruction");
            }
        }

        int counter = 0;
        while (counter >= 0 && counter < instructions.size()) {
            counter += instructions.get(counter).execute();
        }

        System.out.println(REGISTRY.get('a'));
    }

    private sealed interface Instruction {

        int execute();
    }

    private record CpyInstruction(boolean isRegistry, char registry, int value, char target) implements Instruction {

        @Override
        public int execute() {
            if (isRegistry) {
                REGISTRY.put(target, REGISTRY.computeIfAbsent(registry, a -> 0));
            } else {
                REGISTRY.put(target, value);
            }

            return 1;
        }
    }

    private record IncInstruction(char registry) implements Instruction {

        @Override
        public int execute() {
            REGISTRY.compute(registry, (a, b) -> b == null ? 1 : b + 1);

            return 1;
        }
    }

    private record DecInstruction(char registry) implements Instruction {

        @Override
        public int execute() {
            REGISTRY.compute(registry, (a, b) -> b == null ? -1 : b - 1);

            return 1;
        }
    }

    private record JnzInstruction(boolean isRegistry, char registry, int value, int line) implements Instruction {

        @Override
        public int execute() {
            if (isRegistry) {
                if (REGISTRY.computeIfAbsent(registry, a -> 0) != 0) {
                    return line;
                }

                return 1;
            }

            if (value != 0) {
                return line;
            }

            return 1;
        }
    }
}
