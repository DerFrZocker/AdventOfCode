package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        char[][] chars = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            chars[i] = lines.get(i).toCharArray();
        }

        int x = 0;
        int y = 0;
        for (int i = 0; i < chars[0].length; i++) {
            char c = chars[0][i];
            if (c == '|') {
                x = i;
            }
        }

        StringBuilder sb = new StringBuilder();
        Direction last = Direction.DOWN;
        while (true) {
            x += last.getXOffset();
            y += last.getYOffset();

            if (x < 0 || y < 0 || x >= chars[0].length || y >= chars.length) {
                break;
            }

            char c = chars[y][x];
            if (c == ' ') {
                break;
            }
            switch (c) {
                case ' ' -> throw new RuntimeException();
                case '|', '-' -> {
                }
                case '+' -> {
                    for (Direction direction : Direction.values()) {
                        if (direction == last) {
                            continue;
                        }
                        if (last == Direction.DOWN && direction == Direction.UP) {
                            continue;
                        }
                        if (last == Direction.UP && direction == Direction.DOWN) {
                            continue;
                        }

                        if (last == Direction.LEFT && direction == Direction.RIGHT) {
                            continue;
                        }
                        if (last == Direction.RIGHT && direction == Direction.LEFT) {
                            continue;
                        }

                        if (chars[y + direction.getYOffset()][x + direction.getXOffset()] != ' ') {
                            last = direction;
                            break;
                        }
                    }
                }
                default -> sb.append(c);
            }
        }

        System.out.print(sb);
    }
}
