import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    static final char[] DIRS = new char[]{'E', 'S', 'W', 'N'};
    private static final Map<Character, int[]> DIRMAP = Map.of('E', new int[]{1, 0},
            'W', new int[]{-1, 0},
            'N', new int[]{0, 1},
            'S', new int[]{0, -1});

    static int distance(List<String> moves, boolean part1) {
        char curDir = 'E';
        int[] pos = new int[]{0,0};
        int[] wayPoints = new int[]{10, 1};
        Pattern pattern = Pattern.compile("([LREFSWN])([0-9]+)");
        for (String move: moves) {
            Matcher matcher = pattern.matcher(move);
            if (matcher.find()) {
                char instruction = matcher.group(1).charAt(0);
                int magnitude = Integer.parseInt(matcher.group(2));
                if (DIRMAP.containsKey(instruction)) {
                    if (part1) {
                        move(pos, magnitude, instruction);
                    } else move(wayPoints, magnitude, instruction);
                } else {
                    switch (instruction) {
                        case 'F':
                            if (part1) {
                                move(pos, magnitude, curDir);
                            } else move(pos, magnitude, wayPoints);
                            break;
                        case 'L':
                        case 'R':
                            if (part1) {
                                curDir = rotate(curDir, instruction == 'L', magnitude);
                            } else rotate(wayPoints, instruction == 'L', magnitude);

                    }
                }
            }
        }
        return Math.abs(pos[0]) + Math.abs(pos[1]);
    }

    private static char rotate(char dir, boolean left, int angle) {
        switch (dir) {
            case 'E':
                return left ? DIRS[parsePos(0, -angle / 90)] : DIRS[parsePos(0, angle / 90)];
            case 'W':
                return left ? DIRS[parsePos(2, -angle / 90)] : DIRS[parsePos(2, angle / 90)];
            case 'S':
                return left ? DIRS[parsePos(1, -angle / 90)] : DIRS[parsePos(1, angle / 90)];
            case 'N':
                return left ? DIRS[parsePos(3, -angle / 90)] : DIRS[parsePos(3, angle / 90)];
        }
        return '\n';
    }

    // [a, b] => [b ,-a] => [-a , -b] => [-b, a] => [a, b]
    private static void rotate(int[] wayPoint, boolean left, int angle) {
        int rotateTimes = (angle / 90) % 4;
        for (int i = 0; i < rotateTimes; i++) {
            int temp = wayPoint[0];
            if (left) {
                wayPoint[0] = -wayPoint[1];
                wayPoint[1] = temp;
            } else {
                wayPoint[0] = wayPoint[1];
                wayPoint[1] = -temp;
            }
        }
    }

    private static int parsePos(int init, int diff) {
        if (0 <= init + diff && init + diff < 4) {
            return init + diff;
        } else if (init + diff < 0) {
            return init + diff + 4;
        } else {
            return init + diff - 4;
        }
    }

    static void move(int[] pos, int magnitude, char dir) {
        pos[0] += magnitude * DIRMAP.get(dir)[0];
        pos[1] += magnitude * DIRMAP.get(dir)[1];
    }

    static void move(int[] pos, int magnitude, int[] waypoint) {
        pos[0] += magnitude * waypoint[0];
        pos[1] += magnitude * waypoint[1];
    }

    public static void main(String[] args) {
        List<String> moves = IOUtils.readEveryLine("input/day12.in");
        System.out.println(distance(moves, true));
        System.out.println(distance(moves, false));
    }
}
