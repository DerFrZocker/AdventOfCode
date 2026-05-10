package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int elves = Integer.parseInt(lines.getFirst());

        int[] presents = new int[elves];
        Arrays.fill(presents, 1);

        while (true) {
            for (int i = 0; i < elves; i++) {
                if (presents[i] == 0) {
                    continue;
                }
                int j = i;
                do {
                    j = inc(elves, j);

                    if (j == i) {
                        System.out.println(i + 1);
                        return;
                    }
                } while (presents[j] == 0);

                presents[i] = presents[i] + presents[j];
                presents[j] = 0;
            }
        }
    }

    private static int inc(int elves, int i) {
        i++;
        if (i == elves) {
            i = 0;
        }

        return i;
    }
}
