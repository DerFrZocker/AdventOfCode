package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int SIZE = 50;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> list = new ArrayList<>();
        for (char c : lines.get(0).toCharArray()) {
            list.add(c - '0');
        }

        for (int k = 0; k < SIZE; k++) {
            List<Integer> tmp = new ArrayList<>();
            int current = list.get(0);
            int amount = 1;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) == current) {
                    amount++;
                } else {
                    for (char c : String.valueOf(amount).toCharArray()) {
                        tmp.add(c - '0');
                    }
                    tmp.add(current);
                    current = list.get(i);
                    amount = 1;
                }
            }

            for (char c : String.valueOf(amount).toCharArray()) {
                tmp.add(c - '0');
            }
            tmp.add(current);

            list = tmp;
            System.out.println(list.size());
        }

        System.out.println(list.size());
    }
}
