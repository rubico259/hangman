package hangman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class HangmanPlay {

    private Map<Character, Integer> map;
    private static final String fileName = "c://hangman.txt";
    private List<String> list;
    private Set<Character> set;

    private HangmanPlay() {
        map = new LinkedHashMap<>();
        list = new ArrayList<>();
        set = new HashSet<>();
        start();
    }

    private void start() {

        HangmanServer server = new HangmanServer();

        Response resp = server.start();
        init(resp.getHang().length());

        while (resp.getHang().indexOf('_') >= 0) {
            Character c = map.entrySet().iterator().next().getKey();
            set.add(c);
            resp = server.guess(c.toString());

            if (resp.isCorrect()) {
                update(c, true);
            } else {
                update(c, false);
            }

            System.out.println(resp.getHang());
        }
    }

    private void init(int length) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream.filter(s -> s.length() == length).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        update('!', false);
    }

    private void update(Character c, boolean b) {
        if (!b) {
            list = list.stream().filter(s -> !s.contains(c.toString())).collect(Collectors.toList());
            updateMap();
        } else {
            list = list.stream().filter(s -> s.contains(c.toString())).collect(Collectors.toList());
            updateMap();
            map.remove(c);
        }
    }

    private void updateMap() {
        String s = String.join("", list);
        map.clear();
        Map<Character, Integer> map = s.chars().boxed()
                .collect(toMap(
                        k -> (char) k.intValue(),
                        v -> 1,
                        Integer::sum, LinkedHashMap::new));
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> this.map.put(x.getKey(), x.getValue()));

        set.forEach(f -> this.map.remove(f));

    }

    public static void main(String[] args) {
        new HangmanPlay();
    }
}
