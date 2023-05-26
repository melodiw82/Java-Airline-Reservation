import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ThreadLocalRandom;

public class TicketsFile extends FileWriter {
    private final File ticket;
    public RandomAccessFile ticketRand;
    private final UsersFile usersFile = new UsersFile();
    private final FlightsFile flightsFile = new FlightsFile();

    public TicketsFile() {
        ticket = new File("ticket.text");
        try {
            ticketRand = new RandomAccessFile(ticket, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateId(int flightIndex) throws IOException {
        flightsFile.flightRand = new RandomAccessFile(flightsFile.flight, "r");

        String newFlightId = new String(flightsFile.readCharsFromFile(flightsFile.flightRand, flightIndex, 5));

        return newFlightId + ThreadLocalRandom.current().nextInt(0, 999 + 1) + ThreadLocalRandom.current().nextInt(0, 9 + 1);
    }

    public void writeTicketInFile(String ticketId, String username, String flightID) throws IOException {
        ticketRand = new RandomAccessFile(ticket, "rw");

        if (ticket.exists()) {
            ticketRand.seek(ticketRand.length());
            try {
                ticketRand.writeBytes(fixStringToWrite(ticketId));
                ticketRand.writeBytes(fixStringToWrite(username));
                ticketRand.writeBytes(fixStringToWrite(flightID));
                ticketRand.writeBytes("\n");

                System.out.println("> Data Successfully Saved");
            } catch (IOException e) {
                System.out.println("> Error writing File...");
                e.printStackTrace();
            }
        }

        ticketRand.close();
    }

    public void buyTicket(int flightIndex, int userIndex) throws IOException {
        ticketRand = new RandomAccessFile(ticket, "rw");
        flightsFile.flightRand = new RandomAccessFile(flightsFile.flight, "rw");
        usersFile.userRand = new RandomAccessFile(usersFile.user, "rw");

        int price = flightsFile.StringToInt(flightsFile.flightRand, flightIndex + 75);
        int seat = flightsFile.StringToInt(flightsFile.flightRand, flightIndex + 90);
        int balance = usersFile.StringToInt(usersFile.userRand, userIndex + 30);

        if (balance < price) {
            long shortOf = price - balance;
            System.out.println();
            System.out.println("> You're short of " + shortOf);
            System.out.println();
            System.out.println("> Please charge your account");
        } else {
            int newBalance = balance - price;
            usersFile.updateBalance(userIndex, newBalance);
            flightsFile.updateSeat("buy", flightIndex, seat);
            String id = generateId(flightIndex);
            String flightId = new String(flightsFile.readCharsFromFile(flightsFile.flightRand, flightIndex, 5));

            writeTicketInFile(id, Menu.currentUsername, flightId);
            System.out.println("> Flight booked successfully");
            System.out.println();
            System.out.println("> Your ticket Id is " + id);

            flightsFile.flightRand.close();
            usersFile.userRand.close();
            ticketRand.close();
        }
    }

    public void searchTicket(String username) throws IOException {
        ticketRand = new RandomAccessFile(ticket, "r");

        boolean found = false;
        for (int i = FIX_SIZE; i < ticketRand.length(); i += ((3 * FIX_SIZE) + 1)) {
            String temp = new String(readCharsFromFile(ticketRand, i, FIX_SIZE));
            if (temp.trim().equals(username)) {
                toString(i - FIX_SIZE);
                found = true;
            }
        }
        if (!found) {
            System.out.println("> Flight not found");
        }
        ticketRand.close();
    }

    private void toString(int ticketIndex) throws IOException {
        ticketRand.seek(ticketIndex);
        System.out.println(ticketRand.readLine());
    }

    public int findTicket(String ticketId) throws IOException {
        ticketRand = new RandomAccessFile(ticket, "r");

        for (int i = 0; i < ticketRand.length(); i += ((3 * FIX_SIZE)) + 1) {
            ticketRand.seek(i);

            String id = new String(readCharsFromFile(ticketRand, i, FIX_SIZE));

            if (id.trim().equals(ticketId)) {
                ticketRand.close();
                return i;
            }
        }
        ticketRand.close();
        return -1;
    }

    public void cancelTicket(int ticketIndex) throws IOException {
        ticketRand = new RandomAccessFile(ticket, "rw");
        flightsFile.flightRand = new RandomAccessFile(flightsFile.flight, "rw");
        usersFile.userRand = new RandomAccessFile(usersFile.user, "rw");

        String flightId = new String(readCharsFromFile(ticketRand, ticketIndex + (2 * FIX_SIZE), 5));
        int flightIndex = flightsFile.findFlight(flightId);
        int seat = flightsFile.StringToInt(flightsFile.flightRand, flightIndex + (6 * FIX_SIZE));
        flightsFile.updateSeat("cancel", flightIndex, seat);

        String username = new String(readCharsFromFile(ticketRand, ticketIndex + FIX_SIZE, FIX_SIZE));
        int userIndex = usersFile.findUser(username.trim());

        int price = flightsFile.StringToInt(flightsFile.flightRand, flightIndex + (5 * FIX_SIZE));
        int balance = usersFile.StringToInt(usersFile.userRand, userIndex + (2 * FIX_SIZE));
        int backToAccount = balance + price;

        usersFile.updateBalance(userIndex, backToAccount);

        removeTicket(ticketIndex);

        System.out.println();
        System.out.println("> Ticket cancelled successfully");
    }

    public void removeTicket(int index) throws IOException {
        ticketRand = new RandomAccessFile(ticket, "rw");

        int j = index;
        for (int i = index + ((3 * FIX_SIZE) + 1); i < ticketRand.length(); i += ((3 * FIX_SIZE) + 1)) {
            ticketRand.seek(i);
            String temp = ticketRand.readLine();

            ticketRand.seek(j);
            ticketRand.writeBytes(temp);

            j += ((3 * FIX_SIZE) + 1);
        }

        ticketRand.setLength(ticketRand.length() - ((3 * FIX_SIZE) + 1));
    }

    // flight fully booked
}
