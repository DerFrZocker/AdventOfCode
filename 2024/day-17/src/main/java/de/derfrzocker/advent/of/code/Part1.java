package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");
    private static final File EXAMPLE2 = new File("example2.txt");

    private static int registerA = 0;
    private static int registerB = 0;
    private static int registerC = 0;
    private static int pointer = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        registerA = Integer.parseInt(lines.get(0).split(": ")[1]);
        registerB = Integer.parseInt(lines.get(1).split(": ")[1]);
        registerC = Integer.parseInt(lines.get(2).split(": ")[1]);

        test();
        System.out.println();
        if (true) {
            return;
        }

        String programm = lines.get(4).split("Program: ")[1];
        String[] instructionsString = programm.split(",");
        int[] instructions = new int[instructionsString.length];

        for (int i = 0; i < instructionsString.length; i++) {
            instructions[i] = Integer.parseInt(instructionsString[i]);
        }

        int steps = 0;
        while (pointer < instructions.length) {
            steps++;
            int opcode = instructions[pointer];
            int literal = instructions[pointer + 1];

            if (opcode == 0) {
                registerA = (int) (registerA / (Math.pow(2, get(literal))));
            }
            if (opcode == 1) {
                registerB = registerB ^ literal;
            }
            if (opcode == 2) {
                registerB = get(instructions[pointer + 1]) % 8;
            }
            if (opcode == 3) {
                if (registerA != 0) {
                    pointer = literal;
                    continue;
                }
            }
            if (opcode == 4) {
                registerB = registerB ^ registerC;
            }
            if (opcode == 5) {
                System.out.print(get(literal) % 8);
                System.out.print(",");
            }
            if (opcode == 6) {
                registerB = (int) (registerA / (Math.pow(2, get(literal))));
            }
            if (opcode == 7) {
                registerC = (int) (registerA / (Math.pow(2, get(literal))));
            }

            pointer++;
            pointer++;
        }

        System.out.println();
        System.out.println(steps);
        System.out.println("Register A: " + registerA);
        System.out.println("Register B: " + registerB);
        System.out.println("Register C: " + registerC);
    }

    private static int get(int instruction) {
        if (instruction == 4) {
            return registerA;
        }

        if (instruction == 5) {
            return registerB;
        }

        if (instruction == 6) {
            return registerC;
        }

        return instruction;
    }

    private static void test() {
        do {
            registerB = registerA % 8;
            registerB = registerB ^ 3;
            registerC = (int) (registerA / Math.pow(2, registerB));
            registerB = registerB ^ registerC;
            registerB = registerB ^ 3;
            registerA = (int) registerA / 8;
            System.out.print(registerB % 8);
            System.out.print(",");

        } while (registerA != 0);
    }
}
