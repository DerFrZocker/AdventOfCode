package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;
        for (String line : lines) {
            String[] split = line.split(" ");

            Set<List<Character>> set = new HashSet<>();
            for (String s : split) {
                List<Character> list = new ArrayList<>();
                for (char c : s.toCharArray()) {
                    list.add(c);
                }
                list.sort(Character::compareTo);
                set.add(list);
            }

            if (set.size() == split.length) {
                result++;
            }
        }

        System.out.println(result);
    }
}
