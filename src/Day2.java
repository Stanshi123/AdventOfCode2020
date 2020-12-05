import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

    static int checkValid(List<String> entries) {
        int validCount = 0;

        String regex = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)";
        Pattern pattern = Pattern.compile(regex);
        for (String entry: entries) {
            Matcher matcher = pattern.matcher(entry);
            if (matcher.find()) {
                int min = Integer.parseInt(matcher.group(1));
                int max = Integer.parseInt(matcher.group(2));
                char character = matcher.group(3).charAt(0);
                String content = matcher.group(4);

                int charCount = 0;
                for (int i = 0; i < content.length(); i++) {
                    if (content.charAt(i) == character) {
                        charCount++;
                    }
                }

                if (charCount >= min && charCount <= max) {
                    validCount++;
                }
            }
        }
        return validCount;
    }

    static int checkValidNewPolicy(List<String> entries) {
        int validCount = 0;

        String regex = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)";
        Pattern pattern = Pattern.compile(regex);
        for (String entry: entries) {
            Matcher matcher = pattern.matcher(entry);
            if (matcher.find()) {
                int place1 = Integer.parseInt(matcher.group(1)) - 1;
                int place2 = Integer.parseInt(matcher.group(2)) - 1;
                char character = matcher.group(3).charAt(0);
                String content = matcher.group(4);

                if ((content.charAt(place1) == character && content.charAt(place2) != character) ||
                (content.charAt(place1) != character && content.charAt(place2) == character)) {
                    validCount++;
                }
            }
        }
        return validCount;
    }

    public static void main(String[] args) {
        List<String> entries = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day2.in"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                entries.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(checkValid(entries));
        System.out.println(checkValidNewPolicy(entries));
    }
}
