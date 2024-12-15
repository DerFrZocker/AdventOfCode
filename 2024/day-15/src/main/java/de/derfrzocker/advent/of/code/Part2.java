package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

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
        width = lines.get(0).length() * 2;

        area = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char at = lines.get(i).charAt(j);
                if (at == 'O') {
                    area[i][j * 2] = '[';
                    area[i][j * 2 + 1] = ']';
                } else if (at == '@') {
                    area[i][j * 2] = '@';
                    area[i][j * 2 + 1] = '.';
                } else {
                    area[i][j * 2] = at;
                    area[i][j * 2 + 1] = at;
                }
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

        for (int h = 0; h < height; h++) {
            System.out.println(area[h]);
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

            if (space == '[' || space == ']') {
                int leftCorner = newX;

                if (space == ']') {
                    leftCorner--;
                }
                if (move == Moves.UP) {
                    if (pushBox(leftCorner, newY, -1, true)) {
                        area[newY][newX] = '@';
                        area[robotY][robotX] = '.';

                        robotX = newX;
                        robotY = newY;
                    }
                    continue;
                } else if (move == Moves.DOWN) {
                    if (pushBox(leftCorner, newY, 1, true)) {
                        area[newY][newX] = '@';
                        area[robotY][robotX] = '.';

                        robotX = newX;
                        robotY = newY;
                    }
                    continue;
                }

                boolean canMove = false;
                int i = 1;
                while (true) {
                    int mulX = robotX + (i * move.xOffset);
                    int mulY = robotY + (i * move.yOffset);
                    if (area[mulY][mulX] == '.') {
                        canMove = true;
                        break;
                    } else if (area[mulY][mulX] == '#') {
                        canMove = false;
                        break;
                    }
                    i++;
                }

                if (canMove) {
                    for (int k = i; k > 0; k--) {
                        int mulX = robotX + (k * move.xOffset);
                        int mulY = robotY + (k * move.yOffset);

                        area[mulY][mulX] = area[mulY - move.yOffset][mulX - move.xOffset];
                    }
                    area[newY][newX] = '@';
                    area[robotY][robotX] = '.';

                    robotX = newX;
                    robotY = newY;
                }
            } else {
                System.out.println("WTF: " + space);
            }
        }

        for (int h = 0; h < height; h++) {
            System.out.println(area[h]);
        }

        long result = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char space = area[y][x];
                if (space == '[') {
                    result = Math.addExact(result, Math.addExact(Math.multiplyExact(100, y), x));
                }
            }
        }

        System.out.println(result);
    }

    private static boolean pushBox(int leftX, int y, int direction, boolean shouldPush) {
        char leftNext = area[y + direction][leftX];
        char rightNext = area[y + direction][leftX + 1];

        if (leftNext == '[') {
            if (pushBox(leftX, y + direction, direction, shouldPush)) {
                if (shouldPush) {
                    area[y + direction][leftX] = '[';
                    area[y + direction][leftX + 1] = ']';

                    area[y][leftX] = '.';
                    area[y][leftX + 1] = '.';
                }
                return true;
            } else {
                return false;
            }
        }

        if (leftNext == '#' || rightNext == '#') {
            return false;
        }

        if (leftNext == ']' && rightNext == '.') {
            if (pushBox(leftX - 1, y + direction, direction, shouldPush)) {
                if (shouldPush) {
                    area[y + direction][leftX] = '[';
                    area[y + direction][leftX + 1] = ']';

                    area[y][leftX] = '.';
                    area[y][leftX + 1] = '.';
                }
                return true;
            } else {
                return false;
            }
        }

        if (leftNext == '.' && rightNext == '[') {
            if (pushBox(leftX + 1, y + direction, direction, shouldPush)) {
                if (shouldPush) {
                    area[y + direction][leftX] = '[';
                    area[y + direction][leftX + 1] = ']';

                    area[y][leftX] = '.';
                    area[y][leftX + 1] = '.';
                }
                return true;
            } else {
                return false;
            }
        }

        if (leftNext == ']' && rightNext == '[') {
            if (pushBox(leftX - 1, y + direction, direction, false) && pushBox(leftX + 1, y + direction, direction, false)) {
                if (shouldPush) {
                    pushBox(leftX - 1, y + direction, direction, true);
                    pushBox(leftX + 1, y + direction, direction, true);
                    area[y + direction][leftX] = '[';
                    area[y + direction][leftX + 1] = ']';

                    area[y][leftX] = '.';
                    area[y][leftX + 1] = '.';
                }
                return true;
            } else {
                return false;
            }
        }

        if (leftNext == '.' && rightNext == '.') {
            if (shouldPush) {
                area[y + direction][leftX] = '[';
                area[y + direction][leftX + 1] = ']';

                area[y][leftX] = '.';
                area[y][leftX + 1] = '.';
            }

            return true;
        }

        System.out.println("WTF?");
        return false;
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
