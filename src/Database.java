import java.util.ArrayList;

public class Database {

    private static final Flight flight = new Flight();
    public static ArrayList<Flight> flights = new ArrayList<>();

    public static void main(String[] args) {
        Database database = new Database();
        System.out.println(flights);

       flights.get(0).setFlightId("melo");
    }

    // constructor
    public Database() {
        flight.addFlight("WX-12", "Yazd", "Tehran", "1401-12-10", "12:30", 700_000, 51);
        flight.addFlight("WX-15", "Mashhad", "Ahvaz", "1401-12-11", "08:00", 900_000, 245);
        flight.addFlight("BG-22", "Shiraz", "Tabriz", "1401-12-12", "22:30", 1_100_000, 12);
    }
}