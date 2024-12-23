package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static Map<String, Set<String>> connections = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            String[] parts = line.split("-");
            String first = parts[0];
            String second = parts[1];

            connections.computeIfAbsent(first, k -> new HashSet<>()).add(second);
            connections.computeIfAbsent(second, k -> new HashSet<>()).add(first);
        }

        Set<String> result = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : connections.entrySet()) {
            String first = entry.getKey();
            for (String second : entry.getValue()) {
                for (String potentialThird : connections.get(second)) {
                    if (entry.getValue().contains(potentialThird)) {
                        if (!first.equals(second) && !first.equals(potentialThird) && !second.equals(potentialThird)) {
                            if (!first.startsWith("t") && !second.startsWith("t") && !potentialThird.startsWith("t")) {
                                continue;
                            }
                            // Is third
                            NavigableSet<String> sorted = new TreeSet<>();
                            sorted.add(first);
                            sorted.add(second);
                            sorted.add(potentialThird);

                            result.add(sorted.removeFirst() + "-" + sorted.removeFirst() + "-" + sorted.removeFirst());
                        }
                    }
                }
            }
        }


        System.out.println(result);
        System.out.println("Size: " + result.size());
    }
}
