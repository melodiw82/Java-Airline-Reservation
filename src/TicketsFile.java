import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class TicketsFile extends FileWriter{
    private final File ticket;

    public RandomAccessFile ticketRand;

    public TicketsFile() {
        ticket = new File("ticket.text");
        try {
            ticketRand = new RandomAccessFile(ticket, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
