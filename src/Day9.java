import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9 {

    // Part 1
    static long firstInvalid(List<Long> nums) {
        Map<Long, Integer> countMap = new HashMap<>();

        for (int i = 0; i < nums.size(); i++) {
            if (i >= 25) {
                if (!validNum(nums.get(i), countMap)) {
                    return nums.get(i);
                }
                removeOneOccurrence(nums.get(i - 25), countMap);
            }
            addOneOccurrence(nums.get(i), countMap);
        }

        return -1;
    }

    static boolean validNum(Long num, Map<Long, Integer> countMap) {
        for (Map.Entry<Long, Integer> entry: countMap.entrySet()) {
            long complement = num - entry.getKey();
            if (complement != entry.getKey() && countMap.containsKey(complement)) {
                return true;
            } else if (complement == entry.getKey() && entry.getValue() >= 2) {
                return true;
            }
        }
        return false;
    }

    static void addOneOccurrence(Long num, Map<Long, Integer> countMap) {
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
    }

    static void removeOneOccurrence(Long num, Map<Long, Integer> countMap) {
        if (!countMap.containsKey(num)) return;
        int count = countMap.get(num);
        if (count == 1) {
            countMap.remove(num);
        } else {
            countMap.put(num, count - 1);
        }
    }

    // Part 2
    static long findWeakness(List<Long> nums, Long target) {
        for (int i = 0; i < nums.size() - 1; i++) {
            Long sum = nums.get(i);
            for (int j = i + 1; j < nums.size(); j++) {
                sum += nums.get(j);
                if (sum.equals(target)) {
                    return nums.subList(i, j + 1).stream().max(Long::compareTo).get() +
                            nums.subList(i, j + 1).stream().min(Long::compareTo).get() ;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        List<Long> nums = IOUtils.readEveryLineLong("input/day9.in");
        Long firstInvalid = firstInvalid(nums);
        System.out.println(firstInvalid);
        System.out.println(findWeakness(nums, firstInvalid));
    }
}
