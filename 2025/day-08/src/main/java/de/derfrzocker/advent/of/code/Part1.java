package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static List<Set<Position>> circ = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Position> positions = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(",");
            positions.add(new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }

        Map<Pair, Long> distances = new HashMap<>();
        for (Position first : positions) {
            for (Position second : positions) {
                if (first.equals(second)) {
                    continue;
                }
                if (distances.containsKey(new Pair(first, second))) {
                    continue;
                }
                if (distances.containsKey(new Pair(second, first))) {
                    continue;
                }

                distances.put(new Pair(first, second), calc(first, second));
            }
        }

        List<Map.Entry<Pair, Long>> entries = new ArrayList<>(distances.entrySet());
        entries.sort(Comparator.comparingLong(Map.Entry::getValue));

        int shouldConnect = 1000; // 1000
        int connected = 0;
        for (Map.Entry<Pair, Long> entry : entries) {
            Position first = entry.getKey().first();
            Position second = entry.getKey().second();
            if (!isInSameCirc(first, second)) {
                addInCirc(first, second);
            }
            connected++;
            if (connected >= shouldConnect) {
                break;
            }
        }

        circ.sort(Comparator.comparing(Set::size));
        circ = circ.reversed();
        System.out.println(Math.multiplyExact(Math.multiplyExact((long) circ.get(0).size(), (long) circ.get(1).size()), (long) circ.get(2).size()));
    }

    private static boolean isInSameCirc(Position first, Position second) {
        for (Set<Position> cirs : circ) {
            if (cirs.contains(first) && cirs.contains(second)) {
                return true;
            }
        }

        return false;
    }

    private static void addInCirc(Position first, Position second) {
        Set<Position> firstCirc = null;
        Set<Position> secondCirc = null;
        for (Set<Position> cirs : circ) {
            if (cirs.contains(first)) {
                firstCirc = cirs;
            }
            if (cirs.contains(second)) {
                secondCirc = cirs;
            }
            if (firstCirc != null && secondCirc != null) {
                break;
            }
        }

        if (firstCirc == null && secondCirc == null) {
            Set<Position> a = new HashSet<>();
            a.add(first);
            a.add(second);
            circ.add(a);
        }
        if (firstCirc != null && secondCirc == null) {
            firstCirc.add(second);
        }
        if (firstCirc == null && secondCirc != null) {
            secondCirc.add(first);
        }
        if (firstCirc != null && secondCirc != null) {
            firstCirc.addAll(secondCirc);
            circ.remove(secondCirc);
        }
    }

    private static long calc(Position first, Position second) {
        return (long) (Math.pow(first.x - second.x, 2) + Math.pow(first.y - second.y, 2) + Math.pow(first.z - second.z, 2));
    }

    private record Pair(Position first, Position second) {
    }

    public record Position(int x, int y, int z) {

    }
}
