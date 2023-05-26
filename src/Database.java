import java.util.ArrayList;

public class Database {
    public static ArrayList<Flight> flights = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Ticket> tickets = new ArrayList<>();

    // constructor that adds the list of flights to the flights array list whenever an instance of Database class is created
    // users and tickets can also be added
    public Database() {
        User user = new User();
        user.addUser("Melodiw82", "Zara72", 7_000_000);
    }
}