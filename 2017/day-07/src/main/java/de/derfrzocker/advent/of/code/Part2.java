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

        List<Tree> trees = new ArrayList<>();
        for (String line : lines) {
            String name = line.substring(0, line.indexOf(' '));
            int weight = Integer.parseInt(line.substring(line.indexOf('(') + 1, line.indexOf(')')));
            if (line.contains("->")) {
                String childrenLine = line.substring(line.indexOf('>') + 2);
                String[] children = childrenLine.split(", ");

                List<Tree> childrenList = new ArrayList<>();
                dance:
                for (String child : children) {
                    for (Tree tree : trees) {
                        if (tree.name.equals(child)) {
                            childrenList.add(tree);
                            continue dance;
                        }
                    }

                    childrenList.add(new Tree(child, -1, new ArrayList<>()));
                }

                trees.removeAll(childrenList);
                if (!find(trees, name, weight, childrenList)) {
                    trees.add(new Tree(name, weight, childrenList));
                }
            } else {
                if (!find(trees, name, weight, new ArrayList<>())) {
                    trees.add(new Tree(name, weight, new ArrayList<>()));
                }
            }
        }

        if (trees.size() > 1) {
            throw new IllegalStateException(String.valueOf(trees.size()));
        }

        Result value = balance(trees.getFirst());
        System.out.println(value.result);
    }

    private static boolean find(List<Tree> trees, String name, int weight, List<Tree> childrenList) {
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if (tree.name.equals(name)) {
                trees.set(i, new Tree(name, weight, childrenList));
                return true;
            }
            if (find(tree.children, name, weight, childrenList)) {
                return true;
            }
        }

        return false;
    }

    private static Result balance(Tree tree) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < tree.children.size(); i++) {
            Tree child = tree.children.get(i);
            Result result1 = balance(child);
            if (result1.balance) {
                return result1;
            }

            result.add(result1.sum);
        }

        if (!result.isEmpty()) {
            int target = result.getFirst();
            for (int i = 1; i < result.size(); i++) {
                int other = result.get(i);
                if (target != other) {
                    if (i == 1) {
                        if (target != result.get(i + 1)) {
                            int diff = other - target;
                            return new Result(true, tree.children.getFirst().weight + diff, -1);
                        } else {
                            int diff = target - other;
                            return new Result(true, tree.children.get(i).weight + diff, -1);
                        }
                    } else {
                        int diff = target - other;
                        return new Result(true, tree.children.get(i).weight + diff, -1);
                    }
                }
            }
        }


        return new Result(false, -1, result.stream().reduce(0, Integer::sum) + tree.weight);
    }

    private static class Tree {
        public final String name;
        public final int weight;
        public List<Tree> children;

        Tree(String name, int weight, List<Tree> children) {
            this.name = name;
            this.weight = weight;
            this.children = children;
        }
    }

    private record Result(boolean balance, int result, int sum) {

    }
}
