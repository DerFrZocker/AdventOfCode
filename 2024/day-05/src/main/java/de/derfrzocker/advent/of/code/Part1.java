package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File UPDATES = new File("upadates.txt");


    public static void main(String[] args) throws IOException {
        List<String> rulesS = Files.readAllLines(DATA.toPath());
        List<RulePair> rules = new ArrayList<>();

        for (String rule : rulesS) {
            String[] split = rule.split("\\|");
            rules.add(new RulePair(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }

        List<String> updatesS = Files.readAllLines(UPDATES.toPath());
        List<Updates> updates = new ArrayList<>();
        for (String update : updatesS) {
            List<Integer> list  = new ArrayList<>();
            String[] split = update.split(",");

            for (String s : split) {
                list.add(Integer.parseInt(s));
            }

            updates.add(new Updates(list));
        }

        int result = 0;

        dance: for (Updates update : updates) {
            for (RulePair rule : rules) {
                if (!isCorrect(update.updates, rule)) {
                    continue dance;
                }
            }

            result += update.updates.get(update.updates.size() / 2);
        }

        System.out.println(result);
    }

    private static boolean isCorrect(List<Integer> update, RulePair rule) {
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int value: update) {
            if (value == rule.first) {
                foundFirst = true;
                if (foundSecond) {
                    return false;
                }
            } else if (value == rule.second) {
                foundSecond = true;
                if (foundFirst) {
                    return true;
                }
            }
        }

        return true;
    }

    private record Updates(List<Integer> updates){}

    private record RulePair(int first, int second) {}
}
