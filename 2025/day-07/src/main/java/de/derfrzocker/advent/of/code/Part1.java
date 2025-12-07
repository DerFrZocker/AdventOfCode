package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long splits = 0;
        boolean first = true;
        Set<Integer> previous = new HashSet<>();
        Set<Integer> next = new HashSet<>();
        for (String line : lines) {
            if (first) {
                first = false;
                int start = line.indexOf("S");
                previous.add(start);
                continue;
            }

            for (Integer prev : previous) {
                if (line.charAt(prev) == '^') {
                    int left = prev - 1;
                    int right = prev + 1;

                    /* Content the wrong thing
                    if (next.add(left) && !previous.contains(left)) {
                        splits = Math.addExact(splits, 1);
                    }

                    if (next.add(right) && !previous.contains(right)) {
                        splits = Math.addExact(splits, 1);
                    }
                     */

                    next.add(left);
                    next.add(right);

                    splits = Math.addExact(splits, 1);
                } else {
                    next.add(prev);
                }
            }

            previous = next;
            next = new HashSet<>();
        }

        System.out.println(splits);
    }
}
