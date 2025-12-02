package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

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

            other: for (long i = start; i <= end; i++) {
                String value = String.valueOf(i);

                dance: for (int l = 1; l <= (value.length() / 2); l++) {
                    String first = value.substring(0, l);

                    if (value.length() % l != 0) {
                        continue dance;
                    }

                    for (int c = l; c <= (value.length() - l); c = c + l) {
                        String other = value.substring(c, c + l);

                        if (!first.equals(other)) {
                            continue dance;
                        }
                    }

                    result = Math.addExact(result, i);
                    continue other;
                }
            }
        }

        System.out.println(result);
    }
}
