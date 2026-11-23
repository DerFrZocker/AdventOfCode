package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Map<Integer, Particle> particles = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] split = line.split(">, ");
            String[] position = split[0].substring(3).split(",");
            String[] velocity = split[1].substring(3).split(",");
            String[] acceleration = split[2].substring(3, split[2].length() - 1).split(",");

            Particle particle = new Particle(
                    i,
                    Integer.parseInt(position[0]),
                    Integer.parseInt(position[1]),
                    Integer.parseInt(position[2]),
                    Integer.parseInt(velocity[0]),
                    Integer.parseInt(velocity[1]),
                    Integer.parseInt(velocity[2]),
                    Integer.parseInt(acceleration[0]),
                    Integer.parseInt(acceleration[1]),
                    Integer.parseInt(acceleration[2]));

            particles.put(particle.id(), particle);
        }

        int t = 0;
        check:
        while (true) {
            Map<Position, List<Particle>> positions = new HashMap<>();
            for (Particle particle : particles.values()) {
                int xVelocity = particle.velocityX() + particle.accelerationX();
                int yVelocity = particle.velocityY() + particle.accelerationY();
                int zVelocity = particle.velocityZ() + particle.accelerationZ();
                int x = particle.x() + xVelocity;
                int y = particle.y() + yVelocity;
                int z = particle.z() + zVelocity;
                Position position = new Position(x, y, z);
                Particle newParticle = new Particle(
                        particle.id(),
                        x,
                        y,
                        z,
                        xVelocity,
                        yVelocity,
                        zVelocity,
                        particle.accelerationX(),
                        particle.accelerationY(),
                        particle.accelerationZ());

                positions.computeIfAbsent(position, k -> new ArrayList<>()).add(newParticle);
            }

            Map<Integer, Particle> newParticles = new HashMap<>();
            for (Map.Entry<Position, List<Particle>> entry : positions.entrySet()) {
                if (entry.getValue().size() == 1) {
                    Particle particle = entry.getValue().getFirst();
                    newParticles.put(particle.id(), particle);
                }
            }

            if (newParticles.isEmpty()) {
                System.out.println("No particles left: 0");
                return;
            }
            particles = newParticles;
            t++;

            // Lazy method
            if (t == 100000) {
                break check;
            }
        }

        System.out.println(particles.size());
    }

    private record Particle(
            int id,
            int x,
            int y,
            int z,
            int velocityX,
            int velocityY,
            int velocityZ,
            int accelerationX,
            int accelerationY,
            int accelerationZ) {

    }

    private record Position(int x, int y, int z) {

    }
}
