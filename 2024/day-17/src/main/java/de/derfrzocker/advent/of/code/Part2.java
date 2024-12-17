package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * This solution is specific to one input and does not work for every input.
 */
public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");
    private static final File EXAMPLE2 = new File("example2.txt");

    private static long registerA = 0;
    private static long registerB = 0;
    private static long registerC = 0;
    private static int pointer = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());


        String programm = lines.get(4).split("Program: ")[1];
        String[] instructionsString = programm.split(",");
        int[] instructions = new int[instructionsString.length];


        for (int i = 0; i < instructionsString.length; i++) {
            instructions[i] = Integer.parseInt(instructionsString[i]);
        }

        // Magic number was calculated per hand, but also can iterate from 1 to 7 and check.
        int magicNumber = 3;
        calculate(instructions, instructions.length - 2, magicNumber);
    }

    // Just as info and to check the solution, if it could be the correct solution
    long min = 35184372088832L; // Min value 2^15
    long max = 246290604621824L; // Max value 2^15 * 7

    // Code is specific to the given input and does not work for all inputs.
    private static boolean calculate(int[] goal, int index, long previous) {
        // Because of the independent natur of the programm between iteration.
        // And the fact that mod 8 is used, we need to only check 0 values per iteration.
        long[] a = new long[8];
        long[] b = new long[8];
        long[] c = new long[8];

        for (int j = 0; j < 8; j++) {
            a[j] = previous * 8 + j;
        }

        // Each for loop represents one instruction of the input
        for (int j = 0; j < 8; j++) {
            b[j] = a[j] % 8;
        }

        for (int j = 0; j < 8; j++) {
            b[j] = b[j] ^ 3;
        }

        for (int j = 0; j < 8; j++) {
            c[j] = (long) (a[j] / Math.pow(2, b[j])); // This could be replaced with bit shifting
        }

        for (int j = 0; j < 8; j++) {
            b[j] = b[j] ^ c[j];
        }

        for (int j = 0; j < 8; j++) {
            b[j] = b[j] ^ 3;
        }

        for (int j = 0; j < 8; j++) {
            b[j] = b[j] % 8;
        }

        for (int j = 0; j < 8; j++) {
            long toPrint = b[j];

            if (toPrint == goal[index]) {
                if (index == 0) {
                    System.out.println(a[j]);
                    return true;
                }
                if (calculate(goal, index - 1, a[j])) {
                    return true;
                }
            }
        }
        return false;

    }

    // Method is not used, only for reference.
    // This is the java version of the input code and specific to one input.
    private static void test() {
        do {
            registerB = registerA % 8;
            registerB = registerB ^ 3;
            registerC = (long) (registerA / Math.pow(2, registerB)); // This could be replaced with bit shifting
            registerB = registerB ^ registerC;
            registerB = registerB ^ 3;
            registerA = (long) registerA / 8;

            System.out.print(registerB % 8);
            System.out.print(",");

        } while (registerA != 0);
    }
}
