package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int WIDTH = 50;
    private static final int HEIGHT = 6;

    private static final boolean[][] SCREEN = new boolean[WIDTH][HEIGHT];

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("rect ")) {
                String[] split = line.substring("rect ".length()).split("x");
                instructions.add(new RectInstruction(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            } else if (line.startsWith("rotate row ")) {
                String[] split = line.substring("rotate row ".length()).split(" ");
                int row = Integer.parseInt(split[0].split("=")[1]);
                int amount = Integer.parseInt(split[2]);
                instructions.add(new RotateRowInstruction(row, amount));
            } else if (line.startsWith("rotate column ")) {
                String[] split = line.substring("rotate column ".length()).split(" ");
                int column = Integer.parseInt(split[0].split("=")[1]);
                int amount = Integer.parseInt(split[2]);
                instructions.add(new RotateColumnInstruction(column, amount));
            } else {
                throw new RuntimeException("Unknown instruction");
            }
        }

        instructions.forEach(Instruction::execute);

        int amount = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (SCREEN[x][y]) {
                    amount++;
                }
            }
        }

        System.out.println(amount);
    }

    private sealed interface Instruction {

        void execute();
    }

    private record RectInstruction(int width, int heigh) implements Instruction {

        @Override
        public void execute() {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < heigh; y++) {
                    SCREEN[x][y] = true;
                }
            }
        }
    }

    private record RotateRowInstruction(int row, int amount) implements Instruction {

        @Override
        public void execute() {
            for (int i = 0; i < amount; i++) {
                boolean next = SCREEN[0][row];
                for (int x = 0; x < WIDTH; x++) {
                    boolean tmp = SCREEN[(x + 1) % WIDTH][row];
                    SCREEN[(x + 1) % WIDTH][row] = next;
                    next = tmp;
                }
            }
        }
    }

    private record RotateColumnInstruction(int column, int amount) implements Instruction {

        @Override
        public void execute() {
            for (int i = 0; i < amount; i++) {
                boolean next = SCREEN[column][0];
                for (int y = 0; y < HEIGHT; y++) {
                    boolean tmp = SCREEN[column][(y + 1) % HEIGHT];
                    SCREEN[column][(y + 1) % HEIGHT] = next;
                    next = tmp;
                }
            }
        }
    }
}
