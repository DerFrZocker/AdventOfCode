package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Part1 {

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

        List<Equipment> weapons = List.of(
                new Equipment(Type.WEAPON, 8, 4, 0),
                new Equipment(Type.WEAPON, 10, 5, 0),
                new Equipment(Type.WEAPON, 25, 6, 0),
                new Equipment(Type.WEAPON, 40, 7, 0),
                new Equipment(Type.WEAPON, 74, 8, 0)
        );

        List<Equipment> armors = List.of(
                new Equipment(Type.ARMOR, 13, 0, 1),
                new Equipment(Type.ARMOR, 31, 0, 2),
                new Equipment(Type.ARMOR, 53, 0, 3),
                new Equipment(Type.ARMOR, 75, 0, 4),
                new Equipment(Type.ARMOR, 102, 0, 5)
        );

        List<Equipment> rings = List.of(
                new Equipment(Type.RING, 25, 1, 0),
                new Equipment(Type.RING, 50, 2, 0),
                new Equipment(Type.RING, 100, 3, 0),
                new Equipment(Type.RING, 20, 0, 1),
                new Equipment(Type.RING, 40, 0, 2),
                new Equipment(Type.RING, 80, 0, 3)
        );

        List<Equipment> equipments = new ArrayList<>();
        equipments.addAll(armors);
        equipments.addAll(rings);
        equipments.sort(Comparator.comparing(Equipment::cost));

        Map<Integer, List<List<Equipment>>> graph = new TreeMap<>();

        for (Equipment equipment : weapons) {
            List<Equipment> eq = new ArrayList<>();
            eq.add(equipment);
            graph.computeIfAbsent(equipment.cost, x -> new ArrayList<>()).add(eq);
        }

        while (true) {
            List<Equipment> current = null;

            for (Map.Entry<Integer, List<List<Equipment>>> entry : graph.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    current = entry.getValue().removeFirst();
                    break;
                }
            }

            if (current == null) {
                throw new RuntimeException("There should be a load out.");
            }

            int damage = current.stream().mapToInt(Equipment::damage).sum();
            int armor = current.stream().mapToInt(Equipment::armor).sum();

            if (battle(new Stats(100, damage, armor), enemy)) {
                System.out.println(current.stream().mapToInt(Equipment::cost).sum());
                break;
            }

            int armorAmount = 0;
            int ringAmount = 0;
            for (Equipment equipment : current) {
                if (equipment.type == Type.ARMOR) {
                    armorAmount++;
                }
                if (equipment.type == Type.RING) {
                    ringAmount++;
                }
            }

            List<Equipment> tmpEquipments = new ArrayList<>(equipments);
            tmpEquipments.removeAll(current);
            for (Equipment newEquipment : tmpEquipments) {
                List<Equipment> nextEquipments = new ArrayList<>(current);
                if (newEquipment.type == Type.ARMOR && armorAmount == 0) {
                    nextEquipments.add(newEquipment);
                } else if (newEquipment.type == Type.RING && ringAmount < 2) {
                    nextEquipments.add(newEquipment);
                } else {
                    continue;
                }

                nextEquipments.sort(Comparator.comparing(Equipment::cost));
                int cost = nextEquipments.stream().mapToInt(Equipment::cost).sum();
                graph.computeIfAbsent(cost, x -> new ArrayList<>()).add(nextEquipments);
            }
        }
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
}
