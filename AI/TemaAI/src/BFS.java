import java.util.*;

public class BFS {
    public static class Result {
        public List<String> path;
        public int cost;

        public Result(List<String> path, int cost) {
            this.path = path;
            this.cost = cost;
        }
    }

    public static class Node {
        public List<String> path;
        public int cost;

        public Node(List<String> path, int cost) {
            this.path = path;
            this.cost = cost;
        }
    }

    // BFS implementation
    public static Result bfsTSP(String[] cities, int[][] distances) {
        int n = cities.length;
        Queue<Node> queue = new LinkedList<>();
        List<String> startPath = new ArrayList<>();
        startPath.add(cities[0]);
        queue.add(new Node(startPath, 0));

        int minCost = Integer.MAX_VALUE;
        List<String> bestPath = new ArrayList<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            List<String> currentPath = currentNode.path;
            int currentCost = currentNode.cost;

            if (currentPath.size() == n + 1 && currentPath.get(n).equals(cities[0])) {
                if (currentCost < minCost) {
                    minCost = currentCost;
                    bestPath = new ArrayList<>(currentPath);
                }
                continue;
            }

            String lastCity = currentPath.get(currentPath.size() - 1);

            for (String city : cities) {
                if (!currentPath.contains(city) || (currentPath.size() == n && city.equals(cities[0]))) {
                    List<String> newPath = new ArrayList<>(currentPath);
                    newPath.add(city);
                    int newCost = currentCost + distances[getIndex(cities, lastCity)][getIndex(cities, city)];
                    queue.add(new Node(newPath, newCost));
                }
            }
        }

        return new Result(bestPath, minCost);
    }

    private static int getIndex(String[] arr, String element) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}
