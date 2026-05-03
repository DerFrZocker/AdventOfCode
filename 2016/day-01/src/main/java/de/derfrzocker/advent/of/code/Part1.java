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

        List<Instruction> instructions = new ArrayList<>();

        for (String line : lines.getFirst().split(",")) {
            line = line.trim();
            char direction = line.charAt(0);
            int blocks = Integer.parseInt(line.substring(1));
            instructions.add(new Instruction(direction, blocks));
        }

        Direction current = Direction.UP;
        int currentX = 0;
        int currentY = 0;
        for (Instruction instruction : instructions) {
            if (instruction.direction == 'R') {
                current = switch (current) {
                    case UP -> Direction.RIGHT;
                    case DOWN -> Direction.LEFT;
                    case LEFT -> Direction.UP;
                    case RIGHT -> Direction.DOWN;
                };
            } else {
                current = switch (current) {
                    case UP -> Direction.LEFT;
                    case DOWN -> Direction.RIGHT;
                    case LEFT -> Direction.DOWN;
                    case RIGHT -> Direction.UP;
                };
            }

            currentX += instruction.blocks * current.getXOffset();
            currentY += instruction.blocks * current.getYOffset();
        }

        System.out.println(Math.abs(currentX) + Math.abs(currentY));
    }

    private record Instruction(char direction, int blocks) {

    }
}
