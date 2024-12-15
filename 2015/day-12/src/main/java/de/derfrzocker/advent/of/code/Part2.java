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

        long total = 0;
        Map<Integer, Long> depthTotal = new HashMap<>();
        char[] chars = lines.get(0).toCharArray();
        int depth = 0;
        int red = Integer.MAX_VALUE;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == '{' || c == '[') {
                depth++;
                depthTotal.remove(depth);
            }

            if (c == '}' || c == ']') {
                if (red == Integer.MAX_VALUE) {
                    long tmp = depthTotal.getOrDefault(depth, 0L);
                    long other = depthTotal.getOrDefault(depth - 1, 0L);
                    depthTotal.put(depth - 1, tmp + other);
                }

                if (red == depth) {
                    red = Integer.MAX_VALUE;
                }

                depth--;
            }

            if (c == ':' && red == Integer.MAX_VALUE) {
                if (chars[i + 1] == '"' && chars[i + 2] == 'r' && chars[i + 3] == 'e' && chars[i + 4] == 'd' && chars[i + 5] == '"') {
                    red = depth;
                }
            }

            if (red != Integer.MAX_VALUE) {
                continue;
            }

            if (c >= '0' && c <= '9') {
                boolean minus = false;
                if (chars[i - 1] == '-') {
                    minus = true;
                }
                int result = c - '0';
                char next = chars[i + 1];
                while (next >= '0' && next <= '9') {
                    i++;
                    result *= 10;
                    result += (next - '0');
                    next = chars[i + 1];
                }

                if (minus) {
                    result = -result;
                }

                if (depthTotal.containsKey(depth)) {
                    depthTotal.put(depth, depthTotal.get(depth) + result);
                } else {
                    depthTotal.put(depth, (long) result);
                }
            }
        }

        total = depthTotal.getOrDefault(0, 0L);
        System.out.println(total);
    }
}
