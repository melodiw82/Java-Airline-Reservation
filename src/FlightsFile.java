import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FlightsFile extends FileWriter {
    public final File flight;
    public RandomAccessFile flightRand;

    /**
     * constructor to generate a random access file
     */
    public FlightsFile() {
        flight = new File("flight.text");
        try {
            flightRand = new RandomAccessFile(flight, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes flight's information at the end of the file
     * @param flightId id of the flight
     * @param origin origin of the flight
     * @param destination destination of the flight
     * @param date date of the flight
     * @param time time of the flight
     * @param price price of the flight in Thomans
     * @param seats available seats left
     */
    public void writeFlightInFile(String flightId, String origin, String destination, String date, String time, int price, int seats) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");
        if (flight.exists()) {
            flightRand.seek(flightRand.length());

            try {
                flightRand.writeBytes(fixStringToWrite(flightId));
                flightRand.writeBytes(fixStringToWrite(origin));
                flightRand.writeBytes(fixStringToWrite(destination));
                flightRand.writeBytes(fixStringToWrite(date));
                flightRand.writeBytes(fixStringToWrite(time));
                flightRand.writeBytes(fixIntToWrite(price));
                flightRand.writeBytes(fixIntToWrite(seats));
                flightRand.writeBytes("\n");

                System.out.println("> Data Successfully Saved");
            } catch (IOException e) {
                System.out.println("> Error writing File...");
                e.printStackTrace();
            }
        }

        flightRand.close();
    }

    /**
     * finds the flight index using flight id
     * @param flightId id of the flight
     * @return index of the line in which given flight is, if flight doesn't exist it will return -1
     */
    public int findFlight(String flightId) throws IOException {
        flightRand = new RandomAccessFile(flight, "r");

        for (int i = 0; i < flightRand.length(); i += ((7 * FIX_SIZE) + 1)) {
            flightRand.seek(i);

            String id = new String(readCharsFromFile(flightRand, i, 5));

            if (id.equals(flightId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * reads all the flights from the beginning of the file
     */
    public void readSchedule() throws IOException {
        flightRand = new RandomAccessFile(flight, "r");
        flightRand.seek(0);

        String tmp;
        System.out.printf("%s%-14s%s%-14s%s%-14s%s%-14s%s%-14s%s%-14s%s%-14s%n", "|", "FlightId", "|", "Origin",
                "|", "Destination",
                "|", "Date", "|", "Time",
                "|", "Price", "|", "Seats"
        );
        System.out.println(".....................................................................................................");
        while ((tmp = flightRand.readLine()) != null) {
            System.out.println(tmp);
        }

        flightRand.close();
    }

    /**
     * sets said parameters and overrides the file to save new info in place of the old ones
     * @param index index of the beginning line of the flight
     * @param section section which will be updated
     * @param updateCommand new value of the given section
     */
    public void updateFlight(int index, String section, String updateCommand) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        switch (section) {
            case "origin" -> {
                flightRand.seek(index + FIX_SIZE);
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }

            case "destination" -> {
                flightRand.seek(index + (2 * FIX_SIZE));
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }

            case "date" -> {
                flightRand.seek(index + (3 * FIX_SIZE));
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }

            case "time" -> {
                flightRand.seek(index + (4 * FIX_SIZE));
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }
        }

        flightRand.close();
    }

    /**
     * overrides the above method, for integers (price and seat)
     * @param index index of the flight
     * @param section section to be updated
     * @param updateCommand new value that will override the old information
     */
    public void updateFlight(int index, String section, int updateCommand) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        switch (section) {
            case "price" -> {
                flightRand.seek(index + (5 * FIX_SIZE));
                flightRand.writeBytes(fixIntToWrite(updateCommand));
            }

            case "seat" -> {
                flightRand.seek(index + (6 * FIX_SIZE));
                flightRand.writeBytes(fixIntToWrite(updateCommand));
            }
        }

        flightRand.close();
    }

    /**
     * shift all the flights after the removed on by 1 line and removes the last line of the file
     * @param index index of the flight
     */
    public void removeFlight(int index) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        int j = index;
        for (int i = index + ((7 * FIX_SIZE) + 1); i < flightRand.length(); i += ((7 * FIX_SIZE) + 1)) {
            flightRand.seek(i);
            String temp = flightRand.readLine();

            flightRand.seek(j);
            flightRand.writeBytes(temp);

            j += ((7 * FIX_SIZE) + 1);
        }

        flightRand.setLength(flightRand.length() - ((7 * FIX_SIZE) + 1));

        flightRand.close();
    }

    /**
     * used in search flight field, searches the file and the wanted field
     * @param seek determines the field to be searched
     * @param str given string to be searched
     */
    public void searchFlight(int seek, String str) throws IOException {
        flightRand = new RandomAccessFile(flight, "r");
        boolean found = false;

        for (int i = seek; i < flightRand.length(); i += ((7 * FIX_SIZE) + 1)) {
            String temp = new String(readCharsFromFile(flightRand, i, FIX_SIZE));
            if (temp.trim().equals(str)) {
                toString(i - seek);
                found = true;
            }
        }
        if (!found) {
            System.out.println("> Flight not found");
        }
        flightRand.close();
    }

    /**
     * used in above method to display the flight line
     * @param flightIndex index of the flight
     */
    private void toString(int flightIndex) throws IOException {
        flightRand.seek(flightIndex);
        System.out.println(flightRand.readLine());
    }

    /**
     * used for buying or cancelling a ticket, if the section is "buy" a seat will be reduced and if it is "cancel" a seat will be added
     * @param section buy or cancel
     * @param index index of the flight
     * @param seat number of seats in the flight
     */
    public void updateSeat(String section, int index, int seat) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        flightRand.seek(index + (6 * FIX_SIZE));
        switch (section) {
            case "buy" -> flightRand.writeBytes(fixIntToWrite(seat - 1));
            case "cancel" -> flightRand.writeBytes(fixIntToWrite(seat + 1));
        }
    }
}