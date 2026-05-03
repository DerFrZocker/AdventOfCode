package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int targetRow = 0;
        int targetColumn = 0;

        String[] firstSplit = lines.getFirst().split("row ");
        targetRow = Integer.parseInt(firstSplit[1].substring(0, firstSplit[1].indexOf(",")));
        String[] secondSplit = lines.getFirst().split("column ");
        targetColumn = Integer.parseInt(secondSplit[1].substring(0, secondSplit[1].indexOf(".")));

        int currentRow = 1;
        int currentColumn = 1;

        long current = 20151125L;
        while (true) {
            long next = Math.multiplyExact(current, 252533L);
            next = next % 33554393;

            current = next;

            if (currentRow == 1) {
                currentRow = currentColumn + 1;
                currentColumn = 1;
             } else {
                currentRow--;
                currentColumn++;
            }

            if (currentRow == targetRow && currentColumn == targetColumn) {
                System.out.println(next);
                break;
            }
        }
    }
}
