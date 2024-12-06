package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final char AIR = '.';
    private static final char STUFF = '#';

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");


    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int guardX = -1;
        int guardY = -1;
        Facing facing = null;

        int height = lines.size();
        int width = lines.get(0).length();

        char[][] area = new char[height][width];
        for (int i = 0; i < lines.size(); i++) {
            area[i] = lines.get(i).toCharArray();
        }

        boolean couldFind = false;
        Set<Postion> positions = new HashSet<>();

        do {
            try {
                couldFind = false;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        char at = area[y][x];
                        if (at == Facing.UP.symbol) {
                            positions.add(new Postion(x, y));
                            couldFind = true;
                            if (y == 0) {
                                area[y][x] = AIR;
                            } else if (area[y - 1][x] == AIR) {
                                area[y - 1][x] = Facing.UP.symbol;
                                area[y][x] = AIR;
                            } else {
                                area[y][x] = Facing.RIGHT.symbol;
                            }
                        } else if (at == Facing.DOWN.symbol) {
                            positions.add(new Postion(x, y));
                            couldFind = true;
                            if (y == (height - 1)) {
                                area[y][x] = AIR;
                            } else if (area[y + 1][x] == AIR) {
                                area[y + 1][x] = Facing.DOWN.symbol;
                                area[y][x] = AIR;
                            } else {
                                area[y][x] = Facing.LEFT.symbol;
                            }
                        } else if (at == Facing.LEFT.symbol) {
                            positions.add(new Postion(x, y));
                            couldFind = true;

                            if (x == 0) {
                                area[y][x] = AIR;
                            } else if (area[y][x - 1] == AIR) {
                                area[y][x - 1] = Facing.LEFT.symbol;
                                area[y][x] = AIR;
                            } else {
                                area[y][x] = Facing.UP.symbol;
                            }
                        } else if (at == Facing.RIGHT.symbol) {
                            positions.add(new Postion(x, y));
                            couldFind = true;
                            if (x == (width - 1)) {
                                area[y][x] = AIR;
                            } else if (area[y][x + 1] == AIR) {
                                area[y][x + 1] = Facing.RIGHT.symbol;
                                area[y][x] = AIR;
                            } else {
                                area[y][x] = Facing.DOWN.symbol;
                            }
                        }
                    }
                }
            } catch (Exception e) {

            }
        } while (couldFind);

        System.out.println(positions.size());
    }

    private record Postion(int x, int y) {
    }

    private enum Facing {
        UP('^'),
        RIGHT('>'),
        DOWN('v'),
        LEFT('<');

        final char symbol;

        Facing(char symbol) {
            this.symbol = symbol;
        }
    }
}
