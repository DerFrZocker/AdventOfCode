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

        String line = lines.getFirst();

        List<Character> characters = new ArrayList<>();
        for (char c : line.toCharArray()) {
            if (!characters.contains(Character.toLowerCase(c))) {
                characters.add(Character.toLowerCase(c));
            }
        }

        int min = Integer.MAX_VALUE;
        for (char c : characters) {
            StringBuilder builder =
                    new StringBuilder(line.replace(c + "", "").replace(Character.toUpperCase(c) + "", ""));

            boolean changed;
            do {
                changed = false;

                for (int i = 0; i < builder.length() - 1; i++) {
                    char current = builder.charAt(i);
                    char next = builder.charAt(i + 1);

                    if ((Character.isLowerCase(current) && Character.isUpperCase(next)) ||
                        (Character.isUpperCase(current) && Character.isLowerCase(next))) {

                        if (Character.toLowerCase(current) == Character.toLowerCase(next)) {
                            builder.delete(i, i + 2);
                            changed = true;
                            i--;
                        }
                    }
                }
            } while (changed);

            min = Math.min(min, builder.length());
        }

        System.out.println(min);
    }
}
