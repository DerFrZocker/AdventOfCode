package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

    private static final Map<Integer, List<Integer>> GRAPH = new HashMap<>();

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

        Set<Integer> checked = new HashSet<>();
        NavigableSet<Integer> toCheck = new TreeSet<>();

        int result = 0;

        toCheck.add(0);
        while (!toCheck.isEmpty()) {
            int value = toCheck.removeFirst();
            if (checked.contains(value)) {
                continue;
            }

            result++;
            checked.add(value);
            toCheck.addAll(GRAPH.get(value));
        }


        System.out.println(result);
    }
}
