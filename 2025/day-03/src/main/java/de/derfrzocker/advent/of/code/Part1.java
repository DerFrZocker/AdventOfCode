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

        long result = 0;
        for (String value : lines) {
            int pos = -1;
            char first = '0';
            for (int i = 0; i < value.length() - 1; i++) {
                if (first < value.charAt(i)) {
                    first = value.charAt(i);
                    pos = i;
                }
            }

            char second = '0';
            for (int i = pos + 1; i < value.length(); i++) {
                if (second < value.charAt(i)) {
                    second = value.charAt(i);
                }
            }

            String total = String.valueOf(first) + String.valueOf(second);
            result = Math.addExact(result, Integer.parseInt(total));
        }

        System.out.println(result);
    }
}
