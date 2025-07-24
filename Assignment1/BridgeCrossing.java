import java.util.*;

public class BridgeCrossing {

    static class State {
        Set<String> left;   // People on the starting side
        Set<String> right;  // People on the far side
        boolean isUmbrellaLeft;
        int time;
        List<String> path;

        State(Set<String> left, Set<String> right, boolean isUmbrellaLeft, int time, List<String> path) {
            this.left = new HashSet<>(left);
            this.right = new HashSet<>(right);
            this.isUmbrellaLeft = isUmbrellaLeft;
            this.time = time;
            this.path = new ArrayList<>(path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right, isUmbrellaLeft);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof State)) return false;
            State s = (State) obj;
            return left.equals(s.left) && right.equals(s.right) && isUmbrellaLeft == s.isUmbrellaLeft;
        }
    }

    static Map<String, Integer> timeMap = new HashMap<>();

    public static void main(String[] args) {
        timeMap.put("Amogh", 5);
        timeMap.put("Ameya", 10);
        timeMap.put("Grandma", 20);
        timeMap.put("Grandpa", 25);

        Set<String> start = new HashSet<>(timeMap.keySet());
        Set<String> goal = new HashSet<>();

        State initial = new State(start, goal, true, 0, new ArrayList<>());
        bfs(initial);
    }

    public static void bfs(State initial) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.add(initial);
        visited.add(initial);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.right.size() == 4 && !current.isUmbrellaLeft) {
                System.out.println("Solution found in " + current.time + " minutes:");
                for (String step : current.path) {
                    System.out.println(step);
                }
                return;
            }

            if (current.time > 60) continue;

            if (current.isUmbrellaLeft) {
                // Move two people from left to right
                List<String> leftList = new ArrayList<>(current.left);
                for (int i = 0; i < leftList.size(); i++) {
                    for (int j = i; j < leftList.size(); j++) {
                        String p1 = leftList.get(i);
                        String p2 = leftList.get(j);
                        Set<String> newLeft = new HashSet<>(current.left);
                        Set<String> newRight = new HashSet<>(current.right);
                        newLeft.remove(p1);
                        newLeft.remove(p2);
                        newRight.add(p1);
                        newRight.add(p2);

                        int newTime = current.time + Math.max(timeMap.get(p1), timeMap.get(p2));
                        List<String> newPath = new ArrayList<>(current.path);
                        newPath.add(p1 + " and " + p2 + " cross to right (" + newTime + " min)");

                        State newState = new State(newLeft, newRight, false, newTime, newPath);
                        if (!visited.contains(newState)) {
                            visited.add(newState);
                            queue.add(newState);
                        }
                    }
                }
            } else {
                // Move one person back from right to left
                for (String p : current.right) {
                    Set<String> newLeft = new HashSet<>(current.left);
                    Set<String> newRight = new HashSet<>(current.right);
                    newRight.remove(p);
                    newLeft.add(p);

                    int newTime = current.time + timeMap.get(p);
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(p + " returns to left (" + newTime + " min)");

                    State newState = new State(newLeft, newRight, true, newTime, newPath);
                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        queue.add(newState);
                    }
                }
            }
        }

        System.out.println("No solution found within 60 minutes.");
    }
}

