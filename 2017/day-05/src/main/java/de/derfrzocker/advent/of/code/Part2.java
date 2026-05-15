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

        List<Integer> values = new ArrayList<>();
        for (String line : lines) {
            values.add(Integer.parseInt(line));
        }

        int counter = 0;
        int step = 0;
        while (counter < values.size()) {
            step++;
            int current = counter;
            counter += values.get(current);
            int value = values.get(current);
            if (value >= 3) {
                values.set(current, value - 1);
            } else {
                values.set(current, value + 1);
            }
        }

        System.out.println(step);
    }
}
