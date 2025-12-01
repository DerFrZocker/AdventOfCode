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


        int start = 50;
        int count = 0;
        for (String line : lines) {
            char first =  line.charAt(0);
            String rest =  line.substring(1);
            int rotate = Integer.parseInt(rest);

            if (first == 'L') {
                int full = rotate / 100;
                int rest2 = rotate % 100;

                int end = start - rest2;

                if (end < 0) {
                    start = 100 + end;
                } else {
                    start = end;
                }
            } else if (first == 'R') {
                int full = rotate / 100;
                int rest2 = rotate % 100;
                int end =  start + rest2;
                if (end > 99) {
                    start = end - 100;
                } else {
                    start = end;
                }
            } else {
                throw new RuntimeException();
            }

            if (start == 0) {
                count++;
            }

        }

        System.out.println("Start: " + start);
        System.out.println("Count: " + count);
    }
}
