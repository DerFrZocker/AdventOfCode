package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int ITERATION = 50000000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int step = Integer.parseInt(lines.getFirst());

        int index = 0;
        int value = 0;
        for (int i = 1; i <= ITERATION; i++) {
            index += step;
            index %= i;
            index++;
            if (index == 1) {
                value = i;
            }
        }

        System.out.println(value);
    }
}
