import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Passenger {

    static Pattern pattern = Pattern.compile(Signup.regex);

    private static final Scanner sc = new Scanner(System.in);
    private static final User new_user = new User();
    private static final Ticket ticket = new Ticket();
    private static final Flight flight = new Flight();

    public static void passengerMenuExe() {
        Menu.clearScreen();
        passengerMenu();
        System.out.println();
        System.out.println("> Enter your command: ");
        int userCommand = sc.nextInt();
        while (userCommand != 0) {
            switch (userCommand) {
                case 1 -> {
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                        CHANGE PASSWORD
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);
                    System.out.println("> Enter your username: ");
                    String user = sc.next();
                    boolean found = false;
                    for (int i = 0; i < Database.users.size(); i++) {
                        if (Database.users.get(i).getUsername().equals(user)) {
                            System.out.println(">> Your current password is " + Database.users.get(i).getPassword());
                            System.out.println(Signup.TEXT_ITALIC + Signup.RED_BOLD + """
                                    ::::::::::::::::::::::::::::::::::::::::::::::::::::
                                    password must contain the followings:
                                    >> Capital letters
                                    >> Small letters
                                    >> numbers
                                    >> with the length of at least 4 digits
                                    ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + Signup.RESET);
                            System.out.println("> Enter your new password: ");
                            String pass = sc.next();

                            Matcher matcher = pattern.matcher(pass);
                            if ((pass.length() >= 4) && matcher.matches()) {
                                Database.users.get(i).setPassword(pass);
                                System.out.println(Signup.GREEN_BOLD + "> Password changed successfully" + Signup.RESET);
                                found = true;
                            }
                        }
                    }
                    if (!found) {
                        System.out.println(Signup.RED_BOLD + "> Invalid username" + Signup.RESET);
                    }
                    Menu.pressEnterToContinue();
                }
                case 2 -> {
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                      SEARCH FLIGHT TICKETS
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);
                    System.out.println("> Enter the field you want to search:\n1.flight Id\n2.origin\n3.destination\n4.date\n5.time\n6.price range ");
                    int searchCom = sc.nextInt();
                    switch (searchCom) {
                        case 1 -> {
                            Menu.clearScreen();
                            System.out.println("> Enter the flight Id: ");
                            String id = sc.next();
                            for (int i = 0; i < Database.flights.size(); i++) {
                                if (Database.flights.get(i).getFlightId().equals(id)) {
                                    Flight.toString(i);
                                }
                            }
                            Menu.pressEnterToContinue();
                        }
                        case 2 -> {
                            System.out.println("> Enter the flight origin: ");
                            String origin = sc.next();
                            for (int i = 0; i < Database.flights.size(); i++) {
                                if (Database.flights.get(i).getOrigin().equals(origin)) {
                                    Flight.toString(i);
                                }
                            }
                            Menu.pressEnterToContinue();
                        }
                        case 3 -> {
                            System.out.println("> Enter the flight destination: ");
                            String destination = sc.next();
                            for (int i = 0; i < Database.flights.size(); i++) {
                                if (Database.flights.get(i).getDestination().equals(destination)) {
                                    Flight.toString(i);
                                }
                            }
                            Menu.pressEnterToContinue();
                        }
                        case 4 -> {
                            System.out.println("> Enter the flight date(yyyy-mm-dd): ");
                            String date = sc.next();
                            for (int i = 0; i < Database.flights.size(); i++) {
                                if (Database.flights.get(i).getDate().equals(date)) {
                                    Flight.toString(i);
                                }
                            }
                            Menu.pressEnterToContinue();
                        }
                        case 5 -> {
                            System.out.println("> Enter the flight time(hh:mm): ");
                            String time = sc.next();
                            for (int i = 0; i < Database.flights.size(); i++) {
                                if (Database.flights.get(i).getTime().equals(time)) {
                                    Flight.toString(i);
                                }
                            }
                            Menu.pressEnterToContinue();
                        }
                        case 6 -> {
                            System.out.println("> Price of the flight would be greater than: ");
                            int price = sc.nextInt();
                            for (int i = 0; i < Database.flights.size(); i++) {
                                if (Database.flights.get(i).getPrice() >= price) {
                                    Flight.toString(i);
                                }
                            }
                            Menu.pressEnterToContinue();
                        }
                    }
                }
                case 3 -> {
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                        BOOKING TICKET
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);
                    System.out.println("> Enter your username: ");
                    int userIndex = new_user.findUser(sc.next());
                    if (userIndex == -1) {
                        System.out.println("> User not found");
                        Menu.pressEnterToContinue();
                        break;

                    }

                    System.out.println("> Enter the flightId you want to book: ");
                    int flightIndex = flight.findFlight(sc.next());
                    if (flightIndex == -1) {
                        System.out.println("> Flight not found");
                        Menu.pressEnterToContinue();
                        break;
                    }

                    if (userIndex >= 0 && flightIndex >= 0) {
                        ticket.buyTicket(userIndex, flightIndex);
                        Menu.pressEnterToContinue();
                    }
                }
                case 4 -> {
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                      TICKET CANCELLATION
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);
                    System.out.println("> Enter your your ticket Id: ");
                    int ticketIndex = ticket.findTicket(sc.next());
                    if (ticketIndex == -1) {
                        System.out.println("> Ticket not found");
                        Menu.pressEnterToContinue();
                    } else if (ticketIndex >= 0) {
                        ticket.cancelTicket(ticketIndex);
                        Menu.pressEnterToContinue();
                    }

                }
                case 5 -> {
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                        BOOKED TICKET
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);

                    System.out.println("> Enter your username: ");
                    int userIndex = new_user.findUser(sc.next());
                    if (userIndex == -1) {
                        System.out.println("> User not found");
                        Menu.pressEnterToContinue();
                    } else {
                        ticket.bookedTicket(userIndex);
                        Menu.pressEnterToContinue();
                    }

                }
                case 6 -> {
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                          ADD CHARGE
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);
                    System.out.println("> Enter your username: ");
                    int userIndex = new_user.findUser(sc.next());
                    if (userIndex == -1) {
                        System.out.println("> User not found");
                        Menu.pressEnterToContinue();
                    } else {
                        System.out.println("> Your current balance is " + Database.users.get(userIndex).getBalance());
                        System.out.println();
                        System.out.println("> How much would you like to charge your account? ");
                        System.out.println("> to the limit of " + Integer.MAX_VALUE);
                        new_user.addBalance(sc.nextInt(), userIndex);
                        Menu.pressEnterToContinue();
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + userCommand);
            }
            Menu.clearScreen();
            passengerMenu();
            System.out.println();
            System.out.println("> Enter your command: ");
            userCommand = sc.nextInt();
        }
    }

    private static void passengerMenu() {
        System.out.println(Signup.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                         PASSENGER MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                    <1> Change password
                    <2> Search flight tickets
                    <3> Booking ticket
                    <4> Ticket cancellation
                    <5> Booked tickets
                    <6> Add charge
                    <0> Sign out""" + Signup.RESET);
    }
}

// make date and time right
// big numbers in charge int
// use of iterators
// I ask for username constantly
// Menus shouldn't be called inside each other
// complete the database
// comments