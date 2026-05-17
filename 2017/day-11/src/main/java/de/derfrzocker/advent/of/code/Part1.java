package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<String> directions = new ArrayList<>();
        Collections.addAll(directions, lines.getFirst().split(","));

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
        }

        x = Math.abs(x);
        y = Math.abs(y);

        if (x >= y) {
            System.out.println(x);
        } else {
            System.out.println(x + (y - x));
        }
    }
}
