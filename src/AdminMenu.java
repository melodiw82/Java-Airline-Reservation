import java.util.Scanner;
import java.sql.*;

public class AdminMenu {
    private final Scanner sc = new Scanner(System.in);
    private final Utils utils = new Utils();
    private final Conn conn = new Conn();

    // executes the admin menu
    public void adminMenuExe() {
        utils.clearScreen();
        adminMenu();
        System.out.printf("%n%s%n", "> Enter your command: ");
        int adminCommand = utils.inputNum();
        while (adminCommand != 0) {
            switch (adminCommand) {
                case 1 -> addFlightMenu();

                case 2 -> updateFlightMenu();

                case 3 -> removeFlightMenu();

                case 4 -> scheduleFlightMenu();

                default -> System.out.println(utils.RED_BOLD + "> Invalid input" + utils.RESET);
            }
            utils.clearScreen();
            adminMenu();
            System.out.println();
            System.out.println("> Enter your command");
            adminCommand = utils.inputNum();
        }
    }

    // adds new flights to the flight arraylist
    private void addFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                               ADD FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);

//        boolean isFound = false;
        System.out.println("> Add flight ID: (Ex. WX-12)");
        String fId = sc.next();
//        for (int i = 0; i < Database.flights.size(); i++) {
//            if (Database.flights.get(i).getFlightId().equals(fId)) {
//                System.out.println();
//                System.out.println(utils.RED_BOLD + "> Flight already exists" + utils.RESET);
//                isFound = true;
//            }
//        }

//        if (!isFound) {
        System.out.println();
        System.out.println("> Add flight origin: (Ex. Yazd)");
        String origin = sc.next();
        System.out.println();
        System.out.println("> Add flight destination: (Ex.Tehran)");
        String destination = sc.next();
        System.out.println();
        System.out.println("> Add flight date:");
        System.out.println();
        String date = utils.inputDate();
        System.out.println();
        System.out.println("> Add flight time: (HH:MM)");
        System.out.println();
        String time = utils.inputTime();
        System.out.println();
        System.out.println("> Add flight price: (integer)");
        int price = utils.inputNum();
        System.out.println();
        System.out.println("> Add available seats: (integer)");
        int seat = utils.inputNum();

        try {
            String query = "INSERT INTO flights VALUES('" + fId + "', '" + origin + "', '" + destination + "', '" + date + "', '" + time + "', '" + price + "', '" + seat + "')";
            conn.statement.execute(query);
//            conn.statement.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(utils.RED_BOLD + "> Flight already exists" + utils.RESET);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        System.out.println();
        System.out.println(utils.GREEN_BOLD + ">> Flight added successfully" + utils.RESET);
//        }
        utils.pressEnterToContinue();
    }

    // updates the flights, doesn't let the admin update already booked flights
    private void updateFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            UPDATE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        System.out.println("> Enter the flight ID to be updated: ");
        String flightId = sc.next();
        System.out.println();

//        boolean isBooked = false;

//        for (int i = 0; i < Database.tickets.size(); i++) {
//            if (Database.tickets.get(i).getTiFlightId().equals(flightId)) {
//                System.out.println(utils.RED_BOLD + "> Flight is already booked and cannot be changed" + utils.RESET);
//                System.out.println();
//                isBooked = true;
//                utils.pressEnterToContinue();
//            }
//        }

//        if (!isBooked) {
        System.out.println(utils.CYAN_BOLD + "> Enter the section that needs to be updated: \n\n1.origin\n\n2.destination\n\n3.date\n\n4.time\n\n5.price\n\n6.seat" + utils.RESET);
        System.out.println();
        int command = utils.inputNum();

