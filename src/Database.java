import java.util.ArrayList;

public class Database {
    public ArrayList<Flight> flights = new ArrayList<>();

    // constructor
    public Database() {
    }

    // getters and setters
    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void addFlight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        flights.add(new Flight(flightId, origin, destination, date, time, price, seats));
    }
}