public class Ticket {
    private String ticketId, tiFlightId, tiUsername;
    private Flight flight = new Flight();
    private User user = new User();
    private Utils utils = new Utils();

    // static integer to count the tickets in order to generate unique ticket IDs
    private static int ticketCounter = 0;

    // constructor
    public Ticket(String ticketId, String flightId, String username) {
        this.ticketId = ticketId;
        this.tiFlightId = flightId;
        this.tiUsername = username;
    }

    // constructor for initialization
    public Ticket() {
        ticketId = "Not available";
        tiFlightId = "No flights found";
        tiUsername = "No username";
    }

    // generates unique ticket IDs using flight ID and the amount of tickets that had been booked
    private String GenerateId(int flightIndex) {
        String newFlightId = Database.flights.get(flightIndex).getFlightId();
        ticketCounter++;
        return newFlightId + ticketCounter;
    }

    // method to add ticket to the array list of tickets
    public void addTicket(String ticketId, String flightId, String username) {
        Database.tickets.add(new Ticket(ticketId, flightId, username));
    }

    // prints the ticket ID and flight ID of the user to be used in booked tickets field
    public void toString(int index) {
        System.out.printf("%s%-15s%s%-15s%s%n", "|TicketID: ", Database.tickets.get(index).getTicketId(), "|FlightID: ", Database.tickets.get(index).getTiFlightId(), "|");
    }

    // finds the tickets[index] of tickets arraylist
    public int findTicket(String ticketId) {
        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTicketId().equals(ticketId)) {
                return i;
            }
        }
        return -1;
    }

    // books ticket for the user, pays the price of ticket using user's balance and reduces the booked flight's seats by 1
    public void buyTicket(int userIndex, int flightIndex) {
        int price = Database.flights.get(flightIndex).getPrice();
        long userBalance = Database.users.get(userIndex).getBalance();

        if (userBalance < price) {
            long shortOf = price - userBalance;
            System.out.println();
            System.out.println(utils.RED_BOLD + "> You're short of " + shortOf + utils.RESET);
            System.out.println();
            System.out.println(utils.GREY_BOLD + "> Please charge your account" + utils.RESET);
        } else {
            long newBalance = userBalance - price;
            Database.users.get(userIndex).setBalance(newBalance);

            boolean isAvailable = bookSeat(flightIndex);

            if (isAvailable) {
                System.out.println();
                System.out.println(utils.GREEN_BOLD + "> Flight booked successfully" + utils.RESET);

                String newTicketId = GenerateId(flightIndex);
                addTicket(newTicketId, Database.flights.get(flightIndex).getFlightId(), Database.users.get(userIndex).getUsername());
                System.out.println();
                System.out.println("> Your ticket Id is " + utils.PINK_BOLD + newTicketId + utils.RESET);
            }

            if (!isAvailable) {
                System.out.println();
                System.out.println(utils.RED_BOLD + "> Flight is booked fully" + utils.RESET);
            }
        }
    }

    // reduces the booked flight's seats by 1
    private boolean bookSeat(int flightIndex) {
        int temp = Database.flights.get(flightIndex).getSeats();
        if (temp == 0) {
            return false;
        } else {
            temp -= 1;
            Database.flights.get(flightIndex).setSeats(temp);
            return true;
        }
    }

    // finds the booked tickets of the user
    public void bookedTicket(int userIndex) {
        String username = Database.users.get(userIndex).getUsername();

        boolean found = false;
        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTiUsername().equals(username)) {
                toString(i);
                found = true;
            }
            System.out.println();
        }
        if (!found) {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> No tickets available..." + utils.RESET);
        }
    }

    // cancels the ticket for the user using the ticket ID, adds the extra charge back to the user's account and increases the flight's seat by 1
    public void cancelTicket(int ticketIndex) {
        String flightId = Database.tickets.get(ticketIndex).getTiFlightId();
        int flightIndex = flight.findFlight(flightId);
        cancelSeat(flightIndex);

        String username = Database.tickets.get(ticketIndex).getTiUsername();
        int userIndex = user.findUser(username);
        int price = Database.flights.get(flightIndex).getPrice();
        long balance = Database.users.get(userIndex).getBalance();
        long backToAccount = balance + price;

        Database.users.get(userIndex).setBalance(backToAccount);

        Database.tickets.remove(ticketIndex);

        System.out.println();
        System.out.println(utils.GREEN_BOLD + "> Ticket cancelled successfully" + utils.RESET);
    }

    // increase the flight's seats by 1
    private void cancelSeat(int flightIndex) {
        int temp = Database.flights.get(flightIndex).getSeats();
        temp += 1;
        Database.flights.get(flightIndex).setSeats(temp);
    }

    // getters
    public String getTicketId() {
        return ticketId;
    }

    public String getTiFlightId() {
        return tiFlightId;
    }

    public String getTiUsername() {
        return tiUsername;
    }
}