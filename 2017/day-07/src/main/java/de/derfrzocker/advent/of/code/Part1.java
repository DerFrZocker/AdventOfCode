package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Tree> trees = new ArrayList<>();
        for (String line : lines) {
            String name = line.substring(0, line.indexOf(' '));
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

                    childrenList.add(new Tree(child, new ArrayList<>()));
                }

                trees.removeAll(childrenList);
                if (!find(trees, name, childrenList)) {
                    trees.add(new Tree(name, childrenList));
                }
            }
        }

        if (trees.size() > 1) {
            throw new IllegalStateException(String.valueOf(trees.size()));
        }
        System.out.println(trees.getFirst().name);
    }

    private static boolean find(List<Tree> trees, String name, List<Tree> childrenList) {
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if (tree.name.equals(name)) {
                trees.set(i, new Tree(name, childrenList));
                return true;
            }
            if (find(tree.children, name, childrenList)) {
                return true;
            }
        }

        return false;
    }

    private static class Tree {
        public String name;
        public List<Tree> children;

        Tree(String name, List<Tree> children) {
            this.name = name;
            this.children = children;
        }
    }
}
