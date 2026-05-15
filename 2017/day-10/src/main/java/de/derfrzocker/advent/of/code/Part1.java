package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> lengths = new ArrayList<>();
        for (String split : lines.getFirst().split(",")) {
            lengths.add(Integer.parseInt(split));
        }

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            integers.add(i);
        }

        int skip = 0;
        int index = 0;
        for (int length : lengths) {
            for (int i = 0; i < (length / 2); i++) {
                int first = (index + i) % integers.size();
                int second = (index + (length - 1) - i) % integers.size();
                int tmp = integers.get(first);
                integers.set(first, integers.get(second));
                integers.set(second, tmp);
            }

            index += length;
            index += skip;
            index = index % integers.size();
            skip++;
        }

        System.out.println(Math.multiplyExact(integers.getFirst(), integers.get(1)));
    }
}
