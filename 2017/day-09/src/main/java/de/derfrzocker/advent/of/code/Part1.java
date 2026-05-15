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

        String line = lines.getFirst();
        int result = 0;
        int group = 0;
        boolean trash = false;
        for (int i = 0; i < line.length(); i++) {
            char c =  line.charAt(i);
            if (c == '!') {
                i++;
                continue;
            }
            if (trash && c == '>') {
                trash = false;
                continue;
            }
            if (trash) {
                continue;
            }
            if (c == '<') {
                trash = true;
                continue;
            }
            if (c == '{') {
                group++;
                continue;
            }
            if (c == '}') {
                result += group;
                group--;
                continue;
            }
        }

        System.out.println(result);
    }
}
