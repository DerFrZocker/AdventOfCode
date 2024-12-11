package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    private static final int AMOUNT = 75;
    private static final int CACHE_SIZE = 1_000_000;
    private static final long[][] CACHE = new long[AMOUNT][CACHE_SIZE];

    private final long[] input = new long[2];

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> stones = new ArrayList<>();
        String[] split = lines.get(0).split(" ");
        for (int i = 0; i < split.length; i++) {
            stones.add(Integer.parseInt(split[i]));
        }

        for (int i = 0; i < CACHE.length; i++) {
            long[] cache = CACHE[i];
            for (int j = 0; j < CACHE_SIZE; j++) {
                cache[j] = -1;
            }
        }

        long start = System.currentTimeMillis();
        BigInteger result = BigInteger.ZERO;
        for (BigInteger value : stones
                .stream()
                .map(BigInteger::valueOf)
                .peek(d -> System.out.println("Calc: " + d))
                .map(d -> {
                    Part2 m = new Part2();
                    long f = m.better(d.longValue(), 0, 1);
                    return BigInteger.valueOf(f);
                })
                .peek(d -> System.out.println("Got: " + d))
                .toList()) {
            result = result.add(value);
        }
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println("Duration: " + (end - start));
    }

    private long better(long value, int amount, long adding) {
        for (int c = amount; c < AMOUNT; c++) {
            if (value < CACHE_SIZE) {
                long cache = CACHE[c][(int) value];
                if (cache != -1) {
                    return cache;
                }
            }

            if (value == 0) {
                value = 1;
                continue;
            }

            if (amount(value)) {
                long first = input[0];
                long second = input[1];
                if (first == second) {
                    long tmp = better(first, c + 1, adding * 2);
                    if (value < CACHE_SIZE) {
                        CACHE[c][(int) value] = Math.multiplyExact(tmp, 2);
                    }
                    return Math.multiplyExact(tmp, 2);
                } else {
                    long tmp = better(first, c + 1, adding);
                    long tmp2 = better(second, c + 1, adding);
                    if (value < CACHE_SIZE) {
                        CACHE[c][(int) value] = Math.addExact(tmp, tmp2);
                    }
                    return Math.addExact(tmp, tmp2);
                }
            }

            value = Math.multiplyExact(value, 2024);
        }

        return 1;
    }

    public boolean amount(long n) {
        // 18
        if (n < 100000) { // 1 to 5
            if (n < 100) { // 1 or 2
                if (n < 10) {
                    return false;
                }
                split(n, 10);
                return true;
            } else { // 3, 4 or 5
                if (n < 1000) {
                    return false;
                }
                if (n < 10000) {
                    split(n, 100);
                    return true;
                }
                return false;
            }
        } else { // 6 to 7
            if (n < 10000000) { // 6 or 7
                if (n < 1000000) {
                    split(n, 1000);
                    return true;
                }
                return false;
            } else { // 8, 9 or 10
                if (n < 100000000) {
                    split(n, 10000);
                    return true;
                }
                if (n < 1000000000) {
                    return false;
                }
                if (n < 10000000000L) {
                    split(n, 100000);
                    return true;
                }
                if (n < 100000000000L) {
                    return false;
                }
                if (n < 1000000000000L) {
                    split(n, 1000000);
                    return true;
                }
                if (n < 10000000000000L) {
                    return false;
                }
                if (n < 100000000000000L) {
                    split(n, 10000000);
                    return true;
                }
                if (n < 1000000000000000L) {
                    return false;
                }

                throw new RuntimeException("Too big " + n);
            }
        }
    }

    public void split(long n, int length) {
        input[0] = n / length;
        input[1] = n - ((n / length) * length);
    }
}
