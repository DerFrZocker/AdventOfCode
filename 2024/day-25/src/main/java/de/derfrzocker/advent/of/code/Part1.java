package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final List<Key> keys = new ArrayList<>();
    private static final List<Lock> looks = new ArrayList<>();

    private static final int MAX_SIZE = 5;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int[] current = null;
        boolean isKey = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                if (current == null) {
                    throw new RuntimeException("Error");
                }
                if (isKey) {
                    for (int j = 0; j < current.length; j++) {
                        current[j]--;
                    }
                    keys.add(new Key(current));
                } else {
                    looks.add(new Lock(current));
                }
                current = null;
                continue;
            }

            if (current == null) {
                current = new int[line.length()];
                if (line.charAt(0) == '.') {
                    isKey = true;
                } else {
                    isKey = false;
                }
                continue;
            }

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == '#') {
                    current[j]++;
                }
            }
        }

        if (current != null) {
            if (isKey) {
                for (int j = 0; j < current.length; j++) {
                    current[j]--;
                }
                keys.add(new Key(current));
            } else {
                looks.add(new Lock(current));
            }
            current = null;
        }

        int result = 0;
        for (Lock lock : looks) {
            dance:
            for (Key key : keys) {
                for (int i = 0; i < lock.values().length; i++) {
                    if (lock.values()[i] + key.values()[i] > MAX_SIZE) {
                        continue dance;
                    }
                }
                result++;
            }
        }

        System.out.println("Result: " + result);
    }

    private record Key(int[] values) {

    }

    private record Lock(int[] values) {

    }
}
