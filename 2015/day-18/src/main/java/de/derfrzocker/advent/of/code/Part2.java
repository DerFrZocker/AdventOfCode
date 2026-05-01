package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int SIZE = 100;
    private static final int STEPS = 100;
    private static boolean[][] state = new boolean[SIZE][SIZE];
    private static boolean[][] state2 = new boolean[SIZE][SIZE];

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char c = lines.get(i).charAt(j);

                if (c == '#') {
                    state[i][j] = true;
                }
            }
        }

        state[0][0] = true;
        state[0][SIZE - 1] = true;
        state[SIZE - 1][0] = true;
        state[SIZE - 1][SIZE - 1] = true;

        for (int i = 0; i < STEPS; i++) {
            for (int x = 0; x < SIZE; x++) {
                for (int y = 0; y < SIZE; y++) {
                    int lights = 0;
                    for (int x1 = x - 1; x1 <= x + 1; x1++) {
                        for (int y1 = y - 1; y1 <= y + 1; y1++) {
                            if (x1 == x && y1 == y) {
                                continue;
                            }

                            if (x1 < 0 || x1 == SIZE || y1 < 0 || y1 == SIZE) {
                                continue;
                            }

                            if (state[x1][y1]) {
                                lights++;
                            }
                        }
                    }

                    if (state[x][y]) {
                        if (lights == 2 || lights == 3) {
                            state2[x][y] = true;
                        } else {
                            state2[x][y] = false;
                        }
                    } else {
                        if (lights == 3) {
                            state2[x][y] = true;
                        } else {
                            state2[x][y] = false;
                        }
                    }
                }
            }

            boolean[][] tmp = state;
            state = state2;
            state2 = tmp;

            state[0][0] = true;
            state[0][SIZE - 1] = true;
            state[SIZE - 1][0] = true;
            state[SIZE - 1][SIZE - 1] = true;
        }

        int count = 0;
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (state[x][y]) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }
}
