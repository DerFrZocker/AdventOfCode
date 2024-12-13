package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i = i + 4) {
            String aL = lines.get(i);
            String bL = lines.get(i + 1);
            String prizeL = lines.get(i + 2);

            Postion a = new Postion(Integer.parseInt(aL.substring(aL.indexOf('X') + 1, aL.lastIndexOf(','))), Integer.parseInt(aL.substring(aL.lastIndexOf(' ') + 2)));
            Postion b = new Postion(Integer.parseInt(bL.substring(bL.indexOf('X') + 1, bL.lastIndexOf(','))), Integer.parseInt(bL.substring(bL.lastIndexOf(' ') + 2)));
            Postion prize = new Postion(Integer.parseInt(prizeL.substring(prizeL.indexOf('=') + 1, prizeL.lastIndexOf(','))), Integer.parseInt(prizeL.substring(prizeL.lastIndexOf('=') + 1)));

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

        int result = 0;
        int index = -1;
        for (Machine machine : machines) {
            index++;
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 100; i++) {
                int x = machine.a.x * i;
                int y = machine.a.y * i;

                if (x > machine.prize.x || y > machine.prize.y) {
                    continue;
                }

                int restX = (machine.prize.x - x) % machine.b.x;
                int restY = (machine.prize.y - y) % machine.b.y;

                if (restX != 0 || restY != 0) {
                    continue;
                }

                int aX = (machine.prize.x - x) / machine.b.x;
                int aY = (machine.prize.y - y) / machine.b.y;

                if (aX != aY) {
                    continue;
                }

                int amount = i * 3 + aX;
                if (amount < best) {
                    System.out.println("(" + index + ") Ib: " + aX);
                    best = amount;
                }
            }

            if (best != Integer.MAX_VALUE) {
                result += best;
            }
        }

        System.out.println(result);
    }

    private record Postion(int x, int y) {
    }

    private record Machine(Postion a, Postion b, Postion prize) {
    }
}
