package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

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
        }

        int result = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node first = nodes.get(i);
            if (first.used == 0) {
                continue;
            }
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) {
                    continue;
                }
                Node second = nodes.get(j);
                if ((second.size - second.used) >= first.used) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }

    private record Node(int x, int y, int size, int used) {

    }
}
