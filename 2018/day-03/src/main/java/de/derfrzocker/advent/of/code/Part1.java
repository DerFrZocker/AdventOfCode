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

        int[][] fabric = new int[1000][1000];

        for (String line : lines) {
            String[] split = line.split(" ");
            String[] coords = split[2].substring(0, split[2].length() - 1).split(",");
            String[] area = split[3].split("x");

            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);
            int xArea = Integer.parseInt(area[0]);
            int yArea = Integer.parseInt(area[1]);

            for (int x = xCoord; x < xCoord + xArea; x++) {
                for (int y = yCoord; y < yCoord + yArea; y++) {
                    fabric[x][y]++;
                }
            }
        }

        int result = 0;
        for (int[] row : fabric) {
            for (int value : row) {
                if (value > 1) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }
}
