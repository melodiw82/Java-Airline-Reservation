import java.util.Scanner;

public class Admin {
    private static final Scanner sc = new Scanner(System.in);
    private static final Database database = new Database();
    private static final String GREEN_BOLD = "\033[1;32m";
    private static final String RESET = "\033[0m";

    public static void main(String[] args) {
        adminMenuExe();
    }

    public static void adminMenuExe() {
        clearScreen();
        adminMenu();
        System.out.printf("%n%s%n", "> Enter your command: ");
        int adminCommand = sc.nextInt();
        switch (adminCommand) {
            case 1:
                clearScreen();
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
                database.addFlight(fId, origin, destination, date, time, price, seat);
                System.out.println(GREEN_BOLD + ">> flight added successfully" + RESET);

                pressEnterToContinue();
                adminMenuExe();
                break;

            case 2:
                System.out.println("> Enter the flight ID to be updated: ");
                fId = sc.next();
                if (database.flights.contains(fId)) {
                    
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + adminCommand);
        }
    }

    private static void adminMenu() {
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

    private static void pressEnterToContinue() {
        System.out.printf("%n%s%n", "Press Enter key to continue...");
        try {
            sc.nextLine();
            sc.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}