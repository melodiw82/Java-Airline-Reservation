import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket {
    private String ticketId, tiFlightId, tiUsername;
    private Utils utils = new Utils();
    private final Conn conn = new Conn();

    // static integer to count the tickets in order to generate unique ticket IDs
    private static int ticketCounter = 0;

    // constructor
    public Ticket(String ticketId, String flightId, String username) {
        this.ticketId = ticketId;
        this.tiFlightId = flightId;
        this.tiUsername = username;
    }

    // constructor for initialization
    public Ticket() {
        ticketId = "Not available";
        tiFlightId = "No flights found";
        tiUsername = "No username";
    }

    // generates unique ticket IDs using flight ID and the amount of tickets that had been booked
    private String GenerateId(String tiFlightId) {
        ticketCounter++;
        return tiFlightId + ticketCounter;
    }

    // books ticket for the user, pays the price of ticket using user's balance and reduces the booked flight's seats by 1
    public void buyTicket(String tiFlightId) throws SQLException {
        String query = "SELECT price FROM flights WHERE flight_id = ?";
        PreparedStatement statement = conn.connection.prepareStatement(query);
        statement.setString(1, tiFlightId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            int price = rs.getInt("price");
            long userBalance = utils.getBalance();

            if (userBalance < price) {
                long shortOf = price - userBalance;
                System.out.println();
                System.out.println(utils.RED_BOLD + "> You're short of " + shortOf + utils.RESET);
                System.out.println();
                System.out.println(utils.GREY_BOLD + "> Please charge your account" + utils.RESET);
            } else {
                long newBalance = userBalance - price;
                PreparedStatement st = conn.connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
                st.setLong(1, newBalance);
                st.setString(2, Menu.currentUsername);
                st.executeUpdate();

                bookSeat(tiFlightId);

                System.out.println();
                System.out.println(utils.GREEN_BOLD + "> Flight booked successfully" + utils.RESET);

                String newTicketId = GenerateId(tiFlightId);
                PreparedStatement statement1 = conn.connection.prepareStatement("INSERT INTO tickets VALUES (?, ?, ?)");
                statement1.setString(1, newTicketId);
                statement1.setString(2, tiFlightId);
                statement1.setString(3, Menu.currentUsername);
                statement1.execute();
                System.out.println();
            }
        }
    }

    // reduces the booked flight's seats by 1
    private void bookSeat(String tiFlightId) throws SQLException {
        String query = "SELECT seat FROM flights WHERE flight_id = ?";
        PreparedStatement statement = conn.connection.prepareStatement(query);
        statement.setString(1, tiFlightId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            int seat = rs.getInt("seat");
            seat = seat - 1;
            PreparedStatement statement1 = conn.connection.prepareStatement("UPDATE flights SET seat = ? WHERE flight_id = ?");
            statement1.setInt(1, seat);
            statement1.setString(2, tiFlightId);
            statement1.executeUpdate();
        }
    }
}