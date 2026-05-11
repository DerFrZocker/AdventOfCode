package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * With the help of Part 1 and looking at the input data, we know, that there is only one "0" used node.
 * We also know, that there is now way, that the data of any two nodes can merge (except the "0" node), because there is not enough space.
 * So we know we need to only move the zero Node, which makes this all way easier.
 * We simply mark every node, which can go into the "0" node, as path and any other node as wall.
 */
public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int maxSize = 0;
        int xMax = 0;
        int yMax = 0;
        List<Node> nodes = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);
            String name = line.substring(16, 23).trim();
            String[] split = name.split("-y");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int size = Integer.parseInt(line.substring(23, 27).trim());
            int used = Integer.parseInt(line.substring(28, 33).trim());

            nodes.add(new Node(x, y, size, used));

            xMax = Math.max(xMax, x);
            yMax = Math.max(yMax, y);
            maxSize = Math.max(maxSize, size);
        }

        System.out.println("xMax: " + xMax);
        System.out.println("yMax: " + yMax);
        System.out.println("maxSize: " + maxSize);

        SubNode[][] startNodes = new SubNode[xMax + 1][yMax + 1];
        Node zeroNode = null;
        for (Node node : nodes) {
            if (node.used == 0) {
                if (zeroNode != null) {
                    throw new RuntimeException("More than one zero node");
                }
                zeroNode = node;
            }
            startNodes[node.x()][node.y()] = new SubNode(node.size(), node.used(), (node.x == xMax && node.y == 0));
        }

        if (zeroNode == null) {
            throw new RuntimeException("No zero node");
        }

        for (int x = 0; x <= xMax; x++) {
            if (startNodes[x][0].used > zeroNode.size) {
                throw new RuntimeException("No straight line to 0 0");
            }
        }

        int maxUsed = 0;
        int minSize = Integer.MAX_VALUE;
        for (Node node : nodes) {
            if (node.used > zeroNode.size) {
                continue;
            }

            maxUsed = Math.max(maxUsed, node.used);
            minSize = Math.min(minSize, node.size);
        }

        if (maxUsed > minSize) {
            throw new RuntimeException("Max used is bigger than min Size");
        }

        for (int y = 0; y <= yMax; y++) {
            for (int x = 0; x <= xMax; x++) {
                SubNode node = startNodes[x][y];
                System.out.printf("(%03d/%03d) ", node.used, node.size);
            }
            System.out.println();
        }

        Set<Position> visited = new HashSet<>();
        Set<Position> toCheck = new HashSet<>();
        toCheck.add(new Position(zeroNode.x, zeroNode.y));
        int step = 0;
        dance:
        while (true) {
            step++;
            Set<Position> nextToCheck = new HashSet<>();
            for (Position position : toCheck) {
                for (Direction direction : Direction.values()) {
                    int nextX = position.x() + direction.getXOffset();
                    int nextY = position.y() + direction.getYOffset();
                    if (nextX < 0 || nextX > xMax || nextY < 0 || nextY > yMax) {
                        continue;
                    }

                    if (nextX == xMax - 1 && nextY == 0) {
                        break dance;
                    }

                    SubNode second = startNodes[nextX][nextY];
                    if (second.used > zeroNode.size) {
                        continue;
                    }

                    nextToCheck.add(new Position(nextX, nextY));
                }
            }

            toCheck = nextToCheck;
        }

        step += 5 * (xMax - 1);
        step++;

        System.out.println(step);
    }

    private record Node(int x, int y, int size, int used) {

    }

    private record SubNode(int size, int used, boolean goal) {

    }
}
