import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class passenger {

    static Pattern pattern = Pattern.compile(Signup.regex);

    private static final Scanner sc = new Scanner(System.in);
    private static final User new_user = new User();

    public static void main(String[] args) {
        Database database = new Database();
        passengerMenuExe();
    }

    private static void passengerMenuExe() {
        Menu.clearScreen();
        passengerMenu();
        System.out.println("> Enter your command: ");
        int userCommand = sc.nextInt();
        while (userCommand != 0) {
            switch (userCommand) {
                case 1:
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
                    break;
                case 2:
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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    Menu.clearScreen();
                    System.out.println(Signup.CYAN_BOLD + """
                            ::::::::::::::::::::::::::::::::::::::::
                                          ADD CHARGE
                            ::::::::::::::::::::::::::::::::::::::::
                              """ + Signup.RESET);

                    System.out.println("> Enter your username: ");
                    int index = new_user.findUser(sc.next());
                    if (index == -1) {
                        Menu.pressEnterToContinue();
                        passengerMenuExe();
                    }
                    System.out.println("> How much would you like to charge your account? ");
                    new_user.addBalance(sc.nextInt(), index);
                    Menu.pressEnterToContinue();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + userCommand);
            }
            Menu.clearScreen();
            passengerMenu();
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