package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int TARGET = 35651584;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        StringBuilder input = new StringBuilder(lines.getFirst());

        String result = null;
        while (true) {
            StringBuilder builder = new StringBuilder();
            for (int i = input.length() - 1; i >= 0; i--) {
                char c = input.charAt(i);
                if (c == '0') {
                    builder.append('1');
                } else {
                    builder.append('0');
                }
            }

            input.append('0').append(builder);

            if (input.length() >= TARGET) {
                result = input.substring(0, TARGET);
                break;
            }
        }

        while (true) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < result.length(); i += 2) {
                char c = result.charAt(i);
                char c2 = result.charAt(i + 1);
                if (c == c2) {
                    builder.append('1');
                } else {
                    builder.append('0');
                }
            }

            if (builder.length() % 2 == 1) {
                System.out.println(builder);
                return;
            }
            result = builder.toString();
        }
    }
}
