import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOUtils {
    public static List<String> readEveryLine(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<Integer> readEveryLineInt(String filename) {
        List<Integer> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<List<String>> readPerGroup(String filename) {
        List<List<String>> groups = new ArrayList<>();
        List<String> group = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    groups.add(group);
                    group = new ArrayList<>();
                } else {
                    group.add(line);
                }
            }
            if (!group.isEmpty()) {
                groups.add(group);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public static List<String> readAndConcatenate(String filename, String delim) {
        List<List<String>> groups = readPerGroup(filename);
        List<String> result = new ArrayList<>();
        for (List<String> group: groups) {
            StringBuilder sb = new StringBuilder();
            for (String element: group) {
                sb.append(delim);
                sb.append(element);
            }
            result.add(sb.toString());
        }
        return result;
    }
}
