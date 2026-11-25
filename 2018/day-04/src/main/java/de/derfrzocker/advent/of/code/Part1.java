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

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<String> sorted = new ArrayList<>(lines);
        sorted.sort(String::compareTo);

        Map<String, Integer> sleepTotal = new HashMap<>();
        Map<String, Map<Integer, Integer>> sleepCountMinutes = new HashMap<>();

        String currentGuard = null;
        int currentStart = 0;
        for (String line : sorted) {
            if (line.contains("#")) {
                currentGuard = line.split(" ")[3].substring(1);
                continue;
            }

            if (line.contains("falls asleep")) {
                currentStart = Integer.parseInt(line.substring(15, 17));
                continue;
            }

            if (line.contains("wakes up")) {
                int end = Integer.parseInt(line.substring(15, 17));
                sleepTotal.put(currentGuard, sleepTotal.getOrDefault(currentGuard, 0) + end - currentStart);

                for (int i = currentStart; i < end; i++) {
                    sleepCountMinutes.computeIfAbsent(currentGuard, _ -> new HashMap<>())
                            .compute(i, (k, v) -> v == null ? 1 : v + 1);
                }
                continue;
            }
        }

        String maxGuard = null;
        int max = 0;
        for (Map.Entry<String, Integer> entries : sleepTotal.entrySet()) {
            if (entries.getValue() > max) {
                max = entries.getValue();
                maxGuard = entries.getKey();
            }
        }
        
        int maxMinute = 0;
        int maxMinuteCount = 0;
        for (Map.Entry<Integer, Integer> entries : sleepCountMinutes.get(maxGuard).entrySet()) {
            if (entries.getValue() > maxMinuteCount) {
                maxMinuteCount = entries.getValue();
                maxMinute = entries.getKey();
            }
        }

        System.out.println(maxGuard);
        System.out.println(max);
        System.out.println(maxMinute * Integer.parseInt(maxGuard));
    }
}
