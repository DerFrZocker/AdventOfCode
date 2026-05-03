package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Integer> weights = new ArrayList<>();

        lines.forEach(line -> weights.add(Integer.parseInt(line)));

        int sum = weights.stream().mapToInt(Integer::intValue).sum();

        if (sum % 4 != 0) {
            throw new RuntimeException("Sum must be a multiple of 3");
        }

        int target = sum / 4;

        weights.sort(Comparator.reverseOrder());

        List<Integer> solved = solve(target, new ArrayList<>(), 0, weights, 0);

        System.out.println(solved.stream().mapToLong(Integer::longValue).reduce(1, (a, b) -> a * b));
    }

    private static List<Integer> solve(int target, List<Integer> current, int currentSum, List<Integer> weights, int index) {
        List<Integer> currentBest = null;
        for (int i = index; i < weights.size(); i++) {
            int weight = weights.get(i);
            if (currentSum + weight > target) {
                continue;
            }

            List<Integer> result = new ArrayList<>(current);
            result.add(weight);

            if (currentSum + weight == target) {
                List<Integer> newWeights = new ArrayList<>(weights);
                newWeights.removeAll(result);
                boolean canSolve = canSolveRest(target, 0, new ArrayList<>(), newWeights, 0, 0);
                if (canSolve) {
                    if (currentBest == null) {
                        currentBest = result;
                    } else if (result.size() < currentBest.size()) {
                        currentBest = result;
                    } else {
                        long currentQuantum = currentBest.stream().mapToLong(Integer::intValue).reduce(1, (a, b) -> a * b);
                        long resultQuantum = result.stream().mapToLong(Integer::intValue).reduce(1, (a, b) -> a * b);

                        if (currentQuantum == resultQuantum) {
                            throw new RuntimeException("Same");
                        }

                        if (resultQuantum < currentQuantum) {
                            currentBest = result;
                        }
                    }
                }
                continue;
            }

            if (currentBest != null && currentBest.size() < current.size() + 1) {
                continue;
            }

            List<Integer> best = solve(target, result, currentSum + weight, weights, i + 1);

            if (best == null) {
                continue;
            }

            if (currentBest == null || best.size() < currentBest.size()) {
                currentBest = best;
            } else if (best.size() == currentBest.size()) {
                long currentQuantum = currentBest.stream().mapToLong(Integer::intValue).reduce(1, (a, b) -> a * b);
                long resultQuantum = best.stream().mapToLong(Integer::intValue).reduce(1, (a, b) -> a * b);

                if (currentQuantum == resultQuantum) {
                    throw new RuntimeException("Same");
                }

                if (resultQuantum < currentQuantum) {
                    currentBest = best;
                }
            }
        }

        return currentBest;
    }

    private static boolean canSolveRest(int target, int currentSum, List<Integer> current, List<Integer> weights, int index, int depth) {
        for (int i = index; i < weights.size(); i++) {
            int weight = weights.get(i);
            if (currentSum + weight > target) {
                continue;
            }

            List<Integer> result = new ArrayList<>(current);
            result.add(weight);

            if (currentSum + weight == target) {
                if (depth == 1) {
                    return true;
                }
                List<Integer> newWeights = new ArrayList<>(weights);
                newWeights.removeAll(result);
                boolean canSolve = canSolveRest(target, 0, new ArrayList<>(), newWeights, 0, depth + 1);
                if (canSolve) {
                    return true;
                }
                continue;
            }

            boolean canSolve = canSolveRest(target, currentSum + weight, result, weights, i + 1, depth);

            if (canSolve) {
                return true;
            }
        }

        return false;
    }
}
