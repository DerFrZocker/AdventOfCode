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

        List<Disc> discs = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" ");
            int positions = Integer.parseInt(split[3]);
            int start = Integer.parseInt(split[11].substring(0, split[11].length() - 1));
            discs.add(new Disc(positions, start));
        }

        discs.add(new Disc(11, 0));

        int index = 0;
        dance:
        while (true) {
            for (int i = 0; i < discs.size(); i++) {
                Disc disc = discs.get(i);
                int time = (index + 1 + i) % disc.positions;
                if ((disc.start + time) % disc.positions != 0) {
                    index++;
                    continue dance;
                }
            }

            System.out.println(index);
            return;
        }
    }

    private record Disc(int positions, int start) {

    }
}
