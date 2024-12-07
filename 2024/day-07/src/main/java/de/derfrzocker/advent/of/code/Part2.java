package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Part2 {

    private static final char AIR = '.';
    private static final char STUFF = '#';

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        test();
        if (false) {
            return;
        }

        BigInteger sumOfAll = BigInteger.valueOf(0);
        dance:

        for (BigInteger part : lines.stream().parallel().map(line -> {
            System.out.println("(%d | %d)".formatted(counter.incrementAndGet(), lines.size()));
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

            long total = (long) Math.pow(2, values.length - 1);
            for (long c = 0; c < (total); c++) {
                for (long b = 0; b < (total); b++) {
                    tmp = BigInteger.valueOf(values[0]);
//                    System.out.print(target + ": " + values[0]);
                    for (int i = 1; i < values.length; i++) {
                        if (!isMul(c, i, values.length)) {
                            long otherThmp = values[i];
                            long tT = String.valueOf(otherThmp).length();
                            for (long t = 0; t < tT; t++) {
                                tmp = tmp.multiply(BigInteger.TEN);
                            }
                            tmp = tmp.add(BigInteger.valueOf(otherThmp));
                        } else if (isMul(b, i, values.length)) {
                            tmp = tmp.multiply(BigInteger.valueOf(values[i]));
//                            System.out.print(" * " + values[i]);
                        } else {
                            tmp = tmp.add(BigInteger.valueOf(values[i]));
//                            System.out.print(" + " + values[i]);
                        }
                    }
//                    System.out.print(" Is " + tmp);
//                    System.out.println();
                    if (tmp.equals(target)) {
//                        sumOfAll = sumOfAll.add(target);
//                        continue dance;
                        return target;
                    }


                }
                if (total == (c + 1)) {

                    System.out.println("Not Possible: " + target);
                }
            }

            return null;
        }).filter(value -> value != null).toList()) {
            sumOfAll = sumOfAll.add(part);
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
