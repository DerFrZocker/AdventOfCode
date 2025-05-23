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

        int floor = 0;
        for (char c : lines.get(0).toCharArray()) {
            if (c == '(') {
                floor++;
            } else if (c == ')') {
                floor--;
            } else {
                System.out.println("WTF? " + c);
            }
        }

        System.out.println(floor);
    }
}
