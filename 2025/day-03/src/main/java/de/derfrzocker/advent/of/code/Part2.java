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

        long result = 0;
        for (String value : lines) {
            int pos = -1;
            String total = "";
            for (int c = 0; c < 12; c++) {
                char first = '0';
                for (int i = pos + 1; i < (value.length() - (11 - c)); i++) {
                    if (first < value.charAt(i)) {
                        first = value.charAt(i);
                        pos = i;
                    }
                }
                total = total + String.valueOf(first);
            }

            result = Math.addExact(result, Long.parseLong(total));
        }

        System.out.println(result);
    }
}
