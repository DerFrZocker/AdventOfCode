package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());


        int height = lines.size();
        int width = lines.get(0).length();

        char[][] area = new char[height][width];
        for (int i = 0; i < lines.size(); i++) {
            area[i] = lines.get(i).toCharArray();
        }

        Set<Position> allAntennas = new HashSet<>();
        Map<Character, List<Position>> antennas = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (area[y][x] == '.') {
                    continue;
                }

                antennas.computeIfAbsent(area[y][x], k -> new ArrayList<>()).add(new Position(x, y));
                allAntennas.add(new Position(x, y));
            }
        }

        Set<Position> positions = new HashSet<>();
        for (Map.Entry<Character, List<Position>> entry : antennas.entrySet()) {
            for (Position first : entry.getValue()) {
                for (Position second : entry.getValue()) {
                    if (first.equals(second)) {
                        continue;
                    }

                    int xDiv = second.x - first.x;
                    int yDiv = second.y - first.y;

                    if (inBound(height, width, first.x - xDiv, first.y - yDiv)) {
                        positions.add(new Position(first.x - xDiv, first.y - yDiv));
                    }

                    if (inBound(height, width, second.x + xDiv, second.y + yDiv)) {
                        positions.add(new Position(second.x + xDiv, second.y + yDiv));
                    }
                }
            }
        }

        System.out.println(positions);

        System.out.println(positions.size());
    }

    private static boolean inBound(int height, int width, int x, int y) {
        if (y < 0) {
            return false;
        }

        if (x < 0) {
            return false;
        }

        if (x >= width) {
            return false;
        }

        if (y >= height) {
            return false;
        }

        return true;
    }


    private record Position(int x, int y) {

    }
}
