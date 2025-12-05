package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        boolean range = true;
        List<Range> fresh = new ArrayList<>();
        long result = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                range = false;

                continue;
            }
            if (range) {

                String[] split = line.split("-");
                long start = Long.parseLong(split[0]);
                long end = Long.parseLong(split[1]);

                fresh.add(new Range(start, end));
                continue;
            }

            long value = Long.parseLong(line);
            dance:
            for (Range r : fresh) {
                if (r.start <= value && r.end >= value) {
                    result++;
                    break dance;
                }
            }
        }

        System.out.println(result);
    }

    public record Range(long start, long end) {

    }
}
