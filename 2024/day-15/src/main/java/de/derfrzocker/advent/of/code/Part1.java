package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    static int height;
    static int width;
    static char[][] area;
    static int robotX = 0;
    static int robotY = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int empty = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty()) {
                empty = i;
                break;
            }
        }

        height = empty;
        width = lines.get(0).length();

        area = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                area[i][j] = lines.get(i).charAt(j);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (area[y][x] == '@') {
                    robotX = x;
                    robotY = y;
                    break;
                }
            }
        }

        List<Moves> moves = new ArrayList<>();
        for (int i = empty + 1; i < lines.size(); i++) {
            for (char c : lines.get(i).toCharArray()) {
                if (c == '^') {
                    moves.add(Moves.UP);
                } else if (c == 'v') {
                    moves.add(Moves.DOWN);
                } else if (c == '<') {
                    moves.add(Moves.LEFT);
                } else if (c == '>') {
                    moves.add(Moves.RIGHT);
                }
            }
        }


        for (Moves move : moves) {
            int newX = robotX + move.xOffset;
            int newY = robotY + move.yOffset;
            char space = area[newY][newX];
            if (space == '.') {
                area[newY][newX] = '@';
                area[robotY][robotX] = '.';

                robotX = newX;
                robotY = newY;
                continue;
            }


            if (space == '#') {
                continue;
            }

            if (space == 'O') {
                int i = 1;
                while (true) {
                    int mulX = robotX + (i * move.xOffset);
                    int mulY = robotY + (i * move.yOffset);
                    if (area[mulY][mulX] == '.') {
                        area[mulY][mulX] = 'O';
                        area[newY][newX] = '@';
                        area[robotY][robotX] = '.';

                        robotX = newX;
                        robotY = newY;
                        break;
                    } else if (area[mulY][mulX] == '#') {
                        break;
                    }
                    i++;
                }
            } else {
                System.out.print("WTF: " + space);
            }
        }

        for (int h = 0; h < height; h++) {
            System.out.println(area[h]);
        }

        long result = 0;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                char space = area[y][x];
                if (space == 'O') {
                    result = Math.addExact(result, Math.addExact(Math.multiplyExact(100, y), x));
                }
            }
        }

        System.out.println(result);
    }

    private enum Moves {
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        final int xOffset;
        final int yOffset;

        Moves(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
    }
}
