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

        int tls = 0;
        dance:
        for (String line : lines) {
            int hypernet = 0;
            for (int i = 0; i < line.length() - 2; i++) {
                char c = line.charAt(i);
                if (c == '[') {
                    hypernet++;
                    continue;
                }
                if (c == ']') {
                    hypernet--;
                    continue;
                }

                if (hypernet > 0) {
                    continue;
                }

                char c2 = line.charAt(i + 1);
                char c3 = line.charAt(i + 2);

                if (c == c3) {

                    int innerHypernet = 0;
                    for (int j = 0; j < line.length() - 2; j++) {
                        char innerC = line.charAt(j);
                        if (innerC == '[') {
                            innerHypernet++;
                            continue;
                        }
                        if (innerC == ']') {
                            innerHypernet--;
                            continue;
                        }

                        if (innerHypernet <= 0) {
                            continue;
                        }

                        char innerC2 = line.charAt(j + 1);
                        char innerC3 = line.charAt(j + 2);

                        if (c == innerC2 && c2 == innerC && c2 == innerC3) {
                            tls++;
                            continue dance;
                        }
                    }
                }
            }
        }

        System.out.println(tls);
    }
}
