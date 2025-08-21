import java.util.*;

class BestFirstSearch {
    static class Cell {
        int x, y;
        double heuristic;
        Cell parent;

        Cell(int x, int y, double heuristic, Cell parent) {
            this.x = x;
            this.y = y;
            this.heuristic = heuristic;
            this.parent = parent;
        }
    }

    static int[][] directions = {
        {-1,-1}, {-1,0}, {-1,1},
        {0,-1},          {0,1},
        {1,-1}, {1,0}, {1,1}
    };

    static double heuristic(int x, int y, int goalX, int goalY) {
        return Math.sqrt(Math.pow(x - goalX, 2) + Math.pow(y - goalY, 2));
    }

    public static List<int[]> bestFirstSearch(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return new ArrayList<>();

        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingDouble(c -> c.heuristic));
        boolean[][] visited = new boolean[n][n];

        Cell start = new Cell(0, 0, heuristic(0, 0, n-1, n-1), null);
        pq.offer(start);

        while (!pq.isEmpty()) {
            Cell current = pq.poll();

            if (current.x == n-1 && current.y == n-1) {
                List<int[]> path = new ArrayList<>();
                while (current != null) {
                    path.add(new int[]{current.x, current.y});
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            visited[current.x][current.y] = true;

            for (int[] dir : directions) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < n && ny < n && grid[nx][ny] == 0 && !visited[nx][ny]) {
                    pq.offer(new Cell(nx, ny, heuristic(nx, ny, n-1, n-1), current));
                }
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        int[][] grid = {{0,1},{1,0}};
        List<int[]> path = bestFirstSearch(grid);
        if (path.isEmpty()) {
            System.out.println("Best First Search → Path length: -1");
        } else {
            System.out.print("Best First Search → Path length: " + path.size() + ", Path: ");
            System.out.println(pathToString(path));
        }
    }

    private static String pathToString(List<int[]> path) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < path.size(); i++) {
            sb.append("(").append(path.get(i)[0]).append(",").append(path.get(i)[1]).append(")");
            if (i < path.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

