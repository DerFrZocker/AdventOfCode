package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Point> points = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split("x");
            int l = Integer.parseInt(split[0]);
            int w = Integer.parseInt(split[1]);
            int h = Integer.parseInt(split[2]);

            points.add(new Point(l, w, h));
        }

        long total = 0;
        for (Point point : points) {
            long first = point.l * point.w;
            long second = point.w * point.h;
            long third = point.h * point.l;

            long sub = 2 * first + 2 * second + 2 * third;
            sub += Math.min(Math.min(first, second), third);
            total += sub;
        }

        System.out.println(total);
    }

    private record Point(int l, int w, int h) {

    }
}
