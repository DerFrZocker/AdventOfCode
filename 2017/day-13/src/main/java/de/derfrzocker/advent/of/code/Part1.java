package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        NavigableMap<Integer, Integer> map = new TreeMap<>();
        for (String line : lines) {
            String[] split = line.split(": ");
            int depth = Integer.parseInt(split[0]);
            int range = Integer.parseInt(split[1]);

            map.put(depth, range);
        }

        int result = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int depth = entry.getKey();
            int range = entry.getValue();

            int position = depth % ((range * 2) - 2);

            if (position == 0) {
                result += depth * range;
            }
        }

        System.out.println(result);
    }
}
