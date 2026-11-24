package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int twoCount = 0;
        int threeCount = 0;

        for (String line : lines) {
            Map<Character, Integer> counts = new HashMap<>();

            for (char c : line.toCharArray()) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }

            boolean twoIncreased = false;
            boolean threeIncreased = false;

            for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
                if (entry.getValue() == 2 && !twoIncreased) {
                    twoCount++;
                    twoIncreased = true;
                } else if (entry.getValue() == 3 && !threeIncreased) {
                    threeCount++;
                    threeIncreased = true;
                }
            }
        }

        System.out.println(twoCount * threeCount);
    }
}
