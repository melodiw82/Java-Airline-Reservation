import java.util.ArrayList;

public class Database {
    public static ArrayList<Flight> flights = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Ticket> tickets = new ArrayList<>();

    // constructor that adds the list of flights to the flights array list whenever an instance of Database class is created
    // users and tickets can also be added
    public Database() {
        Flight flight = new Flight();
        flight.addFlight("FA-17", "Yazd", "Shiraz", "1401-12-10", "12:30", 500_000, 73);
        flight.addFlight("TA-55", "Yazd", "Isfahan", "1401-10-10", "13:30", 600_000, 100);
        flight.addFlight("TR-64", "Shiraz", "Mashhad", "1401-11-10", "14:30", 700_000, 201);
        flight.addFlight("WX-12", "Mashhad", "Tehran", "1401-12-15", "15:30", 700_000, 64);
        flight.addFlight("WX-15", "Tehran", "Ahvaz", "1402-01-11", "17:00", 900_000, 245);
        flight.addFlight("WX-17", "Yazd", "Tehran", "1402-02-10", "21:30", 1_000_000, 72);
        flight.addFlight("BG-22", "Semnan", "Tabriz", "1402-05-12", "23:30", 1_100_000, 63);
        flight.addFlight("WA-25", "Tabriz", "Mashhad", "1402-04-10", "16:30", 1_200_000, 45);
        flight.addFlight("WA-27", "Semnan", "Yazd", "1402-06-10", "17:30", 1_500_000, 82);
        flight.addFlight("FS-12", "Mashahd", "Ahvaz", "1401-12-20", "22:30", 1_700_000, 12);

        User user = new User();
        user.addUser("Melodiw82", "Zara72", 7_000_000);
    }
}