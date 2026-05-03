package de.derfrzocker.advent.of.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    private static final File DATA = new File("data.txt");
    private static final File EXAMPLE = new File("example.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(DATA.toPath());

        List<Room> rooms = new ArrayList<>();
        for (String line : lines) {
            int lasDash = line.lastIndexOf('-');

            String encryption = line.substring(0, lasDash).replace("-", "");
            int sectorId = Integer.parseInt(line.substring(lasDash + 1, line.indexOf('[')));
            String checksum = line.substring(line.indexOf('[') + 1, line.lastIndexOf(']'));

            rooms.add(new Room(encryption, sectorId, checksum));
        }

        int result = 0;
        dance:
        for (Room room : rooms) {
            Map<Character, Integer> chars = new HashMap<>();

            for (char c : room.encryption.toCharArray()) {
                chars.compute(c, (k, v) -> v == null ? 1 : v + 1);
            }

            List<Entry> entries = new ArrayList<>();
            chars.forEach((k, v) -> entries.add(new Entry(k, v)));

            entries.sort((a, b) -> {
                int r = Integer.compare(b.amount, a.amount);
                if (r == 0) {
                    return Character.compare(a.c, b.c);
                }

                return r;
            });

            for (int i = 0; i < room.checksum.length(); i++) {
                if (entries.get(i).c != room.checksum.charAt(i)) {
                    continue dance;
                }
            }

            result += room.sectorId;
        }

        System.out.println(result);
    }

    private record Entry(char c, int amount) {

    }

    private record Room(String encryption, int sectorId, String checksum) {

    }
}
