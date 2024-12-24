package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a semi-manual solution, with the code below and a debugger, break points where used,
 * to pause when the instructions don't make any sens.
 * Then they were manually fixed in the data input, and the program gets rerun, until the four pairs where swap
 * and the instructions all make sense.
 */
public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static Map<String, Integer> states = new HashMap<>();
    private static List<Instruction> instructions = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

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
                Instruction instruction = new Instruction(parts[0], parts[2], parts[4], parts[1]);
                instructions.add(instruction);
            }
        }

        out(1, "x01", "y01", "gwq"); // Magic value, hand-picked
    }

    private static int out(int index, String x, String y, String carry) {
        if (index == 45) {
            return 0;
        }

        Instruction inputAnd = search(x, y, "AND");
        Instruction inputXor = search(x, y, "XOR");
        Instruction output = search(inputXor.goal(), carry, "XOR");
        Instruction partCarry = search(inputXor.goal(), carry, "AND");

        if (partCarry == null) {
            System.out.println("partCarry is null");
        }

        Instruction newCarry = search(inputAnd.goal(), partCarry.goal(), "OR");

        if (output == null) {
            System.out.println("null");
        }

        if (newCarry == null) {
            System.out.println("newCarry is null");
        }

        if (!output.goal.equals("z%02d".formatted(index))) {
            throw new RuntimeException("Unknown goal " + output.goal + " at index " + index);
        }

        int newIndex = index + 1;
        return out(newIndex, "x%02d".formatted(newIndex), "y%02d".formatted(newIndex), newCarry.goal);
    }

    private static Instruction search(String first, String second, String operation) {
        for (Instruction instruction : instructions) {
            if (!instruction.operation.equals(operation)) {
                continue;
            }
            if (instruction.first.equals(first) && instruction.second.equals(second)) {
                return instruction;
            }
            if (instruction.first.equals(second) && instruction.second.equals(first)) {
                return instruction;
            }
        }

        return null;
    }

    private record Instruction(String first, String second, String goal, String operation) {

    }
}
