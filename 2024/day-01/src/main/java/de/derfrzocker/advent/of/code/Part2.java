package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");

    public static void main(String[] args) throws IOException {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();

        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                System.out.println("empty");
                continue;
            }

            String[] split = line.split("   ");
            first.add(Integer.parseInt(split[0]));
            second.add(Integer.parseInt(split[1]));
        }

        first.sort(Integer::compareTo);
        first = first.stream().distinct().toList();
        second.sort(Integer::compareTo);

        int result = 0;
        for (int i = 0; i < first.size(); i++) {
            int current = first.get(i);
            int counter = 0;
            for (int number : second) {
                if (number == current) {
                    counter++;
                }
            }
            result += (current * counter);
        }

        System.out.println(result);
    }
}
