import java.io.IOException;

/**
 * airline reservation system terminal app
 * using random access file
 *
 * @author Zahra Rafiei
 */
public class Main {
    private static final Menu menu = new Menu();

    public static void main(String[] args) throws IOException {
        FlightsFile flightsFile = new FlightsFile();
        UsersFile usersFile = new UsersFile();
        TicketsFile ticketsFile = new TicketsFile();

        menu.menuExecution();
    }
}