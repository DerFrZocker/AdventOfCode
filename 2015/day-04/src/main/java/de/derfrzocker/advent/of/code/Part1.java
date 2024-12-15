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

        String input = lines.get(0);

        MessageDigest digest = MessageDigest.getInstance("MD5");

        long index = 0;
        dance:
        while (true) {
            byte[] hash = digest.digest((input + index).getBytes());

            for (int i = 0; i < 2; i++) {
                if (hash[i] != 0) {
                    index++;
                    continue dance;
                }
            }

            if (hash[2] >> 4 == 0) {
                break;
            }

            index++;
        }

        System.out.println(index);
    }
}
