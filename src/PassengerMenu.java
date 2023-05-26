import java.io.IOException;
import java.io.RandomAccessFile;
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
    private final Utils utils = new Utils();
    private final UsersFile usersFile = new UsersFile();
    private final FlightsFile flightsFile = new FlightsFile();
    private final TicketsFile ticketsFile = new TicketsFile();

    // executes the passenger menu
    public void passengerMenuExe() throws IOException {
        menuHelp();
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
            menuHelp();
            userCommand = utils.inputNum();
        }
    }

    private void menuHelp() throws IOException {
        usersFile.userRand = new RandomAccessFile(usersFile.user, "r");

        utils.clearScreen();
        passengerMenu();
        System.out.println();
        System.out.println(utils.PINK_BOLD + "> username: " + new String(usersFile.readCharsFromFile(usersFile.userRand, usersFile.findUser(Menu.currentUsername), usersFile.FIX_SIZE)));
        System.out.println("> balance: " + new String(usersFile.readCharsFromFile(usersFile.userRand, usersFile.findUser(Menu.currentUsername) + (2 * usersFile.FIX_SIZE), usersFile.FIX_SIZE)) + utils.RESET);
        System.out.println();
        System.out.println("> Enter your command: ");

        usersFile.userRand.close();
    }

    // change the password of the user, checks for the password to have all the requirements
    private void changePassword() throws IOException {
        changePasswordMenu();

        System.out.println(utils.TEXT_ITALIC + utils.RED_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::
                password must contain the followings:
                >> Capital letters
                >> Small letters
                >> numbers
                >> with the length of at least 4 digits and maximum of 15 digits
                ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + utils.RESET);
        System.out.println();
        System.out.println("> Enter your new password: ");
        String pass = sc.next();

        Matcher matcher = pattern.matcher(pass);
//        if (pass.equals(Database.users.get(i).getPassword())) {
//            System.out.println();
//            System.out.println(utils.RED_BOLD + "> Password is the same as before..." + utils.RESET);
//        }
        if ((pass.length() >= 4) && matcher.matches() && (pass.length() < 16)) {
            usersFile.updatePassword(usersFile.findUser(Menu.currentUsername), pass);
            System.out.println();
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
    private void searchFlight() throws IOException {
        searchFlightMenu();

        System.out.println(utils.CYAN_BOLD + "> Enter the field you want to search:\n\n1.flight Id\n\n2.origin\n\n3.destination\n\n4.date\n\n5.time\n\n6.price range " + utils.RESET);
        System.out.println();
        int searchCom = utils.inputNum();
        System.out.println();
        switch (searchCom) {
            case 1 -> {
                System.out.println("> Enter the flight Id: ");
                String id = sc.next();

                flightsFile.searchFlight(0, id);
                utils.pressEnterToContinue();
            }
            case 2 -> {
                System.out.println("> Enter the flight origin: ");
                String origin = sc.next();

                flightsFile.searchFlight(flightsFile.FIX_SIZE, origin);
                utils.pressEnterToContinue();
            }
            case 3 -> {
                System.out.println("> Enter the flight destination: ");
                String destination = sc.next();

                flightsFile.searchFlight((2 * flightsFile.FIX_SIZE), destination);
                utils.pressEnterToContinue();
            }
            case 4 -> {
                String date = utils.inputDate();

                flightsFile.searchFlight((3 * flightsFile.FIX_SIZE), date);
                utils.pressEnterToContinue();
            }
            case 5 -> {
                String time = utils.inputTime();

                flightsFile.searchFlight((4 * flightsFile.FIX_SIZE), time);
                utils.pressEnterToContinue();
            }
            case 6 -> {
                System.out.println("> Price of the flight would be: ");
                int price = utils.inputNum();

                flightsFile.searchFlight((5 * flightsFile.FIX_SIZE), Integer.toString(price));
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
        int userIndex = usersFile.findUser(Menu.currentUsername);
        utils.schedulePrinter();

        System.out.println();
        System.out.println("> Enter the flightId you want to book: ");
        int flightIndex = flightsFile.findFlight(sc.next());

        if (flightIndex >= 0) {
            ticketsFile.buyTicket(flightIndex, userIndex);

            utils.pressEnterToContinue();
        }

        if (flightIndex == -1) {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Flight not found" + utils.RESET);
            utils.pressEnterToContinue();
        }
    }

    // cancels the ticket using the ticket ID
    private void cancelTicket() throws IOException {
        cancelTicketMenu();
        ticketsFile.searchTicket(Menu.currentUsername);
        System.out.println();
        System.out.println("> Enter your ticket Id: ");
        int ticketIndex = ticketsFile.findTicket(sc.next());

        if (ticketIndex == -1) {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Ticket not found" + utils.RESET);
            utils.pressEnterToContinue();
        } else if (ticketIndex >= 0) {

            ticketsFile.cancelTicket(ticketIndex);
            utils.pressEnterToContinue();
        }
    }

    // users can see the already booked tickets of them
    private void bookedTicket() throws IOException {
        bookedTicketMenu();
        int userIndex = usersFile.findUser(Menu.currentUsername);
        System.out.println("> You can find information about your flight by searching the flight Id in " + utils.CYAN_BOLD + "2. Search flight tickets" + utils.RESET + " field");
        System.out.println();

        ticketsFile.searchTicket(Menu.currentUsername);

        utils.pressEnterToContinue();
    }

    // adds charges to the user's account in order to book flights
    private void addCharge() throws IOException {
        usersFile.userRand = new RandomAccessFile(usersFile.user, "rw");

        addChargeMenu();
        System.out.println("> Your current balance is " + utils.PINK_BOLD + new String(usersFile.readCharsFromFile(usersFile.userRand, (usersFile.findUser(Menu.currentUsername) + (2 * usersFile.FIX_SIZE)), usersFile.FIX_SIZE)) + utils.RESET);
        System.out.println();
        System.out.println("> How much would you like to charge your account? ");
        System.out.println("> to the limit of " + Integer.MAX_VALUE);
        System.out.println();
        int update = utils.inputNum();
        while (update < 0) {
            System.out.println("> Please enter a valid amount");
            update = utils.inputNum();
        }
        int balance = usersFile.StringToInt(usersFile.userRand, (usersFile.findUser(Menu.currentUsername) + (2 * usersFile.FIX_SIZE)));
        usersFile.updateBalance(usersFile.findUser(Menu.currentUsername), (update + balance));

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