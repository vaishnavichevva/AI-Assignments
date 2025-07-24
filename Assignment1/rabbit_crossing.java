import java.util.*;

public class RabbitLeap {

    static final String START = "EEE_WWW";
    static final String GOAL = "WWW_EEE";

    // Move generator: returns all valid next states
    public static List<String> getNextStates(String state) {
        List<String> nextStates = new ArrayList<>();
        char[] chars = state.toCharArray();
        int empty = state.indexOf('_');

        // East-bound rabbit 'E' can move right
        if (empty > 0 && chars[empty - 1] == 'E') {
            swap(chars, empty, empty - 1);
            nextStates.add(new String(chars));
            swap(chars, empty, empty - 1); // backtrack
        }

        if (empty > 1 && chars[empty - 2] == 'E') {
            swap(chars, empty, empty - 2);
            nextStates.add(new String(chars));
            swap(chars, empty, empty - 2);
        }

        // West-bound rabbit 'W' can move left
        if (empty < 6 && chars[empty + 1] == 'W') {
            swap(chars, empty, empty + 1);
            nextStates.add(new String(chars));
            swap(chars, empty, empty + 1);
        }

        if (empty < 5 && chars[empty + 2] == 'W') {
            swap(chars, empty, empty + 2);
            nextStates.add(new String(chars));
            swap(chars, empty, empty + 2);
        }

        return nextStates;
    }

    // Utility method to swap characters
    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // BFS search for shortest solution
    public static void solveBFS() {
        System.out.println("BFS Solution Path:");
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(List.of(START));
        visited.add(START);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String current = path.get(path.size() - 1);

            if (current.equals(GOAL)) {
                for (String state : path)
                    System.out.println(state);
                return;
            }

            for (String next : getNextStates(current)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(newPath);
                }
            }
        }
    }

    // DFS search (prints any valid solution path)
    public static void solveDFS() {
        System.out.println("DFS Solution Path:");
        Set<String> visited = new HashSet<>();
        dfsHelper(START, new ArrayList<>(), visited);
    }

    private static boolean dfsHelper(String current, List<String> path, Set<String> visited) {
        path.add(current);
        visited.add(current);

        if (current.equals(GOAL)) {
            for (String state : path)
                System.out.println(state);
            return true;
        }

        for (String next : getNextStates(current)) {
            if (!visited.contains(next)) {
                if (dfsHelper(next, path, visited)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    // Main method
    public static void main(String[] args) {
        solveBFS();  // Find the shortest path
        System.out.println("\n-----------------------\n");
        solveDFS();  // Find any valid path
    }
}

