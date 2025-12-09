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

        List<Position> positions = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(",");
            positions.add(new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }

        long maxArea = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            Position first = positions.get(i);
            for (int j = i + 1; j < positions.size(); j++) {
                Position second = positions.get(j);

                // This area calculation is so wrong on many levels -> But it worked for Part 1.
                // It is still here in memory of all the time I searched for problems in my algo in Part 2,
                // when the problem was this calc.
                long area = Math.multiplyExact(((long) first.x() - second.x() + 1), (first.y() - second.y() + 1));

                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        System.out.println(maxArea);
    }
}
