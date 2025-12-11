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

    private static Map<String, List<String>> map = new HashMap<>();
    private static String start = "you";
    private static String end = "out";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            String[] splits = line.split(":");
            if (splits.length != 2) {
                throw new IllegalArgumentException("Invalid line " + line);
            }

            List<String> outputs = new ArrayList<>();
            for (String split : splits[1].trim().split(" ")) {
                outputs.add(split.trim());
            }

            if (map.containsKey(splits[0])) {
                throw new IllegalArgumentException("Duplicate line " + splits[0]);
            }

            map.put(splits[0], outputs);
        }

        long result = find(start);
        System.out.println(result);
    }

    private static long find(String next) {
        List<String> list = map.get(next);
        if (list == null) {
            return 0;
        }
        if (list.contains(end)) {
            return 1;
        }

        long result = 0;
        for (String s : list) {
            result = Math.addExact(result, find(s));
        }

        return result;
    }
}
