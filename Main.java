import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Integer, City> cities = null;
        try {
            //Read the cities from the file
            cities = CityReader.readCitiesFromFile("TSP_107.txt");

            // Generate a random tour of the cities
            List<City> tour = TourGenerator.generateRandomTour(cities);

            // Improve the tour using 2-opt
            //<City> improvedTour = TwoOpt.twoOpt(cities);

            // Print the distances of the original tour and the improved tour
            System.out.println("Original tour distance: " + calculateTourDistance(tour));
            //System.out.println("Improved tour distance (2-opt): " + calculateTourDistance(improvedTour));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Solve the TSP using simulated annealing
        List<City> tour = SA.solveTSP(cities, 10000, 0.000001);

        // Print the tour
        System.out.println("Tour (SA): ");
        for (City city : tour) {
            System.out.print(city.getId() + " ");
        }
        System.out.println();

        // Calculate and print the total distance
        double distance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        distance += tour.get(tour.size() - 1).distanceTo(tour.get(0));
        System.out.println("Total distance: " + distance);

        double gadistance = calculateTourDistance(GA.solveTSP(cities));
        System.out.println("GA distance: " + gadistance);
    }


    // Calculate the total distance of a tour
    public static double calculateTourDistance(List<City> tour) {
        double distance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        // Add the distance back to the starting city to complete the tour
        distance += tour.get(tour.size() - 1).distanceTo(tour.get(0));
        return distance;
    }
}
