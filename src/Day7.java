import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {

    static class Bag {
        String name;
        Map<Bag, Integer> next = new HashMap<>();
        List<Bag> prev = new ArrayList<>();

        Bag(String name) {
            this.name = name;
        }

        void setNext(Map<Bag, Integer> next) {
            this.next = next;
        }

        void addPrev(Bag bag) {
            prev.add(bag);
        }
    }

    static Map<String, Bag> parseRules(List<String> rules) {
        Map<String, Bag> map = new HashMap<>();

        String bagReg = "([a-z]+ [a-z]+) bags contain ";
        String nextReg = "(\\d+) ([a-z]+ [a-z]+) bags?";
        Pattern bagRegPattern = Pattern.compile(bagReg);
        Pattern nextRegPattern = Pattern.compile(nextReg);

        rules.forEach(rule -> {
            Matcher bagMatcher = bagRegPattern.matcher(rule);
            Matcher nextMatcher = nextRegPattern.matcher(rule);
            if (bagMatcher.find()) {
                Bag bag = map.getOrDefault(bagMatcher.group(1), new Bag(bagMatcher.group(1)));
                Map<Bag, Integer> contentMap = new HashMap<>();
                while (nextMatcher.find()) {
                    Bag contentBag = map.getOrDefault(nextMatcher.group(2), new Bag(nextMatcher.group(2)));
                    contentBag.addPrev(bag);
                    contentMap.put(contentBag, Integer.parseInt(nextMatcher.group(1)));
                    bag.setNext(contentMap);
                    map.put(contentBag.name, contentBag);
                }
                map.put(bag.name, bag);
            }
        });
        return map;
    }

    // Part 1
    private static int countOutsideBags(Map<String, Bag> map, String name) {
        Bag bag = map.get(name);
        Queue<Bag> queue = new ArrayDeque<>(bag.prev);
        Set<String> set = bag.prev.stream().map(b -> b.name).collect(Collectors.toSet());
        while (!queue.isEmpty()) {
            Bag top = queue.poll();
            top.prev.forEach(b -> {
                if (!set.contains(b.name)) {
                    queue.add(b);
                    set.add(b.name);
                }
            });
        }
        return set.size();
    }

    // Part 2
    private static int countInsideBags(Map<String, Bag> map, String name) {
        Bag bag = map.get(name);
        int[] count = new int[]{0};
        countInsideHelper(count, 1, bag);
        return count[0];
    }

    private static void countInsideHelper(int[] count, int multiple, Bag bag) {
        for (Map.Entry<Bag, Integer> entry: bag.next.entrySet()) {
            count[0] += multiple * entry.getValue();
            countInsideHelper(count, multiple * entry.getValue(), entry.getKey());
        }
    }

    public static void main(String[] args) {
        Map<String, Bag> map = parseRules(IOUtils.readEveryLine("day7.in"));
        System.out.println(countOutsideBags(map, "shiny gold"));
        System.out.println(countInsideBags(map, "shiny gold"));
    }

}
