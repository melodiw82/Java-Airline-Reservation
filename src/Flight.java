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

    // constructor
    public Flight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        setFlightId(flightId);
        setOrigin(origin);
        setDestination(destination);
        setDate(date);
        setTime(time);
        setPrice(price);
        setSeats(seats);
    }

    // adds flights to the flights arraylist
    public void addFlight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        Database.flights.add(new Flight(flightId, origin, destination, date, time, price, seats));
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

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}