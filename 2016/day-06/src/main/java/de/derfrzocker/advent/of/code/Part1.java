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

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<List<Character>> strings = new ArrayList<>();

        for (int i = 0; i < lines.getFirst().length(); i++) {
            strings.add(new ArrayList<>());
        }

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                List<Character> list = strings.get(i);

                list.add(line.charAt(i));
            }
        }

        StringBuilder builder = new StringBuilder();

        for (List<Character> list : strings) {
            Map<Character, Integer> map = new HashMap<>();
            for (Character character : list) {
                map.compute(character, (c, count) -> count == null ? 1 : count + 1);
            }

            Character best = '$';
            int amount = 0;
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                if (entry.getValue() > amount) {
                    best = entry.getKey();
                    amount = entry.getValue();
                }
            }

            builder.append(best);
        }

        System.out.println(builder);
    }
}
