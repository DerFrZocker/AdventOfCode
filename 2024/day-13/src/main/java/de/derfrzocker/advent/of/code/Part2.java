package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    private static final long ADD = 10000000000000L;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i = i + 4) {
            String aL = lines.get(i);
            String bL = lines.get(i + 1);
            String prizeL = lines.get(i + 2);

            Postion a = new Postion(Long.parseLong(aL.substring(aL.indexOf('X') + 1, aL.lastIndexOf(','))), Long.parseLong(aL.substring(aL.lastIndexOf(' ') + 2)));
            Postion b = new Postion(Long.parseLong(bL.substring(bL.indexOf('X') + 1, bL.lastIndexOf(','))), Long.parseLong(bL.substring(bL.lastIndexOf(' ') + 2)));
            Postion prize = new Postion(Long.parseLong(prizeL.substring(prizeL.indexOf('=') + 1, prizeL.lastIndexOf(','))) + ADD, Long.parseLong(prizeL.substring(prizeL.lastIndexOf('=') + 1)) + ADD);

            if (a.x <= 0) {
                System.out.println("WARN");
            }

            if (a.x <= 0) {
                System.out.println("WARN");
            }

            if (b.x <= 0) {
                System.out.println("WARN");
            }

            if (b.x <= 0) {
                System.out.println("WARN");
            }

            machines.add(new Machine(a, b, prize));
        }

        System.out.println(machines);

        long result = 0;
        int index = 0;
        dance:
        for (Machine machine : machines) {
            long best = Long.MAX_VALUE;

            double px = (double) machine.prize.x;
            double py = (double) machine.prize.y;

            double ax = (double) machine.a.x;
            double ay = (double) machine.a.y;


            double bx = (double) machine.b.x;
            double by = (double) machine.b.y;

            long iB = (long) ((-(py / (ay * ((bx / ax) - (by / ay))))) + ((px) / (ax * ((bx / ax) - (by / ay)))));
            System.out.println("(" + index + ") Ib: " + iB);
            index++;

            for (long i = -2; i < 3; i++) { // Check around to account for float / double errors
                long x = Math.multiplyExact(machine.b.x, i + iB);
                long y = Math.multiplyExact(machine.b.y, i + iB);

                if (x > machine.prize.x || y > machine.prize.y) {
                    continue;
                }

                long restX = (machine.prize.x - x) % machine.a.x;
                long restY = (machine.prize.y - y) % machine.a.y;

                if (restX != 0 || restY != 0) {
                    continue;
                }

                long aX = (machine.prize.x - x) / machine.a.x;
                long aY = (machine.prize.y - y) / machine.a.y;

                if (aX != aY) {
                    continue;
                }

                long amount = i + iB + aX * 3;
                if (amount < best) {
                    System.out.println("Found");
                    best = amount;
                }
            }

            if (best != Long.MAX_VALUE) {
                result += best;
            }
        }

        System.out.println(result);
    }

    private record Postion(long x, long y) {
    }

    private record Machine(Postion a, Postion b, Postion prize) {
    }
}
