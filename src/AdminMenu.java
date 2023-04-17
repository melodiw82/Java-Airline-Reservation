import java.util.Scanner;

public class AdminMenu {
    private final Scanner sc = new Scanner(System.in);
    private final Utils utils = new Utils();
    private final Flight flight = new Flight();

    public void adminMenuExe() {
        utils.clearScreen();
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
            utils.clearScreen();
            adminMenu();
            adminCommand = sc.nextInt();
        }
    }

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

    private void addFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                               ADD FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
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
        System.out.println(utils.GREEN_BOLD + ">> flight added successfully" + utils.RESET);
        utils.pressEnterToContinue();
    }

    private void updateFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            UPDATE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
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
                System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                utils.pressEnterToContinue();
            }
        }
        if (!foundFlight) {
            System.out.println(utils.RED_BOLD + ">> flight not found" + utils.RESET);
            utils.pressEnterToContinue();
        }
    }

    private void removeFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            REMOVE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        System.out.println("> Enter the flight ID to be removed: ");
        String flightId = sc.next();
        boolean foundFlight = false;
        for (int i = 0; i < Database.flights.size(); i++) {
            if ((Database.flights.get(i).getFlightId()).equals(flightId)) {
                Database.flights.remove(i);
                foundFlight = true;
                System.out.println(utils.GREEN_BOLD + ">> flight removed successfully" + utils.RESET);
                break;
            }
        }
        if (!foundFlight) {
            System.out.println(utils.RED_BOLD + "> Invalid flight ID" + utils.RESET);
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
        utils.pressEnterToContinue();
    }
}