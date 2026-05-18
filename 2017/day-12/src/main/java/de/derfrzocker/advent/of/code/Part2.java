package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final NavigableMap<Integer, List<Integer>> GRAPH = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            int number = Integer.parseInt(line.split(" ")[0]);
            String[] split = line.substring(line.indexOf(">") + 2).split(", ");

            List<Integer> values = new ArrayList<>();
            for (String s : split) {
                values.add(Integer.parseInt(s));
            }

            GRAPH.put(number, values);
        }

        int result = 0;
        while (!GRAPH.isEmpty()) {
            result++;

            Set<Integer> checked = new HashSet<>();
            NavigableSet<Integer> toCheck = new TreeSet<>();

            toCheck.add(GRAPH.firstKey());
            while (!toCheck.isEmpty()) {
                int value = toCheck.removeFirst();
                if (checked.contains(value)) {
                    continue;
                }

                checked.add(value);
                toCheck.addAll(GRAPH.get(value));
            }

            checked.forEach(GRAPH::remove);
        }

        System.out.println(result);
    }
}
