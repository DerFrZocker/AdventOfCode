package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> found = new ArrayList<>();
        int sum = 0;
        while (true) {
            for (String line : lines) {
                sum += Integer.parseInt(line);
                if (found.contains(sum)) {
                    System.out.println(sum);
                    return;
                }
                found.add(sum);
            }
        }
    }
}
