import java.io.IOException;

/**
 * airline reservation system terminal app
 * to run the program click on the run.bat file
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