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

        int elves = Integer.parseInt(lines.getFirst());
        List<Integer> presents = new ArrayList<>(elves);
        for (int i = 1; i <= elves; i++) {
            presents.add(i);
        }

        int index = 0;
        while (presents.size() > 1) {

            int target = presents.size() / 2;
            target += index;

            if (target >= presents.size()) {
                target -= presents.size();
            }

            presents.remove(target);
            if (target > index) {
                index++;
                if (index >= presents.size()) {
                    index = 0;
                }
            } else if (index == presents.size()) {
                index = 0;
            }
        }

        System.out.println(presents.getFirst());
    }
}
