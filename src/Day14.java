import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    static class Op {
        public String mask; public List<long[]> ops;
        Op(String mask, List<long[]> ops) {
            this.mask = mask; this.ops = ops;
        }
    }

    static List<Op> parse(List<String> input) {
        List<Op> output = new ArrayList<>();
        Pattern pattern = Pattern.compile("mem\\[([0-9]+)] = ([0-9]+)");
        for (int i = 0; i < input.size();) {
            String maskString = input.get(i).split(" = ")[1];
            List<long[]> ops = new ArrayList<>();
            i+= 1;
            while (i < input.size() && !input.get(i).startsWith("mask")) {
                Matcher matcher = pattern.matcher(input.get(i));
                if (matcher.find()) {
                    ops.add(new long[]{Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2))});
                }
                i++;
            }
            output.add(new Op(maskString, ops));
        }
        return output;
    }

    static String getAddStr(long add) {
        String addStr = Long.toBinaryString(add);
        return "0".repeat(Math.max(0, 36 - addStr.length())) + addStr;
    }
    static long part2(List<String> input) {
        Map<Long, Long> mem = new HashMap<>();
        List<Op> ops = parse(input);
        for (Op op: ops) {
            List<Integer> floatingBits = new ArrayList<>();
            for (int i = 0; i < 36; i++) {
                if (op.mask.charAt(i) == 'X') {
                    floatingBits.add(i);
                }
            }
            for (long[] line: op.ops) {
                StringBuilder addBuilder = new StringBuilder();
                String addStr = getAddStr(line[0]);
                for (int i = 0; i < 36; i++) {
                    if (op.mask.charAt(i) == '0') {
                        addBuilder.append(addStr.charAt(i));
                    } else if (op.mask.charAt(i) == '1') {
                        addBuilder.append("1");
                    } else {
                        addBuilder.append("0");
                    }
                }
                List<Long> adds = floatingAdd(floatingBits, addBuilder);
                adds.forEach(l -> mem.put(l, line[1]));
            }
        }
        return sum(mem);
    }

    static List<Long> floatingAdd(List<Integer> floatingBits, StringBuilder sb) {
        List<Long> ret = new ArrayList<>();
        helper(ret, floatingBits, 0, sb);
        return ret;
    }

    static void helper(List<Long> adds, List<Integer> floatingBits, int index, StringBuilder res) {
        if (index == floatingBits.size()) {
            adds.add(Long.parseLong(res.toString(), 2));
            return;
        }
        res.setCharAt(floatingBits.get(index), '0');
        helper(adds, floatingBits, index + 1, res);
        res.setCharAt(floatingBits.get(index), '1');
        helper(adds, floatingBits, index + 1, res);
    }

    static long part1(List<String> input) {
        Map<Long, Long> mem = new HashMap<>();
        List<Op> ops = parse(input);
        for (Op op: ops) {
            BitSet xx = new BitSet(36);
            BitSet mask = new BitSet(36);
            for (int j = 0; j < op.mask.length(); j++) {
                if (op.mask.charAt(j) == 'X') {
                    xx.set(35 - j, true);
                } else {
                    mask.set(35 - j, op.mask.charAt(j) == '1');
                }
            }
            for (long[] operation: op.ops) {
                BitSet value = longToBitSet(operation[1]);
                value.and(xx);
                value.or(mask);
                mem.put(operation[0], value.toLongArray()[0]);
            }
        }
        return sum(mem);
    }

    static long sum(Map<Long, Long> mem) {
        Long sum = 0L;
        for (Map.Entry<Long, Long> entry: mem.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public static BitSet longToBitSet(Long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    public static void main(String[] args) {
       System.out.println(part1(IOUtils.readEveryLine("input/day14.in")));
       System.out.println(part2(IOUtils.readEveryLine("input/day14.in")));
    }
}
