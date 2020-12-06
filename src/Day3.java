import java.util.List;

public class Day3 {
    static int countTreeEncounter(List<String> grid, int right, int down) {
        int x = 0;

        int treeCount = 0;
        for (int i = 0; i < grid.size(); i += down) {
            String level = grid.get(i);
            if (x >= level.length()) {
                x -= level.length();
            }
            if (level.charAt(x) == '#') {
                treeCount++;
            }

            x += right;
        }
        return treeCount;
    }


    public static void main(String[] args) {
        List<String> grid = IOUtils.readEveryLine("day3.in");
        System.out.println(countTreeEncounter(grid,3 ,1));
        System.out.println(countTreeEncounter(grid,1 ,1));
        System.out.println(countTreeEncounter(grid,5 ,1));
        System.out.println(countTreeEncounter(grid,7,1));
        System.out.println(countTreeEncounter(grid,1 ,2));
        long count = (long) countTreeEncounter(grid, 3, 1)
                * countTreeEncounter(grid,1 ,1)
                * countTreeEncounter(grid,5 ,1)
                * countTreeEncounter(grid,7,1)
                * countTreeEncounter(grid,1 ,2);
        System.out.println(count);
    }
}
