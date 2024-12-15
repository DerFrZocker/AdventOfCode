package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int total = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            int vowels = 0;
            boolean twice = false;
            boolean naughty = false;
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == 'a') {
                    vowels++;
                }
                if (c == 'e') {
                    vowels++;
                }
                if (c == 'i') {
                    vowels++;
                }
                if (c == 'o') {
                    vowels++;
                }
                if (c == 'u') {
                    vowels++;
                }

                if (i != chars.length - 1) {
                    char next = chars[i + 1];
                    if (c == next) {
                        twice = true;
                    }
                    if (c == 'a' && next == 'b') {
                        naughty = true;
                    }
                    if (c == 'c' && next == 'd') {
                        naughty = true;
                    }
                    if (c == 'p' && next == 'q') {
                        naughty = true;
                    }
                    if (c == 'x' && next == 'y') {
                        naughty = true;
                    }
                }
            }

            if (!naughty && twice && vowels >= 3) {
                total++;
            }
        }

        System.out.println(total);
    }
}
