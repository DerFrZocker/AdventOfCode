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

    private static final int RACE_TIME = 2503;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<ReindeerData> data = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String name = parts[0];
            int speed = Integer.parseInt(parts[3]);
            int flyTime = Integer.parseInt(parts[6]);
            int restTime = Integer.parseInt(parts[13]);
            data.add(new ReindeerData(name, speed, flyTime, restTime));
        }

        Map<ReindeerData, Long> points = new HashMap<>();
        data.forEach(reindeer -> points.put(reindeer, 0L));

        for (int i = 1; i <= RACE_TIME; i++) {
            Map<ReindeerData, Long> range = new HashMap<>();
            for (ReindeerData reindeer : data) {

                int cycleTime = reindeer.flyTime() + reindeer.restTime();

                int cycles = i / cycleTime;
                int fullCycleTime = cycles * cycleTime;

                long distance = Math.multiplyExact(cycles, reindeer.flyTime());
                distance = Math.multiplyExact(distance, reindeer.speed());

                int restTime = i - fullCycleTime;
                if (restTime < reindeer.flyTime()) {
                    long tmp = Math.multiplyExact(reindeer.speed(), restTime);
                    distance = Math.addExact(distance, tmp);
                } else {
                    long tmp = Math.multiplyExact(reindeer.speed(), reindeer.flyTime());
                    distance = Math.addExact(distance, tmp);
                }

                range.put(reindeer, distance);
            }

            List<ReindeerData> best = new ArrayList<>();
            long bestDistance = Long.MIN_VALUE;
            for (ReindeerData reindeer : range.keySet()) {
                long distance = range.get(reindeer);
                if (distance > bestDistance) {
                    best.clear();
                    best.add(reindeer);
                    bestDistance = distance;
                } else if (distance == bestDistance) {
                    best.add(reindeer);
                }
            }

            best.forEach(reindeer -> points.compute(reindeer, (key, val) -> val == null ? 1 : val + 1));
        }

        System.out.println(points.values().stream().mapToLong(Long::longValue).sorted().max().getAsLong());
    }

    private record ReindeerData(String name, int speed, int flyTime, int restTime) {

    }
}
