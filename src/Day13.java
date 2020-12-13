import java.util.ArrayList;
import java.util.List;

public class Day13 {

    static int part1(List<String> input) {
        int start = Integer.parseInt(input.get(0));
        String[] ids = input.get(1).split(",");
        List<Integer> buses = new ArrayList<>();
        for (String id: ids) {
            if (id.equals("x")) continue;
            buses.add(Integer.parseInt(id));
        }
        for (int i = start; ; i++) {
            for (int bus: buses) {
                if (i % bus == 0) {
                    return bus * (i - start);
                }
            }
        }
    }

    static long part2(String input) {
        String[] ids = input.split(",");
        List<int[]> remainders = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            if (!ids[i].equals("x")) {
                remainders.add(new int[]{Integer.parseInt(ids[i]), getRemainder(i, Integer.parseInt(ids[i]))});
            }
        }

        // Chinese Remainder Theorem
        long M = 1;
        for (int[] rem: remainders) {
            M *= rem[0];
        }

        long[] t = new long[remainders.size()];
        for (int i = 0; i < t.length; i++) {
            t[i] = modInv(M / remainders.get(i)[0], remainders.get(i)[0]);
        }

        long x = 0;
        for (int i = 0; i < t.length; i++) {
            x += remainders.get(i)[1] * t[i] * (M / remainders.get(i)[0]);
        }

        while (x - M > 0) {
            x -= M;
        }

        return x;
    }

    static int getRemainder(int index, int mod) {
        return (mod - index % mod) == mod ? 0 : mod - index % mod;
    }

    static long extGCD(long a, long b, long[] x, long[] y)
    {
        if (b == 0) {
            x[0] = 1; y[0] = 0;
            return a;
        }
        long gcd = extGCD(b, a % b, y, x);
        y[0] -= (a / b) * x[0];
        return gcd;
    }

    static long modInv(long a, long n) {
        long[] x = new long[]{0};
        return extGCD(a, n, x, new long[]{0}) == 1 ? (x[0] + n) % n : -1;
    }

    public static void main(String[] args) {
        List<String> input = IOUtils.readEveryLine("input/day13.in");
        System.out.println(part1(input));
        System.out.println(part2(input.get(1)));

    }
}
