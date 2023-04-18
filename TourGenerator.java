import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TourGenerator {


    public static List<City> generateRandomTour(Map<Integer, City> cities) {
        // Create a list of all the city IDs
        List<Integer> cityIds = new ArrayList<>(cities.keySet());

        // Shuffle the list of city IDs to generate a random tour
        Collections.shuffle(cityIds);

        // Create a new list to hold the tour
        List<City> tour = new ArrayList<>();

        // Add each city in the shuffled order to the tour
        for (Integer cityId : cityIds) {
            tour.add(cities.get(cityId));
        }

        // Return the random tour
        return tour;
    }

}
