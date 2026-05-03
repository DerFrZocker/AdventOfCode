package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        String input = lines.getFirst();

        MessageDigest digest = MessageDigest.getInstance("MD5");

        StringBuilder sb = new StringBuilder();
        int length = 0;
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

            sb.append(Integer.toHexString(hash[2] & 0b00001111));

            length++;
            if (length == 8) {
                break;
            }

            current++;
        }

        System.out.println(sb);
    }
}
