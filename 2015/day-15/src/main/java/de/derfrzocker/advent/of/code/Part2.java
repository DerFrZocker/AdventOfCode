package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final List<Ingredient> ingredients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        lines.forEach(line -> {
            String[] firstSplit = line.split(":");
            String name = firstSplit[0];

            String[] secondSplit = firstSplit[1].split(",");

            int capacity = 0;
            int durability = 0;
            int flavor = 0;
            int texture = 0;
            int calories = 0;
            for (String part : secondSplit) {
                String[] partSplit = part.trim().split(" ");
                if (partSplit.length != 2) {
                    throw new IllegalArgumentException("invalid part " + part);
                }

                String propertyName = partSplit[0];
                int value = Integer.parseInt(partSplit[1]);

                if (propertyName.equals("capacity")) {
                    capacity = value;
                } else if (propertyName.equals("durability")) {
                    durability = value;
                } else if (propertyName.equals("flavor")) {
                    flavor = value;
                } else if (propertyName.equals("texture")) {
                    texture = value;
                } else if (propertyName.equals("calories")) {
                    calories = value;
                } else {
                    throw new IllegalArgumentException("invalid property " + propertyName);
                }
            }

            ingredients.add(new Ingredient(capacity, durability, flavor, texture, calories));
        });

        int[] counter = new int[ingredients.size()];
        counter[0] = 100;

        long max = 0;
        do {
            long capacity = 0;
            long durability = 0;
            long flavor = 0;
            long texture = 0;
            long calories = 0;

            for (int i = 0; i < counter.length; i++) {
                Ingredient ingredient = ingredients.get(i);
                int multiplier = counter[i];

                capacity = Math.addExact(capacity, Math.multiplyExact(ingredient.capacity, multiplier));
                durability = Math.addExact(durability, Math.multiplyExact(ingredient.durability, multiplier));
                flavor = Math.addExact(flavor, Math.multiplyExact(ingredient.flavor, multiplier));
                texture = Math.addExact(texture, Math.multiplyExact(ingredient.texture, multiplier));
                calories = Math.addExact(calories, Math.multiplyExact(ingredient.calories, multiplier));
            }

            capacity = Math.max(capacity, 0);
            durability = Math.max(durability, 0);
            flavor = Math.max(flavor, 0);
            texture = Math.max(texture, 0);

            long result = Math.multiplyExact(capacity, Math.multiplyExact(durability, Math.multiplyExact(flavor, texture)));

            if (result > max && calories == 500) {
                max = result;
            }
        } while (reduce(counter, 0));

        // 1801800 -> to low
        System.out.println(max);
    }

    private record Ingredient(int capacity, int durability, int flavor, int texture, int calories) {

    }

    private static boolean reduce(int[] counter, int baseIndex) {
        if (baseIndex >= counter.length) {
            throw new IllegalArgumentException("invalid baseIndex " + baseIndex);
        }

        for (int i = baseIndex + 1; i < counter.length - 1; i++) {
            if (counter[i] != 0) {
                return reduce(counter, baseIndex + 1);
            }
        }

        if (baseIndex == 0 && counter[baseIndex] == 0) {
            return false;
        }

        if (baseIndex != 0) {
            counter[baseIndex]--;
            int value = counter[counter.length - 1];
            counter[counter.length - 1] = 0;
            counter[baseIndex + 1] += value;
            counter[baseIndex + 1]++;
            return true;
        }

        for (int i = counter.length - 2; i >= 0; i--) {
            int value = counter[i];
            if (value != 0 && i != baseIndex) {
                counter[i] = value - 1;
                counter[i + 1] = counter[i + 1] + 1;
                return true;
            } else if (value != 0) {
                counter[i] = value - 1;
                counter[i + 1] = 100 - (value - 1);
                for (int i2 = baseIndex + 2; i2 < counter.length; i2++) {
                    counter[i2] = 0;
                }
                return true;
            }
        }

        return false;
    }
}
