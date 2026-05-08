package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part2 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Map<String, Character> charsMapping = new HashMap<>();
        char currentChar = 'a';

        Map<Integer, List<String>> startMap = new HashMap<>();
        for (String line : lines) {
            int floor = -1;
            if (line.startsWith("The first")) {
                floor = 0;
            } else if (line.startsWith("The second")) {
                floor = 1;
            } else if (line.startsWith("The third")) {
                floor = 2;
            } else if (line.startsWith("The fourth")) {
                floor = 3;
            }

            if (line.endsWith("nothing relevant")) {
                startMap.put(floor, new ArrayList<>());
                continue;
            }

            List<String> list = new ArrayList<>();
            String[] split = line.split(" ");

            for (int i = 5; i < split.length - 1; i++) {
                if (split[i].equals("and") || split[i].equals("a")) {
                    continue;
                }

                if (split[i + 1].charAt(0) == 'm') {
                    String[] subSplit = split[i].split("-");
                    if (!charsMapping.containsKey(subSplit[0])) {
                        charsMapping.put(subSplit[0], currentChar++);
                    }
                    char value = charsMapping.get(subSplit[0]);

                    list.add(value + "m");
                } else {
                    if (!charsMapping.containsKey(split[i])) {
                        charsMapping.put(split[i], currentChar++);
                    }
                    char value = charsMapping.get(split[i]);

                    list.add(value + "g");
                }

                i++;
            }

            startMap.put(floor, list);
        }

        startMap.computeIfAbsent(0, x -> new ArrayList<>()).add("xg");
        startMap.computeIfAbsent(0, x -> new ArrayList<>()).add("xm");
        startMap.computeIfAbsent(0, x -> new ArrayList<>()).add("yg");
        startMap.computeIfAbsent(0, x -> new ArrayList<>()).add("ym");

        State start = new State(startMap, 0);

        Set<State> visited = new HashSet<>();
        Set<State> states = new HashSet<>();
        states.add(start);
        int step = 0;
        while (true) {
            step++;
            Set<State> nextStates = new HashSet<>();

            for (State state : states) {
                List<String> floorContent = state.floors.get(state.floor);
                for (int i = 0; i < floorContent.size(); i++) {
                    for (int j = i; j < floorContent.size(); j++) {
                        String first = floorContent.get(i);
                        String second = floorContent.get(j);

                        if (first.charAt(1) == 'm' && second.charAt(1) == 'g') {
                            if (first.charAt(0) != second.charAt(0)) {
                                continue;
                            }
                        }

                        if (first.charAt(1) == 'g' && second.charAt(1) == 'm') {
                            if (first.charAt(0) != second.charAt(0)) {
                                continue;
                            }
                        }

                        Map<Integer, List<String>> upMap = new HashMap<>();
                        Map<Integer, List<String>> downMap = new HashMap<>();
                        int downFlor = state.floor - 1;
                        int upFlor = state.floor + 1;

                        for (int z = 0; z < 4; z++) {
                            downMap.computeIfAbsent(z, x -> new ArrayList<>()).addAll(state.floors.get(z));
                            upMap.computeIfAbsent(z, x -> new ArrayList<>()).addAll(state.floors.get(z));
                        }

                        downMap.computeIfAbsent(state.floor, x -> new ArrayList<>()).remove(first);
                        downMap.computeIfAbsent(state.floor, x -> new ArrayList<>()).remove(second);

                        upMap.computeIfAbsent(state.floor, x -> new ArrayList<>()).remove(first);
                        upMap.computeIfAbsent(state.floor, x -> new ArrayList<>()).remove(second);

                        if (downFlor >= 0) {
                            downMap.computeIfAbsent(downFlor, x -> new ArrayList<>()).add(first);
                            if (i != j) {
                                downMap.computeIfAbsent(downFlor, x -> new ArrayList<>()).add(second);
                            }
                        }
                        if (upFlor < 4) {
                            upMap.computeIfAbsent(upFlor, x -> new ArrayList<>()).add(first);
                            if (i != j) {
                                upMap.computeIfAbsent(upFlor, x -> new ArrayList<>()).add(second);
                            }
                        }

                        downMap.forEach((a, b) -> b.sort(String::compareTo));
                        upMap.forEach((a, b) -> b.sort(String::compareTo));

                        State downState = new State(downMap, downFlor);
                        State upState = new State(upMap, upFlor);

                        if (solved(upState)) {
                            System.out.println(step);
                            return;
                        }

                        if (solved(downState)) {
                            System.out.println(step);
                            return;
                        }

                        if (!visited.contains(downState) && validate(downState)) {
                            visited.add(downState);
                            nextStates.add(downState);

                        }

                        if (!visited.contains(upState) && validate(upState)) {
                            visited.add(upState);
                            nextStates.add(upState);
                        }
                    }
                }
            }

            states = nextStates;
        }
    }

    private static boolean validate(State state) {
        if (state.floor >= 4) {
            return false;
        }
        if (state.floor < 0) {
            return false;
        }

        for (Map.Entry<Integer, List<String>> entry : state.floors.entrySet()) {
            for (String first : entry.getValue()) {
                if (first.charAt(1) == 'm') {
                    if (entry.getValue().contains(first.charAt(0) + "g")) {
                        continue;
                    }

                    for (String second : entry.getValue()) {
                        if (second.charAt(1) == 'g') {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private static boolean solved(State state) {
        if (state.floor != 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!state.floors.get(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private record State(Map<Integer, List<String>> floors, int floor) {

    }
}
