package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int ITERATION = 2017;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        LinkedList<Integer> values = new LinkedList<>();
        int step = Integer.parseInt(lines.getFirst());

        values.add(0);

        int index = 0;
        for (int i = 1; i <= ITERATION; i++) {
            index += step;
            index %= values.size();
            index++;
            values.add(index, i);
        }

        index++;
        index %= values.size();
        System.out.println(values.get(index));
    }
}
