import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {

    static int calculateId(String seat) {
        int low = 0, high = 127;
        for (int i = 0; i < 7; i++) {
            char c = seat.charAt(i);
            if (c == 'F') { // F keep lower half
                high = low + (high - low) / 2;
            } else {
                low = low + (high - low) / 2 + 1;
            }
        }
        int row = low;

        low = 0; high = 7;
        for (int i = 0; i < 3; i++) {
            char c = seat.charAt(7 + i);
            if (c == 'L') { // L keep lower half
                high = low + (high - low) / 2;
            } else {
                low = low + (high - low) / 2 + 1;
            }
        }
        int col = low;

        return 8 * row + col;
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
        List<String> seats = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day5.in"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                seats.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(biggestId(seats));
        System.out.println(findMissingId(ids(seats)));
    }
}
