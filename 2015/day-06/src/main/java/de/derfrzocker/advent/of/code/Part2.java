package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int[][] area = new int[WIDTH][HEIGHT];

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            Type type = null;
            if (line.startsWith("turn on ")) {
                type = Type.TURN_ON;
                line = line.substring("turn on ".length());
            } else if (line.startsWith("turn off ")) {
                type = Type.TURN_OFF;
                line = line.substring("turn off ".length());
            } else if (line.startsWith("toggle ")) {
                type = Type.TOGGLE;
                line = line.substring("toggle ".length());
            }

            String[] parts = line.split(" through ");
            String[] from = parts[0].split(",");
            String[] to = parts[1].split(",");

            instructions.add(new Instruction(type, Position.of(from[0], from[1]), Position.of(to[0], to[1])));
        }

        for (Instruction instruction : instructions) {
            for (int x = instruction.from.x(); x <= instruction.to.x(); x++) {
                for (int y = instruction.from.y(); y <= instruction.to.y(); y++) {
                    switch (instruction.type) {
                        case TURN_ON -> {
                            area[y][x]++;
                        }
                        case TURN_OFF -> {
                            area[y][x]--;
                            if (area[y][x] < 0) {
                                area[y][x] = 0;
                            }
                        }
                        case TOGGLE -> {
                            area[y][x] += 2;
                        }
                    }
                }
            }
        }

        int on = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                on += area[y][x];
            }
        }

        System.out.println(on);
    }

    private record Instruction(Type type, Position from, Position to) {

    }

    private enum Type {
        TURN_ON,
        TURN_OFF,
        TOGGLE
    }
}
