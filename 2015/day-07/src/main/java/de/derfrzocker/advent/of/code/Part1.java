package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");


    private static final Map<String, Integer> result = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<String> values = new ArrayList<>(lines);

        while (!values.isEmpty()) {
            List<String> set = new ArrayList<>(values);
            values.clear();
            for (String line : set) {
                String[] parts = line.split(" ");

                if ("NOT".equals(parts[0])) {
                    Integer input = get(parts[1]);
                    String output = parts[3];

                    if (input != null) {
                        int value = input;
                        value = ~value;

                        result.put(output, clamp(value));
                    } else {
                        values.add(line);
                    }
                    continue;
                }

                if ("AND".equals(parts[1])) {
                    Integer input1 = get(parts[0]);
                    Integer input2 = get(parts[2]);
                    String output = parts[4];

                    if (input1 != null && input2 != null) {
                        int value = input1 & input2;

                        result.put(output, clamp(value));
                    } else {
                        values.add(line);
                    }

                    continue;
                }

                if ("OR".equals(parts[1])) {
                    Integer input1 = get(parts[0]);
                    Integer input2 = get(parts[2]);
                    String output = parts[4];

                    if (input1 != null && input2 != null) {
                        int value = input1 | input2;

                        result.put(output, clamp(value));
                    } else {
                        values.add(line);
                    }

                    continue;
                }

                if ("LSHIFT".equals(parts[1])) {
                    Integer input1 = get(parts[0]);
                    Integer input2 = get(parts[2]);
                    String output = parts[4];

                    if (input1 != null && input2 != null) {
                        int value = input1 << input2;

                        result.put(output, clamp(value));
                    } else {
                        values.add(line);
                    }

                    continue;
                }

                if ("RSHIFT".equals(parts[1])) {
                    Integer input1 = get(parts[0]);
                    Integer input2 = get(parts[2]);
                    String output = parts[4];

                    if (input1 != null && input2 != null) {
                        int value = input1 >> input2;

                        result.put(output, clamp(value));
                    } else {
                        values.add(line);
                    }

                    continue;
                }

                if ("->".equals(parts[1])) {
                    Integer input1 = get(parts[0]);
                    String output = parts[2];

                    if (input1 != null) {
                        result.put(output, clamp(input1));
                    } else {
                        values.add(line);
                    }

                    continue;
                }
            }
        }

        System.out.println(result.get("a"));
    }

    private static int clamp(int value) {
        return value & 0b1111111111111111;
    }

    private static Integer get(String input) {
        if (Utils.isNumber(input)) {
            return Integer.parseInt(input);
        }

        return result.get(input);
    }
}
