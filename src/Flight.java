public class Flight {
    // Fields
    private String flightId, origin, destination, date, time;
    private int price, seats;

    // constructor for initialization
    public Flight() {
        flightId = "no available flight ID";
        origin = "unknown origin city";
        destination = "unknown destination city";
        date = "no available date";
        time = "no available time";
        price = 0;
        seats = 100;
    }

    // prints the list of flights used in flight schedule field
    public void toString(int index) {
        System.out.printf("%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%n", "|", Database.flights.get(index).getFlightId(), "|", Database.flights.get(index).getOrigin(),
                "|", Database.flights.get(index).getDestination(),
                "|", Database.flights.get(index).getDate(), "|", Database.flights.get(index).getTime(),
                "|", Database.flights.get(index).getPrice(), "|", Database.flights.get(index).getSeats(), "|"
        );
    }

    // finds the flights[index] from the flights arraylist
    public int findFlight(String flightId) {
        for (int i = 0; i < Database.flights.size(); i++) {
            if (Database.flights.get(i).getFlightId().equals(flightId)) {
                return i;
            }
        }
        return -1;
    }

    //getters and setters
    public String getFlightId() {
        return flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}