package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String input = lines.getFirst();

        long count = decompress(input);
        System.out.print(count);
    }

    private static long decompress(String input) {
        long count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '(') {
                count = Math.addExact(count, 1);
                continue;
            }

            int end = i;
            while (input.charAt(end) != ')') {
                end++;
            }

            String subString = input.substring(i + 1, end);
            String[] split = subString.split("x");
            int chars = Integer.parseInt(split[0]);
            int amount = Integer.parseInt(split[1]);
            long subCount = decompress(input.substring(end + 1, end + chars + 1));
            count = Math.addExact(count, Math.multiplyExact(subCount, amount));
            i = end + chars;
        }

        return count;
    }
}
