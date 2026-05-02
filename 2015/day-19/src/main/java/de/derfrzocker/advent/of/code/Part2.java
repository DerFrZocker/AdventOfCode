package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    private static final Set<String> VISIT = new HashSet<>();
    private static final List<Replacement> REPLACEMENTS = new ArrayList<>();
    private static final Map<String, Map<Replacement, Integer>> SOLVE_SOME_CACHE = new HashMap<>();
    private static final Map<String, Map<Replacement, Entry>> SOLVE_SOME_START_CACHE = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        boolean isInput = false;
        String input = null;
        for (String line : lines) {
            if (line.isBlank()) {
                isInput = true;
                continue;
            }
            if (isInput) {
                input = line;
                break;
            }

            String[] split = line.split(" => ");
            String key = split[0];
            String value = split[1];

            REPLACEMENTS.add(new Replacement(key, value));
        }

        if (input == null) {
            throw new IllegalArgumentException("No input provided");
        }

        for (Replacement value : REPLACEMENTS) {
            int matches = 0;
            for (Replacement other : REPLACEMENTS) {
                if (other.value.contains(value.value)) {
                    matches++;
                }
            }

            if (matches != 1) {
                throw new IllegalArgumentException("Error value " + value);
            }
        }

        Set<Replacement> uniqueValueChar = new HashSet<>();
        Set<Character> uniqueChar = new HashSet<>();
        for (Replacement value : REPLACEMENTS) {
            dance:
            for (char c : value.value.toCharArray()) {
                for (Replacement other : REPLACEMENTS) {
                    if (other.key().indexOf(c) != -1) {
                        continue dance;
                    }
                }
                uniqueValueChar.add(value);
                uniqueChar.add(c);
            }
        }

        System.out.println("Found unique chars: " + uniqueChar);
        System.out.println("Found unique value chars: " + uniqueValueChar);
        REPLACEMENTS.removeAll(uniqueValueChar);

        Set<Replacement> cas = new HashSet<>();
        for (Replacement replacement : REPLACEMENTS) {
            if (replacement.value.contains("Ca")) {
                cas.add(replacement);
            }
        }

        Map<String, List<Replacement>> uniqueParts = new HashMap<>();
        Map<String, List<Replacement>> uniqueStartParts = new HashMap<>();
        for (Replacement replacement : uniqueValueChar) {
            String part = replacement.value.substring(replacement.value.indexOf('n') + 1, replacement.value.indexOf('r'));
            String startPart = replacement.value.substring(0, replacement.value.indexOf('R'));
            if (uniqueParts.containsKey(part)) {
                System.out.println("Dup part");
            }

            if (uniqueStartParts.containsKey(startPart)) {
                System.out.println("Dup start part");
            }

            uniqueParts.computeIfAbsent(part, d -> new ArrayList<>()).add(replacement);
            uniqueStartParts.computeIfAbsent(startPart, d -> new ArrayList<>()).add(replacement);
        }

        Entry resultE = solve(0, input, uniqueParts, uniqueStartParts, cas);
        int result = solveRest(resultE.step, resultE.rest);

        System.out.println("Result: " + result);
    }

    private static int solveRest(int steps, String target) {
        Set<String> visit = new HashSet<>();
        Set<String> toCheck = new HashSet<>();
        toCheck.add(target);
        while (true) {
            steps++;
            Set<String> newToCheck = new HashSet<>();
            for (String input : toCheck) {
                for (Replacement replacement : REPLACEMENTS) {
                    String key = replacement.key();
                    String value = replacement.value();

                    dance:
                    for (int i = 0; i < (input.length() - value.length()) + 1; i++) {
                        for (int j = 0; j < value.length(); j++) {
                            char c = input.charAt(i + j);
                            char kc = value.charAt(j);

                            if (c != kc) {
                                continue dance;
                            }
                        }

                        String result = input.substring(0, i) + key + input.substring(i + value.length());

                        if (result.equals("e")) {
                            return steps;
                        }

                        if (!visit.contains(result)) {
                            visit.add(result);
                            newToCheck.add(result);
                        }
                    }
                }

            }

            toCheck = newToCheck;
        }
    }

    private static Entry solve(int steps, String input, Map<String, List<Replacement>> uniqueParts, Map<String, List<Replacement>> uniqueStartParts, Set<Replacement> cas) {
        dance2:
        while (input.contains("r")) {
            Entry stripResult = stripCaCa(input, cas);
            input = stripResult.rest;
            steps = steps + stripResult.step();
            dance:
            for (int i = 0; i < input.length() - 3; i++) {
                if (input.charAt(i) != 'R' || input.charAt(i + 1) != 'n') {
                    continue;
                }

                for (int j = i + 1; j < input.length(); j++) {
                    if (input.charAt(j) == 'R') {
                        continue dance;
                    }

                    if (input.charAt(j) != 'r') {
                        continue;
                    }

                    int k;
                    for (k = i - 1; k > 0; k--) {
                        if (input.charAt(k) == 'n') {
                            k++;
                            break;
                        }
                        if (input.charAt(k) == 'r') {
                            continue dance;
                        }
                    }

                    String sub = input.substring(i + 2, j);
                    String subStart = input.substring(k, i);
                    Map<Replacement, Integer> solved = solveSome(sub, uniqueParts, cas);
                    Map<Replacement, Entry> solvedStart = solveSomeStart(subStart, uniqueStartParts, cas);

                    if (solved.isEmpty()) {
                        return new Entry(null, Integer.MAX_VALUE);
                    }

                    if (solvedStart.isEmpty()) {
                        return new Entry(null, Integer.MAX_VALUE);
                    }


                    List<Replacement> potential = new ArrayList<>();
                    for (Replacement replacement : solved.keySet()) {
                        if (solvedStart.containsKey(replacement)) {
                            potential.add(replacement);
                        }
                    }

                    if (potential.isEmpty()) {
                        System.out.println("Empty");
                        continue dance;
                    }

                    if (potential.size() > 1) {
                        Entry best = new Entry(null, Integer.MAX_VALUE);
                        for (Replacement replacement : potential) {
                            int substeps = steps + 1;
                            substeps += solved.get(replacement);
                            substeps += solvedStart.get(replacement).step();

                            String subTarget = input.substring(0, k) + solvedStart.get(replacement).rest() + replacement.key + input.substring(j + 1);
                            Entry newSteps = solve(substeps, subTarget, uniqueParts, uniqueStartParts, cas);
                            if (newSteps.step < best.step) {
                                best = newSteps;
                            }
                        }
                        System.out.println("More than one");
                        if (best.step != Integer.MAX_VALUE) {
                            return best;
                        }
                        continue dance;
                    }

                    System.out.println("replacing");

                    steps++;
                    steps += solved.get(potential.getFirst());
                    steps += solvedStart.get(potential.getFirst()).step();

                    String start = input.substring(0, k);
                    String rest = solvedStart.get(potential.getFirst()).rest();
                    String key = potential.getFirst().key();
                    String end = input.substring(j + 1);
                    input = start + rest + key + end;
                    continue dance2;
                }
            }

            System.out.println("Nothing");
            return new Entry(null, Integer.MAX_VALUE);
        }

        System.out.println("Rest: " + input);

        return new Entry(input, steps);
    }

    private static Map<Replacement, Integer> solveSome(String target, Map<String, List<Replacement>> unique, Set<Replacement> cas) {
        if (SOLVE_SOME_CACHE.containsKey(target)) {
            return SOLVE_SOME_CACHE.get(target);
        }

        Map<Replacement, Integer> resultM = new LinkedHashMap<>();
        Set<String> visit = new HashSet<>();
        Map<Integer, Set<String>> toCheck = new HashMap<>();
        toCheck.computeIfAbsent(0, x -> new HashSet<>()).add(target);
        int steps = 0;
        if (unique.containsKey(target)) {
            unique.get(target).forEach(d -> {
                if (resultM.put(d, 0) != null) {
                    throw new RuntimeException("Should be first");
                }
            });
        }
        while (true) {
            steps++;
            for (String input : toCheck.computeIfAbsent(steps - 1, x -> new HashSet<>())) {
                for (Replacement replacement : REPLACEMENTS) {
                    String key = replacement.key();
                    String value = replacement.value();

                    dance:
                    for (int i = 0; i < (input.length() - value.length()) + 1; i++) {
                        for (int j = 0; j < value.length(); j++) {
                            char c = input.charAt(i + j);
                            char kc = value.charAt(j);

                            if (c != kc) {
                                continue dance;
                            }
                        }

                        String result = input.substring(0, i) + key + input.substring(i + value.length());
                        Entry reduced = stripCaCa(result, cas);
                        result = reduced.rest;

                        if (unique.containsKey(result)) {
                            int finalSteps = steps + reduced.step();
                            unique.get(result).forEach(d -> resultM.computeIfAbsent(d, x -> finalSteps));
                        }

                        if (!visit.contains(result)) {
                            visit.add(result);
                            toCheck.computeIfAbsent(steps + reduced.step(), x -> new HashSet<>()).add(result);
                        }
                    }
                }
            }

            boolean more = false;
            for (Map.Entry<Integer, Set<String>> entry : toCheck.entrySet()) {
                if (entry.getKey() >= steps) {
                    more = true;
                    break;
                }
            }
            if (!more) {
                break;
            }
        }

        if (resultM.size() > 1) {
            System.out.println("More");
        }

        if (resultM.size() == 0) {
            System.out.println("Zero");
        }

        SOLVE_SOME_CACHE.put(target, resultM);

        return resultM;
    }

    private static Map<Replacement, Entry> solveSomeStart(String target, Map<String, List<Replacement>> unique, Set<Replacement> cas) {
        if (SOLVE_SOME_START_CACHE.containsKey(target)) {
            return SOLVE_SOME_START_CACHE.get(target);
        }

        Map<Replacement, Entry> resultM = new LinkedHashMap<>();
        Set<String> visit = new HashSet<>();
        Map<Integer, Set<String>> toCheck = new HashMap<>();
        toCheck.computeIfAbsent(0, x -> new HashSet<>()).add(target);
        int steps = 0;
        for (Map.Entry<String, List<Replacement>> entry : unique.entrySet()) {
            if (target.endsWith(entry.getKey())) {
                entry.getValue().forEach(d -> {
                    if (resultM.put(d, new Entry(target.substring(0, target.length() - entry.getKey().length()), 0)) != null) {
                        throw new RuntimeException("Should be first");
                    }
                });
            }
        }
        while (true) {
            steps++;
            for (String input : toCheck.computeIfAbsent(steps - 1, x -> new HashSet<>())) {
                for (Replacement replacement : REPLACEMENTS) {
                    String key = replacement.key();
                    String value = replacement.value();

                    dance:
                    for (int i = 0; i < (input.length() - value.length()) + 1; i++) {
                        for (int j = 0; j < value.length(); j++) {
                            char c = input.charAt(i + j);
                            char kc = value.charAt(j);

                            if (c != kc) {
                                continue dance;
                            }
                        }

                        String result = input.substring(0, i) + key + input.substring(i + value.length());

                        Entry reduced = stripCaCa(result, cas);
                        result = reduced.rest;

                        for (Map.Entry<String, List<Replacement>> entry : unique.entrySet()) {
                            if (result.endsWith(entry.getKey())) {
                                int finalSteps = steps + reduced.step;
                                String finalResult = result;
                                entry.getValue().forEach(d -> resultM.computeIfAbsent(d, x -> new Entry(finalResult.substring(0, finalResult.length() - entry.getKey().length()), finalSteps)));
                            }
                        }

                        if (!visit.contains(result)) {
                            visit.add(result);
                            toCheck.computeIfAbsent(steps + reduced.step(), x -> new HashSet<>()).add(result);
                        }
                    }
                }
            }

            boolean more = false;
            for (Map.Entry<Integer, Set<String>> entry : toCheck.entrySet()) {
                if (entry.getKey() >= steps) {
                    more = true;
                    break;
                }
            }
            if (!more) {
                break;
            }

        }

        if (resultM.size() > 1) {
            System.out.println("More");
        }

        if (resultM.isEmpty()) {
            System.out.println("Zero");
        }

        SOLVE_SOME_START_CACHE.put(target, resultM);

        return resultM;
    }

    private static Entry stripCaCa(String input, Set<Replacement> cas) {
        boolean modified = false;
        int steps = 0;
        do {
            modified = false;
            for (Replacement replacement : cas) {
                if (input.contains(replacement.value)) {
                    steps++;
                    modified = true;
                    input = input.replaceFirst(replacement.value, replacement.key);
                }
            }
        } while (modified);

        return new Entry(input, steps);
    }

    private record Replacement(String key, String value) {

    }

    private record Entry(String rest, int step) {

    }
}
