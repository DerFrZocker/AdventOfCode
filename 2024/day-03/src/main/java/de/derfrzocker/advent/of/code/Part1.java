package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;
        for (String line : lines) {
            dance: for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) != 'm') {
                    continue;
                }
                if (line.charAt(i + 1) != 'u') {
                    continue;
                }
                if (line.charAt(i + 2) != 'l') {
                    continue;
                }
                if (line.charAt(i + 3) != '(') {
                    continue;
                }

                int firstNumber = 0;
                if (line.charAt(i + 4) >= '0' && line.charAt(i + 4) <= '9') {
                    firstNumber = line.charAt(i + 4) - '0';
                } else {
                    continue;
                }

                int offset = 3;
                for (int j = 1; j < 3; j++) {
                    if (line.charAt(i + j + 4) == ',') {
                        offset = j;
                        break;
                    }

                    if (line.charAt(i + j + 4) >= '0' && line.charAt(i + j + 4) <= '9') {
                        firstNumber = firstNumber * 10 + line.charAt(i + j + 4) - '0';
                    } else {
                        continue dance;
                    }
                }

                if (offset == 3) {
                    if (line.charAt(i + 4 + offset) != ',') {
                        continue dance;
                    }
                }

                int secondNumber = 0;
                if (line.charAt(i + 4+ offset + 1) >= '0' && line.charAt(i + 4+ offset + 1) <= '9') {
                    secondNumber = line.charAt(i + 4+ offset + 1) - '0';
                } else {
                    continue;
                }

                int offset2 = 3;
                for (int j = 1; j < 3; j++) {
                    if (line.charAt(i + j + 4+ offset + 1) == ')') {
                        offset2 = j;
                        break;
                    }

                    if (line.charAt(i + j + 4+ offset + 1) >= '0' && line.charAt(i + j + 4+ offset + 1) <= '9') {
                        secondNumber = secondNumber * 10 + line.charAt(i + j + 4 +offset + 1) - '0';
                    } else {
                        continue dance;
                    }
                }

                if (offset2 == 3) {
                    if (line.charAt(i + 4+ offset + offset2 +1) != ')') {
                        continue;
                    }
                }

                result += firstNumber * secondNumber;
            }
        }

        System.out.println(result);
    }
}
