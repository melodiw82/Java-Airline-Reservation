public class Ticket {
    private String ticketId, tiFlightId, tiUsername;
    private final Flight flight = new Flight();
    private final User user = new User();
    private final Utils utils = new Utils();

    private static int ticketCounter = 0;

    public String getTicketId() {
        return ticketId;
    }

    public String getTiFlightId() {
        return tiFlightId;
    }

    public String getTiUsername() {
        return tiUsername;
    }

    public Ticket(String ticketId, String flightId, String username) {
        this.ticketId = ticketId;
        this.tiFlightId = flightId;
        this.tiUsername = username;
    }

    public Ticket() {
        ticketId = "Not available";
        tiFlightId = "No flights found";
        tiUsername = "No username";
    }

    private String GenerateId(int flightIndex) {
        String newFlightId = Database.flights.get(flightIndex).getFlightId();
        ticketCounter++;
        return newFlightId + ticketCounter;
    }

    public void addTicket(String ticketId, String flightId, String username) {
        Database.tickets.add(new Ticket(ticketId, flightId, username));
    }

    public void toString(int index) {
        System.out.printf("%s%-15s%s%-15s%s%n", "|TicketID: ", Database.tickets.get(index).getTicketId(), "|FlightID: ", Database.tickets.get(index).getTiFlightId(), "|");
    }

    public int findTicket(String ticketId) {
        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTicketId().equals(ticketId)) {
                return i;
            }
        }
        return -1;
    }

    public void buyTicket(int userIndex, int flightIndex) {
        int price = Database.flights.get(flightIndex).getPrice();
        long userBalance = Database.users.get(userIndex).getBalance();

        if (userBalance < price) {
            long shortOf = price - userBalance;
            System.out.println("> You're short of " + shortOf);
            System.out.println("> Please charge your account");
        } else {
            long newBalance = userBalance - price;
            Database.users.get(userIndex).setBalance(newBalance);

            boolean isAvailable = bookSeat(flightIndex);

            if (isAvailable) {
                System.out.println();
                System.out.println(utils.GREEN_BOLD + "> Flight booked successfully" + utils.RESET);

                String newTicketId = GenerateId(flightIndex);
                addTicket(newTicketId, Database.flights.get(flightIndex).getFlightId(), Database.users.get(userIndex).getUsername());
                System.out.println("> Your ticket Id is " + newTicketId);
            }

            if (!isAvailable) {
                System.out.println("> Flight is booked fully");
            }
        }
    }

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

    public void bookedTicket(int userIndex) {
        String username = Database.users.get(userIndex).getUsername();

        boolean found = false;
        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTiUsername().equals(username)) {
                toString(i);
                found = true;
            }
        }
        if (!found) {
            System.out.println("> No tickets available...");
        }
    }

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

        System.out.println(utils.GREEN_BOLD + "> Ticket cancelled successfully" + utils.RESET);
    }

    private void cancelSeat(int flightIndex) {
        int temp = Database.flights.get(flightIndex).getSeats();
        temp += 1;
        Database.flights.get(flightIndex).setSeats(temp);
    }
}