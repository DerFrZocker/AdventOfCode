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

        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] split = line.split(">, ");
            String[] position = split[0].substring(3).split(",");
            String[] velocity = split[1].substring(3).split(",");
            String[] acceleration = split[2].substring(3, split[2].length() - 1).split(",");

            Particle particle = new Particle(i, Integer.parseInt(position[0]), Integer.parseInt(position[1]), Integer.parseInt(position[2]), Integer.parseInt(velocity[0]), Integer.parseInt(velocity[1]), Integer.parseInt(velocity[2]), Integer.parseInt(acceleration[0]), Integer.parseInt(acceleration[1]), Integer.parseInt(acceleration[2]));

            particles.add(particle);
        }

        Particle best = null;
        int worstAcc = Integer.MAX_VALUE;
        for (Particle particle : particles) {
            int acc = Math.abs(particle.accelerationX) + Math.abs(particle.accelerationY) + Math.abs(particle.accelerationZ);

            if (acc < worstAcc) {
                best = particle;
                worstAcc = acc;
            }
        }

        System.out.println(best.id());
    }

    private record Particle(int id, int x, int y, int z, int velocityX, int velocityY, int velocityZ, int accelerationX,
                            int accelerationY, int accelerationZ) {

    }
}
