import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TwoOpt {

    // Define a constant for the number of iterations to perform
    private static final int ITERATIONS = 100000;

    // Define a method to perform 2-opt on a map of cities
    public static List<City> twoOpt(Map<Integer, City> cities) {
        // Convert the map to a list for easier manipulation
        List<City> cityList = new ArrayList<>(cities.values());

        // Initialize the best distance and best route
        double bestDistance = calculateTotalDistance(cityList);
        List<City> bestRoute = new ArrayList<>(cityList);

        // Perform 2-opt for the specified number of iterations
        for (int i = 0; i < ITERATIONS; i++) {
            // Choose two random cities to swap
            int index1 = (int) (Math.random() * cityList.size());
            int index2 = (int) (Math.random() * cityList.size());

            // Ensure that the two indices are different
            while (index2 == index1) {
                index2 = (int) (Math.random() * cityList.size());
            }

            // Calculate the indices for the two edges to be reversed
            int index1a = Math.min(index1, index2);
            int index2a = Math.max(index1, index2);

            // Reverse the order of the cities between the two edges
            Collections.reverse(cityList.subList(index1a, index2a));

            // Calculate the new distance for the route
            double newDistance = calculateTotalDistance(cityList);

            // If the new distance is better, update the best distance and route
            if (newDistance < bestDistance) {
                bestDistance = newDistance;
                bestRoute = new ArrayList<>(cityList);
            } else {
                // Otherwise, undo the reversal
                Collections.reverse(cityList.subList(index1a, index2a));
            }
        }

        // Return the best route
        return bestRoute;
    }

    // Define a method to calculate the total distance of a route
    private static double calculateTotalDistance(List<City> route) {
        double totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            City currentCity = route.get(i);
            City nextCity = route.get(i + 1);
            totalDistance += currentCity.distanceTo(nextCity);
        }
        // Add the distance back to the starting city to complete the route
        totalDistance += route.get(route.size() - 1).distanceTo(route.get(0));
        return totalDistance;
    }
}
