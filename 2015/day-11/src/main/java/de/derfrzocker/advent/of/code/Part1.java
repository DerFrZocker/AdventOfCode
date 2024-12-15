package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        char[] password = lines.get(0).toCharArray();

        while (!isValid(password)) {
            increment(password, 7);
        }

        System.out.println(new String(password));
    }

    public static boolean isValid(char[] password) {
        int pairs = 0;
        int last = 0;
        boolean straight = false;
        for (int i = 0; i < password.length; i++) {
            char c = password[i];
            if (c == 'i' || c == 'o' || c == 'l') {
                return false;
            }

            if (i < password.length - 1 && pairs < 2 && last != i) {
                char next = password[i + 1];
                if (next == c) {
                    pairs++;
                    last = i + 1;
                    continue;
                }
            }

            if (i < password.length - 2 && !straight) {
                char c2 = password[i + 1];
                char c3 = password[i + 2];

                if (c == (c2 - 1) && c == (c3 - 2)) {
                    straight = true;
                }
            }
        }

        if (pairs >= 2 && straight) {
            return true;
        }

        return false;
    }

    public static void increment(char[] password, int index) {
        char c = password[index];
        c++;

        if (c > 'z') {
            increment(password, index - 1);
            password[index] = 'a';
        } else {
            password[index] = c;
        }
    }
}
