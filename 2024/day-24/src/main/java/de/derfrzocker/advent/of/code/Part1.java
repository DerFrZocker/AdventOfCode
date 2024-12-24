package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Map<String, Integer> states = new HashMap<>();

        List<Instruction> instructions = new ArrayList<>();

        boolean next = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                next = true;
                continue;
            }

            if (!next) {
                String[] parts = line.split(": ");
                states.put(parts[0], Integer.parseInt(parts[1]));
            } else {
                String[] parts = line.split(" ");
                instructions.add(new Instruction(parts[0], parts[2], parts[4], parts[1]));
            }
        }

        while (!instructions.isEmpty()) {
            Instruction instruction = instructions.removeFirst();

            Integer first = states.get(instruction.first);
            Integer second = states.get(instruction.second);

            if (first == null || second == null) {
                instructions.add(instruction);
                continue;
            }

            int result;
            if ("AND".equals(instruction.operation)) {
                result = first & second;
            } else if ("OR".equals(instruction.operation)) {
                result = first | second;
            } else if ("XOR".equals(instruction.operation)) {
                result = first ^ second;
            } else {
                throw new RuntimeException("Unknown operation: " + instruction.operation);
            }

            states.put(instruction.goal, result);
        }

        NavigableMap<String, Integer> result = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : states.entrySet()) {
            if (entry.getKey().startsWith("z")) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        long value = 0;
        for (Map.Entry<String, Integer> entry : result.reversed().entrySet()) {
            value += entry.getValue();
            value = value << 1;
        }

        value = value >> 1;

        System.out.println(Long.toBinaryString(value));
        System.out.println(Long.toString(value));
    }

    private record Instruction(String first, String second, String goal, String operation) {

    }
}
