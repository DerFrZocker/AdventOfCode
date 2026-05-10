package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int ROWS = 400000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;
        boolean[] previous = new boolean[lines.getFirst().length()];
        for (int i = 0; i < previous.length; i++) {
            previous[i] = lines.getFirst().charAt(i) == '.';
            if (previous[i]) {
                result++;
            }
        }

        for (int i = 1; i < ROWS; i++) {
            boolean[] newPrevious = new boolean[previous.length];

            for (int j = 0; j < previous.length; j++) {
                boolean first;
                boolean second = previous[j];
                boolean third;

                if (j != 0) {
                    first = previous[j - 1];
                } else {
                    first = true;
                }

                if (j != previous.length - 1) {
                    third = previous[j + 1];
                } else  {
                    third = true;
                }

                if (!first && !second && third) {
                    newPrevious[j] = false;
                } else if (first && !second && !third) {
                    newPrevious[j] = false;
                } else if (!first && second && third) {
                    newPrevious[j] = false;
                } else if (first && second && !third) {
                    newPrevious[j] = false;
                } else {
                    newPrevious[j] = true;
                    result = Math.addExact(result, 1);
                }
            }

            previous = newPrevious;
        }

        System.out.println(result);
    }
}
