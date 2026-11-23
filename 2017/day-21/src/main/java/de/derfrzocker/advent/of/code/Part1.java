package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Map<String, boolean[][]> twoRules = new HashMap<>();
        Map<String, boolean[][]> threeRules = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(" => ");

            String input = split[0];
            String[] output = split[1].split("/");

            if (input.length() == 5) {
                // 2x2
                boolean[][] outputArray = new boolean[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        outputArray[i][j] = output[i].charAt(j) == '#';
                    }
                }

                Set<String> inputs = new HashSet<>();
                for (int i = 0; i < 4; i++) {
                    input = rotateTwo(input);
                    inputs.add(input);
                    inputs.add(flipVerticalTwo(input));
                    inputs.add(flipHorizontalTwo(input));
                }

                for (String result : inputs) {
                    if (twoRules.containsKey(result)) {
                        throw new IllegalArgumentException("Duplicate rule: " + result);
                    }

                    twoRules.put(result, outputArray);
                }
            } else if (input.length() == 11) {
                // 3x3

                boolean[][] outputArray = new boolean[4][4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        outputArray[i][j] = output[i].charAt(j) == '#';
                    }
                }

                Set<String> inputs = new HashSet<>();
                for (int i = 0; i < 4; i++) {
                    input = rotateThree(input);
                    inputs.add(input);
                    inputs.add(flipVerticalThree(input));
                    inputs.add(flipHorizontalThree(input));
                }

                for (String result : inputs) {
                    if (threeRules.containsKey(result)) {
                        throw new IllegalArgumentException("Duplicate rule: " + result);
                    }

                    threeRules.put(result, outputArray);
                }
            } else {
                throw new IllegalArgumentException("Invalid input length: " + input.length());
            }
        }

        boolean[][] array = new boolean[3][3];
        array[0][1] = true;
        array[1][2] = true;
        array[2][0] = true;
        array[2][1] = true;
        array[2][2] = true;

        for (int i = 0; i < 5; i++) {
            if (array.length % 2 == 0) {
                int j = array.length / 2;
                boolean[][] outputArray = new boolean[j * 3][j * 3];

                for (int k = 0; k < j; k++) {
                    for (int l = 0; l < j; l++) {
                        StringBuilder builder = new StringBuilder();
                        for (int x = 0; x < 2; x++) {
                            for (int y = 0; y < 2; y++) {
                                boolean value = array[k * 2 + x][l * 2 + y];
                                builder.append(value ? '#' : '.');
                            }
                            if (x != 1) {
                                builder.append('/');
                            }
                        }

                        boolean[][] replacement = twoRules.get(builder.toString());
                        if (replacement == null) {
                            throw new IllegalArgumentException("No replacement found for: " + builder.toString());
                        }

                        for (int x = 0; x < replacement.length; x++) {
                            for (int y = 0; y < replacement[0].length; y++) {
                                outputArray[k * 3 + x][l * 3 + y] = replacement[x][y];
                            }
                        }
                    }
                }

                array = outputArray;
            } else if (array.length % 3 == 0) {
                int j = array.length / 3;
                boolean[][] outputArray = new boolean[j * 4][j * 4];

                for (int k = 0; k < j; k++) {
                    for (int l = 0; l < j; l++) {
                        StringBuilder builder = new StringBuilder();
                        for (int x = 0; x < 3; x++) {
                            for (int y = 0; y < 3; y++) {
                                boolean value = array[k * 3 + x][l * 3 + y];
                                builder.append(value ? '#' : '.');
                            }
                            if (x != 2) {
                                builder.append('/');
                            }
                        }

                        boolean[][] replacement = threeRules.get(builder.toString());
                        if (replacement == null) {
                            throw new IllegalArgumentException("No replacement found for: " + builder.toString());
                        }

                        for (int x = 0; x < replacement.length; x++) {
                            for (int y = 0; y < replacement[0].length; y++) {
                                outputArray[k * 4 + x][l * 4 + y] = replacement[x][y];
                            }
                        }
                    }
                }

                array = outputArray;
            } else {
                throw new IllegalArgumentException("Invalid input length: " + array.length);
            }
        }

        int on = 0;
        for (boolean[] row : array) {
            for (boolean value : row) {
                if (value) {
                    on++;
                }
            }
        }

        System.out.println(on);
    }

    private static String rotateTwo(String input) {
        StringBuilder builder = new StringBuilder();
        builder.append(input.charAt(3));
        builder.append(input.charAt(0));
        builder.append("/");
        builder.append(input.charAt(4));
        builder.append(input.charAt(1));
        return builder.toString();
    }

    private static String flipVerticalTwo(String input) {
        StringBuilder builder = new StringBuilder();
        builder.append(input.charAt(1));
        builder.append(input.charAt(0));
        builder.append("/");
        builder.append(input.charAt(4));
        builder.append(input.charAt(3));
        return builder.toString();
    }

    private static String flipHorizontalTwo(String input) {
        StringBuilder builder = new StringBuilder();
        builder.append(input.charAt(3));
        builder.append(input.charAt(4));
        builder.append("/");
        builder.append(input.charAt(0));
        builder.append(input.charAt(1));
        return builder.toString();
    }

    private static String rotateThree(String input) {
        StringBuilder builder = new StringBuilder();
        
        /*
        0 1 2
        4 5 6
        8 9 10
         */

        builder.append(input.charAt(8));
        builder.append(input.charAt(4));
        builder.append(input.charAt(0));
        builder.append("/");
        builder.append(input.charAt(9));
        builder.append(input.charAt(5));
        builder.append(input.charAt(1));
        builder.append("/");
        builder.append(input.charAt(10));
        builder.append(input.charAt(6));
        builder.append(input.charAt(2));

        return builder.toString();
    }

    private static String flipVerticalThree(String input) {
        StringBuilder builder = new StringBuilder();
        
        /*
        0 1 2
        4 5 6
        8 9 10
         */

        builder.append(input.charAt(2));
        builder.append(input.charAt(1));
        builder.append(input.charAt(0));
        builder.append("/");
        builder.append(input.charAt(6));
        builder.append(input.charAt(5));
        builder.append(input.charAt(4));
        builder.append("/");
        builder.append(input.charAt(10));
        builder.append(input.charAt(9));
        builder.append(input.charAt(8));

        return builder.toString();
    }

    private static String flipHorizontalThree(String input) {
        StringBuilder builder = new StringBuilder();
        /*
        0 1 2
        4 5 6
        8 9 10
         */

        builder.append(input.charAt(8));
        builder.append(input.charAt(9));
        builder.append(input.charAt(10));
        builder.append("/");
        builder.append(input.charAt(4));
        builder.append(input.charAt(5));
        builder.append(input.charAt(6));
        builder.append("/");
        builder.append(input.charAt(0));
        builder.append(input.charAt(1));
        builder.append(input.charAt(2));

        return builder.toString();
    }

}
