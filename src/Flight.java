public class Flight {
    // Fields
    private String flightId, origin, destination, date, time;
    private int price, seats;

    // Constructor
    public Flight() {
        flightId = "no available flight ID";
        origin = "unknown origin city";
        destination = "unknown destination city";
        date = "no available date";
        time = "no available time";
        price = 0;
        seats = 100;
    }

    public Flight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        setFlightId(flightId);
        setOrigin(origin);
        setDestination(destination);
        setDate(date);
        setTime(time);
        setPrice(price);
        setSeats(seats);
    }

    public void addFlight(String flightId, String origin, String destination, String date, String time, int price, int seats) {
        Database.flights.add(new Flight(flightId, origin, destination, date, time, price, seats));
    }

    public static void toString(int index) {
        System.out.printf("%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%-15s%s%n", "|", Database.flights.get(index).getFlightId(), "|", Database.flights.get(index).getOrigin(),
                "|", Database.flights.get(index).getDestination(),
                "|", Database.flights.get(index).getDate(), "|", Database.flights.get(index).getTime(),
                "|", Database.flights.get(index).getPrice(), "|", Database.flights.get(index).getSeats(), "|"
        );
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