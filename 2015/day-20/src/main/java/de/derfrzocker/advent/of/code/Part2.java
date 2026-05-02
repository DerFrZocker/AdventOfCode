package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long input = Integer.parseInt(lines.getFirst());

        int houseId = 1;
        while (true) {
            long result = calc(houseId);
            if (result >= input) {
                break;
            }
            houseId++;
        }

        System.out.println(houseId);
    }

    private static long calc(int houseId) {
        Set<Integer> elfId = new HashSet<>();
        elfId.add(houseId);

        for (int i = 1; i <= (houseId / 2); i++) {
            if (houseId % i == 0 && houseId / i <= 50) {
                elfId.add(i);
            }
        }

        long result = 0;
        for (int id : elfId) {
            result = Math.addExact(result, (Math.multiplyExact(id, 11)));
        }

        return result;
    }
}
