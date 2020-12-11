import java.util.Arrays;

public class Day11 {

    static final int[][] DIRS = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {-1, -1}, {1 , 1}, {-1, 1}, {1, -1}};
    // Part 1
    static int run(char[][] grid, boolean part1) {
        while (true) {
            char[][] newGrid = oneIteration(grid, part1);
            if (identical(grid, newGrid)) {
                return countOccupied(grid);
            }
            grid = newGrid;
        }
    }

    static int countOccupied(char[][] grid) {
        int count = 0;
        for (char[] chars : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (chars[j] == '#') count += 1;
            }
        }
        return count;
    }

    static char[][] oneIteration(char[][] grid, boolean part1) {
        char[][] newGrid = copy(grid);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                applyRules(grid, newGrid, i, j, part1);
            }
        }
        return newGrid;
    }

    static boolean identical(char[][] grid, char[][] newGrid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != newGrid[i][j]) return false;
            }
        }
        return true;
    }

    static boolean validPosition(char[][] grid, int i, int j) {
        return i >= 0 && j >= 0 && i < grid.length && j < grid[0].length;
    }

    static void applyRules(char[][] grid, char[][] newGrid, int i, int j, boolean part1) {
        if (grid[i][j] == '.') return;
        boolean shouldFlip = true;
        if (grid[i][j] == 'L') {
            if (part1) {
                for (int[] dir : DIRS) {
                    if ((validPosition(grid, i + dir[0], j + dir[1])) && (grid[i + dir[0]][j + dir[1]] == '#')) {
                        shouldFlip = false;
                        break;
                    }
                }
            } else {
                int occupied = checkSurOccupied(grid, i, j);
                if (occupied != 0) {
                    shouldFlip = false;
                }
            }
        } else if (grid[i][j] == '#') {
            if (part1) {
                int occupiedCount = 0;
                for (int[] dir : DIRS) {
                    if ((validPosition(grid, i + dir[0], j + dir[1])) && (grid[i + dir[0]][j + dir[1]] == '#')) {
                        occupiedCount += 1;
                    }
                }
                if (occupiedCount < 4) {
                    shouldFlip = false;
                }
            } else { // part2
                int occupied = checkSurOccupied(grid, i, j);
                if (occupied < 5) {
                    shouldFlip = false;
                }
            }
        }
        if (shouldFlip) {
            newGrid[i][j] = grid[i][j] == 'L' ? '#': 'L';
        }
    }

    static char[][] copy(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return copy;
    }

    // Part 2
    static int checkSurOccupied(char[][] grid, int i, int j) {
        int occupiedSeats = 0;
        for (int[] dir: DIRS) {
            int step = 1;
            while (validPosition(grid, i + step * dir[0], j + step * dir[1])) {
                if (grid[i + step * dir[0]][j + step * dir[1]] == 'L') { break; }
                else if (grid[i + step * dir[0]][j + step * dir[1]] == '#') {
                    occupiedSeats += 1;
                    break;
                }
                step++;
            }
        }
        return occupiedSeats;
    }

    public static void main(String[] args) {
        char[][] grid = IOUtils.readAsGrid("input/day11.in");
        System.out.println(run(grid, true));
        System.out.println(run(grid, false));
    }
}
