package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> values = new ArrayList<>();
        for (String split : lines.getFirst().split("	")) {
            values.add(Integer.parseInt(split));
        }

        Set<List<Integer>> visited = new HashSet<>();
        int step = 0;
        do {
            visited.add(new ArrayList<>(values));
            step++;
            int max = Integer.MIN_VALUE;
            int index = -1;
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) > max) {
                    max = values.get(i);
                    index = i;
                }
            }

            int value = values.get(index);
            values.set(index, 0);
            for (int i = 1; i <= value; i++) {
                values.set((index + i) % values.size(), values.get((index + i) % values.size()) + 1);
            }

        } while (!visited.contains(values));

        System.out.println(step);
    }
}
