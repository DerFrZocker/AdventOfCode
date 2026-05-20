package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String value : lines.getFirst().split(",")) {
            if (value.startsWith("s")) {
                instructions.add(new SpinInstruction(Integer.parseInt(value.substring(1))));
            } else if (value.startsWith("x")) {
                int first = Integer.parseInt(value.substring(1).split("/")[0]);
                int second = Integer.parseInt(value.substring(1).split("/")[1]);
                instructions.add(new ExchangeInstruction(first, second));
            } else if (value.startsWith("p")) {
                char first = value.substring(1).split("/")[0].charAt(0);
                char second = value.substring(1).split("/")[1].charAt(0);
                instructions.add(new PartnerInstruction(first, second));
            } else {
                throw new RuntimeException();
            }
        }

        LinkedList<Character> input = new LinkedList<>();
        for (int i = 0; i < 16; i++) {
            input.add((char) ('a' + i));
        }

        instructions.forEach(instruction -> instruction.execute(input));

        System.out.println(input.stream().map(String::valueOf).reduce(String::concat).get());
    }

    private interface Instruction {

        void execute(LinkedList<Character> chars);
    }

    private record SpinInstruction(int amount) implements Instruction {

        @Override
        public void execute(LinkedList<Character> chars) {
            for (int i = 0; i < amount; i++) {
                chars.addFirst(chars.removeLast());
            }
        }
    }

    private record ExchangeInstruction(int first, int second) implements Instruction {

        @Override
        public void execute(LinkedList<Character> chars) {
            char tmp = chars.get(first);
            chars.set(first, chars.get(second));
            chars.set(second, tmp);
        }
    }

    private record PartnerInstruction(char first, char second) implements Instruction {

        @Override
        public void execute(LinkedList<Character> chars) {
            int firstIndex = chars.indexOf(first);
            int secondIndex = chars.indexOf(second);
            char tmp = chars.get(firstIndex);
            chars.set(firstIndex, chars.get(secondIndex));
            chars.set(secondIndex, tmp);
        }
    }
}
