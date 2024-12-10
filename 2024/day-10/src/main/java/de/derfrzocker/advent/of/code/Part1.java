package de.derfrzocker.advent.of.code;

import javax.swing.text.Position;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    static int height;
    static int width;
    static int[][] area;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        height = lines.size();
        width = lines.get(0).length();

        area = new int[height][width];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                area[i][j] = lines.get(i).charAt(j) - '0';
            }
        }

        List<Position> heads = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (area[y][x] != 0) {
                    continue;
                }
                heads.add(new Position(x, y));
            }
        }

        System.out.println("Heads: " + heads);

        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < heads.size(); i++) {
            Position head = heads.get(i);

            positions.addAll(searchHigher(head, 0));
            System.out.println(positions);
        }

        System.out.println("Positions: " + positions.size());
    }

    private static Set<Position> searchHigher(Position position, int current) {
        Set<Position> positions = new HashSet<>();

        if (position.x - 1 >= 0 && area[position.y][position.x - 1] == current + 1) {
            if (current + 1 == 9) {
                positions.add(new Position(position.x - 1, position.y));
            } else {
                positions.addAll(searchHigher(new Position(position.x - 1, position.y), current + 1));
            }
        }

        if (position.y - 1 >= 0 && area[position.y - 1][position.x] == current + 1) {
            if (current + 1 == 9) {
                positions.add(new Position(position.x, position.y - 1));
            } else {
                positions.addAll(searchHigher(new Position(position.x, position.y - 1), current + 1));
            }
        }

        if (position.y + 1 < height && area[position.y + 1][position.x] == current + 1) {
            if (current + 1 == 9) {
                positions.add(new Position(position.x, position.y + 1));
            } else {
                positions.addAll(searchHigher(new Position(position.x, position.y + 1), current + 1));
            }
        }

        if (position.x + 1 < width && area[position.y][position.x + 1] == current + 1) {
            if (current + 1 == 9) {
                positions.add(new Position(position.x + 1, position.y));
            } else {
                positions.addAll(searchHigher(new Position(position.x + 1, position.y), current + 1));
            }
        }

        return positions;
    }

    private record Position(int x, int y) {
    }
}
