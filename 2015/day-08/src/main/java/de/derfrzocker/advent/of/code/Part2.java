package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

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
            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '\\') {
                    if (i < line.length() - 1) {
                        char next = line.charAt(i + 1);
                        if (next == '\\') {
                            chars.add('\\');
                            chars.add('\\');
                            chars.add(c);
                            chars.add(next);
                            i++;
                        }
                        if (next == '"') {
                            chars.add('\\');
                            chars.add('\\');
                            chars.add(c);
                            chars.add(next);
                            i++;
                        }
                        if (next == 'x' && i < line.length() - 3) {
                            char firstHex = line.charAt(i + 2);
                            char secondHex = line.charAt(i + 3);

                            chars.add('\\');
                            chars.add(c);
                            chars.add(next);
                            chars.add(firstHex);
                            chars.add(secondHex);

                            i++;
                            i++;
                            i++;
                        }
                    }
                } else {
                    chars.add(c);
                }
            }

            memoryCount += (chars.size() + 4);
        }

        System.out.println(memoryCount - literalCount);

    }
}
