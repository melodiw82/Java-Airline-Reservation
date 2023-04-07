import java.util.Scanner;

public class Admin {
    private static final Scanner sc = new Scanner(System.in);
    private static final Flight flight = new Flight();

    public static void main(String[] args) {
        Database database = new Database();

        adminMenuExe();
    }

    public static void adminMenuExe() {
        Menu.clearScreen();
        adminMenu();
        System.out.printf("%n%s%n", "> Enter your command: ");
        int adminCommand = sc.nextInt();
        while (adminCommand != 0) {
            switch (adminCommand) {
                case 1 -> {
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
                }

                case 2 -> System.out.println("> Enter the flight ID to be updated: ");

                case 3 -> {
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
                }

                case 4 -> {
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
}