package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final Map<String, List<Edge>> EDGES = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Set<String> nodes = new HashSet<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            nodes.add(parts[0]);
            nodes.add(parts[2]);
        }

        nodes.forEach(node -> EDGES.put(node, new ArrayList<>()));
        for (String line : lines) {
            String[] parts = line.split(" ");
            EDGES.get(parts[0]).add(new Edge(parts[2], Integer.parseInt(parts[4])));
            EDGES.get(parts[2]).add(new Edge(parts[0], Integer.parseInt(parts[4])));
        }

        int currentBest = Integer.MAX_VALUE;
        for (String node : nodes) {
            Set<String> visited = new HashSet<>();
            visited.add(node);

            int best = routes(node, visited);

            if (best < currentBest && visited.size() == nodes.size()) {
                currentBest = best;
            }
        }

        System.out.println(currentBest);
    }

    private static int routes(String node, Set<String> visited) {
        Set<String> sets = new HashSet<>();
        int best = 0;
        for (Edge edge : EDGES.get(node)) {
            if (visited.contains(edge.to)) {
                continue;
            }

            Set<String> current = new HashSet<>(visited);
            current.add(edge.to);
            int result = routes(edge.to, current);
            result += edge.distance;
            if (result < best || best == 0) {
                best = result;
                sets = current;
            }
        }

        visited.addAll(sets);
        return best;
    }

    private record Edge(String to, int distance) {

    }
}
