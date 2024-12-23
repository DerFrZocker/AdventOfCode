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

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static Map<String, Set<String>> connections = new HashMap<>();

    private static Set<Set<String>> notGood = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            String[] parts = line.split("-");
            String first = parts[0];
            String second = parts[1];

            connections.computeIfAbsent(first, k -> new HashSet<>()).add(second);
            connections.computeIfAbsent(second, k -> new HashSet<>()).add(first);
        }

        NavigableSet<String> current = new TreeSet<>();
        for (Map.Entry<String, Set<String>> entry : connections.entrySet()) {
            NavigableSet<String> largest = largest(new TreeSet<>(), entry.getKey());
            if (largest.size() > current.size()) {
                if (!current.isEmpty()) {
                    notGood.add(current);
                }
                current = largest;
            } else {
                notGood.add(largest);
            }
        }


        System.out.println(current);
        System.out.println(current.size());

        StringBuilder result = new StringBuilder();
        while (!current.isEmpty()) {
            result.append(",").append(current.removeFirst());
        }

        System.out.println(result);
    }

    private static NavigableSet<String> largest(NavigableSet<String> base, String newOne) {
        if (notGood.contains(base)) {
            return base;
        }
        NavigableSet<String> current;
        NavigableSet<String> copy = new TreeSet<>(base);
        copy.add(newOne);
        if (notGood.contains(copy)) {
            return copy;
        }
        current = new TreeSet<>(copy);
        for (String value : connections.get(newOne)) {
            if (copy.contains(value)) {
                continue;
            }
            if (canBeAdded(copy, value)) {
                NavigableSet<String> largest = largest(copy, value);
                if (largest.size() > current.size()) {
                    notGood.add(current);
                    current = largest;
                } else {
                    notGood.add(largest);
                }
            }
        }

        return current;
    }

    private static boolean canBeAdded(Set<String> group, String newOne) {
        for (String connection : group) {
            Set<String> values = connections.get(connection);
            if (!values.contains(newOne)) {
                return false;
            }
        }

        return true;
    }
}
