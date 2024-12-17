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

    private static final Map<String, Map<String, Info>> infos = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        Set<String> valuesD = new HashSet<>();
        for (String line : lines) {
            String[] parts = line.split(" ");

            int value = Integer.parseInt(parts[3]);

            if ("lose".equals(parts[2])) {
                value = -value;
            }

            Info info = new Info(parts[0], parts[10].substring(0, parts[10].length() - 1), value);

            infos.computeIfAbsent(info.from, k -> new HashMap<>()).put(info.to, info);
            valuesD.add(info.from);
            valuesD.add(info.to);
        }

        for (String other : valuesD) {
            Info first = new Info(other, "MySelf", 0);
            Info second = new Info("MySelf", other, 0);
            infos.computeIfAbsent(first.from, k -> new HashMap<>()).put(first.to, first);
            infos.computeIfAbsent(second.from, k -> new HashMap<>()).put(second.to, first);
        }

        valuesD.add("MySelf");

        List<String> values = new ArrayList<>(valuesD);

        doStuff(values, new String[values.size()], 0);

        System.out.println(best);
    }

    static int best = Integer.MIN_VALUE;

    private static void doStuff(List<String> values, String[] array, int i) {
        if (values.isEmpty()) {
            int current = 0;
            for (int j = 0; j < array.length; j++) {
                int k = j + 1;
                if (k == array.length) {
                    k = 0;
                }
                String left = array[j];
                String right = array[k];
                Info leftInfo = infos.get(left).get(right);
                Info rightInfo = infos.get(right).get(left);

                current += leftInfo.value;
                current += rightInfo.value;
            }

            if (current > best) {
                best = current;
            }
            return;
        }
        for (String value : values) {
            array[i] = value;
            List<String> sub = new ArrayList<>(values);
            sub.remove(value);
            doStuff(sub, array.clone(), i + 1);
        }
    }

    public record Info(String from, String to, int value) {
    }
}
