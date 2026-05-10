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

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("swap position ")) {
                String[] split = line.split(" ");
                int first = Integer.parseInt(split[2]);
                int second = Integer.parseInt(split[5]);
                instructions.add(new SwapPosition(first, second));
            } else if (line.startsWith("swap letter ")) {
                String[] split = line.split(" ");
                char first = split[2].charAt(0);
                char second = split[5].charAt(0);
                instructions.add(new SwapLetter(first, second));
            } else if (line.startsWith("rotate left ")) {
                String[] split = line.split(" ");
                int amount = Integer.parseInt(split[2]);
                instructions.add(new RotateLeftRight(true, amount));
            } else if (line.startsWith("rotate right ")) {
                String[] split = line.split(" ");
                int amount = Integer.parseInt(split[2]);
                instructions.add(new RotateLeftRight(false, amount));
            } else if (line.startsWith("rotate based ")) {
                String[] split = line.split(" ");
                char letter = split[6].charAt(0);
                instructions.add(new RotateBasedOnPosition(letter));
            } else if (line.startsWith("reverse ")) {
                String[] split = line.split(" ");
                int first = Integer.parseInt(split[2]);
                int second = Integer.parseInt(split[4]);
                instructions.add(new Reverse(first, second));
            } else if (line.startsWith("move ")) {
                String[] split = line.split(" ");
                int first = Integer.parseInt(split[2]);
                int second = Integer.parseInt(split[5]);
                instructions.add(new Move(first, second));
            } else {
                throw new RuntimeException("Unkown");
            }
        }

        StringBuilder builder = new StringBuilder("fbgdceah");
        for (Instruction instruction : instructions.reversed()) {
            instruction.execute(builder);
        }

        System.out.println(builder);
    }

    private interface Instruction {

        void execute(StringBuilder builder);
    }

    private record SwapPosition(int first, int second) implements Instruction {

        @Override
        public void execute(StringBuilder builder) {
            char tmp = builder.charAt(first);
            builder.setCharAt(first, builder.charAt(second));
            builder.setCharAt(second, tmp);
        }
    }

    private record SwapLetter(char first, char second) implements Instruction {

        @Override
        public void execute(StringBuilder builder) {
            for (int i = 0; i < builder.length(); i++) {
                if (builder.charAt(i) == first) {
                    builder.setCharAt(i, second);
                } else if (builder.charAt(i) == second) {
                    builder.setCharAt(i, first);
                }
            }
        }
    }

    private record RotateLeftRight(boolean left, int amount) implements Instruction {

        @Override
        public void execute(StringBuilder builder) {
            if (!left) {
                for (int j = 0; j < amount; j++) {
                    char prev = builder.charAt(0);
                    for (int i = builder.length() - 1; i >= 0; i--) {
                        char tmp = builder.charAt(i);
                        builder.setCharAt(i, prev);
                        prev = tmp;
                    }
                }
            } else {
                for (int j = 0; j < amount; j++) {
                    char prev = builder.charAt(builder.length() - 1);
                    for (int i = 0; i < builder.length(); i++) {
                        char tmp = builder.charAt(i);
                        builder.setCharAt(i, prev);
                        prev = tmp;
                    }
                }
            }
        }
    }

    private record RotateBasedOnPosition(char letter) implements Instruction {

        @Override
        public void execute(StringBuilder builder) {

            int done = 0;
            do {
                char prev = builder.charAt(0);
                for (int i = builder.length() - 1; i >= 0; i--) {
                    char tmp = builder.charAt(i);
                    builder.setCharAt(i, prev);
                    prev = tmp;
                }
                done++;
                int amount = builder.indexOf(String.valueOf(letter));
                if (amount >= 4) {
                    amount++;
                }
                amount++;

                if (done == amount) {
                    break;
                }
            } while (true);
        }
    }

    private record Reverse(int start, int end) implements Instruction {

        @Override
        public void execute(StringBuilder builder) {
            int length = end - start + 1;
            for (int i = 0; i < length / 2; i++) {
                char tmp = builder.charAt(start + i);
                builder.setCharAt(start + i, builder.charAt(end - i));
                builder.setCharAt(end - i, tmp);
            }
        }
    }

    private record Move(int first, int second) implements Instruction {

        @Override
        public void execute(StringBuilder builder) {
            char letter = builder.charAt(second);
            builder.deleteCharAt(second);
            builder.insert(first, letter);
        }
    }
}
