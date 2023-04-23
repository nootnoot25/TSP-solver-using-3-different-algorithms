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

            System.out.println("Original tour distance: " + calculateTourDistance(tour));

            //double average2opt = 0;
            // Improve the tour using 2-opt
            //for (int i=0; i<30; i++) {
            //    List<City> improvedTour = TwoOpt.twoOpt(cities);
                //System.out.println("Improved tour distance (2-opt): " + calculateTourDistance(improvedTour));
            //    average2opt = average2opt + calculateTourDistance(improvedTour);
            //}

            //System.out.println("average 2-opt: " + (average2opt/30));

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        //for (int i = 0; i < 30; i++) {
            //long startTime = System.currentTimeMillis();

            //double sadistance = calculateTourDistance(SA.solveTSP(cities, 100000, 0.0000001));

            //long endTime = System.currentTimeMillis();
            //long duration = endTime - startTime;

            //System.out.println("SA: " + sadistance);
            //System.out.println(duration + " milliseconds");
        //}

        for (int i = 0; i < 30; i++) {
            long startTime = System.currentTimeMillis();

            double gadistance = calculateTourDistance(GA.solveTSP(cities));
            System.out.println("GA distance: " + gadistance);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println(duration + " milliseconds\n");
        }

        //for (int i = 0; i < 30; i++) {
        //startTime = System.currentTimeMillis();

        //double tabudistance = calculateTourDistance(Tabu.runTabuSearch(cities, 100));
        //System.out.println("tabu distance: " + tabudistance);

        //endTime = System.currentTimeMillis();
        //duration = endTime - startTime;
        //System.out.println(duration + " milliseconds");
        //}
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
