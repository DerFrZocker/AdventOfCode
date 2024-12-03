package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int result = 0;
        boolean enabled = true;
        for (String line : lines) {
            dance:
            for (int i = 0; i < line.length(); i++) {

                if (isDonT(i, line)) {
                    enabled = false;
                    continue dance;
                }

                if (isDo(i, line)) {
                    enabled = true;
                    continue dance;
                }

                if (!enabled) {
                    continue dance;
                }

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
                if (line.charAt(i + 4 + offset + 1) >= '0' && line.charAt(i + 4 + offset + 1) <= '9') {
                    secondNumber = line.charAt(i + 4 + offset + 1) - '0';
                } else {
                    continue;
                }

                int offset2 = 3;
                for (int j = 1; j < 3; j++) {
                    if (line.charAt(i + j + 4 + offset + 1) == ')') {
                        offset2 = j;
                        break;
                    }

                    if (line.charAt(i + j + 4 + offset + 1) >= '0' && line.charAt(i + j + 4 + offset + 1) <= '9') {
                        secondNumber = secondNumber * 10 + line.charAt(i + j + 4 + offset + 1) - '0';
                    } else {
                        continue dance;
                    }
                }

                if (offset2 == 3) {
                    if (line.charAt(i + 4 + offset + offset2 + 1) != ')') {
                        continue;
                    }
                }

                result += firstNumber * secondNumber;
            }
        }

        System.out.println(result);
    }

    private static boolean isDo(int i, String line) {
        if (line.charAt(i) != 'd') {
            return false;
        }

        if (line.charAt(i + 1) != 'o') {
            return false;
        }

        if (line.charAt(i + 2) != '(') {
            return false;
        }

        if (line.charAt(i + 3) != ')') {
            return false;
        }

        return true;
    }

    private static boolean isDonT(int i, String line) {
        if (line.charAt(i) != 'd') {
            return false;
        }

        if (line.charAt(i + 1) != 'o') {
            return false;
        }

        if (line.charAt(i + 2) != 'n') {
            return false;
        }

        if (line.charAt(i + 3) != '\'') {
            return false;
        }

        if (line.charAt(i + 4) != 't') {
            return false;
        }

        if (line.charAt(i + 5) != '(') {
            return false;
        }

        if (line.charAt(i + 6) != ')') {
            return false;
        }

        return true;
    }
}
