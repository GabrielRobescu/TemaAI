import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Number of cities
        Random rand = new Random();
        int numberOfCities = rand.nextInt(10) + 3;
        String[] cities = generateCities(numberOfCities);
        int[][] distances = generateRandomGraph(numberOfCities);

        System.out.println("Cities: " + Arrays.toString(cities));
        System.out.println("Distances:");
        for (int[] row : distances) {
            System.out.println(Arrays.toString(row));
        }

        long startTime, endTime, duration;

        startTime = System.nanoTime();
        BFS.Result result = BFS.bfsTSP(cities, distances);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;  // divide by 1000000 to get milliseconds.
        System.out.println("BFS execution time: " + duration + " ms");
        System.out.println("Best path: " + result.path);
        System.out.println("Cost: " + result.cost);

        startTime = System.nanoTime();
        UniformCost.Result result2 = UniformCost.uniformCostTSP(cities, distances);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;  // divide by 1000000 to get milliseconds.
        System.out.println("Uniform Cost execution time: " + duration + " ms");
        System.out.println("Best path: " + result2.path);
        System.out.println("Cost: " + result2.cost);

        startTime = System.nanoTime();
        AStar.Result result3 = AStar.aStarTSP(cities, distances);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;  // divide by 1000000 to get milliseconds.
        System.out.println("A* execution time: " + duration + " ms");
        System.out.println("Best path: " + result3.path);
        System.out.println("Cost: " + result3.cost);

    }

    private static String[] generateCities(int numberOfCities) {
        String[] cities = new String[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            cities[i] = "City" + i;
        }
        return cities;
    }

    private static int[][] generateRandomGraph(int numberOfCities) {
        Random rand = new Random();
        int[][] distances = new int[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                } else {
                    distances[i][j] = rand.nextInt(10) + 1; // Random distance between 1 and 100
                    distances[j][i] = distances[i][j]; // Symmetric distance
                }
            }
        }
        return distances;
    }
}