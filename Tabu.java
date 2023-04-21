import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabu {

    private int tabuTenure;
    private static int maxIterations = 10000;

    public static List<City> runTabuSearch(Map<Integer, City> cities, int tabuTenure) {

        List<City> bestTour = TwoOpt.twoOpt(cities);
        System.out.println("Improved tour distance (2-opt to Tabu): " + Main.calculateTourDistance(bestTour));

        int bestCost = calculateTourCost(bestTour);
        List<City> currentTour = new ArrayList<>(bestTour);
        int currentCost = bestCost;
        Map<Integer, Integer> tabuList = new HashMap<>();

        for (int i = 0; i < maxIterations; i++) {
            List<City> candidateTour = null;
            int candidateCost = Integer.MAX_VALUE;

            int j;
            int k = 0;
            for (j = 0; j < cities.size(); j++) {
                for (k = j + 1; k < cities.size(); k++) {
                    List<City> newTour = swapCities(currentTour, j, k);
                    int newCost = calculateTourCost(newTour);

                    if (newCost < candidateCost && !isTabu(j, k, tabuList, i)) {
                        candidateTour = newTour;
                        candidateCost = newCost;
                    }
                }
            }

            if (candidateTour == null) {
                break;
            }

            currentTour = candidateTour;
            currentCost = candidateCost;

            if (currentCost < bestCost) {
                bestTour = currentTour;
                bestCost = currentCost;
            }

            updateTabuList(tabuList, i, j, k, tabuTenure);
        }

        System.out.println("-------------------TABU TOUR--------------------");

        for (City city : bestTour) {
            System.out.println(city.getId() + " " + city.getX() + " " + city.getY());
        }
        System.out.println("----------------------------------------");


        return bestTour;
    }

    private static List<City> swapCities(List<City> tour, int i, int j) {
        List<City> newTour = new ArrayList<>(tour);
        Collections.swap(newTour, i, j);
        return newTour;
    }

    private static int calculateTourCost(List<City> tour) {
        int cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            cost += tour.get(i).distanceTo(tour.get(i + 1));
        }
        cost += tour.get(tour.size() - 1).distanceTo(tour.get(0));
        return cost;
    }

    private static boolean isTabu(int i, int j, Map<Integer, Integer> tabuList, int currentIteration) {
        int tabuKey = getTabuKey(i, j);
        if (tabuList.containsKey(tabuKey)) {
            return tabuList.get(tabuKey) >= currentIteration;
        }
        return false;
    }

    private static void updateTabuList(Map<Integer, Integer> tabuList, int currentIteration, int i, int j, int tabuTenure) {
        for (Integer key : new ArrayList<>(tabuList.keySet())) {
            Integer value = tabuList.get(key);
            if (value <= currentIteration) {
                tabuList.remove(key);
            }
        }
        tabuList.put(getTabuKey(i, j), currentIteration + tabuTenure);
    }

    private static int getTabuKey(int i, int j) {
        return i * 200 + j;
    }
}
