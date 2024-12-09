package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final int FREE = -1;

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String input = lines.get(0);
        List<Integer> uncompressed = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int value = c - '0';

            if (i % 2 == 0) {
                for (int i2 = 0; i2 < value; i2++) {
                    uncompressed.add(id);
                }
                id++;
            } else {
                for (int i2 = 0; i2 < value; i2++) {
                    uncompressed.add(FREE);
                }
            }
        }


        System.out.println(uncompressed);

        dance: for (int i = uncompressed.size() - 1; i >= 0; i--) {
            if (uncompressed.get(i) == FREE) {
                continue;
            }

            for (int i2 = 0; i2 < i; i2++) {
                if (uncompressed.get(i2) == FREE) {
                    uncompressed.set(i2, uncompressed.get(i));
                    uncompressed.set(i, FREE);
                    continue dance;
                }
            }
        }

        System.out.println(uncompressed);

        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < uncompressed.size(); i++) {
            if (uncompressed.get(i) == FREE) {
                break;
            }

            result = result.add(BigInteger.valueOf(i * uncompressed.get(i)));
        }

        System.out.println(result);
    }
}
