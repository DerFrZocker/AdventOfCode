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

        List<Triangle> triangles = new ArrayList<>();
        for (int i = 0; i < lines.size(); i = i + 3) {
            String a = lines.get(i);
            String b = lines.get(i + 1);
            String c = lines.get(i + 2);
            for (int j = 0; j < 3; j++) {
                int first = Integer.parseInt(a.substring(j * 5, j * 5 + 5).trim());
                int second = Integer.parseInt(b.substring(j * 5, j * 5 + 5).trim());
                int third = Integer.parseInt(c.substring(j * 5, j * 5 + 5).trim());

                triangles.add(new Triangle(first, second, third));
            }
        }

        int valid = 0;
        for (Triangle triangle : triangles) {
            int firstSecond = triangle.first + triangle.second;
            int firstThird = triangle.first + triangle.third;
            int secondThird = triangle.second + triangle.third;

            if (firstSecond <= triangle.third) {
                continue;
            }
            if (firstThird <= triangle.second) {
                continue;
            }
            if (secondThird <= triangle.first) {
                continue;
            }

            valid++;
        }

        System.out.println(valid);
    }

    private record Triangle(int first, int second, int third) {

    }
}
