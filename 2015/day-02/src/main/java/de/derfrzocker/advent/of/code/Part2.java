package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

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
            long sub = point.h * 2 + point.w * 2 + point.l * 2;
            sub -= Math.max(Math.max(point.l, point.w), point.h) * 2;

            sub += point.h * point.l * point.w;

            total += sub;
        }

        System.out.println(total);
    }

    private record Point(int l, int w, int h) {

    }
}
