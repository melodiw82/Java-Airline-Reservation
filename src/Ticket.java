public class Ticket {
    private String ticketId, tiFlightId, tiUsername;
    private static final Flight flight = new Flight();
    private static final User user = new User();

    private static int ticketCounter = 0;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTiFlightId() {
        return tiFlightId;
    }

    public void setTiFlightId(String tiFlightId) {
        this.tiFlightId = tiFlightId;
    }

    public String getTiUsername() {
        return tiUsername;
    }

    public void setTiUsername(String tiUsername) {
        this.tiUsername = tiUsername;
    }

    public Ticket(String ticketId, String flightId, String username) {
        this.ticketId = ticketId;
        this.tiFlightId = flightId;
        this.tiUsername = username;
    }

    public Ticket() {

    }

    public static String GenerateId(int flightIndex) {
        String newFlightId = Database.flights.get(flightIndex).getFlightId();
        ticketCounter++;
        return newFlightId + ticketCounter;
    }

    public void addTicket(String ticketId, String flightId, String username) {
        Database.tickets.add(new Ticket(ticketId, flightId, username));
    }

    public void buyTicket(int userIndex, int flightIndex) {
        int price = Database.flights.get(flightIndex).getPrice();
        int userBalance = Database.users.get(userIndex).getBalance();
        if (userBalance < price) {
            int shortOf = price - userBalance;
            System.out.println("> You're short of " + shortOf);
            System.out.println("> Please charge your account");
            Menu.pressEnterToContinue();
            Passenger.passengerMenuExe();
        } else {
            int newBalance = userBalance - price;
            Database.users.get(userIndex).setBalance(newBalance);
            bookFlight(flightIndex);

            System.out.println(Signup.GREEN_BOLD + "> Flight booked successfully" + Signup.RESET);

            String newTicketId = GenerateId(flightIndex);
            addTicket(newTicketId, Database.flights.get(flightIndex).getFlightId(), Database.users.get(userIndex).getUsername());
            System.out.println("> Your ticket Id is " + newTicketId);
        }
    }

    public void bookFlight(int flightIndex) {
        int temp = Database.flights.get(flightIndex).getSeats();
        temp -= 1;
        Database.flights.get(flightIndex).setSeats(temp);
    }

    public static void toString(int index) {
        System.out.printf("%s%-15s%s%-15s%s%n", "|TicketID: ", Database.tickets.get(index).getTicketId(), "|FlightID: ", Database.tickets.get(index).getTiFlightId(), "|");
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

    public int findTicket(String ticketId) {
        for (int i = 0; i < Database.tickets.size(); i++) {
            if (Database.tickets.get(i).getTicketId().equals(ticketId)) {
                return i;
            }
        }
        return -1;
    }

    public void cancelFlight(int flightIndex) {
        int temp = Database.flights.get(flightIndex).getSeats();
        temp += 1;
        Database.flights.get(flightIndex).setSeats(temp);
    }

    public void cancelTicket(int ticketIndex) {
        String flightId = Database.tickets.get(ticketIndex).getTiFlightId();
        int flightIndex = flight.findFlight(flightId);
        cancelFlight(flightIndex);

        String username = Database.tickets.get(ticketIndex).getTiUsername();
        int userIndex = user.findUser(username);
        int price = Database.flights.get(flightIndex).getPrice();
        int balance = Database.users.get(userIndex).getBalance();
        int backToAccount = balance + price;

        Database.users.get(userIndex).setBalance(backToAccount);

        Database.tickets.remove(ticketIndex);

        System.out.println(Signup.GREEN_BOLD + "> Ticket cancelled successfully" + Signup.RESET);
    }
}