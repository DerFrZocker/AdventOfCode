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
        String[] split = lines.getFirst().split(",");

        long result = 0;
        for (String line : split) {
            String[] parts = line.split("-");
            long start  = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            for (long i = start; i <= end; i++) {
                String value = String.valueOf(i);

                if (value.length() % 2 == 0) {
                    String first = value.substring(0, value.length() / 2);
                    String second = value.substring(value.length() / 2);
                    if (first.equals(second)) {
                        result = Math.addExact(result, i);
                    }
                }
            }
        }

        System.out.println(result);
    }
}
