public class City {
    private int id;
    private double x;
    private double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // Override the toString method to return a string representation of the City object
    @Override
    public String toString() {
        return "City " + id + " (" + x + ", " + y + ")";
    }

    public double distanceTo(City other) {
        double xDiff = this.x - other.x;
        double yDiff = this.y - other.y;
        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }


    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
