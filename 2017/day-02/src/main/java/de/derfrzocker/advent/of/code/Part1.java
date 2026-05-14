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
        for (String line : lines) {
            String[] split = line.split("	");
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (String s : split) {
                min = Math.min(min, Integer.parseInt(s));
                max = Math.max(max, Integer.parseInt(s));
            }

            result += max - min;
        }

        System.out.println(result);
    }
}
