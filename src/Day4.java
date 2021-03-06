import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    static boolean checkExists(String passport) {
        return passport.contains("byr")
                && passport.contains("iyr")
                && passport.contains("eyr")
                && passport.contains("hgt")
                && passport.contains("hcl")
                && passport.contains("ecl")
                && passport.contains("pid");
    }

    static boolean validYear(String passport, String regex, int min, int max) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passport);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1));
            return min <= year && year <= max;
        }
        return false;
    }

    static boolean validHeight(String passport) {
        String regex = "hgt:([0-9]+)(cm|in)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passport);
        if (matcher.find()) {
            int hgt = Integer.parseInt(matcher.group(1));
            switch (matcher.group(2)){
                case "in":
                    return 59 <= hgt && hgt <= 76;
                case "cm":
                    return 150 <= hgt && hgt <= 193;
            }
        }
        return false;
    }

    static boolean validHairColor(String passport) {
        String regex = "hcl:#([a-zA-Z0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passport);
        if (matcher.find()) {
            return matcher.group(1).matches("^[0-9a-f]{6}$");
        }
        return false;
    }

    static boolean validEyeColor(String passport) {
        String regex = "ecl:([a-zA-Z0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passport);
        if (matcher.find()) {
            return matcher.group(1).matches("amb|blu|brn|gry|grn|hzl|oth");
        }
        return false;
    }

    static boolean validPid(String passport) {
        String regex = "pid:([a-zA-Z0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passport);
        if (matcher.find()) {
            return matcher.group(1).matches("^[0-9]{9}$");
        }
        return false;
    }

    static boolean checkValid(String passport) {
        return validYear(passport, "byr:([0-9]+)", 1920, 2002)
                && validYear(passport, "iyr:([0-9]+)", 2010, 2020)
                && validYear(passport, "eyr:([0-9]+)", 2020, 2030)
                && validHeight(passport)
                && validHairColor(passport)
                && validEyeColor(passport)
                && validPid(passport);
    }

    static int countExists(List<String> passports) {
        int count = 0;
        for (String passport: passports) {
            if (checkExists(passport)) {
                    count++;
            }
        }
        return count;
    }

    static int countValid(List<String> passports) {
        int count = 0;

        for (String passport: passports) {
            if (checkValid(passport)) {
                    count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        List<String> passports = IOUtils.readAndConcatenate("input/day4.in", " ");
        System.out.println(countExists(passports));
        System.out.println(countValid(passports));
    }
}
