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

    private static Map<Character, Long> REGISTRIES = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int counter = 0;
        List<Instruction> instructions = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("hlf")) {
                instructions.add(new HlfInstruction(line.charAt(4)));
            } else if (line.startsWith("tpl")) {
                instructions.add(new TplInstruction(line.charAt(4)));
            } else if (line.startsWith("inc")) {
                instructions.add(new IncInstruction(line.charAt(4)));
            } else if (line.startsWith("jmp")) {
                instructions.add(new JmpInstruction(Integer.parseInt(line.substring(4))));
            } else if (line.startsWith("jie")) {
                instructions.add(new JieInstruction(line.charAt(4), Integer.parseInt(line.substring(7))));
            } else if (line.startsWith("jio")) {
                instructions.add(new JioInstruction(line.charAt(4), Integer.parseInt(line.substring(7))));
            } else {
                throw new IllegalArgumentException("Unknown instruction: " + line);
            }
        }

        REGISTRIES.put('a', 1L);

        while (counter >= 0 && counter < instructions.size()) {
            counter += instructions.get(counter).execute();
        }

        System.out.println(REGISTRIES.getOrDefault('b', 0L));
    }

    private sealed interface Instruction {

        int execute();
    }

    private record HlfInstruction(char registry) implements Instruction {

        @Override
        public int execute() {
            REGISTRIES.compute(registry, (key, value) -> value == null ? 0 : value / 2);

            return 1;
        }
    }

    private record TplInstruction(char registry) implements Instruction {

        @Override
        public int execute() {
            REGISTRIES.compute(registry, (key, value) -> value == null ? 0 : value * 3);

            return 1;
        }
    }

    private record IncInstruction(char registry) implements Instruction {

        @Override
        public int execute() {
            REGISTRIES.compute(registry, (key, value) -> value == null ? 1 : value + 1);

            return 1;
        }
    }

    private record JmpInstruction(int offset) implements Instruction {

        @Override
        public int execute() {
            return offset;
        }
    }

    private record JieInstruction(char registry, int offset) implements Instruction {

        @Override
        public int execute() {
            long result = REGISTRIES.getOrDefault(registry, 0L);

            if (result % 2 == 0) {
                return offset;
            }
            return 1;
        }
    }

    private record JioInstruction(char registry, int offset) implements Instruction {

        @Override
        public int execute() {
            long result = REGISTRIES.getOrDefault(registry, 0L);

            if (result == 1) {
                return offset;
            }

            return 1;
        }
    }
}
