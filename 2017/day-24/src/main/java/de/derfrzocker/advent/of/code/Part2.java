package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

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

        Result max = max(0, new ArrayList<>());

        System.out.println(max.strength());
    }

    private static Result max(int current, List<Pair> pairs) {
        List<Pair> connections = CONNECTIONS.get(current);
        if (connections == null || connections.isEmpty()) {
            return new Result(0, 0);
        }

        int maxLength = 0;
        int maxStrength = 0;
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

            Result result = max(newCurrent, newPairs);
            int length = Math.addExact(result.length(), 1);
            int strength = Math.addExact(result.strength(), pair.a() + pair.b());

            if (maxLength < length) {
                maxLength = length;
                maxStrength = strength;
            } else if (maxLength == length) {
                if (maxStrength < strength) {
                    maxStrength = strength;
                }
            }
        }

        return new Result(maxLength, maxStrength);
    }

    private record Result(int length, int strength) {

    }

    private record Pair(int a, int b) {

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    }
}
