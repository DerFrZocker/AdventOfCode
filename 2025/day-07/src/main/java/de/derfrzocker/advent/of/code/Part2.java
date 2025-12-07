package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        boolean first = true;
        Map<Integer, Long> previous = new HashMap<>();
        Map<Integer, Long> next = new HashMap<>();
        for (String line : lines) {
            if (first) {
                first = false;
                int start = line.indexOf("S");
                previous.put(start, 1L);
                continue;
            }

            for (Map.Entry<Integer, Long> prev : previous.entrySet()) {
                if (line.charAt(prev.getKey()) == '^') {
                    int left = prev.getKey() - 1;
                    int right = prev.getKey() + 1;

                    next.compute(left, (key, value) -> value == null ? prev.getValue() : Math.addExact(value, prev.getValue()));
                    next.compute(right, (key, value) -> value == null ? prev.getValue() : Math.addExact(value, prev.getValue()));
                } else {
                    next.compute(prev.getKey(), (key, value) -> value == null ? prev.getValue() : Math.addExact(value, prev.getValue()));
                }
            }

            previous = next;
            next = new HashMap<>();
        }


        long result = 0;
        for (Long value : previous.values()) {
            result = Math.addExact(result, value);
        }

        System.out.println(result);
    }
}
