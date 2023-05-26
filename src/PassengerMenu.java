import java.io.IOException;
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

    private Scanner sc = new Scanner(System.in);
    private User newUser = new User();
    private Ticket ticket = new Ticket();
    private Flight flight = new Flight();
    private Utils utils = new Utils();

    // executes the passenger menu
    public void passengerMenuExe() throws IOException {
        utils.clearScreen();
        int userIndex = newUser.findUser(Menu.currentUsername);
        passengerMenu();
        System.out.println();
        System.out.println(">> Username: " + utils.PINK_BOLD + Menu.currentUsername + utils.RESET);
        System.out.println(">> Balance: " + utils.PINK_BOLD + Database.users.get(userIndex).getBalance() + utils.RESET);
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
            System.out.println(">> Balance:" + utils.PINK_BOLD + Database.users.get(userIndex).getBalance() + utils.RESET);
            System.out.println();
            System.out.println();
            System.out.println("> Enter your command: ");
            userCommand = utils.inputNum();
        }
    }

    // change the password of the user, checks for the password to have all the requirements
    private void changePassword() {
        changePasswordMenu();

        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(Menu.currentUsername)) {
                System.out.println(">> Your current password is " + utils.PINK_BOLD + Database.users.get(i).getPassword() + utils.RESET);

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
                if (pass.equals(Database.users.get(i).getPassword())) {
                    System.out.println();
                    System.out.println(utils.RED_BOLD + "> Password is the same as before..." + utils.RESET);
                } else if ((pass.length() >= 4) && matcher.matches()) {
                    Database.users.get(i).setPassword(pass);
                    System.out.println();
                    System.out.println(utils.GREEN_BOLD + "> Password changed successfully" + utils.RESET);
                } else {
                    System.out.println();
                    System.out.println(utils.RED_BOLD + "> Password doesn't match the requirements" + utils.RESET);
                    System.out.println();
                    System.out.println(utils.GREY_BOLD + "Please try again... " + utils.RESET);
                }
            }
        }
        utils.pressEnterToContinue();
    }

    // searches in the flights' arraylist
    private void searchFlight() {
        searchFlightMenu();

        System.out.println(utils.CYAN_BOLD + "> Enter the field you want to search:\n\n1.flight Id\n\n2.origin\n\n3.destination\n\n4.date\n\n5.time\n\n6.price range " + utils.RESET);
        System.out.println();
        int searchCom = utils.inputNum();
        System.out.println();
        switch (searchCom) {
            case 1 -> {
                System.out.println("> Enter the flight Id: ");
                String id = sc.next();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getFlightId().equals(id)) {
                        System.out.println();
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println(utils.RED_BOLD + "> Flight not found" + utils.RESET);
                }
                utils.pressEnterToContinue();
            }
            case 2 -> {
                System.out.println("> Enter the flight origin: ");
                String origin = sc.next();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getOrigin().equals(origin)) {
                        System.out.println();
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println();
                    System.out.println(utils.RED_BOLD + "> Origin is not available" + utils.RESET);
                }
                utils.pressEnterToContinue();
            }
            case 3 -> {
                System.out.println("> Enter the flight destination: ");
                String destination = sc.next();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getDestination().equals(destination)) {
                        System.out.println();
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println();
                    System.out.println(utils.RED_BOLD + "> Destination in not available" + utils.RESET);
                }
                utils.pressEnterToContinue();
            }
            case 4 -> {
                String date = utils.inputDate();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getDate().equals(date)) {
                        System.out.println();
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println();
                    System.out.println(utils.RED_BOLD + "> Date unavailable" + utils.RESET);
                }
                utils.pressEnterToContinue();
            }
            case 5 -> {
                String time = utils.inputTime();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getTime().equals(time)) {
                        System.out.println();
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println(utils.RED_BOLD + "> Time unavailable" + utils.RESET);
                }
                utils.pressEnterToContinue();
            }
            case 6 -> {
                System.out.println("> Price of the flight would be greater than: ");
                int price = utils.inputNum();
                boolean isFound = false;
                for (int i = 0; i < Database.flights.size(); i++) {
                    if (Database.flights.get(i).getPrice() >= price) {
                        System.out.println();
                        flight.toString(i);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    System.out.println();
                    System.out.println(utils.RED_BOLD + "> Price unavailable" + utils.RESET);
                }
                utils.pressEnterToContinue();
            }
            default -> {
                System.out.println(utils.RED_BOLD + "> Invalid input" + utils.RESET);
                utils.pressEnterToContinue();
            }
        }
    }

    // prints the schedule of flights and let user book the desirable flights
    private void bookTicket() throws IOException {
        bookTicketMenu();
        int userIndex = newUser.findUser(Menu.currentUsername);

        utils.schedulePrinter();

        System.out.println();
        System.out.println("> Enter the flightId you want to book: ");
        int flightIndex = flight.findFlight(sc.next());

        if (userIndex >= 0 && flightIndex >= 0) {
            ticket.buyTicket(userIndex, flightIndex);
            utils.pressEnterToContinue();
        }

        if (flightIndex == -1) {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Flight not found" + utils.RESET);
            utils.pressEnterToContinue();
        }
    }

    // cancels the ticket using the ticket ID
    private void cancelTicket() {
        cancelTicketMenu();
        System.out.println(">> You can find your ticket Id in " + utils.CYAN_BOLD + "5. Booked tickets" + utils.RESET + " field");
        System.out.println();
        System.out.println("> Enter your ticket Id: ");
        int ticketIndex = ticket.findTicket(sc.next());
        if (ticketIndex == -1) {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Ticket not found" + utils.RESET);
            utils.pressEnterToContinue();
        } else if (ticketIndex >= 0) {
            ticket.cancelTicket(ticketIndex);
            utils.pressEnterToContinue();
        }
    }

    // users can see the already booked tickets of them
    private void bookedTicket() {
        bookedTicketMenu();
        int userIndex = newUser.findUser(Menu.currentUsername);
        System.out.println("> You can find information about your flight by searching the flight Id in " + utils.CYAN_BOLD + "2. Search flight tickets" + utils.RESET + " field");
        System.out.println();
        ticket.bookedTicket(userIndex);
        utils.pressEnterToContinue();
    }

    // adds charges to the user's account in order to book flights
    private void addCharge() {
        addChargeMenu();
        int userIndex = newUser.findUser(Menu.currentUsername);

        System.out.println("> Your current balance is " + utils.PINK_BOLD + Database.users.get(userIndex).getBalance() + utils.RESET);
        System.out.println();
        System.out.println("> How much would you like to charge your account? ");
        System.out.println("> to the limit of " + Long.MAX_VALUE);
        System.out.println();
        newUser.addBalance(utils.inputLong(), userIndex);
        utils.pressEnterToContinue();
    }

    // list of all flights
    public void scheduleFlight() throws IOException {
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
}