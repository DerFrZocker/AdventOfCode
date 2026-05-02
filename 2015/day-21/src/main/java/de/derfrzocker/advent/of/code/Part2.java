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

        int enemyHitPoints = 0;
        int enemyDamage = 0;
        int enemyArmor = 0;

        for (String line : lines) {
            if (line.startsWith("Hit Points: ")) {
                enemyHitPoints = Integer.parseInt(line.split(" ")[2]);
            } else if (line.startsWith("Damage: ")) {
                enemyDamage = Integer.parseInt(line.split(" ")[1]);
            } else if (line.startsWith("Armor: ")) {
                enemyArmor = Integer.parseInt(line.split(" ")[1]);
            }
        }

        Stats enemy = new Stats(enemyHitPoints, enemyDamage, enemyArmor);

        List<Equipment> weapons = new ArrayList<>(List.of(
                new Equipment(Type.WEAPON, 8, 4, 0),
                new Equipment(Type.WEAPON, 10, 5, 0),
                new Equipment(Type.WEAPON, 25, 6, 0),
                new Equipment(Type.WEAPON, 40, 7, 0),
                new Equipment(Type.WEAPON, 74, 8, 0)
        ));

        List<Equipment> armors = new ArrayList<>(List.of(
                // Dummy armor
                new Equipment(Type.ARMOR, 0, 0, 0),
                new Equipment(Type.ARMOR, 13, 0, 1),
                new Equipment(Type.ARMOR, 31, 0, 2),
                new Equipment(Type.ARMOR, 53, 0, 3),
                new Equipment(Type.ARMOR, 75, 0, 4),
                new Equipment(Type.ARMOR, 102, 0, 5)
        ));

        List<Equipment> rings = new ArrayList<>(List.of(
                // Dummy ring
                new Equipment(Type.RING, 0, 0, 0),
                new Equipment(Type.RING, 0, 0, 0),
                new Equipment(Type.RING, 25, 1, 0),
                new Equipment(Type.RING, 50, 2, 0),
                new Equipment(Type.RING, 100, 3, 0),
                new Equipment(Type.RING, 20, 0, 1),
                new Equipment(Type.RING, 40, 0, 2),
                new Equipment(Type.RING, 80, 0, 3)
        ));

        int minDamage = 4;
        int maxDamage = 4;
        int minArmor = 0;
        int maxArmor = 0;

        while (true) {
            if (!battle(new Stats(100, maxDamage, 0), enemy)) {
                maxDamage++;
            } else {
                maxDamage--;
                break;
            }
        }

        while (true) {
            if (!battle(new Stats(100, 4, maxArmor), enemy)) {
                maxArmor++;
            } else {
                maxArmor--;
                break;
            }
        }

        List<Result> potential = new ArrayList<>();
        for (int i = minDamage; i <= maxDamage; i++) {
            for (int i2 = minArmor; i2 <= maxArmor; i2++) {
                if (!battle(new Stats(100, i, i2), enemy)) {
                    potential.add(new Result(i, i2));
                }
            }
        }

        int maxCost = 0;
        for (Result result : potential) {
            for (Equipment weapon : weapons) {
                if (weapon.damage > result.damge) {
                    continue;
                }

                for (Equipment armor : armors) {
                    if (armor.armor > result.armor) {
                        continue;
                    }

                    for (Equipment ring1 : rings) {
                        for (Equipment ring2 : rings) {
                            if (ring1 == ring2) {
                                continue;
                            }

                            int damage = weapon.damage + ring1.damage + ring2.damage;
                            int defense = armor.armor + ring1.armor + ring2.armor;

                            if (damage != result.damge) {
                                continue;
                            }
                            if (defense != result.armor) {
                                continue;
                            }

                            int cost = weapon.cost + armor.cost + ring1.cost + ring2.cost;
                            if (cost > maxCost) {
                                maxCost = cost;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(maxCost);
    }

    private static boolean battle(Stats me, Stats enemy) {
        double neededMeTurns = Math.ceil((double) enemy.hitPoints / Math.max(1, me.damage - enemy.armor));
        double neededEnemyTurns = Math.ceil((double) me.hitPoints / Math.max(1, enemy.damage - me.armor));

        if (neededMeTurns <= neededEnemyTurns) {
            return true;
        }

        return false;
    }

    private record Stats(int hitPoints, int damage, int armor) {

    }

    private record Equipment(Type type, int cost, int damage, int armor) {

    }

    private enum Type {

        WEAPON, ARMOR, RING
    }

    private record Result(int damge, int armor) {

    }
}
