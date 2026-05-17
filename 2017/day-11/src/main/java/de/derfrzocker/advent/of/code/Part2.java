package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<String> directions = new ArrayList<>();
        Collections.addAll(directions, lines.getFirst().split(","));

        int max = 0;
        int x = 0;
        int y = 0;
        for (String direction : directions) {
            switch (direction) {
                case "n" -> y -= 1;
                case "ne" -> {
                    x += 1;
                    y -= 1;
                }
                case "se" -> {
                    x += 1;
                    y += 1;
                }
                case "s" -> {
                    y += 1;
                }
                case "sw" -> {
                    x -= 1;
                    y += 1;
                }
                case "nw" -> {
                    x -= 1;
                    y -= 1;
                }
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }

            int tmpX = Math.abs(x);
            int tmpY = Math.abs(y);

            if (tmpX >= tmpY) {
                max = Math.max(max, tmpX);
            } else {
                max = Math.max(max, tmpX + (tmpY - tmpX));
            }
        }

        System.out.println(max);
    }
}
