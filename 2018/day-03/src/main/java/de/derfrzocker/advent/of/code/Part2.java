package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int[][] fabric = new int[1000][1000];

        List<Claim> claims = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            String id = split[0].substring(1);
            String[] coords = split[2].substring(0, split[2].length() - 1).split(",");
            String[] area = split[3].split("x");

            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);
            int xArea = Integer.parseInt(area[0]);
            int yArea = Integer.parseInt(area[1]);

            claims.add(new Claim(id, xCoord, yCoord, xArea, yArea));
        }

        List<Claim> overlappingClaims = new ArrayList<>();
        dance:
        for (int i = 0; i < claims.size(); i++) {
            Claim first = claims.get(i);
            if (overlappingClaims.contains(first)) {
                continue;
            }
            boolean isOverlapping = false;
            for (int j = 0; j < claims.size(); j++) {
                Claim second = claims.get(j);
                if (i == j) {
                    continue;
                }

                if (first.xCoord >= second.xCoord + second.xArea) {
                    continue;
                }
                if (first.yCoord >= second.yCoord + second.yArea) {
                    continue;
                }
                if (second.xCoord >= first.xCoord + first.xArea) {
                    continue;
                }
                if (second.yCoord >= first.yCoord + first.yArea) {
                    continue;
                }

                overlappingClaims.add(second);
                isOverlapping = true;
            }

            if (isOverlapping) {
                continue dance;
            }

            System.out.println(first.id());
            return;
        }
    }

    private record Claim(String id, int xCoord, int yCoord, int xArea, int yArea) {

    }
}
