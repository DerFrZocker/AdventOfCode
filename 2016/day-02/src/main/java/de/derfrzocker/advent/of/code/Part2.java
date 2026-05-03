package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final char[][] PAD = new char[][]{{'X', 'X', '1', 'X', 'X'}, {'X', '2', '3', '4', 'X'}, {'5', '6', '7', '8', '9'}, {'X', 'A', 'B', 'C', 'X'}, {'X', 'X', 'D', 'X', 'X'}};

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            List<Direction> directions = new ArrayList<>();
            for (char c : line.toCharArray()) {
                Direction direction = switch (c) {
                    case 'R' -> Direction.RIGHT;
                    case 'L' -> Direction.LEFT;
                    case 'U' -> Direction.UP;
                    case 'D' -> Direction.DOWN;
                    default -> throw new IllegalArgumentException();
                };
                directions.add(direction);
            }

            instructions.add(new Instruction(directions));
        }

        StringBuilder result = new StringBuilder();
        int currentX = 0;
        int currentY = 2;

        for (Instruction instruction : instructions) {
            for (Direction direction : instruction.directions) {
                int newX = currentX + direction.getXOffset();
                int newY = currentY + direction.getYOffset();

                if (newX >= 0 && newX < 5 && newY >= 0 && newY < 5) {
                    if (PAD[newY][newX] != 'X') {
                        currentX = newX;
                        currentY = newY;
                    }
                }
            }

            result.append(PAD[currentY][currentX]);
        }

        System.out.println(result);
    }

    private record Instruction(List<Direction> directions) {
    }
}
