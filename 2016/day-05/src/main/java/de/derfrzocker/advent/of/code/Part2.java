package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String input = lines.getFirst();

        MessageDigest digest = MessageDigest.getInstance("MD5");

        StringBuilder sb = new StringBuilder("        ");
        Set<Integer> filled = new HashSet<>();
        long current = 0;
        dance:
        while (true) {
            byte[] hash = digest.digest((input + current).getBytes());

            if (hash[0] != 0x0) {
                current++;
                continue dance;
            }

            if (hash[1] != 0x0) {
                current++;
                continue dance;
            }

            if (hash[2] >> 4 != 0x0) {
                current++;
                continue dance;
            }

            int position = Byte.toUnsignedInt(hash[2]) & 0b00001111;

            if (position > 7 || filled.contains(position)) {
                current++;
                continue dance;
            }

            String value = Integer.toHexString(Byte.toUnsignedInt(hash[3]) >> 4);

            if (value.length() != 1) {
                throw new RuntimeException();
            }

            sb.setCharAt(position, value.charAt(0));

            filled.add(position);

            if (filled.size() == 8) {
                break dance;
            }

            current++;
        }

        System.out.println(sb);
    }
}
