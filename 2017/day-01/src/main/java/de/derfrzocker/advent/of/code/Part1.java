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

        int result = 0;

        String input = lines.getFirst();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            char next = input.charAt((i + 1) == input.length() ? 0 : i + 1);
            if (c != next) {
                continue;
            }

            result = Math.addExact(result, c - '0');
        }

        System.out.println(result);
    }
}
