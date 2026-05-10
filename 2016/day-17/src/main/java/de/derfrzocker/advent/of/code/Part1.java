package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int GOAL_X = 4;
    private static final int GOAL_Y = 4;
    private static final Map<String, Map<Direction, Boolean>> CACHE = new HashMap<>();
    private static String INPUT = null;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        INPUT = lines.getFirst();

        Set<State> visited = new HashSet<>();
        Set<State> states = new HashSet<>();

        states.add(new State(1, 1, ""));

        while (true) {
            Set<State> newStates = new HashSet<>();
            for (State state : states) {
                for (Direction direction : Direction.values()) {
                    int newX = state.x + direction.getXOffset();
                    int newY = state.y + direction.getYOffset();

                    if (newX < 1 || newX > 4 || newY < 1 || newY > 4) {
                        continue;
                    }

                    if (!isOpen(direction, state.path)) {
                        continue;
                    }

                    if (newX == GOAL_X && newY == GOAL_Y) {
                        System.out.println(state.path + direction.getSymbol());
                        return;
                    }

                    State newState = new State(newX, newY, state.path + direction.getSymbol());
                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        newStates.add(newState);
                    }
                }
            }

            states = newStates;
        }
    }

    private static boolean isOpen(Direction direction, String path) {
        return CACHE.computeIfAbsent(path, Part1::compute).get(direction);
    }

    private static Map<Direction, Boolean> compute(String path) {
        byte[] hash = DIGEST.digest((INPUT + path).getBytes());

        Map<Direction, Boolean> result = new HashMap<>();

        result.put(Direction.UP, (Byte.toUnsignedInt(hash[0]) >> 4) >= 0xb);
        result.put(Direction.DOWN, (Byte.toUnsignedInt(hash[0]) & 0xf) >= 0xb);
        result.put(Direction.LEFT, (Byte.toUnsignedInt(hash[1]) >> 4) >= 0xb);
        result.put(Direction.RIGHT, (Byte.toUnsignedInt(hash[1]) & 0xf) >= 0xb);

        return result;
    }

    private record State(int x, int y, String path) {

    }
}
