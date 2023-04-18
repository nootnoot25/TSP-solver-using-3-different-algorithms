import java.util.*;

public class GA {

    //ideal 2-opt is > 100000
    private static final int POPULATION_SIZE = 1000;
    private static final int NUM_GENERATIONS = 1000;
    private static final double MUTATION_RATE = 0.001;
    private static final int NUM_ELITES = 10; // Change this value as desired

    public static List<City> solveTSP(Map<Integer, City> cities) {
        // Generate an initial population
        List<List<City>> population = generateInitialPopulation(cities);

        // Iterate through the generations
        for (int i = 0; i < NUM_GENERATIONS; i++) {
            // Evaluate the fitness of each individual in the population
            List<Double> fitnessValues = evaluateFitness(population);

            // Select parents for the next generation
            List<List<City>> parents = selectParents(population, fitnessValues);

            // Create a new population by mating the parents
            List<List<City>> newPopulation = mateParents(parents);

            // Mutate the new population
            mutatePopulation(newPopulation);

            // Replace the old population with the new population
            population = newPopulation;
        }

        // Find the fittest individual in the final population
        List<City> fittestIndividual = Collections.max(population, Comparator.comparingDouble(GA::calculateFitness));

        // Return the fittest individual
        return fittestIndividual;
    }

    // Generate an initial population
    private static List<List<City>> generateInitialPopulation(Map<Integer, City> cities) {
        List<List<City>> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            //population.add(TourGenerator.generateRandomTour(cities));
            population.add(TwoOpt.twoOpt(cities));
        }
        return population;
    }

    // Evaluate the fitness of each individual in the population
    private static List<Double> evaluateFitness(List<List<City>> population) {
        List<Double> fitnessValues = new ArrayList<>();
        for (List<City> individual : population) {
            double fitness = calculateFitness(individual);
            fitnessValues.add(fitness);
        }
        return fitnessValues;
    }

    // Calculate the fitness of an individual
    private static double calculateFitness(List<City> individual) {
        double distance = 0;
        for (int i = 0; i < individual.size() - 1; i++) {
            distance += individual.get(i).distanceTo(individual.get(i + 1));
        }
        distance += individual.get(individual.size() - 1).distanceTo(individual.get(0));
        return 1 / distance;
    }

    // Select parents for the next generation
    // Select parents for the next generation with elitism
    private static List<List<City>> selectParents(List<List<City>> population, List<Double> fitnessValues) {
        List<List<City>> parents = new ArrayList<>();

        // Sort the population by fitness value in descending order
        List<List<City>> sortedPopulation = new ArrayList<>(population);
        sortedPopulation.sort(Comparator.comparingDouble(GA::calculateFitness).reversed());

        // Add the best individuals (elites) to the parents list
        for (int i = 0; i < NUM_ELITES; i++) {
            parents.add(sortedPopulation.get(i));
        }

        // Select the rest of the parents using roulette wheel selection
        double totalFitness = fitnessValues.stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < (POPULATION_SIZE - NUM_ELITES) / 2; i++) {
            List<City> parent1 = selectIndividual(population, fitnessValues, totalFitness);
            List<City> parent2 = selectIndividual(population, fitnessValues, totalFitness);
            parents.add(parent1);
            parents.add(parent2);
        }

        return parents;
    }


    // Select an individual from the population
    private static List<City> selectIndividual(List<List<City>> population, List<Double> fitnessValues, double totalFitness) {
        double r = Math.random() * totalFitness;
        double sum = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            sum += fitnessValues.get(i);
            if (sum > r) {
                return population.get(i);
            }
        }
        return population.get(POPULATION_SIZE - 1);
    }

    // Mate parents to create a new population
    private static List<List<City>> mateParents(List<List<City>> parents) {
        List<List<City>> newPopulation = new ArrayList<>();
        for (int i = 0; i < parents.size(); i += 2) {
            List<City> parent1 = parents.get(i);
            List<City> parent2 = parents.get(i + 1);
            int crossoverPoint = (int) (Math.random() * (parent1.size() - 1)) + 1;
            List<City> child1 = new ArrayList<>(parent1.subList(0, crossoverPoint));
            List<City> child2 = new ArrayList<>(parent2.subList(0, crossoverPoint));
            for (int j = 0; j < parent2.size(); j++) {
                if (!child1.contains(parent2.get(j))) {
                    child1.add(parent2.get(j));
                }
                if (!child2.contains(parent1.get(j))) {
                    child2.add(parent1.get(j));
                }
            }
            newPopulation.add(child1);
            newPopulation.add(child2);
        }
        return newPopulation;
    }

    // Mutate the population
    private static void mutatePopulation(List<List<City>> population) {
        for (List<City> individual : population) {
            if (Math.random() < MUTATION_RATE) {
                int index1 = (int) (Math.random() * individual.size());
                int index2 = (int) (Math.random() * individual.size());
                Collections.swap(individual, index1, index2);
            }
        }
    }

}