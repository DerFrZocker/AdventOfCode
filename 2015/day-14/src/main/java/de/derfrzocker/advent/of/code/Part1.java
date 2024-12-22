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

        Map<ReindeerData, Long> range = new HashMap<>();

        for (ReindeerData reindeer : data) {
            int cycleTime = reindeer.flyTime() + reindeer.restTime();

            int cycles = RACE_TIME / cycleTime;
            int fullCycleTime = cycles * cycleTime;

            long distance = Math.multiplyExact(cycles, reindeer.flyTime());
            distance = Math.multiplyExact(distance, reindeer.speed());

            int restTime = RACE_TIME - fullCycleTime;
            if (restTime < reindeer.flyTime()) {
                long tmp = Math.multiplyExact(reindeer.speed(), restTime);
                distance = Math.addExact(distance, tmp);
            } else {
                long tmp = Math.multiplyExact(reindeer.speed(), reindeer.flyTime());
                distance = Math.addExact(distance, tmp);
            }

            range.put(reindeer, distance);
        }

        System.out.println(range.values().stream().mapToLong(Long::longValue).sorted().max().getAsLong());
    }

    private record ReindeerData(String name, int speed, int flyTime, int restTime) {

    }
}
