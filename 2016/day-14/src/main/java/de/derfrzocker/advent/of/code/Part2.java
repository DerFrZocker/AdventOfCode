package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int TARGET = 64;
    private static final Map<Integer, String> CACHE = new HashMap<>();
    private static final MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String salt = lines.getFirst();

        int current = 0;
        int index = 0;
        dance:
        while (true) {
            String value = get(salt, index);
            for (int i = 0; i < value.length() - 2; i++) {
                char first = value.charAt(i);
                char second = value.charAt(i + 1);
                if (first != second) {
                    continue;
                }
                char third = value.charAt(i + 2);
                if (first != third) {
                    continue;
                }

                for (int j = 1; j <= 1000; j++) {
                    String other = get(salt, index + j);
                    for (int t = 0; t < other.length() - 4; t++) {
                        char oFirst = other.charAt(t);
                        if (first != oFirst) {
                            continue;
                        }
                        char oSecond = other.charAt(t + 1);
                        if (first != oSecond) {
                            continue;
                        }
                        char oThird = other.charAt(t + 2);
                        if (first != oThird) {
                            continue;
                        }
                        char oFourth = other.charAt(t + 3);
                        if (first != oFourth) {
                            continue;
                        }
                        char oFifth = other.charAt(t + 4);
                        if (first != oFifth) {
                            continue;
                        }

                        current++;
                        if (current == TARGET) {
                            System.out.println(index);
                            return;
                        }

                        index++;
                        continue dance;
                    }
                }

                index++;
                continue dance;
            }

            index++;
        }
    }

    private static String get(String salt, int index) {
        return CACHE.computeIfAbsent(index, i -> compute(salt, i));
    }

    private static String compute(String salt, int index) {
        String first = compute2(salt + index);
        for (int i = 0; i < 2016; i++) {
            first = compute2(first);
        }

        return first;
    }

    private static String compute2(String input) {
        byte[] bytes = DIGEST.digest((input).getBytes());

        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
