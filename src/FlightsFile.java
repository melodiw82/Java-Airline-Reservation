import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FlightsFile extends FileWriter {
    public final File flight;
    public RandomAccessFile flightRand;

    public FlightsFile() {
        flight = new File("flight.text");
        try {
            flightRand = new RandomAccessFile(flight, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // writes the flight information at the end of the file
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

    // reads all the flights from the beginning of the file
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

    // shifts the flights up by 1 line and deletes the last line of file
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

    private void toString(int flightIndex) throws IOException {
        flightRand.seek(flightIndex);
        System.out.println(flightRand.readLine());
    }

    public void updateSeat(String section, int index, int seat) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        flightRand.seek(index + (6 * FIX_SIZE));
        switch (section) {
            case "buy" -> flightRand.writeBytes(fixIntToWrite(seat - 1));
            case "cancel" -> flightRand.writeBytes(fixIntToWrite(seat + 1));
        }
    }
}