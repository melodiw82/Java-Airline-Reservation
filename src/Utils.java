import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * carries the necessary methods and static final strings used in almost all other classes
 */
public class Utils {
    public final String RESET = "\033[0m";
    public final String RED_BOLD = "\033[91m";
    public final String GREEN_BOLD = "\033[92m";
    public final String CYAN_BOLD = "\033[96m";
    public final String TEXT_ITALIC = "\033[3m";
    public final String GREY_BOLD = "\033[90m";
    public final String PINK_BOLD = "\033[95m";
    private final Scanner sc = new Scanner(System.in);
    private final Conn conn = new Conn();

    // clears the screen in cmd
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    // press enter key to continue with the rest of the program
    public void pressEnterToContinue() {
        System.out.printf("%n%s%n", "Press Enter key to continue...");
        try {
            sc.nextLine();
            sc.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // used for getting input for flight dates to avoid incorrect and false inputs
    public String inputDate() {
        System.out.println(PINK_BOLD + "> Enter the year: " + RESET);
        int year = inputNum();

        if (year <= 1400 || year >= 1403) {
            System.out.println("> Available years are 1401 & 1402");
            System.out.println();
            System.out.println(GREY_BOLD + "> Please enter again" + RESET);
            year = inputNum();
        }

        System.out.println(PINK_BOLD + "> Enter the month: " + RESET);
        int month = inputNum();

        if (month <= 0 || month >= 13) {
            System.out.println(RED_BOLD + "> Invalid month number" + RESET);
            System.out.println();
            System.out.println(GREY_BOLD + "> Please enter again" + RESET);
            month = inputNum();
        }

        System.out.println(PINK_BOLD + "> Enter the day: " + RESET);
        int day = inputNum();

        if (day <= 0 || day >= 32) {
            System.out.println(RED_BOLD + "> Invalid day number" + RESET);
            System.out.println();
            System.out.println(GREY_BOLD + "> Please enter again" + RESET);
            day = inputNum();
        }

        return year + "-" + month + "-" + day;
    }

    // used for getting input for flight time to avoid incorrect and false inputs
    public String inputTime() {
        System.out.println(PINK_BOLD + "> Enter the hour:" + RESET);
        int hour = inputNum();
        if (hour <= 0 || hour >= 25) {
            System.out.println(RED_BOLD + "> Invalid hour" + RESET);
            System.out.println();
            System.out.println(GREY_BOLD + "> Please enter again" + RESET);
            hour = inputNum();
        }
        System.out.println(PINK_BOLD + "> Enter the minute: " + RESET);
        int min = inputNum();
        if (min < 0 || min >= 60) {
            System.out.println(RED_BOLD + "> Invalid minute" + RESET);
            System.out.println(GREY_BOLD + "> Please enter again" + RESET);
            min = inputNum();
        }

        if (countDigit(hour) == 1 && countDigit(min) == 2) {
            return "0" + hour + ":" + min + ":" + "00";
        } else if (countDigit(hour) == 2 && countDigit(min) == 1) {
            return hour + ":" + "0" + min + ":" + "00";
        } else if (countDigit(hour) == 1 && countDigit(min) == 1) {
            return "0" + hour + ":" + "0" + min + ":" + "00";
        } else if (countDigit(hour) == 2 && countDigit(min) == 2) {
            return hour + ":" + min + ":" + "00";
        } else {
            System.out.println("> Invalid input");
        }
        return null;
    }

    // counts the digits of an integer used for inputting time correctly
    private int countDigit(int number) {
        int count = 0;
        String stringNum = Integer.toString(number);

        for (int i = 0; i < stringNum.length(); i++) {
            count++;
        }
        return count;
    }

    // used for getting integers' input to avoid InputMissMatch exception
    public int inputNum() {
        boolean isNotDone = true;
        int tempInt = 0;
        do {
            try {
                tempInt = sc.nextInt();
                isNotDone = false;
            } catch (InputMismatchException e) {
                System.out.println(GREY_BOLD + "> Wrong input try again: " + RESET);
                sc.nextLine();
            }
        } while (isNotDone);

        return tempInt;
    }

    // used for getting Longs' input to avoid InputMissMatch exception
    public long inputLong() {
        boolean isNotDone = true;
        long tempInt = 0;
        do {
            try {
                tempInt = sc.nextLong();
                isNotDone = false;
            } catch (InputMismatchException e) {
                System.out.println(GREY_BOLD + "> Wrong input try again: " + RESET);
                sc.nextLine();
            }
        } while (isNotDone);

        return tempInt;
    }

    public void schedulePrinter() {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "flight ID", "origin", "destination", "date", "time", "price", "seat");
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM flights ORDER BY date");
            while (rs.next()) {
                String flightId = rs.getNString("flight_id");
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                int price = rs.getInt("price");
                int seat = rs.getInt("seat");
                System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flightId, origin, destination, date, time, price, seat);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public long getBalance() throws SQLException {
        Conn conn = new Conn();
        String query = "SELECT balance FROM users WHERE username = ?";
        PreparedStatement statement = conn.connection.prepareStatement(query);
        statement.setString(1, Menu.currentUsername);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getLong("balance");
        }
        return 0;
    }
}