import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CityReader {
    public static Map<Integer, City> readCitiesFromFile(String filename) throws IOException {
        // Create a new HashMap to store the cities
        Map<Integer, City> cities = new HashMap<>();

        // Use a try-with-resources block to open the file and read its contents
        try (BufferedReader br = new BufferedReader(new FileReader("TSP_107.txt"))) {
            // Read each line of the file until there are no more lines
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into three parts using a space character as the delimiter
                String[] parts = line.split(" ");

                // Parse the parts into an ID and x/y coordinates
                int id = Integer.parseInt(parts[0]);
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                // Create a new City object with the parsed values and add it to the HashMap
                City city = new City(id, x, y);
                cities.put(id, city);
            }
        }

        // Return the HashMap containing all the cities
        return cities;
    }
}
