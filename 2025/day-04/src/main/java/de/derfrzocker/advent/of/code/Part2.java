package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        boolean[][] maze = new boolean[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            maze[i] = new boolean[line.length()];
            for (int j = 0; j < line.length(); j++) {
                char ch = line.charAt(j);
                if (ch == '@') {
                    maze[i][j] = true;
                }
            }
        }

        long result = 0;

        boolean removed = false;

        do {
            removed = false;
            for (int x = 0; x < maze.length; x++) {
                for (int y = 0; y < maze[x].length; y++) {
                    if (!maze[x][y]) {
                        continue;
                    }

                    int count = 0;
                    for (int x1 = x - 1; x1 <= x + 1; x1++) {
                        for (int y1 = y - 1; y1 <= y + 1; y1++) {
                            if (x1 < 0 || y1 < 0 || x1 >= maze.length || y1 >= maze[x].length) {
                                continue;
                            }
                            if (x1 == x && y1 == y) {
                                continue;
                            }
                            if (maze[x1][y1]) {
                                count++;
                            }
                        }
                    }

                    if (count < 4) {
                        result = Math.addExact(result, 1);
                        removed = true;
                        maze[x][y] = false;
                    }
                }
            }
        } while (removed);

        System.out.println(result);
    }
}
