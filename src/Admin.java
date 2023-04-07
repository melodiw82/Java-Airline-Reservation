import java.util.Scanner;

public class Admin {
    private static final Scanner sc = new Scanner(System.in);
    private static final Flight flight = new Flight();

    public static void main(String[] args) {
        adminMenuExe();
        System.out.println(flight.flights);
    }

    public static void adminMenuExe() {
        Menu.clearScreen();
        adminMenu();
        System.out.printf("%n%s%n", "> Enter your command: ");
        int adminCommand = sc.nextInt();
        switch (adminCommand) {
            case 1:
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

                break;

            case 2:
                System.out.println("> Enter the flight ID to be updated: ");
                fId = sc.next();

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
}