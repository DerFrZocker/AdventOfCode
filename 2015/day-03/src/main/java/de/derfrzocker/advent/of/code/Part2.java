package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int santaX = 0;
        int santaY = 0;

        int roboX = 0;
        int roboY = 0;

        Set<Position> delivered = new HashSet<>();
        delivered.add(new Position(0, 0));
        char[] instructions = lines.get(0).toCharArray();
        for (int i = 0; i < instructions.length; i += 2) {
            char santa = instructions[i];
            char robo = instructions[i + 1];
            Direction santaD = Direction.fromChar(santa);
            Direction roboD = Direction.fromChar(robo);

            santaX += santaD.getXOffset();
            santaY += santaD.getYOffset();


            roboX += roboD.getXOffset();
            roboY += roboD.getYOffset();

            delivered.add(new Position(santaX, santaY));
            delivered.add(new Position(roboX, roboY));
        }

        System.out.println(delivered.size());
    }
}
