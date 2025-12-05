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

        List<Range> fresh = new ArrayList<>();
        long result = 0;
        dance:
        for (String line : lines) {
            if (line.isBlank()) {
                break dance;
            }
            String[] split = line.split("-");
            long start = Long.parseLong(split[0]);
            long end = Long.parseLong(split[1]);

            /*
            for (Range range : fresh) {
                if (start >= range.start && start <= range.end) {
                    start = range.end + 1;
                }
                if (end >= range.start && end <= range.end) {
                    end = range.start - 1;
                }
            }

            if (start > end) {
                continue dance;
            }
             */

            fresh.add(new Range(start, end));
        }

        List<Range> ranges = new ArrayList<>(fresh);

        boolean canMerge = false;
        do {
            canMerge = false;
            dance:
            for (int i = 0; i < ranges.size() - 1; i++) {
                loop:
                for (int j = i + 1; j < ranges.size(); j++) {
                    Range first = ranges.get(i);
                    Range second = ranges.get(j);

                    if (first.end < second.start || first.start > second.end) {
                        canMerge = false;
                        continue loop;
                    }

                    canMerge = true;
                    long start = Math.min(first.start, second.start);
                    long end = Math.max(first.end, second.end);

                    Range range = new Range(start, end);
                    ranges.set(i, range);
                    ranges.remove(j);
                    break dance;
                }
            }

        } while (canMerge);


        for (Range range : ranges) {
            result = Math.addExact(result, range.end - range.start + 1);
        }

        System.out.println(result);
    }

    public record Range(long start, long end) {

    }
}
