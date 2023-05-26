import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FlightsFile {
    public File flight;
    public RandomAccessFile flightRand;
    private final int FIX_SIZE = 15;

    public FlightsFile() {
        flight = new File("flight.text");
        try {
            flightRand = new RandomAccessFile(flight, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String fixStringToWrite(String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < FIX_SIZE) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    private String fixIntToWrite(int integer) {
        StringBuilder temp = new StringBuilder(Integer.toString(integer));
        while (temp.length() < FIX_SIZE) {
            temp.append(" ");
        }
        return temp.toString();
    }

    // writes the flight information at the end of the file
    public void writeFlightInFile(String flightId, String origin, String destination, String date, String time, int price, int seats) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");
        if (flight.exists()) {
            try {
                flightRand.seek(flightRand.length());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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

    public int findFlight(String flightId) throws IOException {
        flightRand = new RandomAccessFile(flight, "r");

        for (int i = 0; i < flightRand.length(); i += 106) {
            flightRand.seek(i);

            String id = new String(readCharsFromFile(i, 5));

            if (id.equals(flightId)) {
                flightRand.close();
                return i;
            }
        }
        flightRand.close();
        return -1;
    }

    private byte[] readCharsFromFile(int seek, int chars) throws IOException {
        flightRand = new RandomAccessFile(flight, "r");

        flightRand.seek(seek);
        byte[] bytes = new byte[chars];
        flightRand.read(bytes);

        return bytes;
    }

    public void updateFlight(int index, String section, String updateCommand) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        switch (section) {
            case "origin" -> {
                flightRand.seek(index + 15);
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }

            case "destination" -> {
                flightRand.seek(index + 30);
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }

            case "date" -> {
                flightRand.seek(index + 45);
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }

            case "time" -> {
                flightRand.seek(index + 60);
                flightRand.writeBytes(fixStringToWrite(updateCommand));
            }
        }

        flightRand.close();
    }

    public void updateFlight(int index, String section, int updateCommand) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        switch (section) {
            case "price" -> {
                flightRand.seek(index + 75);
                flightRand.writeBytes(fixIntToWrite(updateCommand));
            }

            case "seat" -> {
                flightRand.seek(index + 90);
                flightRand.writeBytes(fixIntToWrite(updateCommand));
            }
        }

        flightRand.close();
    }

    // shifts the flights up by 1 line and deletes the last line of file
    public void removeFlight(int index) throws IOException {
        flightRand = new RandomAccessFile(flight, "rw");

        int j = index;
        for (int i = index + 106; i < flightRand.length(); i += 106) {
            flightRand.seek(i);
            String temp = flightRand.readLine();

            flightRand.seek(j);
            flightRand.writeBytes(temp);

            j += 106;
        }

        flightRand.setLength(flightRand.length() - 106);

        flightRand.close();
    }
}