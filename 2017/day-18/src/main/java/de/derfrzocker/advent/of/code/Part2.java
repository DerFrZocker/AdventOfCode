package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static int result = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            String start = line.substring(0, 3);
            Instruction instruction = switch (start) {
                case "snd" -> {
                    String x = line.split(" ")[1];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(x)) {
                        isRegistry = false;
                        value = Integer.parseInt(x);
                    } else {
                        isRegistry = true;
                        registry = x.charAt(0);
                    }

                    yield new SndInstruction(isRegistry, registry, value);
                }
                case "set" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new SetInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "add" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new AddInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "mul" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new MulInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "mod" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        isRegistry = false;
                        value = Integer.parseInt(y);
                    } else {
                        isRegistry = true;
                        registry = y.charAt(0);
                    }

                    yield new ModInstruction(x.charAt(0), isRegistry, registry, value);
                }
                case "rcv" -> {
                    String x = line.split(" ")[1];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(x)) {
                        isRegistry = false;
                        value = Integer.parseInt(x);
                    } else {
                        isRegistry = true;
                        registry = x.charAt(0);
                    }

                    yield new RcvInstruction(isRegistry, registry, value);
                }
                case "jgz" -> {
                    String x = line.split(" ")[1];
                    String y = line.split(" ")[2];
                    boolean isRegistry;
                    char registry = Character.MAX_VALUE;
                    int value = Integer.MAX_VALUE;
                    if (isNumber(x)) {
                        isRegistry = false;
                        value = Integer.parseInt(x);
                    } else {
                        isRegistry = true;
                        registry = x.charAt(0);
                    }

                    boolean offseetIsRegistry;
                    char offsetRegistry = Character.MAX_VALUE;
                    int offsetValue = Integer.MAX_VALUE;
                    if (isNumber(y)) {
                        offseetIsRegistry = false;
                        offsetValue = Integer.parseInt(y);
                    } else {
                        offseetIsRegistry = true;
                        offsetRegistry = y.charAt(0);
                    }

                    yield new JgzInstruction(offseetIsRegistry, offsetRegistry, offsetValue, isRegistry, registry, value);
                }
                default -> throw new IllegalArgumentException("Unknown command: " + line);
            };

            instructions.add(instruction);
        }

        Map<Character, Long> register0 = new HashMap<>();
        register0.put('p', 0L);
        Map<Character, Long> register1 = new HashMap<>();
        register1.put('p', 1L);

        Queue<Long> queue0 = new ArrayDeque<>();
        Queue<Long> queue1 = new ArrayDeque<>();

        int counter0 = 0;
        int counter1 = 0;

        while (true) {
            int diff0 = 0;
            if (counter0 >= 0 && counter0 < instructions.size()) {
                Instruction instruction0 = instructions.get(counter0);
                diff0 = instruction0.execute(register0, queue1, queue0, 0);
                counter0 += diff0;
            }

            int diff1 = 0;
            if (counter1 >= 0 && counter1 < instructions.size()) {
                Instruction instruction1 = instructions.get(counter1);
                diff1 = instruction1.execute(register1, queue0, queue1, 1);
                counter1 += diff1;
            }

            if (diff0 == 0 & diff1 == 0) {
                break;
            }
        }

        System.out.println(result);
    }

    private interface Instruction {

        int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId);
    }

    private record SndInstruction(boolean isRegistry, char registry, int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {
            long v;
            if (isRegistry) {
                v = registryMap.getOrDefault(registry, 0L);
            } else {
                v = value;
            }

            sendQueue.add(v);

            if (programmId == 1) {
                result++;
            }

            return 1;
        }
    }

    private record SetInstruction(char setRegistry, boolean isRegistry, char registry,
                                  int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {
            long v = value;
            if (isRegistry) {
                v = registryMap.getOrDefault(registry, 0L);
            }

            registryMap.put(setRegistry, v);

            return 1;
        }
    }

    private record AddInstruction(char setRegistry, boolean isRegistry, char registry,
                                  int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {
            long v = value;
            if (isRegistry) {
                v = registryMap.getOrDefault(registry, 0L);
            }

            v += registryMap.getOrDefault(setRegistry, 0L);
            registryMap.put(setRegistry, v);

            return 1;
        }
    }

    private record MulInstruction(char setRegistry, boolean isRegistry, char registry,
                                  int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {
            long v = value;
            if (isRegistry) {
                v = registryMap.getOrDefault(registry, 0L);
            }

            v = Math.multiplyExact(registryMap.getOrDefault(setRegistry, 0L), v);
            registryMap.put(setRegistry, v);

            return 1;
        }
    }

    private record ModInstruction(char setRegistry, boolean isRegistry, char registry,
                                  int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {
            long v = value;
            if (isRegistry) {
                v = registryMap.getOrDefault(registry, 0L);
            }

            v = registryMap.getOrDefault(setRegistry, 0L) % v;
            registryMap.put(setRegistry, v);

            return 1;
        }
    }

    private record RcvInstruction(boolean isRegistry, char registry, int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {

            if (recievQueue.isEmpty()) {
                return 0;
            }

            long v = recievQueue.poll();
            if (isRegistry) {
                registryMap.put(registry, v);
            } else {
                throw new RuntimeException();
            }

            return 1;
        }
    }

    private record JgzInstruction(boolean isOffestRegistry, char offsetRegistry, int offsetValue, boolean isRegistry,
                                  char registry, int value) implements Instruction {

        @Override
        public int execute(Map<Character, Long> registryMap, Queue<Long> sendQueue, Queue<Long> recievQueue, int programmId) {
            long v = value;
            if (isRegistry) {
                v = registryMap.getOrDefault(registry, 0L);
            }

            if (v > 0) {
                if (isOffestRegistry) {
                    return Math.toIntExact(registryMap.getOrDefault(offsetRegistry, 0L));
                }

                return offsetValue;
            }

            return 1;
        }
    }

    private static boolean isNumber(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
