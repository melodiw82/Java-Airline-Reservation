import java.util.Scanner;

public class AdminMenu {
    private Scanner sc = new Scanner(System.in);
    private Utils utils = new Utils();
    private Flight flight = new Flight();

    // executes the admin menu
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

                default -> System.out.println(utils.RED_BOLD + "> Invalid input" + utils.RESET);
            }
            utils.clearScreen();
            adminMenu();
            System.out.println();
            System.out.println("> Enter your command");
            adminCommand = utils.inputNum();
        }
    }

    // adds new flights to the flight arraylist
    private void addFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                               ADD FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);

        boolean isFound = false;
        System.out.println("> Add flight ID: (Ex. WX-12)");
        String fId = sc.next();
        for (int i = 0; i < Database.flights.size(); i++) {
            if (Database.flights.get(i).getFlightId().equals(fId)) {
                System.out.println();
                System.out.println(utils.RED_BOLD + "> Flight already exists" + utils.RESET);
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println();
            System.out.println("> Add flight origin: (Ex. Yazd)");
            String origin = sc.next();
            System.out.println();
            System.out.println("> Add flight destination: (Ex.Tehran)");
            String destination = sc.next();
            System.out.println();
            System.out.println("> Add flight date:");
            System.out.println();
            String date = utils.inputDate();
            System.out.println();
            System.out.println("> Add flight time: (HH:MM)");
            System.out.println();
            String time = utils.inputTime();
            System.out.println();
            System.out.println("> Add flight price: (integer)");
            int price = utils.inputNum();
            System.out.println();
            System.out.println("> Add available seats: (integer)");
            int seat = utils.inputNum();
            flight.addFlight(fId, origin, destination, date, time, price, seat);
            System.out.println();
            System.out.println(utils.GREEN_BOLD + ">> Flight added successfully" + utils.RESET);
        }
        utils.pressEnterToContinue();
    }

    // updates the flights, doesn't let the admin update already booked flights
    private void updateFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            UPDATE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        System.out.println("> Enter the flight ID to be updated: ");
        String flightId = sc.next();
        System.out.println();

        boolean isBooked = false;

        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTiFlightId().equals(flightId)) {
                System.out.println(utils.RED_BOLD + "> Flight is already booked and cannot be changed" + utils.RESET);
                System.out.println();
                isBooked = true;
                utils.pressEnterToContinue();
                break;
            }
        }

        if (!isBooked) {
            System.out.println(utils.CYAN_BOLD + "> Enter the section that needs to be updated: \n\n1.flight Id\n\n2.origin\n\n3.destination\n\n4.date\n\n5.time\n\n6.price\n\n7.seat" + utils.RESET);
            System.out.println();
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
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                            break;
                        case 2:
                            System.out.println("> Enter the new origin: ");
                            updateCommand = sc.next();
                            Database.flights.get(i).setOrigin(updateCommand);
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                            break;
                        case 3:
                            System.out.println("> Enter the new destination: ");
                            updateCommand = sc.next();
                            Database.flights.get(i).setDestination(updateCommand);
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                            break;
                        case 4:
                            System.out.println("> Enter the new date: ");
                            System.out.println();
                            updateCommand = utils.inputDate();
                            Database.flights.get(i).setDate(updateCommand);
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                            break;
                        case 5:
                            System.out.println("> Enter the new time: ");
                            System.out.println();
                            updateCommand = utils.inputTime();
                            Database.flights.get(i).setTime(updateCommand);
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                            break;
                        case 6:
                            System.out.println("> Enter the new price: ");
                            int updateCom = utils.inputNum();
                            Database.flights.get(i).setPrice(updateCom);
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                            break;
                        case 7:
                            System.out.println("> Enter the new available seats: ");
                            updateCom = utils.inputNum();
                            Database.flights.get(i).setSeats(updateCom);
                            System.out.println();
                            System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                            break;
                        default:
                            System.out.println();
                            System.out.println(utils.RED_BOLD + "> Invalid input" + utils.RESET);
                    }
                    utils.pressEnterToContinue();
                }
            }
            if (!foundFlight) {
                System.out.println();
                System.out.println(utils.RED_BOLD + ">> Flight not found" + utils.RESET);
                utils.pressEnterToContinue();
            }
        }
    }

    // removes existing flights using the flight ID, doesn't let the admin remove already booked flights
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
                System.out.println();
                System.out.println(utils.RED_BOLD + "> Flight is already booked and cannot be removed" + utils.RESET);
                isBooked = true;
                break;
            }
        }

        if (!isBooked) {
            boolean foundFlight = false;
            for (int i = 0; i < Database.flights.size(); i++) {
                if ((Database.flights.get(i).getFlightId()).equals(flightId)) {
                    Database.flights.remove(i);
                    foundFlight = true;
                    System.out.println();
                    System.out.println(utils.GREEN_BOLD + ">> Flight removed successfully" + utils.RESET);
                    break;
                }
            }
            if (!foundFlight) {
                System.out.println();
                System.out.println(utils.RED_BOLD + "> Flight not found" + utils.RESET);
            }
        }
        utils.pressEnterToContinue();
    }

    // list of all flights
    public void scheduleFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            FLIGHT SCHEDULE
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        utils.schedulePrinter();
        utils.pressEnterToContinue();
    }

    // prints the admin menu
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
}