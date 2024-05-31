import java.util.*;

public class UniformCost {
    public static class Result {
        public List<String> path;
        public int cost;

        public Result(List<String> path, int cost) {
            this.path = path;
            this.cost = cost;
        }
    }

    public static class Node implements Comparable<Node> {
        public List<String> path;
        public int cost;

        public Node(List<String> path, int cost) {
            this.path = path;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    // Uniform Cost Search implementation
    public static Result uniformCostTSP(String[] cities, int[][] distances) {
        int n = cities.length;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        List<String> startPath = new ArrayList<>();
        startPath.add(cities[0]);
        pq.add(new Node(startPath, 0));

        int minCost = Integer.MAX_VALUE;
        List<String> bestPath = new ArrayList<>();

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
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
            int lastCityIndex = getIndex(cities, lastCity);

            for (String city : cities) {
                if (!currentPath.contains(city) || (currentPath.size() == n && city.equals(cities[0]))) {
                    int cityIndex = getIndex(cities, city);
                    int newCost = currentCost + distances[lastCityIndex][cityIndex];
                    List<String> newPath = new ArrayList<>(currentPath);
                    newPath.add(city);
                    pq.add(new Node(newPath, newCost));
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
