package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int target = Integer.parseInt(lines.getFirst());
        int x = 0;
        int y = 0;
        int u = 1;

        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        map.computeIfAbsent(0, k -> new HashMap<>()).put(0, 1);

        while (true) {
            x++;
            {
                int result = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        result += map.computeIfAbsent(x - i, k -> new HashMap<>()).getOrDefault(y - j, 0);
                    }
                }

                if (result > target) {
                    System.out.println(result);
                    return;
                }

                map.computeIfAbsent(x, k -> new HashMap<>()).put(y, result);
            }
            u += 2;

            for (int q = 0; q < (u - 2); q++) {
                y--;
                int result = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        result += map.computeIfAbsent(x - i, k -> new HashMap<>()).getOrDefault(y - j, 0);
                    }
                }

                if (result > target) {
                    System.out.println(result);
                    return;
                }

                map.computeIfAbsent(x, k -> new HashMap<>()).put(y, result);
            }

            for (int q = 0; q < (u - 1); q++) {
                x--;
                int result = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        result += map.computeIfAbsent(x - i, k -> new HashMap<>()).getOrDefault(y - j, 0);
                    }
                }

                if (result > target) {
                    System.out.println(result);
                    return;
                }

                map.computeIfAbsent(x, k -> new HashMap<>()).put(y, result);
            }

            for (int q = 0; q < (u - 1); q++) {
                y++;
                int result = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        result += map.computeIfAbsent(x - i, k -> new HashMap<>()).getOrDefault(y - j, 0);
                    }
                }

                if (result > target) {
                    System.out.println(result);
                    return;
                }

                map.computeIfAbsent(x, k -> new HashMap<>()).put(y, result);
            }

            for (int q = 0; q < (u - 1); q++) {
                x++;
                int result = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        result += map.computeIfAbsent(x - i, k -> new HashMap<>()).getOrDefault(y - j, 0);
                    }
                }

                if (result > target) {
                    System.out.println(result);
                    return;
                }

                map.computeIfAbsent(x, k -> new HashMap<>()).put(y, result);
            }
        }
    }
}
