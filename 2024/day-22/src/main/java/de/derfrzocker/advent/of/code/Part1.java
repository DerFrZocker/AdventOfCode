package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long result = 0;
        for (String line : lines) {
            long value = Long.parseLong(line);
            for (int i = 0; i < 2000; i++) {
                value = next(value);

            }

            result = Math.addExact(result, value);
        }

        System.out.println(result);
    }

    private static long next(long value) {
        value = mix(value, Math.multiplyExact(value, 64));
        value = prune(value);

        value = mix(value, value / 32);
        value = prune(value);

        value = mix(value, Math.multiplyExact(value, 2048));
        value = prune(value);

        return value;
    }

    private static long mix(long value, long result) {
        return value ^ result;
    }

    private static long prune(long value) {
        return value % 16777216;
    }
}
