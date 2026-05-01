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

    private static final Map<String, Integer> TO_SEARCH = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> sues = new ArrayList<>();

        boolean sue = false;
        dance:
        for (String line : lines) {
            if (line.isBlank()) {
                sue = true;
                continue dance;
            }

            if (!sue) {
                String[] firstSplit = line.split(":", 2);
                String key = firstSplit[0];
                int value = Integer.parseInt(firstSplit[1].trim());

                TO_SEARCH.put(key, value);
            }

            if (sue) {
                String[] firstSplit = line.split(":", 2);
                int sueId = Integer.parseInt(firstSplit[0].substring("Sue ".length()));

                String[] secondSplit = firstSplit[1].split(",");

                for (String s : secondSplit) {
                    String[] values = s.split(":");
                    String key = values[0].trim();
                    int value = Integer.parseInt(values[1].trim());

                    int search = TO_SEARCH.get(key);

                    if (search != value) {
                        continue dance;
                    }
                }

                sues.add(sueId);
            }
        }

        System.out.println(sues);
    }
}
