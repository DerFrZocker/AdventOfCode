package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final Map<String, Integer> REGISTRIES = new HashMap<>();
    private static int max;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            String registry = split[0];
            int value = Integer.parseInt(split[2]);
            if ("dec".equals(split[1])) {
                value = -value;
            }

            String ifRegistry = split[4];
            int ifValue = Integer.parseInt(split[6]);
            Type type = switch (split[5]) {
                case "<" -> Type.SMALLER;
                case "<=" -> Type.SMALLER_EQUALS;
                case "==" -> Type.EQUALS;
                case ">" -> Type.BIGGER;
                case ">=" -> Type.BIGGER_EQUALS;
                case "!=" -> Type.NOT_EQUALS;
                default -> throw new IllegalStateException("Unexpected value: " + split[5]);
            };

            instructions.add(new Instruction(registry, value, type, ifRegistry, ifValue));
        }

        instructions.forEach(Instruction::execute);

        System.out.println(max);
    }

    private record Instruction(String registry, int value, Type type, String ifRegistry, int ifValue) {

        void execute() {
            int ifRegistryValue = REGISTRIES.getOrDefault(ifRegistry, 0);
            boolean shouldRun = switch (type) {
                case SMALLER -> ifRegistryValue < ifValue;
                case SMALLER_EQUALS -> ifRegistryValue <= ifValue;
                case EQUALS -> ifRegistryValue == ifValue;
                case BIGGER -> ifRegistryValue > ifValue;
                case BIGGER_EQUALS -> ifRegistryValue >= ifValue;
                case NOT_EQUALS -> ifRegistryValue != ifValue;
            };

            if (shouldRun) {
                REGISTRIES.compute(registry, (key, v) -> v == null ? value : v + value);
                max = Math.max(max, REGISTRIES.get(registry));
            }
        }
    }

    private enum Type {
        SMALLER,
        SMALLER_EQUALS,
        EQUALS,
        BIGGER,
        BIGGER_EQUALS,
        NOT_EQUALS
    }
}
