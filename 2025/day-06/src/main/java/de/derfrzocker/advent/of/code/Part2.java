package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());
        int max = lines.stream().max(Comparator.comparing(String::length)).map(String::length).get() + 1;

        char[][] grid = new char[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] sub = new char[max];
            grid[i] = sub;
            for (int j = 0; j < sub.length; j++) {
                if (j < line.length()) {
                    sub[j] = line.charAt(j);
                } else {
                    sub[j] = ' ';
                }
            }
        }

        long result = 0;
        List<Long> numbers = new ArrayList<>();
        int minX = 0;
        dance:
        for (int x = 0; x < max; x++) {
            boolean gotChar = false;
            long number = 0;
            for (int y = 0; y < grid.length - 1; y++) {
                char c = grid[y][x];
                if (c == ' ' && gotChar) {
                    numbers.add(number);
                    number = 0;
                    continue dance;
                } else if (c != ' ') {
                    gotChar = true;
                    number = Math.multiplyExact(number, 10);
                    number = Math.addExact(number, Long.parseLong(c + ""));
                }
            }

            if (gotChar) {
                numbers.add(number);
                number = 0;
            }

            if (!gotChar) {
                char operator = ' ';
                other:
                for (int subX = minX; subX <= x; subX++) {
                    char c = grid[lines.size() - 1][subX];
                    if (c != ' ') {
                        operator = c;
                        break other;
                    }
                }

                if (operator == '+') {
                    long tmp = 0;
                    for (long n : numbers) {
                        tmp = Math.addExact(tmp, n);
                    }

                    result = Math.addExact(result, tmp);
                } else if (operator == '*') {
                    long tmp = 1;
                    for (long n : numbers) {
                        tmp = Math.multiplyExact(tmp, n);
                    }

                    result = Math.addExact(result, tmp);
                } else {
                    throw new IllegalArgumentException("Wrong operator");
                }

                minX = x + 1;
                numbers.clear();
            }
        }

        System.out.println(result);
    }
}
