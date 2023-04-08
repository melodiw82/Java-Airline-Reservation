import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);
    private static final Flight flight = new Flight();
    public static ArrayList<String> username = new ArrayList<>();
    public static ArrayList<String> password = new ArrayList<>();

    public static void main(String[] args) {
        menuExecution();
    }
    public static void menuExecution() {
        clearScreen();
        mainMenu();
        System.out.println();
        System.out.println("> Enter your command: ");
        int command = sc.nextInt();

        while (true) {
            switch (command) {
                case 1 -> {
                    clearScreen();
                    signIn();
                }
                case 2 -> {
                    clearScreen();
                    Signup signup = new Signup();
                    pressEnterToContinue();
                }
                default -> throw new IllegalStateException("Unexpected value: " + command);
            }
            clearScreen();
            mainMenu();
            command = sc.nextInt();
        }
    }

    private static void signIn() {
        System.out.println(Signup.CYAN_BOLD + """

                                                                                               \s
                                                                                               \s
                                                                                               \s
                              ,--,                                   ,--,                      \s
                            ,--.'|                   ,---,         ,--.'|         ,---,        \s
                  .--.--.   |  |,     ,----._,.  ,-+-. /  |        |  |,      ,-+-. /  |       \s
                 /  /    '  `--'_    /   /  ' / ,--.'|'   |        `--'_     ,--.'|'   |       \s
                |  :  /`./  ,' ,'|  |   :     ||   |  ,"' |        ,' ,'|   |   |  ,"' |       \s
                |  :  ;_    '  | |  |   | .\\  .|   | /  | |        '  | |   |   | /  | |       \s
                 \\  \\    `. |  | :  .   ; ';  ||   | |  | |        |  | :   |   | |  | |       \s
                  `----.   \\'  : |__'   .   . ||   | |  |/         '  : |__ |   | |  |/        \s
                 /  /`--'  /|  | '.'|`---`-'| ||   | |--'          |  | '.'||   | |--'         \s
                '--'.     / ;  :    ;.'__/\\_: ||   |/              ;  :    ;|   |/             \s
                  `--'---'  |  ,   / |   :    :'---'               |  ,   / '---'              \s
                             ---`-'   \\   \\  /                      ---`-'                     \s
                                       `--`-'                                                  \s
                """ + Signup.RESET);
        System.out.println("> Enter your username: ");
        String user = sc.next();
        System.out.println("> Enter your password: ");
        String pass = sc.next();
        if (user.equals("Admin") && pass.equals("Admin")) {
            clearScreen();
            adminMenuExe();
        } else if (username.contains(user) && password.contains(pass)) {
            clearScreen();
            passengerMenu();
        } else {
            System.out.println("> Invalid username or password");
            System.out.println("> If you don't hava an account, create one in sign up menu...");
            pressEnterToContinue();
        }
    }

    private static void mainMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                           WELCOME TO AIRLINE RESERVATION SYSTEM
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ..........................MENU OPTIONS........................

                    <1> Sign in
                    <2> Sign up\s""");
    }

    private static void passengerMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::
                         PASSENGER MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                 ......................................
                    <1> Change password
                    <2> Search flight tickets
                    <3> Booking ticket
                    <4> Ticket cancellation
                    <5> Booked tickets
                    <6> Add charge
                    <0> Sign out""");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public static void pressEnterToContinue() {
        System.out.printf("%n%s%n", "Press Enter key to continue...");
        try {
            sc.nextLine();
            sc.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void adminMenuExe() {
        Menu.clearScreen();
        adminMenu();
        System.out.printf("%n%s%n", "> Enter your command: ");
        int adminCommand = sc.nextInt();
        while (adminCommand != 0) {
            switch (adminCommand) {
                case 1 -> addFlightMenu();

                case 2 -> updateFlightMenu();

                case 3 -> removeFlightMenu();

                case 4 -> scheduleFlightMenu();

                default -> throw new IllegalStateException("Unexpected value: " + adminCommand);
            }
            Menu.clearScreen();
            adminMenu();
            adminCommand = sc.nextInt();
        }
    }

    public static void adminMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::
                           Admin MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                 ......................................
                    <1> Add
                    <2> Update
                    <3> Remove
                    <4> Flight schedules
                    <0> Sign out""");
    }

    public static void addFlightMenu() {
        Menu.clearScreen();
        System.out.println("> Add flight ID: (Ex. WX-12)");
        String fId = sc.next();
        System.out.println("> Add flight origin: (Ex. Yazd)");
        String origin = sc.next();
        System.out.println("> Add flight destination: (Ex.Tehran)");
        String destination = sc.next();
        System.out.println("> Add flight date: (yyyy-mm-dd)");
        String date = sc.next();
        System.out.println("> Add flight time: (HH:MM)");
        String time = sc.next();
        System.out.println("> Add flight price: (integer)");
        int price = sc.nextInt();
        System.out.println("> Add available seats: (integer)");
        int seat = sc.nextInt();
        flight.addFlight(fId, origin, destination, date, time, price, seat);
        System.out.println(Signup.GREEN_BOLD + ">> flight added successfully" + Signup.RESET);
        Menu.pressEnterToContinue();
    }

    public static void updateFlightMenu() {
        Menu.clearScreen();
        System.out.println("> Enter the flight ID to be updated: ");
        String flightId = sc.next();
        System.out.println("> Enter the section that needs to be updated: \n1.flight Id\n2.origin\n3.destination\n4.date\n5.time\n6.price\n7.seat");
        int command = sc.nextInt();

        boolean foundFlight = false;
        for (int i = 0; i < Database.flights.size(); i++) {
            if ((Database.flights.get(i).getFlightId()).equals(flightId)) {
                foundFlight = true;
                switch (command) {
                    case 1:
                        System.out.println("> Enter the new flight Id: ");
                        String updateCommand = sc.next();
                        Database.flights.get(i).setFlightId(updateCommand);
                        break;
                    case 2:
                        System.out.println("> Enter the new flight origin: ");
                        updateCommand = sc.next();
                        Database.flights.get(i).setOrigin(updateCommand);
                        break;
                    case 3:
                        System.out.println("> Enter the new flight destination: ");
                        updateCommand = sc.next();
                        Database.flights.get(i).setDestination(updateCommand);
                        break;
                    case 4:
                        System.out.println("> Enter the new flight date: ");
                        updateCommand = sc.next();
                        Database.flights.get(i).setDate(updateCommand);
                        break;
                    case 5:
                        System.out.println("> Enter the new flight time: ");
                        updateCommand = sc.next();
                        Database.flights.get(i).setTime(updateCommand);
                        break;
                    case 6:
                        System.out.println("> Enter the new flight price: ");
                        int updateCom = sc.nextInt();
                        Database.flights.get(i).setPrice(updateCom);
                        break;
                    case 7:
                        System.out.println("> Enter the new flight available seats: ");
                        updateCom = sc.nextInt();
                        Database.flights.get(i).setSeats(updateCom);
                        break;
                }
                System.out.println(Signup.GREEN_BOLD + "> flight updated successfully" + Signup.RESET);
                Menu.pressEnterToContinue();
            }
        }
        if (!foundFlight) {
            System.out.println(Signup.RED_BOLD + ">> flight not found" + Signup.RESET);
            Menu.pressEnterToContinue();
        }
    }

    public static void removeFlightMenu() {
        System.out.println("> Enter the flight ID to be removed: ");
        String flightId = sc.next();
        boolean foundFlight = false;
        for (int i = 0; i < Database.flights.size(); i++) {
            if ((Database.flights.get(i).getFlightId()).equals(flightId)) {
                Database.flights.remove(i);
                foundFlight = true;
                System.out.println(Signup.GREEN_BOLD + ">> flight removed successfully" + Signup.RESET);
                break;
            }
        }
        if (!foundFlight) {
            System.out.println("> Invalid flight ID");
        }
        Menu.pressEnterToContinue();
    }

    public static void scheduleFlightMenu() {
        System.out.println("> Flight schedule");
        System.out.printf("%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%n", "|", "FlightId", "|", "Origin",
                "|", "Destination",
                "|", "Date", "|", "Time",
                "|", "Price", "|", "Seats", "|"
        );
        for (int i = 0; i < Database.flights.size(); i++) {
            Flight.toString(i);
        }
        Menu.pressEnterToContinue();
    }
}