package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String bestString = null;
        int best = Integer.MAX_VALUE;

        for (int i = 0; i < lines.size() - 1; i++) {
            String first = lines.get(i);
            dance:
            for (int j = i + 1; j < lines.size(); j++) {
                String second = lines.get(j);

                if (first.length() != second.length()) {
                    throw new IllegalArgumentException("Not the same length.");
                }

                StringBuilder builder = new StringBuilder();
                int count = 0;
                for (int k = 0; k < first.length(); k++) {
                    char firstChar = first.charAt(k);
                    char secondChar = second.charAt(k);

                    if (firstChar == secondChar) {
                        builder.append(firstChar);
                    }

                    if (firstChar != secondChar) {
                        count++;
                        if (count > best) {
                            continue dance;
                        }
                    }
                }

                if (count < best) {
                    best = count;
                    bestString = builder.toString();
                }
            }
        }

        System.out.println(bestString);
        System.out.println(best);
    }
}
