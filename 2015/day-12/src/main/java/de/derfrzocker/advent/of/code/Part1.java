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

        long total = 0;
        char[] chars = lines.get(0).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c >= '0' && c <= '9') {
                boolean minus = false;
                if (chars[i - 1] == '-') {
                    minus = true;
                }
                int result = c - '0';
                char next = chars[i + 1];
                while (next >= '0' && next <= '9') {
                    i++;
                    result *= 10;
                    result += (next - '0');
                    next = chars[i + 1];
                }
                if (minus) {
                    total -= result;
                } else {
                    total += result;
                }
            }
        }

        System.out.println(total);
    }
}
