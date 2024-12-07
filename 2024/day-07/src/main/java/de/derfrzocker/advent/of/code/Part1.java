package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.List;

public class Part1 {

    private static final char AIR = '.';
    private static final char STUFF = '#';

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        test();
        if (false) {
            return;
        }

        BigInteger sumOfAll = BigInteger.valueOf(0);
        dance:
        for (String line : lines) {
            String[] result = line.split(": ");

            BigInteger target = BigInteger.valueOf(Long.parseLong(result[0]));
            String[] rest = result[1].split(" ");
            long[] values = new long[rest.length];

            for (int i = 0; i < rest.length; i++) {
                values[i] = Long.parseLong(rest[i]);
            }

            BigInteger tmp = BigInteger.valueOf(values[0]);
//            for (int i = 1; i < values.length; i++) {
//                tmp = tmp.multiply(BigInteger.valueOf(values[i]));
//            }
//
//            if (tmp.compareTo(target) < 0) {
//                continue dance;
//            }
//
//            if (tmp.equals(target)) {
//                sumOfAll = sumOfAll.add(target);
//                continue dance;
//            }

            long total = (long) Math.pow(2, values.length + 1);
            for (long b = 0; b < (total); b++) {
                tmp = BigInteger.valueOf(values[0]);
                System.out.print(target + ": " + values[0]);
                for (int i = 1; i < values.length; i++) {
                    if (isMul(b, i, values.length)) {
                        tmp = tmp.multiply(BigInteger.valueOf(values[i]));
                        System.out.print(" * " + values[i]);
                    } else {
                        tmp = tmp.add(BigInteger.valueOf(values[i]));
                        System.out.print(" + " + values[i]);
                    }
                }
                System.out.print(" Is " + tmp);
                System.out.println();
                if (tmp.equals(target)) {
                    sumOfAll = sumOfAll.add(target);
                    continue dance;
                }

                if (total == (b + 1)) {

                    System.out.println("Not Possible: " + target);
                }
            }
        }

        System.out.println(sumOfAll);
    }

    private static void test() {
        System.out.println(isMul(0b0001, 1, 10));
        System.out.println(isMul(0b1001, 1, 10));
        System.out.println(isMul(0b1001, 2, 10));
        System.out.println(isMul(0b1001, 3, 10));
        System.out.println(isMul(0b1001, 4, 10));
        System.out.println(0b0001);
        System.out.println(0b1001);
        System.out.println(Math.pow(2, 15));
    }

    private static boolean isMul(long b, int i, int length) {
        return ((b >> (i - 1)) & 0b1) == 1;
    }
}
