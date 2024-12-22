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

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    static Map<Integer, List<Integer>> prices = new HashMap<>();
    static Map<Integer, Map<PriceChange, Integer>> globalChanges = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long result = 0;
        for (int i2 = 0; i2 < lines.size(); i2++) {
            long value = Long.parseLong(lines.get(i2));
            List<Integer> price = prices.computeIfAbsent(i2, k -> new ArrayList<>());
            price.add(lastDigit(value));
            for (int i = 0; i < 2000; i++) {
                value = next(value);
                price.add(lastDigit(value));
            }

            result = Math.addExact(result, value);
        }

        for (Map.Entry<Integer, List<Integer>> entry : prices.entrySet()) {
            Map<PriceChange, Integer> changes = new HashMap<>();
            List<Integer> price = entry.getValue();
            int firstChange = price.get(1) - price.get(0);
            int secondChange = price.get(2) - price.get(1);
            int thirdChange = price.get(3) - price.get(2);
            int fourthChange = price.get(4) - price.get(3);

            changes.put(new PriceChange(firstChange, secondChange, thirdChange, fourthChange), price.get(4));

            for (int i = 5; i < price.size(); i++) {
                firstChange = secondChange;
                secondChange = thirdChange;
                thirdChange = fourthChange;
                fourthChange = price.get(i) - price.get(i - 1);
                PriceChange change = new PriceChange(firstChange, secondChange, thirdChange, fourthChange);

                if (!changes.containsKey(change)) {
                    changes.put(change, price.get(i));
                }
            }

            globalChanges.put(entry.getKey(), changes);
        }

        Set<PriceChange> possibleChanges = new HashSet<>();
        for (Map.Entry<Integer, Map<PriceChange, Integer>> entry : globalChanges.entrySet()) {
            possibleChanges.addAll(entry.getValue().keySet());
        }

        int bestBanana = 0;
        for (PriceChange change : possibleChanges) {
            int current = 0;
            for (Map.Entry<Integer, Map<PriceChange, Integer>> entry : globalChanges.entrySet()) {
                Integer value = entry.getValue().get(change);
                if (value != null) {
                    current = Math.addExact(current, value);
                }
            }

            if (current > bestBanana) {
                bestBanana = current;
            }
        }

        System.out.println(bestBanana);
    }

    private static int lastDigit(long number) {
        String numberAsString = String.valueOf(number);
        return numberAsString.charAt(numberAsString.length() - 1) - '0';
    }

    private static long next(long value) {
        value = mix(value, Math.multiplyExact(value, 64));
        value = prune(value);

        value = mix(value, value / 32);
        value = prune(value);

        value = mix(value, Math.multiplyExact(value, 2048));
        value = prune(value);

        return value;
    }

    private static long mix(long value, long result) {
        return value ^ result;
    }

    private static long prune(long value) {
        return value % 16777216;
    }

    private record PriceChange(int first, int second, int third, int fourth) {
    }
}
