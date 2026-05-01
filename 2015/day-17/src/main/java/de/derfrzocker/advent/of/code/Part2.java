package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int TARGET = 150;

    private static int containerUsed = Integer.MAX_VALUE;
    private static int ways = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> containers = lines.stream().map(Integer::parseInt).toList();

        long count = count(containers, TARGET, 0, 0);

        System.out.println(count);
        System.out.println(containerUsed);
        System.out.println(ways);
    }

    private static long count(List<Integer> containers, int missing, int startIndex, int used) {
        long count = 0;
        for (int i = startIndex; i < containers.size(); i++) {
            int container = containers.get(i);

            if (missing == container) {
                count++;
                if (containerUsed > (used + 1)) {
                    containerUsed = used + 1;
                    ways = 1;
                } else if (containerUsed == (used + 1)) {
                    ways++;
                }
            } else if (container < missing) {
                count = Math.addExact(count, count(containers, missing - container, i + 1, used + 1));
            }
        }

        return count;
    }
}
