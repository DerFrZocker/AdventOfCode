package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final Map<Integer, List<Pair>> CONNECTIONS = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            String[] split = line.split("/");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            Pair pair = new Pair(a, b);
            CONNECTIONS.computeIfAbsent(a, k -> new ArrayList<>()).add(pair);
            CONNECTIONS.computeIfAbsent(b, k -> new ArrayList<>()).add(pair);
        }

        int max = max(0, new ArrayList<>());

        System.out.println(max);
    }

    private static int max(int current, List<Pair> pairs) {
        List<Pair> connections = CONNECTIONS.get(current);
        if (connections == null || connections.isEmpty()) {
            return 0;
        }

        int max = 0;
        for (Pair pair : connections) {
            if (pairs.contains(pair)) {
                continue;
            }
            List<Pair> newPairs = new ArrayList<>(pairs);
            newPairs.add(pair);
            int newCurrent;
            if (current == pair.a()) {
                newCurrent = pair.b();
            } else {
                newCurrent = pair.a();
            }

            int result = max(newCurrent, newPairs);
            result = Math.addExact(result, pair.a() + pair.b());
            max = Math.max(max, result);
        }

        return max;
    }

    private record Pair(int a, int b) {

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    }
}
