package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int ITERATIONS = 5_000_000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long a = 0;
        long b = 0;

        for (String line : lines) {
            if (line.startsWith("Generator A starts with")) {
                a = Long.parseLong(line.substring(line.lastIndexOf(" ") + 1));
            } else if (line.startsWith("Generator B starts with")) {
                b = Long.parseLong(line.substring(line.lastIndexOf(" ") + 1));
            } else {
                throw new IllegalArgumentException("Invalid line " + line);
            }
        }

        int result = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            a = next(a, 16807, 4);
            b = next(b, 48271, 8);

            if ((a & 0b1111111111111111) == (b & 0b1111111111111111)) {
                result++;
            }
        }

        System.out.println(result);
    }

    private static long next(long prev, long multi, int div) {
        do {
            prev = Math.multiplyExact(prev, multi) % 2147483647;
        } while (prev % div != 0);

        return prev;
    }
}
