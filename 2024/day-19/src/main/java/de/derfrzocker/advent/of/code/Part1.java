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

    static Map<Character, List<String>> towels = new HashMap<>();

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

        int result = 0;
        for (String pattern : wantedPattern) {
            if (canDo(pattern, 0)) {
                result++;
            }
        }

        System.out.println(result);
    }

    private static boolean canDo(String wantedPattern, int index) {
        char at = wantedPattern.charAt(index);
        List<String> possible = towels.get(at);

        if (possible == null || possible.isEmpty()) {
            return false;
        }

        dance: for (String towel : possible) {
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
                return true;
            }

            if (canDo(wantedPattern, newIndex)) {
                return true;
            }
        }

        return false;
    }
}