//            boolean foundFlight = false;
//            for (int i = 0; i < Database.flights.size(); i++) {
//                if ((Database.flights.get(i).getFlightId()).equals(flightId)) {
//                    foundFlight = true;
        switch (command) {
            case 1:
                try {
                    PreparedStatement statement = conn.connection.prepareStatement("UPDATE flights SET origin = ? WHERE flight_id = ?");
                    System.out.println("> Enter the new origin: ");
                    String updateCommand = sc.next();
                    statement.setString(1, updateCommand);
                    statement.setString(2, flightId);
                    statement.executeUpdate();
                    System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
//                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    PreparedStatement statement = conn.connection.prepareStatement("UPDATE flights SET destination = ? WHERE flight_id = ?");
                    System.out.println("> Enter the new destination: ");
                    String updateCommand = sc.next();
                    statement.setString(1, updateCommand);
                    statement.setString(2, flightId);
                    statement.executeUpdate();
                    System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    PreparedStatement statement = conn.connection.prepareStatement("UPDATE flights SET date = ? WHERE flight_id = ?");
                    System.out.println("> Enter the new date: ");
                    System.out.println();
                    String updateCommand = utils.inputDate();
                    java.sql.Date.valueOf(updateCommand);
                    statement.setString(1, updateCommand);
                    statement.setString(2, flightId);
                    statement.executeUpdate();
                    System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 4:
                try {
                    PreparedStatement statement = conn.connection.prepareStatement("UPDATE flights SET time = ? WHERE flight_id = ?");
                    System.out.println("> Enter the new time: ");
                    System.out.println();
                    String updateCommand = utils.inputTime();
                    java.sql.Time.valueOf(updateCommand);
                    statement.setString(1, updateCommand);
                    statement.setString(2, flightId);
                    statement.executeUpdate();
                    System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 5:
                try {
                    PreparedStatement statement = conn.connection.prepareStatement("UPDATE flights SET price = ? WHERE flight_id = ?");
                    System.out.println("> Enter the new price: ");
                    int updateCom = utils.inputNum();
                    statement.setInt(1, updateCom);
                    statement.setString(2, flightId);
                    statement.executeUpdate();
                    System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 6:
                try {
                    PreparedStatement statement = conn.connection.prepareStatement("UPDATE flights SET seat = ? WHERE flight_id = ?");
                    System.out.println("> Enter the new available seats: ");
                    int updateCom = utils.inputNum();
                    statement.setInt(1, updateCom);
                    statement.setString(2, flightId);
                    statement.executeUpdate();
                    System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println();
                System.out.println(utils.RED_BOLD + "> Invalid input" + utils.RESET);
        }
        utils.pressEnterToContinue();
//                }
//            }
//            if (!foundFlight) {
//                System.out.println();
//                System.out.println(utils.RED_BOLD + ">> Flight not found" + utils.RESET);
//                utils.pressEnterToContinue();
//            }
//        }
    }

    // removes existing flights using the flight ID, doesn't let the admin remove already booked flights
    private void removeFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            REMOVE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
//        boolean isBooked = false;

//        for (int i = 0; i < Database.tickets.size(); i++) {
//            if (Database.tickets.get(i).getTiFlightId().equals(flightId)) {
//                System.out.println();
//                System.out.println(utils.RED_BOLD + "> Flight is already booked and cannot be removed" + utils.RESET);
//                isBooked = true;
//            }
//        }

//        if (!isBooked) {
//        boolean foundFlight = false;

        try {
            String query = "DELETE FROM flights WHERE flight_id = ?";
            PreparedStatement statement = conn.connection.prepareStatement(query);
            System.out.println("> Enter the flight ID to be removed: ");
            String flightId = sc.next();
            statement.setString(1, flightId);
            statement.executeUpdate();
            System.out.println();
            System.out.println(utils.GREEN_BOLD + ">> Flight removed successfully" + utils.RESET);
//            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        utils.pressEnterToContinue();
    }

    public void scheduleFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            FLIGHT SCHEDULE
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);

        utils.schedulePrinter();

        utils.pressEnterToContinue();
    }

    // prints the admin menu
    private void adminMenu() {
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                           ADMIN MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                    <1> Add
                    <2> Update
                    <3> Remove
                    <4> Flight schedules
                    <0> Sign out""" + utils.RESET);
    }
}

// where to close the statement