package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");


    public static void main(String[] args) throws IOException {
        char[][] field = getField();

        other(field);
    }

    public static void other(char[][] field) {
        int result = 0;
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {

                if (field[x][y] != 'X') {
                    continue;
                }

                if (up(field, x, y)) {
                    result++;
                }

                if (down(field, x, y)) {
                    result++;
                }

                if (left(field, x, y)) {
                    result++;
                }
                if (right(field, x, y)) {
                    result++;
                }
                if (upLeft(field, x, y)) {
                    result++;
                }
                if (upRight(field, x, y)) {
                    result++;
                }
                if (downLeft(field, x, y)) {
                    result++;
                }
                if (downRight(field, x, y)) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }

    public static boolean up(char[][] field, int x, int y) {
        try {
        if (field[x][y - 1] != 'M') {
            return false;
        }

        if (field[x][y - 2] != 'A') {
            return false;
        }
        if (field[x][y - 3] != 'S') {
            return false;
        }

        return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean down(char[][] field, int x, int y) {
        try {
            if (field[x][y + 1] != 'M') {
                return false;
            }

            if (field[x][y + 2] != 'A') {
                return false;
            }
            if (field[x][y + 3] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean left(char[][] field, int x, int y) {
        try {
            if (field[x - 1][y] != 'M') {
                return false;
            }

            if (field[x - 2][y ] != 'A') {
                return false;
            }
            if (field[x - 3][y ] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean right(char[][] field, int x, int y) {
        try {
            if (field[x + 1][y] != 'M') {
                return false;
            }

            if (field[x + 2][y ] != 'A') {
                return false;
            }
            if (field[x + 3][y ] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean upRight(char[][] field, int x, int y) {
        try {
            if (field[x + 1][y - 1] != 'M') {
                return false;
            }

            if (field[x + 2][y - 2] != 'A') {
                return false;
            }
            if (field[x + 3][y  -3] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean upLeft(char[][] field, int x, int y) {
        try {
            if (field[x - 1][y - 1] != 'M') {
                return false;
            }

            if (field[x - 2][y - 2] != 'A') {
                return false;
            }
            if (field[x - 3][y  -3] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean downLeft(char[][] field, int x, int y) {
        try {
            if (field[x - 1][y + 1] != 'M') {
                return false;
            }

            if (field[x - 2][y + 2] != 'A') {
                return false;
            }
            if (field[x - 3][y +3] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean downRight(char[][] field, int x, int y) {
        try {
            if (field[x + 1][y + 1] != 'M') {
                return false;
            }

            if (field[x + 2][y + 2] != 'A') {
                return false;
            }
            if (field[x + 3][y + 3] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }


            public static int down(char[][] field) {
        int count = 0;
        for (int i = field.length; i > 3 ; i--) {
            for (int j = field[i].length; j > 3; j--) {
                if (field[i][j] != 'X') {
                    continue;
                }
                if (field[i - 1][j ] != 'M') {
                    continue;
                }
                if (field[i -2][j ] != 'A') {
                    continue;
                }
                if (field[i -3][j] != 'S') {
                    continue;
                }

                count++;
            }
        }

        return count;
    }

    // XMAS
    public static int height(char[][] field) {
        int count = 0;
        for (int i = 0; i < field.length - 3; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != 'X') {
                    continue;
                }
                if (field[i - 1][j] != 'M') {
                    continue;
                }
                if (field[i - 2][j] != 'A') {
                    continue;
                }
                if (field[i - 3][j] != 'S') {
                    continue;
                }

                count++;
            }
        }

        return count;
    }

    public static int backwards(char[][] field) {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = field[i].length; j > 3; j--) {
                if (field[i][j] != 'X') {
                    continue;
                }
                if (field[i][j - 1] != 'M') {
                    continue;
                }
                if (field[i][j - 2] != 'A') {
                    continue;
                }
                if (field[i][j - 3] != 'S') {
                    continue;
                }

                count++;
            }
        }

        return count;
    }

// XMAS
    public static int forwards(char[][] field) {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length - 3; j++) {
                if (field[i][j] != 'X') {
                    continue;
                }
                if (field[i][j + 1] != 'M') {
                    continue;
                }
                if (field[i][j + 2] != 'A') {
                    continue;
                }
                if (field[i][j + 3] != 'S') {
                    continue;
                }

                count++;
            }
        }

        return count;
    }

    public static char[][] getField() throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        char[][] field = new char[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            field[i] = line.toCharArray();
        }

        return field;
    }
}
