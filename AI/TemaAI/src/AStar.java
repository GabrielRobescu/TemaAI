import java.util.*;

public class AStar {
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
        public int heuristic;

        public Node(List<String> path, int cost, int heuristic) {
            this.path = path;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
        }
    }

    public static Result aStarTSP(String[] cities, int[][] distances) {
        int n = cities.length;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        List<String> startPath = new ArrayList<>();
        startPath.add(cities[0]);
        pq.add(new Node(startPath, 0, heuristic(cities[0], startPath, cities, distances)));

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
                    int newHeuristic = heuristic(city, newPath, cities, distances);
                    pq.add(new Node(newPath, newCost, newHeuristic));
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

    private static int heuristic(String currentCity, List<String> currentPath, String[] cities, int[][] distances) {
        Set<String> remainingCities = new HashSet<>(Arrays.asList(cities));
        remainingCities.removeAll(currentPath);

        if (remainingCities.isEmpty()) {
            return distances[getIndex(cities, currentCity)][getIndex(cities, cities[0])];
        }

        int mstCost = calculateMST(remainingCities, cities, distances);
        int returnCost = distances[getIndex(cities, currentCity)][getIndex(cities, cities[0])];

        return mstCost + returnCost;
    }

    private static int calculateMST(Set<String> remainingCities, String[] cities, int[][] distances) {
        int mstCost = 0;
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.cost));
        Map<String, Boolean> inMST = new HashMap<>();
        for (String city : remainingCities) {
            inMST.put(city, false);
        }

        String start = remainingCities.iterator().next();
        inMST.put(start, true);

        for (String city : remainingCities) {
            if (!city.equals(start)) {
                edgeQueue.add(new Edge(start, city, distances[getIndex(cities, start)][getIndex(cities, city)]));
            }
        }

        while (!remainingCities.isEmpty() && !edgeQueue.isEmpty()) { // Check if edgeQueue is empty
            Edge minEdge = edgeQueue.poll();
            if (minEdge == null || (inMST.get(minEdge.city1) && inMST.get(minEdge.city2))) continue; // Check if minEdge is null

            mstCost += minEdge.cost;
            String newCity = inMST.get(minEdge.city1) ? minEdge.city2 : minEdge.city1;
            remainingCities.remove(newCity);
            inMST.put(newCity, true);

            for (String city : remainingCities) {
                edgeQueue.add(new Edge(newCity, city, distances[getIndex(cities, newCity)][getIndex(cities, city)]));
            }
        }

        return mstCost;
    }

    private static class Edge {
        String city1;
        String city2;
        int cost;

        Edge(String city1, String city2, int cost) {
            this.city1 = city1;
            this.city2 = city2;
            this.cost = cost;
        }
    }
}
