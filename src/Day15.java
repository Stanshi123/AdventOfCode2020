import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {
    static int run(List<Integer> input, int iteration) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < input.size() - 1; i++) {
            map.put(input.get(i), i + 1);
        }

        int last = input.get(input.size() - 1);
        for (int i = input.size() + 1; i <= iteration; i++) {
            if (map.containsKey(last)) {
                int newLast = i - 1 - map.get(last);
                map.put(last, i - 1);
                last = newLast;
            } else {
                map.put(last, i - 1);
                last = 0;
            }
            if (i == iteration) {
                return last;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(run(Arrays.asList(1,0,15,2,10,13), 2020));
        System.out.println(run(Arrays.asList(1,0,15,2,10,13), 30000000));
    }
}