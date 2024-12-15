package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int floor = 0;
        int baseIndex = 0;
        char[] code = lines.get(0).toCharArray();
        for (int i = 0; i < code.length; i++) {
            char c = code[i];
            if (c == '(') {
                floor++;
            } else if (c == ')') {
                floor--;
            } else {
                System.out.println("WTF? " + c);
            }

            if (floor < 0) {
                baseIndex = i;
                break;
            }
        }

        System.out.println(baseIndex + 1);
    }
}
