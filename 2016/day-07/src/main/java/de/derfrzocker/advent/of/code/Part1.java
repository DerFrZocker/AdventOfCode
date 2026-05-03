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

        int tls = 0;
        dance:
        for (String line : lines) {
            int hypernet = 0;
            boolean valid = false;
            for (int i = 0; i < line.length() - 3; i++) {
                char c = line.charAt(i);
                if (c == '[') {
                    hypernet++;
                    continue;
                }
                if (c == ']') {
                    hypernet--;
                    continue;
                }

                char c2 = line.charAt(i + 1);
                char c3 = line.charAt(i + 2);
                char c4 = line.charAt(i + 3);

                if (c == c2) {
                    continue;
                }

                if (c == c4 && c2 == c3) {
                    if (hypernet == 0) {
                        valid = true;
                    } else {
                        continue dance;
                    }
                }
            }

            if (valid) {
                tls++;
            }
        }

        System.out.println(tls);
    }
}
