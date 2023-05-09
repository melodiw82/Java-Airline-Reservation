import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerMenu {

    // regex to keep track of username and password requirements
    private final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";

    // compiling the regex
    Pattern pattern = Pattern.compile(regex);

    private final Scanner sc = new Scanner(System.in);
    private final Ticket ticket = new Ticket();
    private final Utils utils = new Utils();
    private final Conn conn = new Conn();

    // executes the passenger menu
    public void passengerMenuExe() throws SQLException {
        utils.clearScreen();
        passengerMenu();
        System.out.println();

        System.out.println(">> Username: " + utils.PINK_BOLD + Menu.currentUsername + utils.RESET);
        System.out.println(">> balance: " + utils.PINK_BOLD + utils.getBalance() + utils.RESET);
        System.out.println();
        System.out.println();
        System.out.println("> Enter your command: ");
        int userCommand = utils.inputNum();

        while (userCommand != 0) {
            switch (userCommand) {
                case 1 -> changePassword();
                case 2 -> searchFlight();
                case 3 -> bookTicket();
                case 4 -> cancelTicket();
                case 5 -> bookedTicket();
                case 6 -> addCharge();
                case 7 -> scheduleFlight();
                default -> {
                    System.out.println("> Invalid input");
                    utils.pressEnterToContinue();
                }
            }
            utils.clearScreen();
            passengerMenu();
            System.out.println();
            System.out.println(">> Username:" + utils.PINK_BOLD + Menu.currentUsername + utils.RESET);
            System.out.println(">> balance: " + utils.PINK_BOLD + utils.getBalance() + utils.RESET);
            System.out.println();
            System.out.println();
            System.out.println("> Enter your command: ");
            userCommand = utils.inputNum();
        }
    }

    // change the password of the user, checks for the password to have all the requirements
    private void changePassword() throws SQLException {
        changePasswordMenu();

        System.out.println(utils.TEXT_ITALIC + utils.RED_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::
                password must contain the followings:
                >> Capital letters
                >> Small letters
                >> numbers
                >> with the length of at least 4 digits
                ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + utils.RESET);
        System.out.println();
        System.out.println("> Enter your new password: ");
        String pass = sc.next();

        Matcher matcher = pattern.matcher(pass);
        if ((pass.length() >= 4) && matcher.matches()) {
            PreparedStatement statement = conn.connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            statement.setString(1, pass);
            statement.setString(2, Menu.currentUsername);
            statement.executeUpdate();
            System.out.println(utils.GREEN_BOLD + "> Password changed successfully" + utils.RESET);
        } else {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Password doesn't match the requirements" + utils.RESET);
            System.out.println();
            System.out.println(utils.GREY_BOLD + "Please try again... " + utils.RESET);
        }
        utils.pressEnterToContinue();
    }

    // searches in the flights' arraylist
    private void searchFlight() throws SQLException {
        searchFlightMenu();

        System.out.println(utils.CYAN_BOLD + "> Enter the flight ID you want to search based on: " + utils.RESET);
        String search = sc.next();
        String query = "SELECT * FROM flights WHERE flight_id = ?";
        PreparedStatement statement = conn.connection.prepareStatement(query);
        statement.setString(1, search);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            String flightId = rs.getNString("flight_id");
            String origin = rs.getString("origin");
            String destination = rs.getString("destination");
            Date date = rs.getDate("date");
            Time time = rs.getTime("time");
            int price = rs.getInt("price");
            int seat = rs.getInt("seat");
            System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flightId, origin, destination, date, time, price, seat);
        }
        utils.pressEnterToContinue();
    }

    // prints the schedule of flights and let user book the desirable flights
    private void bookTicket() throws SQLException {
        bookTicketMenu();

        System.out.println();
        System.out.println("> Enter the flightId you want to book: ");
        String id = sc.next();

        ticket.buyTicket(id);
        utils.pressEnterToContinue();

    }

    // cancels the ticket using the ticket ID
    private void cancelTicket() throws SQLException {
        cancelTicketMenu();
        System.out.println(">> You can find your ticket Id in " + utils.CYAN_BOLD + "5. Booked tickets" + utils.RESET + " field");
        System.out.println();
        System.out.println("> Enter your ticket Id: ");
        String ticket = sc.next();

        String query = "DELETE FROM tickets WHERE ticket_id = ?";
        PreparedStatement statement = conn.connection.prepareStatement(query);
        statement.setString(1, ticket);
        statement.executeUpdate();
        System.out.println("> ticket cancelled successfully");
        utils.pressEnterToContinue();
    }

    // users can see the already booked tickets of them
    private void bookedTicket() {
        System.out.printf("%-15s%-15s%n", "ticket ID", "flightID");
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT ticket_id, flightId_ti FROM tickets WHERE username_ti = '" + Menu.currentUsername + "' ORDER BY ticket_id");

            while (rs.next()) {
                String ticket = rs.getNString("ticket_id");
                String flight = rs.getString("flightId_ti");

                System.out.printf("%-15s%-15s%n", ticket, flight);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        utils.pressEnterToContinue();
    }

    // adds charges to the user's account in order to book flights
    private void addCharge() throws SQLException {
        addChargeMenu();

        long oldBalance = utils.getBalance();

        System.out.println("> Your current balance: " + oldBalance);
        System.out.println();

        System.out.println("> How much would you like to charge your account?");
        long balance = utils.inputLong();

        PreparedStatement st = conn.connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
        st.setString(1, Menu.currentUsername);


        if (balance > 0) {
            PreparedStatement statement = conn.connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
            statement.setLong(1, balance + oldBalance);
            statement.setString(2, Menu.currentUsername);
            statement.executeUpdate();
            System.out.println(utils.GREEN_BOLD + "> Account charged successfully" + utils.RESET);
        } else {
            System.out.println(utils.RED_BOLD + "Enter a valid amount" + utils.RESET);
        }

        utils.pressEnterToContinue();
    }

    // list of all flights
    public void scheduleFlight() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            FLIGHT SCHEDULE
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);

        utils.schedulePrinter();

        utils.pressEnterToContinue();
    }

    // prints the menus
    private void passengerMenu() {
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                         PASSENGER MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                    <1> Change password
                    <2> Search flight tickets
                    <3> Booking ticket
                    <4> Ticket cancellation
                    <5> Booked tickets
                    <6> Add charge
                    <7> Flight schedule
                    <0> Sign out""" + utils.RESET);
    }

    private void changePasswordMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            CHANGE PASSWORD
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }

    private void searchFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                          SEARCH FLIGHT TICKETS
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }

    private void bookTicketMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            BOOKING TICKET
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }

    private void cancelTicketMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                          TICKET CANCELLATION
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }

    private void addChargeMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                              ADD CHARGE
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }
}