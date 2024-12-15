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

        int total = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            boolean pair = false;
            boolean repeat = false;
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];

                if (!repeat && i < chars.length - 2) {
                    char next = chars[i + 2];
                    if (c == next) {
                        repeat = true;
                    }
                }

                if (!pair && i != chars.length - 1) {
                    char next = chars[i + 1];
                    for (int j = i + 2; j < chars.length - 1; j++) {
                        if (chars[j] == c && chars[j + 1] == next) {
                            pair = true;
                            break;
                        }
                    }
                }
            }

            if (pair && repeat) {
                total++;
            }
        }

        System.out.println(total);
    }
}
