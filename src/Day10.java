import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day10 {

    static int part1(List<Integer> adaptorArr) {
        Collections.sort(adaptorArr);
        int prev = 0, countOne = 0, countThree = 1;
        for (int adp: adaptorArr) {
            switch (adp - prev) {
                case 1:
                    countOne += 1;
                    break;
                case 3:
                    countThree += 1;
                    break;
            }
            prev = adp;
        }
        return countOne * countThree;
    }

    static long part2(List<Integer> adpArr) {
        Collections.sort(adpArr);

        List<Integer> diff = new ArrayList<>();
        diff.add(adpArr.get(0));
        for (int i = 1; i < adpArr.size(); i++) {
            diff.add(adpArr.get(i) - adpArr.get(i - 1));
        }

        long count = 1;
        int countOne = 0;
        for (int delta: diff) {
            if (delta == 1) {
                countOne += 1;
            } else {
                count = processCount(count, countOne);
                countOne = 0;
            }
        }
        return processCount(count, countOne);
    }

    private static long processCount(long num, int countOne) {
        if (countOne == 4) {
            num *= 7;
        } else if (countOne == 3) {
            num *= 4;
        } else if (countOne == 2) {
            num *= 2;
        }
        return num;
    }

    public static void main(String[] args) {
        List<Integer> arr = IOUtils.readEveryLineInt("input/day10.in");
        System.out.println(part1(arr));
        System.out.println(part2(Arrays.asList(1,4,5,6,7,10,11,12,15,16)));
        System.out.println(part2(Arrays.asList(1,2,3,4,7,8,9,10,11,14,17,18,19,20,23,24,25,28,31,32,33,34,35,38,39,42,45,46,47,48,49,52)));
        System.out.println(part2(arr));
    }
}
