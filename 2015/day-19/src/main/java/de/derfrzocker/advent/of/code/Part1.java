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

    private static final Set<String> RESULTS = new HashSet<>();
    private static final List<Replacement> REPLACEMENTS = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String input = null;
        boolean isInput = false;
        for (String line : lines) {
            if (line.isBlank()) {
                isInput = true;
                continue;
            }
            if (isInput) {
                input = line;
                break;
            }

            String[] split = line.split(" => ");
            String key = split[0];
            String value = split[1];

            REPLACEMENTS.add(new Replacement(key, value));
        }

        if (input == null) {
            throw new IllegalArgumentException("No input provided");
        }

        for (Replacement replacement : REPLACEMENTS) {
            String key = replacement.key();
            String value = replacement.value();

            dance:
            for (int i = 0; i < (input.length() - key.length()) + 1; i++) {
                for (int j = 0; j < key.length(); j++) {
                    char c = input.charAt(i + j);
                    char kc = key.charAt(j);

                    if (c != kc) {
                        continue dance;
                    }
                }

                String result = input.substring(0, i) + value + input.substring(i + key.length());

                RESULTS.add(result);
            }
        }

        System.out.println(RESULTS.size());
    }

    private record Replacement(String key, String value) {

    }
}
