package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;

        dance:
        for (String line : lines) {
            String[] split = line.split(" ");

            if (isSafe(split)) {
                result++;
                continue dance;
            }

            for (int i = 0; i < split.length; i++) {
                String[] newSplit = new String[split.length - 1];

                for (int j = 0; j < split.length; j++) {
                    if (j == i) {
                        continue;
                    }
                    if (j < i) {
                        newSplit[j] = split[j];
                    } else {
                        newSplit[j - 1] = split[j];
                    }
                }

                if (isSafe(newSplit)) {
                    result++;
                    continue dance;
                }
            }
        }

        System.out.println(result);
    }

    private static boolean isSafe(String[] split) {
        if (split.length == 1) {
            return true;
        }

        int last = Integer.parseInt(split[0]);
        boolean asc = false;
        int next = Integer.parseInt(split[1]);
        if (Math.abs(last - next) <=0) {
            return false;
        }
        if (Math.abs(last - next) > 3) {
            return false;
        }
        if (last > next) {
            // desc
            asc = false;
        } else if (last < next) {
            // asc
            asc = true;
        } else {
            return false;
        }

        last = next;

        for (int i = 2; i < split.length; i++) {
            next = Integer.parseInt(split[i]);
            if (Math.abs(last - next) <= 0) {
                return false;
            }
            if (Math.abs(last - next) > 3) {
                return false;
            }
            if (last > next) {
                // desc
                if (asc) {
                    return false;
                }
            } else if (last < next) {
                // asc
                if (!asc) {
                    return false;
                }
            }
            last = next;
        }

        return true;
    }
}
