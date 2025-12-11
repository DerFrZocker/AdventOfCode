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
    private static final File EXAMPLE = new File("example-part-2.txt");

    private static Map<String, List<String>> map = new HashMap<>();
    private static String start = "svr";
    private static String end = "out";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            String[] splits = line.split(":");
            if (splits.length != 2) {
                throw new IllegalArgumentException("Invalid line " + line);
            }

            List<String> outputs = new ArrayList<>();
            for (String split : splits[1].trim().split(" ")) {
                outputs.add(split.trim());
            }

            if (map.containsKey(splits[0])) {
                throw new IllegalArgumentException("Duplicate line " + splits[0]);
            }

            map.put(splits[0], outputs);
        }

        long first = 0;
        long second = 0;
        long third = 0;
        if (findPath("dac", "fft")) {
            cache.clear();
            first = find(start, "dac");
            cache.clear();
            second = find("dac", "fft");
            cache.clear();
            third = find("fft", end);
        } else if (findPath("fft", "dac")) {
            cache.clear();
            first = find(start, "fft");
            cache.clear();
            second = find("fft", "dac");
            cache.clear();
            third = find("dac", end);
        } else {
            throw new RuntimeException();
        }

        System.out.println(Math.multiplyExact(Math.multiplyExact(first, second), third));
    }

    private static boolean findPath(String next, String target) {
        List<String> list = map.get(next);
        if (list == null) {
            return false;
        }
        if (list.contains(target)) {
            return true;
        }

        for (String s : list) {
            if (findPath(s, target)) {
                return true;
            }
        }

        return false;
    }

    private static Map<String, Long> cache = new HashMap<>();

    private static long find(String next, String target) {
        List<String> list = map.get(next);
        if (list == null) {
            return 0;
        }
        if (list.contains(target)) {
            return 1;
        }
        if (cache.containsKey(next)) {
            return cache.get(next);
        }

        long result = 0;
        for (String s : list) {
            result = Math.addExact(result, find(s, target));
        }

        cache.put(next, result);

        return result;
    }
}
