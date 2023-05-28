import java.io.IOException;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner sc = new Scanner(System.in);
    private final Utils utils = new Utils();
    private final FlightsFile flightsFile = new FlightsFile();
    private final TicketsFile ticketsFile = new TicketsFile();

    // executes the admin menu
    public void adminMenuExe() throws IOException {
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

    // adds new flights
    private void addFlightMenu() throws IOException {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                               ADD FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);

        System.out.println("> Add flight ID: (Ex. WX-12)");
        String fId = utils.inputID(sc.next());
        int index = flightsFile.findFlight(fId);
        if (index != -1) {
            System.out.println(utils.RED_BOLD + "> Flight already exists" + utils.RESET);
        }

        if (index == -1) {
            System.out.println();
            System.out.println("> Add flight origin: (Ex. Yazd)");
            String origin = utils.inputCity(sc.next());
            System.out.println();
            System.out.println("> Add flight destination: (Ex.Tehran)");
            String destination = utils.inputCity(sc.next());
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
            flightsFile.writeFlightInFile(fId, origin, destination, date, time, price, seat);
            System.out.println();
            System.out.println(utils.GREEN_BOLD + ">> Flight added successfully" + utils.RESET);
        }
        utils.pressEnterToContinue();
    }

    // updates the flights, doesn't let the admin update already booked flights
    private void updateFlightMenu() throws IOException {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            UPDATE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        System.out.println("> Enter the flight ID to be updated: ");
        String flightId = sc.next();
        int index = flightsFile.findFlight(utils.inputID(flightId));
        System.out.println();

        boolean isBooked = false;
        if (ticketsFile.alreadyBooked(flightId)) {
            System.out.println(utils.RED_BOLD + "> Flight is already booked and cannot be changed" + utils.RESET);
            System.out.println();
            isBooked = true;
            utils.pressEnterToContinue();
        }

        if (!isBooked) {
            if (index == -1) {
                System.out.println();
                System.out.println(utils.RED_BOLD + ">> Flight not found" + utils.RESET);
                utils.pressEnterToContinue();
            }
            if (index != -1) {
                System.out.println(utils.CYAN_BOLD + "> Enter the section that needs to be updated: \n\n1.origin\n\n2.destination\n\n3.date\n\n4.time\n\n5.price\n\n6.seat" + utils.RESET);
                System.out.println();
                int command = utils.inputNum();

                switch (command) {
                    case 1:
                        System.out.println("> Enter the new origin: ");
                        String updateCommand = sc.next();
                        flightsFile.updateFlight(index, "origin", updateCommand);
                        System.out.println();
                        System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                        break;
                    case 2:
                        System.out.println("> Enter the new destination: ");
                        updateCommand = sc.next();
                        flightsFile.updateFlight(index, "destination", updateCommand);
                        System.out.println();
                        System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                        break;
                    case 3:
                        System.out.println("> Enter the new date: ");
                        System.out.println();
                        updateCommand = utils.inputDate();
                        flightsFile.updateFlight(index, "date", updateCommand);
                        System.out.println();
                        System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                        break;
                    case 4:
                        System.out.println("> Enter the new time: ");
                        System.out.println();
                        updateCommand = utils.inputTime();
                        flightsFile.updateFlight(index, "time", updateCommand);
                        System.out.println();
                        System.out.println(utils.GREEN_BOLD + "> Flight updated successfully" + utils.RESET);
                        break;
                    case 5:
                        System.out.println("> Enter the new price: ");
                        int updateCom = utils.inputNum();
                        flightsFile.updateFlight(index, "price", updateCom);
                        System.out.println();
                        System.out.println(utils.GREEN_BOLD + "> flight updated successfully" + utils.RESET);
                        break;
                    case 6:
                        System.out.println("> Enter the new available seats: ");
                        updateCom = utils.inputNum();
                        flightsFile.updateFlight(index, "seat", updateCom);
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
    }

    // removes existing flights using the flight ID, doesn't let the admin remove already booked flights
    private void removeFlightMenu() throws IOException {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            REMOVE FLIGHT
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        System.out.println("> Enter the flight ID to be removed: ");
        String flightId = sc.next();
        int index = flightsFile.findFlight(utils.inputID(flightId));

        boolean isBooked = false;
        if (ticketsFile.alreadyBooked(flightId)) {
            System.out.println(utils.RED_BOLD + "> Flight is already booked and cannot be changed" + utils.RESET);
            System.out.println();
            isBooked = true;
            utils.pressEnterToContinue();
        }

        if (!isBooked) {
            if (index != -1) {
                flightsFile.removeFlight(index);
                System.out.println(utils.GREEN_BOLD + "> Flight removed successfully" + utils.RESET);
            }

            if (index == -1) {
                System.out.println();
                System.out.println(utils.RED_BOLD + "> Flight not found" + utils.RESET);
            }

            utils.pressEnterToContinue();
        }
    }

    // list of all flights
    public void scheduleFlightMenu() {
        utils.clearScreen();
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                            FLIGHT SCHEDULE
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
        try {
            flightsFile.readSchedule();
        } catch (IOException e) {
            e.printStackTrace();
        }
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