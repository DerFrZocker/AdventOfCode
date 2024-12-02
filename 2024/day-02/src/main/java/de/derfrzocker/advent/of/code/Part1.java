package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;

        dance:
        for (String line : lines) {
            String[] split = line.split(" ");

            if (split.length == 1) {
                result++;
                continue dance;
            }

            int last = Integer.parseInt(split[0]);
            boolean asc = false;
            int next = Integer.parseInt(split[1]);
            if (Math.abs(last - next) <=0) {
                continue dance;
            }
            if (Math.abs(last - next) > 3) {
                continue dance;
            }
            if (last > next) {
                // desc
                asc = false;
            } else if (last < next) {
                // asc
                asc = true;
            } else {
                continue dance;
            }

            last = next;

            for (int i = 2; i < split.length; i++) {
                next = Integer.parseInt(split[i]);
                if (Math.abs(last - next) <= 0) {
                    continue dance;
                }
                if (Math.abs(last - next) > 3) {
                    continue dance;
                }
                if (last > next) {
                    // desc
                    if (asc) {
                        continue dance;
                    }
                } else if (last < next) {
                    // asc
                    if (!asc) {
                        continue dance;
                    }
                }
                last = next;
            }
            result++;
        }

        System.out.println(result);
    }
}
