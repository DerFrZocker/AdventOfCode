package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int santaX = 0;
        int santaY = 0;

        Set<Position> delivered = new HashSet<>();
        delivered.add(new Position(0, 0));
        for (char c : lines.get(0).toCharArray()) {
            Direction direction = Direction.fromChar(c);

            santaX += direction.getXOffset();
            santaY += direction.getYOffset();

            delivered.add(new Position(santaX, santaY));
        }

        System.out.println(delivered.size());
    }
}
