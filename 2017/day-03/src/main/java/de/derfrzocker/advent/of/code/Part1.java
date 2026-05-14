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

        int target = Integer.parseInt(lines.getFirst());
        int x = 0;
        int y = 0;
        int i;
        int j;
        int t;
        int u;

        for (i = 1; i * i <= target; i += 2) {
            x++;
            y++;
        }
        x--;
        y--;
        i--;
        i--;

        if (i * i == target) {
            System.out.println(x + y);
            return;
        }

        x++;
        for (j = 1; j <= (i + 1); j++) {
            if (i * i + j == target) {
                System.out.println(Math.abs(x) + Math.abs(y));
                return;
            }
            y--;
        }
        y++;
        j--;

        for (t = 1; t <= (i + 1); t++) {
            x--;
            if (i * i + j + t == target) {
                System.out.println(Math.abs(x) + Math.abs(y));
                return;
            }
        }
        x++;
        t--;

        for (u = 1; u <= (i + 1); u++) {
            y++;
            if (i * i + j + t + u == target) {
                System.out.println(Math.abs(x) + Math.abs(y));
                return;
            }
        }
        y--;
        u--;

        for (int r = 1; r <= (i + 1); r++) {
            x++;
            if (i * i + j + t + u + r == target) {
                System.out.println(Math.abs(x) + Math.abs(y));
                return;
            }
        }
    }
}
