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

    private static final Map<Character, Integer> REGISTRY = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        REGISTRY.put('a', 12);

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

                boolean isLineRegistry = false;
                char lineRegistry = 'X';
                int lineJump = 0;
                if (target.equals("a") || target.equals("b") || target.equals("c") || target.equals("d")) {
                    isLineRegistry = true;
                    lineRegistry = target.charAt(0);
                } else {
                    lineJump = Integer.parseInt(target);
                }

                instructions.add(new JnzInstruction(isRegistry, registry, value2, isLineRegistry, lineJump, lineRegistry));
            } else if (line.startsWith("tgl ")) {
                String value = line.split(" ")[1];

                boolean isRegistry = false;
                char registry = 'X';
                int value2 = 0;
                if (value.equals("a") || value.equals("b") || value.equals("c") || value.equals("d")) {
                    isRegistry = true;
                    registry = value.charAt(0);
                } else {
                    value2 = Integer.parseInt(value);
                }

                instructions.add(new TglInstruction(isRegistry, registry, value2));
            } else {
                throw new RuntimeException("Unknown instruction");
            }
        }

        int counter = 0;
        while (counter >= 0 && counter < instructions.size()) {
            counter += instructions.get(counter).execute(counter, instructions);
        }

        System.out.println(REGISTRY.get('a'));
    }

    private sealed interface Instruction {

        int execute(int counter, List<Instruction> instructions);
    }

    private record CpyInstruction(boolean isRegistry, char registry, int value, char target) implements Instruction {

        @Override
        public int execute(int counter, List<Instruction> instructions) {
            if (target == '!') {
                return 1;
            }
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
        public int execute(int counter, List<Instruction> instructions) {
            if (registry == '!') {
                return 1;
            }

            REGISTRY.compute(registry, (a, b) -> b == null ? 1 : Math.addExact(b, 1));

            return 1;
        }
    }

    private record DecInstruction(char registry) implements Instruction {

        @Override
        public int execute(int counter, List<Instruction> instructions) {
            REGISTRY.compute(registry, (a, b) -> b == null ? -1 : b - 1);

            return 1;
        }
    }

    private record JnzInstruction(boolean isRegistry, char registry, int value, boolean isLineRegistry, int line,
                                  char lineChar) implements Instruction {

        @Override
        public int execute(int counter, List<Instruction> instructions) {
            if (isRegistry) {
                if (REGISTRY.computeIfAbsent(registry, a -> 0) != 0) {
                    if (!isLineRegistry) {
                        return line;
                    } else {
                        return REGISTRY.computeIfAbsent(lineChar, a -> 0);
                    }
                }

                return 1;
            }

            if (value != 0) {
                if (!isLineRegistry) {
                    return line;
                } else {
                    return REGISTRY.computeIfAbsent(lineChar, a -> 0);
                }
            }

            return 1;
        }
    }

    private record TglInstruction(boolean isRegistry, char registry, int value) implements Instruction {

        @Override
        public int execute(int counter, List<Instruction> instructions) {
            int relative = 0;
            if (isRegistry) {
                relative = REGISTRY.computeIfAbsent(registry, a -> 0);
            } else {
                relative = value;
            }

            int target = relative + counter;

            if (target >= instructions.size()) {
                return 1;
            }

            Instruction newInstruction = switch (instructions.get(target)) {
                case CpyInstruction cpyInstruction ->
                        new JnzInstruction(cpyInstruction.isRegistry, cpyInstruction.registry, cpyInstruction.value, true, Integer.MAX_VALUE, cpyInstruction.target);
                case DecInstruction decInstruction -> new IncInstruction(decInstruction.registry());
                case IncInstruction incInstruction -> new DecInstruction(incInstruction.registry());
                case JnzInstruction jnzInstruction ->
                        new CpyInstruction(jnzInstruction.isRegistry, jnzInstruction.registry, jnzInstruction.value, jnzInstruction.isLineRegistry ? jnzInstruction.lineChar : '!');
                case TglInstruction tglInstruction ->
                        new IncInstruction(tglInstruction.isRegistry ? tglInstruction.registry : '!');
            };

            instructions.set(target, newInstruction);

            return 1;
        }
    }
}
