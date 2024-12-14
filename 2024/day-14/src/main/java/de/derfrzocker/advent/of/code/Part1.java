package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");

    private static final int TIME_STEP = 100;
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Robot> robots = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String position = parts[0];
            String velocity = parts[1];
            position = position.substring(2);
            velocity = velocity.substring(2);

            String[] posXY = position.split(",");
            String[] velXY = velocity.split(",");

            Postion pos = new Postion(Integer.parseInt(posXY[0]), Integer.parseInt(posXY[1]));
            Velocity vel = new Velocity(Integer.parseInt(velXY[0]), Integer.parseInt(velXY[1]));

            robots.add(new Robot(pos, vel));
        }

        System.out.println(robots);

//        robots.clear();
//        robots.add(new Robot(new Postion(2, 4), new Velocity(2, -3)));

        List<Robot> after = new ArrayList<>();
        for (Robot robot : robots) {
            int x = robot.position.x;
            int y = robot.position.y;

            x = x + Math.multiplyExact(robot.velocity.x, TIME_STEP);
            y = y + Math.multiplyExact(robot.velocity.y, TIME_STEP);

            x = x % WIDTH;
            if (x < 0) {
                x = WIDTH + x;
            }

            y = y % HEIGHT;
            if (y < 0) {
                y = HEIGHT + y;
            }

            after.add(new Robot(new Postion(x, y), robot.velocity));
        }

        System.out.println(after);

        long first = 0;
        long second = 0;
        long third = 0;
        long fourth = 0;

        for (Robot robot : after) {
            int x = robot.position.x;
            int y = robot.position.y;

            if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
                System.out.println(robot);
            }

            if (x < (WIDTH / 2) ) {
                // Left site
                if (y < (HEIGHT / 2)) {
                    first++;
                } else if (y > ((HEIGHT / 2))) {
                    third++;
                }
            } else if (x > ((WIDTH / 2))) {
                if (y < (HEIGHT / 2)) {
                    second++;
                } else if (y > ((HEIGHT / 2))) {
                    fourth++;
                }
            }
        }

        System.out.println("first: %d, second: %d, third: %d, fourth: %d".formatted(first, second, third, fourth));

        long result = Math.multiplyExact(first, second);
        result = Math.multiplyExact(result, third);
        result = Math.multiplyExact(result, fourth);

        System.out.println(result);
    }

    private record Postion(int x, int y) {

    }

    private record Velocity(int x, int y) {

    }

    private record Robot(Postion position, Velocity velocity) {

    }
}
