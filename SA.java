import java.util.*;

public class SA {

     //ideal 2-opt is <1000
    public static List<City> solveTSP(Map<Integer, City> cities, int initialTemp, double coolingRate) {

        // Generate an initial solution
        List<City> currentSolution = TourGenerator.generateRandomTour(cities);

        // Initialize the best solution as the 2-opt solution
        List<City> twooptsolution = TwoOpt.twoOpt(cities);
        System.out.println("2-opt initial solution: " + Main.calculateTourDistance(twooptsolution));
        List<City> bestSolution = new ArrayList<>(twooptsolution);

        // Set the initial temperature
        double temperature = initialTemp;

        int maxIterations = 100000000;
        int iterations = 0;

        // Iterate until the temperature reaches 1
        while (temperature > 1 && iterations < maxIterations) {
            // Generate a new solution by swapping two cities
            List<City> newSolution = new ArrayList<>(currentSolution);
            int index1 = (int) (newSolution.size() * Math.random());
            int index2 = (int) (newSolution.size() * Math.random());
            Collections.swap(newSolution, index1, index2);

            // Calculate the energy (total distance) of the new solution
            double currentEnergy = calculateEnergy(currentSolution);
            double newEnergy = calculateEnergy(newSolution);

            // Calculate the acceptance probability
            double acceptanceProbability = calculateAcceptanceProbability(currentEnergy, newEnergy, temperature);

            // Decide whether to accept the new solution
            if (acceptanceProbability > Math.random()) {
                currentSolution = newSolution;
            }

            // Update the best solution if the new solution is better
            if (calculateEnergy(currentSolution) < calculateEnergy(bestSolution)) {
                bestSolution = new ArrayList<>(currentSolution);
            }

            // Reduce the temperature
            temperature *= 1 - coolingRate;

            iterations++;
        }

        // Print the SA tour
        System.out.println("------------------SA TOUR---------------------");
        for (City city : bestSolution) {
            System.out.println(city.getX() + "," + city.getY());
            //System.out.println(city.getId() + "," + city.getX() + "," + city.getY());
        }
        System.out.println("----------------------------------------");


        // Return the best solution
        return bestSolution;
    }

    // Calculate the total distance of a tour
    private static double calculateEnergy(List<City> tour) {
        double distance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        distance += tour.get(tour.size() - 1).distanceTo(tour.get(0));
        return distance;
    }

    // Calculate the acceptance probability for a new solution
    private static double calculateAcceptanceProbability(double currentEnergy, double newEnergy, double temperature) {
        if (newEnergy < currentEnergy) {
            return 1;
        } else {
            return Math.exp((currentEnergy - newEnergy) / temperature);
        }
    }
}
