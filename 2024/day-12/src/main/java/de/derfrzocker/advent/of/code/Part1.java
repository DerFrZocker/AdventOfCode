package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    static int height;
    static int width;
    static char[][] area;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        height = lines.size();
        width = lines.get(0).length();

        area = new char[height][width];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                area[i][j] = lines.get(i).charAt(j);
            }
        }

        long total = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Postion pos = new Postion(x, y);
                if (used.contains(pos)) {
                    continue;
                }
                used.add(pos);
                char current = area[y][x];
                seen = new HashSet<>();
                size = 1;
                fence = 0;
                seen.add(pos);
                accumulate(pos, current);
                System.out.println("%c: %d * %d".formatted(current, size, fence));
                total += (long) size * fence;
            }
        }

        System.out.println(total);
    }

    static Set<Postion> used = new HashSet<>();
    static Set<Postion> seen = new HashSet<>();
    static int size = 1;
    static int fence = 0;

    private static void accumulate(Postion postion, char current) {
        Postion up = next(postion, 0, -1);
        Postion down = next(postion, 0, 1);
        Postion left = next(postion, -1, 0);
        Postion right = next(postion, 1, 0);

        check(up, current);
        check(down, current);
        check(left, current);
        check(right, current);
    }

    private static void check(Postion pos, char current) {
        if (inBound(pos) && isChar(pos, current)) {
            if (!seen.contains(pos)) {
                size++;
                seen.add(pos);
                used.add(pos);
                accumulate(pos, current);
            }
        } else {
            fence++;
        }
    }


    private static boolean isChar(Postion postion, char current) {
        return area[postion.y][postion.x] == current;
    }

    private static Postion next(Postion postion, int xOffset, int yOffset) {
        return new Postion(postion.x + xOffset, postion.y + yOffset);
    }

    private static boolean inBound(Postion postion) {
        return inBound(postion.x, postion.y);
    }

    private static boolean inBound(int x, int y) {
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

    private record Postion(int x, int y) {
    }
}
