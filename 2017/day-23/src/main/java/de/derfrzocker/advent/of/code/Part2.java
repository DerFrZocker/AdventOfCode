package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;

/**
 * This solution is specific to one input and does not work for every input.
 * The input was manually translated into code, followed by simplifying the code,
 * until it was possible to understand what the code does.
 * Then it was optimized so that it runs faster.
 */
public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static long multiplyCalled = 0;

    public static void main(String[] args) throws IOException {
        long a = 1;
        long b = 0;
        long c = 0;
        long d = 0;
        long e = 0;
        long f = 0;
        long g = 0;
        long h = 0;

        b = 84; // 1
        c = b; // 2
        if (a != 0) { // 3
            b = Math.multiplyExact(b, 100); // 5
            multiplyCalled++;
            b = b + 100000; // 6
            c = b; // 7
            c = c + 17000; // 8
        }

        // Programm counts how often a value can be created by multiplying two whole numbers
        do {
            boolean found = false; // 9
            d = 2; // 10
            check:
            do {
                e = 2; // 11
                /*
                do {
//                    g = d; // 12
//                    g = d * e; // 13
                    multiplyCalled++;
//                    g = g - b; // 14
                    if (d * e == b) { // 15
                        found = true; // 16
                    }

                    e++; // 17
//                    g = e; // 18
//                    g = g - b; // 19
                } while (e < b); // 20
                 */

                if (b % d == 0) {
                    found = true;
                    break check;
                }

                d++; // 21
//                g = d; // 22
//                g = g - b; // 23
            } while (d < b); // 24

            if (found) { // 25
                h++; // 26
            }

//            g = b; // 27
//            g = g - c; // 28

            if (c == b) { // 29 + 30
                System.out.println(h);
                System.out.println(multiplyCalled);
                return;
            }

            b = b + 17; // 31
        } while (true); // 32
    }
}
