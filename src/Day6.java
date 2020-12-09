import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {
    // Part 1
    static int countUnionSum(List<String> groups) {
        int count = 0;
        for (String group: groups) {
            Set<Character> characterSet = new HashSet<>();
            for (int i = 0; i < group.length(); i++) {
                characterSet.add(group.charAt(i));
            }
            count += characterSet.size();
        }
        return count;
    }

    // Part 2
    static int countIntersectSum(List<List<String>> groups) {
        int count = 0;
        for (List<String> group: groups) {
            Set<Character> intersect = group.get(0).chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
            for (int i = 1; i < group.size(); i++) {
                intersect.retainAll(group.get(i).chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
            }
            count += intersect.size();
        }
        return count;
    }
    public static void main(String[] args) {
        System.out.println(countUnionSum(IOUtils.readAndConcatenate("input/day6.in","")));
        System.out.println(countIntersectSum(IOUtils.readPerGroup("input/day6.in")));
    }
}
