package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long literalCount = 0;
        for (String line : lines) {
            literalCount += line.length();
        }

        long memoryCount = 0;
        for (String line : lines) {
            line = line.substring(1, line.length() - 1);

            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '\\') {
                    if (i < line.length() - 1) {
                        char next = line.charAt(i + 1);
                        if (next == '\\') {
                            chars.add(next);
                            i++;
                        }
                        if (next == '"') {
                            chars.add('"');
                            i++;
                        }
                        if (next == 'x' && i < line.length() - 3) {
                            char firstHex = line.charAt(i + 2);
                            char secondHex = line.charAt(i + 3);

                            chars.add('X');

                            i++;
                            i++;
                            i++;
                        }
                    }
                } else {
                    chars.add(c);
                }
            }

            memoryCount += chars.size();
        }

        System.out.println(literalCount - memoryCount);
    }
}
