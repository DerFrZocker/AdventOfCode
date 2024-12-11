package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<BigInteger> stones = new ArrayList<>();
        String[] split = lines.get(0).split(" ");
        for (int i = 0; i < split.length; i++) {
            stones.add(BigInteger.valueOf(Integer.parseInt(split[i])));
        }

        System.out.println(stones);

        for (int c = 0; c < 25; c++) {
            List<BigInteger> newStones = new ArrayList<>();
            for (int i = 0; i < stones.size(); i++) {
                BigInteger stone = stones.get(i);

                if (stone.equals(BigInteger.ZERO)) {
                    newStones.add(BigInteger.ONE);
                    continue;
                }
                String string = stone.toString();
                if (string.length() % 2 == 0) {
                    String first = string.substring(0, string.length() / 2);
                    String second = string.substring(string.length() / 2);

                    newStones.add(BigInteger.valueOf(Integer.parseInt(first)));
                    newStones.add(BigInteger.valueOf(Integer.parseInt(second)));
                    continue;
                }

                newStones.add(stone.multiply(BigInteger.valueOf(2024)));
            }

            stones = newStones;
//            System.out.println(stones);
        }

        System.out.println(stones.size());
    }
}
