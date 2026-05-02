package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final int ME_HIT_POINTS = 50;
    private static final int MANA = 500;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        int enemyInitialHitPoints = -1;
        int enemyDamage = -1;
        for (String line : lines) {
            if (line.startsWith("Hit Points: ")) {
                enemyInitialHitPoints = Integer.parseInt(line.split(" ")[2]);
            }
            if (line.startsWith("Damage: ")) {
                enemyDamage = Integer.parseInt(line.split(" ")[1]);
            }
        }

        if (enemyInitialHitPoints == -1 || enemyDamage == -1) {
            throw new RuntimeException("Missing data");
        }

        NavigableSet<Node> toCheck = new TreeSet<>((a, b) -> {
            int result = Integer.compare(a.manaCost, b.manaCost);
            if (result == 0) {
                return 1;
            }

            return result;
        });
        Set<Node> visited = new HashSet<>();

        toCheck.add(new Node(ME_HIT_POINTS, enemyInitialHitPoints, true, MANA, 0, 0, 0, 0));

        while (true) {
            if (toCheck.isEmpty()) {
                throw new RuntimeException("Missing data");
            }
            Node current = toCheck.removeFirst();
            if (visited.contains(current)) {
                continue;
            }

            if (current.enemyHitPoints <= 0) {
                System.out.println(current.manaCost);
                return;
            }

            visited.add(current);

            boolean shield = current.shieldEffect > 0;
            boolean poison = current.poisonEffect > 0;
            boolean recharge = current.rechargeEffect > 0;

            int enemyHitPoints = current.enemyHitPoints;
            if (poison) {
                enemyHitPoints -= 3;
                if (enemyHitPoints <= 0) {
                    System.out.println(current.manaCost);
                    return;
                }
            }

            int mana = current.mana;
            if (recharge) {
                mana += 101;
            }

            int meHitPoints = current.meHitPoints;
            if (!current.meTurn) {
                if (shield) {
                    meHitPoints -= Math.max(1, enemyDamage - 7);
                } else {
                    meHitPoints -= enemyDamage;
                }

                if (meHitPoints <= 0) {
                    continue;
                }

                toCheck.add(new Node(meHitPoints, enemyHitPoints, true, mana, current.manaCost, current.shieldEffect - 1, current.poisonEffect - 1, current.rechargeEffect - 1));
            } else {
                { // Damage 53 mana -> 4 damage
                    if (mana >= 53) {
                        int tmpMana = mana - 53;
                        int tmpEnemyHitPoints = enemyHitPoints - 4;
                        int tmpManaCost = current.manaCost + 53;

                        toCheck.add(new Node(meHitPoints, tmpEnemyHitPoints, false, tmpMana, tmpManaCost, current.shieldEffect - 1, current.poisonEffect - 1, current.rechargeEffect - 1));
                    }
                }

                { // Drain 73 mana -> 2 damage 2 heal
                    if (mana >= 73) {
                        int tmpMana = mana - 73;
                        int tmpMeHitPoints = meHitPoints + 2;
                        int tmpEnemyHitPoints = enemyHitPoints - 2;
                        int tmpManaCost = current.manaCost + 73;

                        toCheck.add(new Node(tmpMeHitPoints, tmpEnemyHitPoints, false, tmpMana, tmpManaCost, current.shieldEffect - 1, current.poisonEffect - 1, current.rechargeEffect - 1));
                    }
                }

                { // Shield 113 mana -> 6 turns
                    if (mana >= 113 && current.shieldEffect <= 1) {
                        int tmpMana = mana - 113;
                        int tmpMeHitPoints = meHitPoints;
                        int tmpEnemyHitPoints = enemyHitPoints;
                        int tmpManaCost = current.manaCost + 113;


                        toCheck.add(new Node(tmpMeHitPoints, tmpEnemyHitPoints, false, tmpMana, tmpManaCost, 6, current.poisonEffect - 1, current.rechargeEffect - 1));
                    }
                }

                { // Poison 173 mana -> 6 turns
                    if (mana >= 173 && current.poisonEffect <= 1) {
                        int tmpMana = mana - 173;
                        int tmpMeHitPoints = meHitPoints;
                        int tmpEnemyHitPoints = enemyHitPoints;
                        int tmpManaCost = current.manaCost + 173;


                        toCheck.add(new Node(tmpMeHitPoints, tmpEnemyHitPoints, false, tmpMana, tmpManaCost, current.shieldEffect - 1, 6, current.rechargeEffect - 1));
                    }
                }

                { // Recharge 229 mana -> 5 turns
                    if (mana >= 229 && current.rechargeEffect <= 1) {
                        int tmpMana = mana - 229;
                        int tmpMeHitPoints = meHitPoints;
                        int tmpEnemyHitPoints = enemyHitPoints;
                        int tmpManaCost = current.manaCost + 229;


                        toCheck.add(new Node(tmpMeHitPoints, tmpEnemyHitPoints, false, tmpMana, tmpManaCost, current.shieldEffect - 1, current.poisonEffect - 1, 5));
                    }
                }
            }
        }
    }

    private record Node(int meHitPoints, int enemyHitPoints, boolean meTurn, int mana, int manaCost, int shieldEffect,
                        int poisonEffect, int rechargeEffect) {

    }
}
