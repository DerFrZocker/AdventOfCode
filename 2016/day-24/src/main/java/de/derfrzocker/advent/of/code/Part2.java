package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Map<Integer, Position> positions = new HashMap<>();
        boolean[][] maze = new boolean[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == '#') {
                    maze[i][j] = false;
                } else if (c == '.') {
                    maze[i][j] = true;
                } else {
                    int value = Integer.parseInt(String.valueOf(c));
                    maze[i][j] = true;
                    positions.put(value, new Position(i, j));
                }
            }
        }

        Map<Integer, Map<Integer, Integer>> distances = new HashMap<>();
        List<Map.Entry<Integer, Position>> entries = new ArrayList<>(positions.entrySet());
        for (int i = 0; i < entries.size() - 1; i++) {
            Map.Entry<Integer, Position> first = entries.get(i);
            for (int j = i + 1; j < entries.size(); j++) {
                Map.Entry<Integer, Position> second = entries.get(j);
                if (Objects.equals(first.getKey(), second.getKey())) {
                    continue;
                }

                Set<Position> visited = new HashSet<>();
                Set<Position> toCheck = new HashSet<>();
                toCheck.add(first.getValue());
                int steps = 0;
                dance:
                while (true) {
                    steps++;
                    Set<Position> newToCheck = new HashSet<>();
                    for (Position position : toCheck) {
                        for (Direction direction : Direction.values()) {
                            int newX = position.x() + direction.getXOffset();
                            int newY = position.y() + direction.getYOffset();

                            if (!maze[newX][newY]) {
                                continue;
                            }

                            if (second.getValue().x() == newX && second.getValue().y() == newY) {
                                break dance;
                            }

                            Position newPosition = new Position(newX, newY);
                            if (!visited.contains(newPosition)) {
                                visited.add(newPosition);
                                newToCheck.add(newPosition);
                            }
                        }
                    }

                    toCheck = newToCheck;
                }

                distances.computeIfAbsent(first.getKey(), v -> new HashMap<>()).put(second.getKey(), steps);
                distances.computeIfAbsent(second.getKey(), v -> new HashMap<>()).put(first.getKey(), steps);
            }
        }

        int best = findBest(positions.size(), distances, 0, List.of(0), 0);

        System.out.println(best);
    }

    private static int findBest(int positions, Map<Integer, Map<Integer, Integer>> distances, int current, List<Integer> already, int last) {
        if (already.size() == positions) {
            return current + distances.get(last).get(0);
        }

        int currentBest = Integer.MAX_VALUE;
        for (int i = 0; i < positions; i++) {
            if (already.contains(i)) {
                continue;
            }
            List<Integer> newAlready = new ArrayList<>(already);
            newAlready.add(i);
            int value = findBest(positions, distances, current + distances.get(last).get(i), newAlready, i);
            if (value < currentBest) {
                currentBest = value;
            }
        }

        return currentBest;
    }
}
