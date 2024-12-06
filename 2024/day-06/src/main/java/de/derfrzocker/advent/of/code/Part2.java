package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {

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

        char[][] area2 = new char[height][width];
        for (int i = 0; i < lines.size(); i++) {
            area2[i] = lines.get(i).toCharArray();
        }

        int stucks = 0;
        boolean couldFind = false;
        for (int y2 = 0; y2 < height; y2++) {
            System.out.println("(%d | %d)".formatted(y2, height));
            for (int x2 = 0; x2 < width; x2++) {
                List<Postion> positions = new ArrayList<>();

                char[][] area = Arrays.stream(area2).map(char[]::clone).toArray(char[][]::new);

                if (area[y2][x2] == AIR) {
                    area[y2][x2] = STUFF;
                } else {
                    continue;
                }

                dance: do {
                    try {
                        couldFind = false;
                        for (int y = 0; y < height; y++) {
                            for (int x = 0; x < width; x++) {
                                char at = area[y][x];
                                if (at == Facing.UP.symbol) {
                                    if (isPresent(x, y, Facing.UP, positions)) {
                                        stucks++;
                                        break dance;
                                    } else {
                                        positions.add(new Postion(x, y, Facing.UP));
                                        couldFind = true;
                                        if (y == 0) {
                                            area[y][x] = AIR;
                                        } else if (area[y - 1][x] == AIR) {
                                            area[y - 1][x] = Facing.UP.symbol;
                                            area[y][x] = AIR;
                                        } else {
                                            area[y][x] = Facing.RIGHT.symbol;
                                        }
                                    }
                                } else if (at == Facing.DOWN.symbol) {
                                    if (isPresent(x, y, Facing.DOWN, positions)) {
                                        stucks++;
                                        break dance;
                                    } else {
                                        positions.add(new Postion(x, y, Facing.DOWN));
                                        couldFind = true;
                                        if (y == (height - 1)) {
                                            area[y][x] = AIR;
                                        } else if (area[y + 1][x] == AIR) {
                                            area[y + 1][x] = Facing.DOWN.symbol;
                                            area[y][x] = AIR;
                                        } else {
                                            area[y][x] = Facing.LEFT.symbol;
                                        }
                                    }
                                } else if (at == Facing.LEFT.symbol) {
                                    if (isPresent(x, y, Facing.LEFT, positions)) {
                                        stucks++;
                                        break dance;
                                    } else {
                                        positions.add(new Postion(x, y, Facing.LEFT));
                                        couldFind = true;

                                        if (x == 0) {
                                            area[y][x] = AIR;
                                        } else if (area[y][x - 1] == AIR) {
                                            area[y][x - 1] = Facing.LEFT.symbol;
                                            area[y][x] = AIR;
                                        } else {
                                            area[y][x] = Facing.UP.symbol;
                                        }
                                    }
                                } else if (at == Facing.RIGHT.symbol) {
                                    if (isPresent(x, y, Facing.RIGHT, positions)) {
                                        stucks++;
                                        break dance;
                                    } else {
                                        positions.add(new Postion(x, y, Facing.RIGHT));
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
                        }
                    } catch (Exception e) {

                    }
                } while (couldFind);

                area[y2][x2] = AIR;
            }
        }

        System.out.println(stucks);
    }

    private static boolean isPresent(int x, int y, Facing facing, List<Postion> positions) {
        for (Postion postion : positions) {
            if (postion.x == x && postion.y == y) {
                if (postion.facing == facing) {
                    return true;
                }
            }
        }
        return false;
    }

    private record Postion(int x, int y, Facing facing) {
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
