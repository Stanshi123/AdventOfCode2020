import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {
    static int reportRepair(List<Integer> nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(2020 - num)) {
                return (2020 - num) * num;
            }
            set.add(num);
        }

        return -1;
    }

    static int reportTriplets(List<Integer> nums) {
        Collections.sort(nums);

        for (int i = 0; i < nums.size() - 2; i++) {
            if (i != 0 && nums.get(i -1 ).equals(nums.get(i))) {
                continue;
            }

            int j = i + 1;
            int k = nums.size() - 1;
            while (j < k) {
                int sum = nums.get(i) + nums.get(j) + nums.get(k);
                if (sum == 2020) {
                    return nums.get(i) * nums.get(j) * nums.get(k);
                } else if (sum < 2020) {
                    j++;
                } else {
                    k--;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        List<Integer> nums = IOUtils.readEveryLineInt("day1.in");
        System.out.println(reportRepair(nums));
        System.out.println(reportTriplets(nums));
    }

}
