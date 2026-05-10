package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Pair> pairs = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split("-");
            long first = Long.parseLong(parts[0]);
            long last = Long.parseLong(parts[1]);

            pairs.add(new Pair(first, last));
        }

        pairs.sort(Comparator.comparing(Pair::start));

        if (pairs.getFirst().start != 0) {
            System.out.println(0);
            return;
        }

        long result = 0;
        long end = pairs.getFirst().end();
        for (int i = 1; i < pairs.size(); i++) {
            Pair pair = pairs.get(i);
            if (pair.start > (end + 1)) {
                result += (pair.start - (end + 1L));
            }

            end = Math.max(end, pair.end);
        }

        result += (4294967295L - end);

        System.out.println(result);
    }

    private record Pair(long start, long end) {

    }
}
