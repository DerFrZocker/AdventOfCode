package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;
        for (String line : lines) {
            String[] split = line.split("	");

            for (int i = 0; i < split.length - 1; i++) {
                for (int j = i + 1; j < split.length; j++) {
                    int first = Integer.parseInt(split[i]);
                    int second = Integer.parseInt(split[j]);

                    if (first % second == 0) {
                        result += first / second;
                    } else if (second % first == 0) {
                        result += second / first;
                    }
                }
            }
        }

        System.out.println(result);
    }
}
