package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    static Map<Character, List<String>> towels = new HashMap<>();
    static Map<String, Long> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String[] split = lines.get(0).split(", ");
        for (String line : split) {
            towels.computeIfAbsent(line.charAt(0), d -> new ArrayList<>()).add(line);
        }

        List<String> wantedPattern = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            wantedPattern.add(lines.get(i));
        }

        long result = 0;
        for (int i = 0; i < wantedPattern.size(); i++) {
            System.out.println("(%d|%d)".formatted(i, wantedPattern.size()));
            String pattern = wantedPattern.get(i);
            result = Math.addExact(result, canDo(pattern, 0));
        }

        System.out.println(result);
    }

    private static long canDo(String wantedPattern, int index) {
        char at = wantedPattern.charAt(index);
        List<String> possible = towels.get(at);

        if (possible == null || possible.isEmpty()) {
            return 0;
        }

        long toReturn = 0;
        Map<Integer, Integer> nextToCheck = new HashMap<>();
        dance:
        for (String towel : possible) {
            for (int i = 0; i < towel.length(); i++) {
                if (wantedPattern.length() <= i + index) {
                    continue dance;
                }

                if (wantedPattern.charAt(index + i) != towel.charAt(i)) {
                    continue dance;
                }
            }

            int newIndex = index + towel.length();
            if (newIndex == wantedPattern.length()) {
                toReturn = Math.addExact(toReturn, 1);
                continue dance;
            }

            int amount = nextToCheck.computeIfAbsent(newIndex, i -> 0);
            nextToCheck.put(newIndex, amount + 1);
        }

        for (Map.Entry<Integer, Integer> entry : nextToCheck.entrySet()) {
            String subString = wantedPattern.substring(entry.getKey());
            Long value = cache.get(subString);
            if (value != null) {
                value = Math.multiplyExact(value, entry.getValue());
                toReturn = Math.addExact(toReturn, value);
            } else {
                long val = canDo(wantedPattern, entry.getKey());
                cache.put(subString, val);
                val = Math.multiplyExact(val, entry.getValue());
                toReturn = Math.addExact(toReturn, val);
            }
        }

        return toReturn;
    }
}
