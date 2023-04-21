import java.util.Scanner;

public class AdminMenu {
    private final Scanner sc = new Scanner(System.in);
    private final Utils utils = new Utils();
    private final Flight flight = new Flight();

    public void adminMenuExe() {
        utils.clearScreen();
        adminMenu();
        System.out.printf("%n%s%n", "> Enter your command: ");
        int adminCommand = utils.inputNum();
        while (adminCommand != 0) {
            switch (adminCommand) {
                case 1 -> addFlightMenu();

                case 2 -> updateFlightMenu();

                case 3 -> removeFlightMenu();

                case 4 -> scheduleFlightMenu();

                default -> System.out.println("> Invalid input");
            }
            utils.clearScreen();
            adminMenu();
            System.out.println();
            System.out.println("> Enter your command");
            adminCommand = utils.inputNum();
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
        System.out.println("> Add flight date:");
        System.out.println();
        String date = utils.inputDate();
        System.out.println("> Add flight time: (HH:MM)");
        System.out.println();
        String time = utils.inputTime();
        System.out.println("> Add flight price: (integer)");
        int price = utils.inputNum();
        System.out.println("> Add available seats: (integer)");
        int seat = utils.inputNum();
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

        boolean isBooked = false;

        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTiFlightId().equals(flightId)) {
                System.out.println("> Flight is already booked and cannot be changed");
                isBooked = true;
                utils.pressEnterToContinue();
            }
        }

        if (!isBooked) {
            System.out.println("> Enter the section that needs to be updated: \n1.flight Id\n2.origin\n3.destination\n4.date\n5.time\n6.price\n7.seat");
            int command = utils.inputNum();

            boolean foundFlight = false;
            for (int i = 0; i < Database.flights.size(); i++) {
                if ((Database.flights.get(i).getFlightId()).equals(flightId)) {
                    foundFlight = true;
                    switch (command) {
                        case 1:
                            System.out.println("> Enter the new flight Id: ");
                            String updateCommand = sc.next();
                            Database.flights.get(i).setFlightId(updateCommand);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 2:
                            System.out.println("> Enter the new flight origin: ");
                            updateCommand = sc.next();
                            Database.flights.get(i).setOrigin(updateCommand);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 3:
                            System.out.println("> Enter the new flight destination: ");
                            updateCommand = sc.next();
                            Database.flights.get(i).setDestination(updateCommand);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 4:
                            System.out.println("> Enter the new flight date: ");
                            updateCommand = utils.inputDate();
                            Database.flights.get(i).setDate(updateCommand);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 5:
                            System.out.println("> Enter the new flight time: ");
                            updateCommand = utils.inputTime();
                            Database.flights.get(i).setTime(updateCommand);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 6:
                            System.out.println("> Enter the new flight price: ");
                            int updateCom = utils.inputNum();
                            Database.flights.get(i).setPrice(updateCom);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 7:
                            System.out.println("> Enter the new flight available seats: ");
                            updateCom = utils.inputNum();
                            Database.flights.get(i).setSeats(updateCom);
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        default:
                            System.out.println("> Invalid input");
                    }
                    utils.pressEnterToContinue();
                }
            }
            if (!foundFlight) {
                System.out.println(utils.RED_BOLD + ">> flight not found" + utils.RESET);
                utils.pressEnterToContinue();
            }
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

        boolean isBooked = false;

        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTiFlightId().equals(flightId)) {
                System.out.println("> Flight is already booked and cannot be removed");
                isBooked = true;
            }
        }

        if (!isBooked) {
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