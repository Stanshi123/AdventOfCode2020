import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<int[]> remainders = IntStream.range(0, ids.length)
                .filter(i -> !ids[i].equals("x"))
                .mapToObj(i -> new int[]{Integer.parseInt(ids[i]), getRemainder(i, Integer.parseInt(ids[i]))})
                .collect(Collectors.toList());

        // Chinese Remainder Theorem
        long M = remainders.stream()
                .mapToLong(e -> e[0])
                .reduce(1, Math::multiplyExact);

        List<Long> t = remainders.stream()
                .map(e -> modInv(M / e[0], e[0]))
                .collect(Collectors.toList());

        long x = IntStream.range(0, t.size())
                .mapToLong(i -> remainders.get(i)[1] * t.get(i) * (M / remainders.get(i)[0]))
                .sum();

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
