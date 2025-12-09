package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static List<Position> positions = new ArrayList<>();
    private static Set<Position> points = new HashSet<>();

    private static final double scale = 1;// (double) 1 / 10;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        for (String line : lines) {
            String[] split = line.split(",");
            positions.add(new Position((int) (Integer.parseInt(split[0]) * scale), (int) (Integer.parseInt(split[1]) * scale)));
        }

        int xMax = 0;
        int yMax = 0;
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        for (Position position : positions) {
            if (position.x() > xMax) {
                xMax = position.x();
            }
            if (position.y() > yMax) {
                yMax = position.y();
            }
            if (position.x() < xMin) {
                xMin = position.x();
            }
            if (position.y() < yMin) {
                yMin = position.y();
            }
        }

        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            Position next = getNext(i);
            points.add(position);
            if (position.x() == next.x()) {
                int firstY = Math.max(position.y(), next.y());
                int secondY = Math.min(position.y(), next.y());
                for (int y = secondY; y <= firstY; y++) {
                    points.add(new Position(position.x(), y));
                }
            } else if (position.y() == next.y()) {
                int firstX = Math.max(position.x(), next.x());
                int secondX = Math.min(position.x(), next.x());
                for (int x = secondX; x <= firstX; x++) {
                    points.add(new Position(x, position.y()));
                }
            } else {
                throw new RuntimeException();
            }
        }

        System.out.println("Points: " + points.size());

        Position start = null;
        dance:
        for (int x = xMin + 3; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                if (points.contains(new Position(x, y - 1))) {
                    continue;
                }
                if (!points.contains(new Position(x, y))) {
                    continue;
                }
                if (points.contains(new Position(x, y + 1))) {
                    continue;
                }
                start = new Position(x, y + 1);
                break dance;
            }
        }

        Set<Position> innerPoints = new HashSet<>();

        int index = getStart(start);
        for (int i = index; i < positions.size(); i++) {
            Position first = positions.get(i);
            Position second = getNext(i);

            if (first.y() == second.y()) {
                if (first.x() < second.x()) {
                    // right
                    for (int x = first.x(); x <= second.x(); x++) {
                        Position inner = new Position(x, first.y() + 1);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                } else {
                    // left
                    for (int x = first.x(); x >= second.x(); x--) {
                        Position inner = new Position(x, first.y() - 1);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                }
            } else {
                if (first.y() < second.y()) {
                    // down
                    for (int y = first.y(); y <= second.y(); y++) {
                        Position inner = new Position(first.x() - 1, y);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                } else {
                    // up
                    for (int y = first.y(); y >= second.y(); y--) {
                        Position inner = new Position(first.x() + 1, y);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                }
            }

        }

        for (int i = 0; i < index; i++) {
            Position first = positions.get(i);
            Position second = getNext(i);

            if (first.y() == second.y()) {
                if (first.x() < second.x()) {
                    // right
                    for (int x = first.x(); x <= second.x(); x++) {
                        Position inner = new Position(x, first.y() + 1);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                } else {
                    // left
                    for (int x = first.x(); x >= second.x(); x--) {
                        Position inner = new Position(x, first.y() - 1);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                }
            } else {
                if (first.y() < second.y()) {
                    // down
                    for (int y = first.y(); y <= second.y(); y++) {
                        Position inner = new Position(first.x() - 1, y);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                } else {
                    // up
                    for (int y = first.y(); y >= second.y(); y--) {
                        Position inner = new Position(first.x() + 1, y);
                        if (points.contains(inner)) {
                            continue;
                        }
                        innerPoints.add(inner);
                    }
                }
            }
        }


//        Visualize.draw(positions, points, innerPoints, xMax, yMax);
//        System.out.println("Drawn");

        /* Logic for checking line algo
        for (Position position : points) {
            boolean has = false;
            dance:
            for (int x = position.x() - 1; x <= position.x() + 1; x++) {
                for (int y = position.y() - 1; y <= position.y() + 1; y++) {
                    if (innerPoints.contains(new Position(x, y))) {
                        has = true;
                        break dance;
                    }
                }
            }
            if (!has) {
                System.out.println("Does not containe: " + position);
            }
        }
         */

        long maxArea = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            Position first = positions.get(i);
            dance:
            for (int j = i + 1; j < positions.size(); j++) {
                Position second = positions.get(j);

                long difX = Math.abs(first.x() - second.x()) + 1;
                long difY = Math.abs(first.y() - second.y()) + 1;
                long area = Math.multiplyExact(difX, difY);

                if (area <= maxArea) {
                    continue dance;
                }

                boolean in = false;
                if (first.x() < second.x()) {
                    in = false;
                    for (int x = first.x(); x <= second.x(); x++) {
                        Position check = new Position(x, first.y());
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                    in = false;
                    for (int x = second.x(); x >= first.x(); x--) {
                        Position check = new Position(x, second.y());
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                } else {
                    in = false;
                    for (int x = first.x(); x >= second.x(); x--) {
                        Position check = new Position(x, first.y());
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                    in = false;
                    for (int x = second.x(); x <= first.x(); x++) {
                        Position check = new Position(x, second.y());
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                }
                if (first.y() < second.y()) {
                    in = false;
                    for (int y = first.y(); y <= second.y(); y++) {
                        Position check = new Position(first.x(), y);
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                    in = false;
                    for (int y = second.y(); y >= first.y(); y--) {
                        Position check = new Position(second.x(), y);
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                } else {
                    in = false;
                    for (int y = first.y(); y >= second.y(); y--) {
                        Position check = new Position(first.x(), y);
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                    in = false;
                    for (int y = second.y(); y <= first.y(); y++) {
                        Position check = new Position(second.x(), y);
                        if (points.contains(check)) {
                            in = false;
                            continue;
                        }
                        if (innerPoints.contains(check)) {
                            in = true;
                            continue;
                        }
                        if (in) {
                            continue;
                        }

                        continue dance;
                    }
                }

                maxArea = area;
            }
        }
        System.out.println(maxArea);
    }

    private static Position getNext(int index) {
        if ((index + 1) == positions.size()) {
            return positions.getFirst();
        }

        return positions.get(index + 1);
    }

    private static int getStart(Position start) {
        Position linePoint = new Position(start.x(), start.y() - 1);
        int index = -1;

        for (int i = 0; i < positions.size(); i++) {
            Position first = positions.get(i);
            Position second = getNext(i);

            if (first.x() == second.x()) {
                continue;
            }

            if (first.y() != linePoint.y()) {
                continue;
            }

            if (first.y() == second.y()) {
                int firstX = Math.max(first.x(), second.x());
                int secondX = Math.min(first.x(), second.x());
                for (int x = secondX; x <= firstX; x++) {
                    if (linePoint.equals(new Position(x, first.y()))) {
                        index = i;
                        break;
                    }
                }
            } else {
                throw new RuntimeException();
            }
        }

        if (index == -1) {
            throw new RuntimeException();
        }

        return index;
    }

    public record Pair(Position start, Position end) {

    }
}
