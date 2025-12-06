package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        long amount = Stream.of(lines.getFirst().split(" ")).filter(s -> !s.isBlank()).count();

        List<Problem> numbers = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            numbers.add(new Problem(new ArrayList<>()));
        }
        for (int i = 0; i < (lines.size() - 1); i++) {
            String[] parts = Stream.of(lines.get(i).split(" ")).filter(s -> !s.isBlank()).toArray(String[]::new);
            if (parts.length != amount) {
                throw new IllegalArgumentException("Wrong amount of lines");
            }

            for (int j = 0; j < parts.length; j++) {
                long number = Long.parseLong(parts[j]);
                numbers.get(j).number.add(number);
            }
        }

        long result = 0;
        String[] parts = Stream.of(lines.getLast().split(" ")).filter(s -> !s.isBlank()).toArray(String[]::new);
        for (int i = 0; i < parts.length; i++) {
            String operator = parts[i].trim();
            if (operator.length() != 1) {
                throw new IllegalArgumentException("Wrong operator");
            }

            Problem problem = numbers.get(i);
            if (operator.equals("+")) {
                long tmp = 0;
                for (long number : problem.number) {
                    tmp = Math.addExact(tmp, number);
                }

                result = Math.addExact(result, tmp);
            } else if (operator.equals("*")) {
                long tmp = 1;
                for (long number : problem.number) {
                    tmp = Math.multiplyExact(tmp, number);
                }

                result = Math.addExact(result, tmp);
            } else {
                throw new IllegalArgumentException("Wrong operator");
            }
        }

        System.out.println(result);
    }

    public record Problem(List<Long> number) {

    }
}
