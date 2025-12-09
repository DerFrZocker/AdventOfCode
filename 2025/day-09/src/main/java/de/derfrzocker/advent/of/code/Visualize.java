package de.derfrzocker.advent.of.code;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Visualize {

    private static final double scale = (double) 1 / 10;

    public static void draw(List<Position> positions, Set<Position> points, Set<Position> inner, int maxX, int maxY) {
        BufferedImage image = new BufferedImage((int) (maxX * scale + 3), (int) (maxY * scale + 3), ColorSpace.TYPE_RGB);
        List<Position> sanity = new ArrayList<>();

        for (Position position : points) {
            image.setRGB((int) (position.x() * scale), (int) (position.y() * scale), Color.WHITE.getRGB());
        }
        for (Position position : positions) {
            Position pos = new Position((int) (position.x() * scale), (int) (position.y() * scale));
            image.setRGB((int) (position.x() * scale), (int) (position.y() * scale), Color.RED.getRGB());
            if (sanity.contains(pos)) {
                throw new RuntimeException();
            }
            sanity.add(pos);
        }
        for (Position position : inner) {
            image.setRGB((int) (position.x() * scale), (int) (position.y() * scale), Color.BLUE.getRGB());
        }

        try {
            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
