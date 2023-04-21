import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerMenu {

    private final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";
    Pattern pattern = Pattern.compile(regex);

    private final Scanner sc = new Scanner(System.in);
    private final User newUser = new User();
    private final Ticket ticket = new Ticket();
    private final Flight flight = new Flight();
    private final Utils utils = new Utils();

    public void passengerMenuExe() {
        utils.clearScreen();
        passengerMenu();
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
                default -> {
                    System.out.println("> Invalid input");
                    utils.pressEnterToContinue();
                }
            }
            utils.clearScreen();
            passengerMenu();
            System.out.println();
            System.out.println("> Enter your command: ");
            userCommand = utils.inputNum();
        }
    }

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

    private void changePassword() {
        changePasswordMenu();

        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(Menu.currentUsername)) {
                System.out.println(">> Your current password is " + Database.users.get(i).getPassword());

                System.out.println(utils.TEXT_ITALIC + utils.RED_BOLD + """
                        ::::::::::::::::::::::::::::::::::::::::::::::::::::
                        password must contain the followings:
                        >> Capital letters
                        >> Small letters
                        >> numbers
                        >> with the length of at least 4 digits
                        ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + utils.RESET);
                System.out.println("> Enter your new password: ");
                String pass = sc.next();

                Matcher matcher = pattern.matcher(pass);
                if (pass.equals(Database.users.get(i).getPassword())) {
                    System.out.println();
                    System.out.println("> Username is the same as before...");
                } else if ((pass.length() >= 4) && matcher.matches()) {
                    Database.users.get(i).setPassword(pass);
                    System.out.println();
                    System.out.println();
                    System.out.println(utils.GREEN_BOLD + "> Password changed successfully" + utils.RESET);
                } else {
                    System.out.println();
                    System.out.println(">" + utils.RED_BOLD + "Invalid password..." + utils.RESET);
                    System.out.println();
                    System.out.println(">" + utils.RED_BOLD + "Please try again... " + utils.RESET);
                }
            }
        }
        utils.pressEnterToContinue();
    }

    private void bookedTicketMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            BOOKED TICKET
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

    private void searchFlight() {
        searchFlightMenu();

        System.out.println("> Enter the field you want to search:\n1.flight Id\n2.origin\n3.destination\n4.date\n5.time\n6.price range ");
        System.out.println();
        int searchCom = utils.inputNum();
        switch (searchCom) {
            case 1 -> {
                utils.clearScreen();
                System.out.println("> Enter the flight Id: ");
                String id = sc.next();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getFlightId().equals(id)) {
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println("> No flight ID found");
                }
                utils.pressEnterToContinue();
            }
            case 2 -> {
                System.out.println("> Enter the flight origin: ");
                String origin = sc.next();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getOrigin().equals(origin)) {
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println("> Origin is not available");
                }
                utils.pressEnterToContinue();
            }
            case 3 -> {
                System.out.println("> Enter the flight destination: ");
                String destination = sc.next();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getDestination().equals(destination)) {
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println("> Destination in not available");
                }
                utils.pressEnterToContinue();
            }
            case 4 -> {
                String date = utils.inputDate();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getDate().equals(date)) {
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println("> Date unavailable");
                }
                utils.pressEnterToContinue();
            }
            case 5 -> {
                String time = utils.inputTime();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getTime().equals(time)) {
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println("> Time unavailable");
                }
                utils.pressEnterToContinue();
            }
            case 6 -> {
                System.out.println("> Price of the flight would be greater than: ");
                int price = utils.inputNum();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getPrice() >= price) {
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println("> Price unavailable");
                }
                utils.pressEnterToContinue();
            }
            default -> {
                System.out.println("> Invalid input");
                utils.pressEnterToContinue();
            }
        }
    }

    private void bookTicket() {
        bookTicketMenu();
        int userIndex = newUser.findUser(Menu.currentUsername);

        System.out.printf("%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%n", "|", "FlightId", "|", "Origin",
                "|", "Destination",
                "|", "Date", "|", "Time",
                "|", "Price", "|", "Seats", "|"
        );
        System.out.println(".................................................................................................................");
        for (int i = 0; i < Database.flights.size(); i++) {
            flight.toString(i);
            System.out.println(".................................................................................................................");
        }

        System.out.println("> Enter the flightId you want to book: ");
        int flightIndex = flight.findFlight(sc.next());

        if (userIndex >= 0 && flightIndex >= 0) {
            ticket.buyTicket(userIndex, flightIndex);
            utils.pressEnterToContinue();
        }

        if (flightIndex == -1) {
            System.out.println("> Flight not found");
            utils.pressEnterToContinue();
        }
    }

    private void cancelTicket() {
        cancelTicketMenu();
        System.out.println("> Enter your your ticket Id: ");
        int ticketIndex = ticket.findTicket(sc.next());
        if (ticketIndex == -1) {
            System.out.println("> Ticket not found");
            utils.pressEnterToContinue();
        } else if (ticketIndex >= 0) {
            ticket.cancelTicket(ticketIndex);
            utils.pressEnterToContinue();
        }
    }

    private void bookedTicket() {
        bookedTicketMenu();
        int userIndex = newUser.findUser(Menu.currentUsername);

        ticket.bookedTicket(userIndex);
        utils.pressEnterToContinue();
    }

    private void addCharge() {
        addChargeMenu();
        int userIndex = newUser.findUser(Menu.currentUsername);

        System.out.println("> Your current balance is " + Database.users.get(userIndex).getBalance());
        System.out.println();
        System.out.println("> How much would you like to charge your account? ");
        System.out.println("> to the limit of " + Long.MAX_VALUE);
        System.out.println();
        newUser.addBalance(utils.inputNum(), userIndex);
        utils.pressEnterToContinue();
    }
}