package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");


    public static void main(String[] args) throws IOException {
        char[][] field = getField();

        other(field);
    }

    public static void other(char[][] field) {
        int result = 0;
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {

                if (field[x][y] != 'A') {
                    continue;
                }

                if (upLeft(field, x, y) || downRight(field, x, y)) {
                    if (upRight(field, x, y) || downLeft(field, x, y)) {
                        result++;
                    }
                }
            }
        }

        System.out.println(result);
    }

    public static boolean upRight(char[][] field, int x, int y) {
        try {
            if (field[x + 1][y - 1] != 'M') {
                return false;
            }

            if (field[x - 1][y + 1] != 'S') {
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

            if (field[x + 1][y + 1] != 'S') {
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

            if (field[x + 1][y - 1] != 'S') {
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

            if (field[x - 1][y - 1] != 'S') {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
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
