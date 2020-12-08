import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {

    static boolean validExecution(List<String> commands, int[] acc, Set<Integer> visited) {
        int counter = 0;

        String regex = "(acc|jmp|nop) (\\+|-)(\\d+)";
        Pattern pattern = Pattern.compile(regex);

        while (counter < commands.size()) {
            if (counter < 0 || visited.contains(counter)) {
                return false;
            }
            String command = commands.get(counter);
            visited.add(counter);
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                switch (matcher.group(1)) {
                    case "jmp":
                        if (matcher.group(2).equals("+")) {
                            counter += Integer.parseInt(matcher.group(3));
                        } else {
                            counter -= Integer.parseInt(matcher.group(3));
                        }
                        break;
                    case "acc":
                        if (matcher.group(2).equals("+")) {
                            acc[0] += Integer.parseInt(matcher.group(3));
                        } else {
                            acc[0] -= Integer.parseInt(matcher.group(3));
                        }
                    case "nop":
                        counter += 1;
                }
            }
        }
        return true;
    }

    static int accBeforeDuplicateCommand(List<String> commands) {
        int[] acc = new int[]{0};
        validExecution(commands, acc, new HashSet<>());
        return acc[0];
    }

    static void flipCommand(List<String> commands, int index) {
        String[] split = commands.get(index).split(" ");
        if (split[0].equals("jmp")) {
            commands.set(index, "nop " + split[1]);
        } else if (split[0].equals("nop")){
            commands.set(index, "jmp " + split[1]);
        }
    }

    static int fixAndRun(List<String> commands) {
        Set<Integer> visited = new HashSet<>();
        int[] acc = new int[]{0};
        validExecution(commands, acc, visited);

        for (int i : visited) {
            flipCommand(commands, i);
            int[] test_acc = new int[]{0};
            if (validExecution(commands, test_acc, new HashSet<>())) {
                return test_acc[0];
            }
            flipCommand(commands, i);
        }
        return -1;
    }


    public static void main(String[] args) {
        List<String> commands = IOUtils.readEveryLine("day8.in");
        System.out.println(accBeforeDuplicateCommand(commands));
        System.out.println(fixAndRun(commands));
    }
}
