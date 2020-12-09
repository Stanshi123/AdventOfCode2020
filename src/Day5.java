import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day5 {

    static int calculateId(String seat) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seat.length(); i++) {
            if (seat.charAt(i) == 'F' || seat.charAt(i) == 'L') {
                sb.append('0');
            } else {
                sb.append('1');
            }
        }
        return Integer.parseInt(sb.toString(), 2);
    }

    static List<Integer> ids(List<String> seats) {
        List<Integer> ids = new ArrayList<>();
        for (String seat: seats) {
            ids.add(calculateId(seat));
        }
        Collections.sort(ids);
        return ids;
    }

    static int findMissingId(List<Integer> ids) {
        for (int i = 1; i < ids.size(); i++) {
            if (!ids.get(i).equals(ids.get(i - 1) + 1)) {
                return ids.get(i) - 1;
            }
        }
        return -1;
    }

    static int biggestId(List<String> seats) {
        int maxId = -1;

        for (String seat: seats) {
            int id = calculateId(seat);
            maxId = Math.max(id, maxId);
        }
        return maxId;
    }

    public static void main(String[] args) {
        List<String> seats = IOUtils.readEveryLine("input/day5.in");
        System.out.println(biggestId(seats));
        System.out.println(findMissingId(ids(seats)));
    }
}
