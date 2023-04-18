import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabu {

    private int tabuTenure;
    private static int maxIterations;

    public static List<City> runTabuSearch(Map<Integer, City> cities) {

        List<City> bestTour = TwoOpt.twoOpt(cities);
        int bestCost = calculateTourCost(bestTour);
        List<City> currentTour = new ArrayList<>(bestTour);
        int currentCost = bestCost;
        Map<Integer, Integer> tabuList = new HashMap<>();

        for (int i = 0; i < maxIterations; i++) {
            List<City> candidateTour = null;
            int candidateCost = Integer.MAX_VALUE;

            for (int j = 0; j < cities.size(); j++) {
                for (int k = j + 1; k < cities.size(); k++) {
                    List<City> newTour = swapCities(currentTour, j, k);
                    int newCost = calculateTourCost(newTour);

                    if (newCost < candidateCost && !isTabu(j, k, tabuList)) {
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

            updateTabuList(tabuList);
        }

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

    private static boolean isTabu(int i, int j, Map<Integer, Integer> tabuList) {
        return tabuList.containsKey(getTabuKey(i, j));
    }

    private static void updateTabuList(Map<Integer, Integer> tabuList) {
        for (Integer key : new ArrayList<>(tabuList.keySet())) {
            Integer value = tabuList.get(key);
            if (value == 1) {
                tabuList.remove(key);
            } else {
                tabuList.put(key, value - 1);
            }
        }
    }

    private static int getTabuKey(int i, int j) {
        return i * 200 + j;
    }
}
