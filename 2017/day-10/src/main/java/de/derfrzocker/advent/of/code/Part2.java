package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> lengths = new ArrayList<>();
        for (char c : lines.getFirst().toCharArray()) {
            lengths.add((int) c);
        }

        lengths.add(17);
        lengths.add(31);
        lengths.add(73);
        lengths.add(47);
        lengths.add(23);

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            integers.add(i);
        }

        int skip = 0;
        int index = 0;
        for (int u = 0; u < 64; u++) {
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
        }

        List<Integer> denseHash = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            int value = 0;
            for (int u = 0; u < 16; u++) {
                value = value ^ integers.get((i * 16) + u);
            }

            denseHash.add(value);
        }

        System.out.println(denseHash.stream().map(Integer::toHexString).map(Part2::pad).reduce(String::concat).get());
    }

    private static String pad(String value) {
        if (value.length() == 2) {
            return value;
        }
        if (value.isEmpty()) {
            return "00";
        }

        if (value.length() == 1) {
            return "0" + value;
        }

        throw new RuntimeException();
    }
}
