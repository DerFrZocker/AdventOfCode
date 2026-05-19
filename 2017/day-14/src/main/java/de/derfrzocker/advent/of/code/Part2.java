package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String prefix = lines.getFirst();

        boolean[][] map = new boolean[128][128];
        for (int i = 0; i < 128; i++) {
            String hash = hash(prefix + "-" + i);
            char[] chars = hash.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == '1') {
                    map[i][j] = true;
                }
            }
        }

        int result = 0;
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                if (map[x][y]) {
                    result++;
                    map[x][y] = false;
                    search(map, x - 1, y);
                    search(map, x + 1, y);
                    search(map, x, y - 1);
                    search(map, x, y + 1);
                }
            }
        }

        System.out.println(result);
    }

    private static void search(boolean[][] map, int x, int y) {
        if (x < 0 || y < 0 || x >= 128 || y >= 128) {
            return;
        }
        if (map[x][y]) {
            map[x][y] = false;
            search(map, x - 1, y);
            search(map, x + 1, y);
            search(map, x, y - 1);
            search(map, x, y + 1);
        }
    }

    private static String hash(String input) {
        List<Integer> lengths = new ArrayList<>();
        for (char c : input.toCharArray()) {
            lengths.add((int) c);
        }

        lengths.add(17);
        lengths.add(31);
        lengths.add(73);
        lengths.add(47);
        lengths.add(23);

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            integers.add(i);
        }

        int skip = 0;
        int index = 0;
        for (int u = 0; u < 64; u++) {
            for (int length : lengths) {
                for (int i = 0; i < (length / 2); i++) {
                    int first = (index + i) % integers.size();
                    int second = (index + (length - 1) - i) % integers.size();
                    int tmp = integers.get(first);
                    integers.set(first, integers.get(second));
                    integers.set(second, tmp);
                }

                index += length;
                index += skip;
                index = index % integers.size();
                skip++;
            }
        }

        List<Integer> denseHash = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            int value = 0;
            for (int u = 0; u < 16; u++) {
                value = value ^ integers.get((i * 16) + u);
            }

            denseHash.add(value);
        }

        return denseHash.stream().map(Integer::toBinaryString).map(Part2::pad).reduce(String::concat).get();
    }

    private static String pad(String value) {
        if (value.length() == 8) {
            return value;
        }
        if (value.isEmpty()) {
            return "00000000";
        }

        if (value.length() == 1) {
            return "0000000" + value;
        }

        if (value.length() == 2) {
            return "000000" + value;
        }

        if (value.length() == 3) {
            return "00000" + value;
        }

        if (value.length() == 4) {
            return "0000" + value;
        }

        if (value.length() == 5) {
            return "000" + value;
        }

        if (value.length() == 6) {
            return "00" + value;
        }

        if (value.length() == 7) {
            return "0" + value;
        }

        throw new RuntimeException();
    }
}
