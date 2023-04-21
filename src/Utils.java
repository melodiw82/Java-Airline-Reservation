import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
    public final String RESET = "\033[0m";
    public final String RED_BOLD = "\033[1;31m";
    public final String GREEN_BOLD = "\u001B[32m";
    public final String CYAN_BOLD = "\033[1;36m";
    public final String TEXT_ITALIC = "\033[3m";
    private final Scanner sc = new Scanner(System.in);

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public void pressEnterToContinue() {
        System.out.printf("%n%s%n", "Press Enter key to continue...");
        try {
            sc.nextLine();
            sc.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String inputDate() {
        System.out.println("> Enter the year: ");
        int year = inputNum();

        if (year <= 1400 || year >= 1403) {
            System.out.println("> Available years are 1401 & 1402");
            System.out.println("> Please enter again");
            year = inputNum();
        }

        System.out.println("> Enter the month: ");
        int month = inputNum();

        if (month <= 0 || month >= 13) {
            System.out.println("> Invalid month number");
            System.out.println("> Please enter again");
            month = inputNum();
        }

        System.out.println("> Enter the day: ");
        int day = inputNum();

        if (day <= 0 || day >= 32) {
            System.out.println("> Invalid day number");
            System.out.println("> Please enter again");
            day = inputNum();
        }

        return year + "-" + month + "-" + day;
    }

    public String inputTime() {
        System.out.println();
        System.out.println("> Enter the hour:");
        int hour = inputNum();
        if (hour <= 0 || hour >= 25) {
            System.out.println("> Invalid hour");
            System.out.println("> Please enter again");
            hour = inputNum();
        }
        System.out.println("> Enter the minute: ");
        int min = inputNum();
        if (min < 0 || min >= 61) {
            System.out.println("> Invalid minute");
            System.out.println("> Please enter again");
            min = inputNum();
        }

        if (countDigit(hour) == 1 && countDigit(min) == 2) {
            return "0" + hour + ":" + min;
        } else if (countDigit(hour) == 2 && countDigit(min) == 1) {
            return hour + ":" + "0" + min;
        } else if (countDigit(hour) == 1 && countDigit(min) == 1) {
            return "0" + hour + ":" + "0" + min;
        } else if (countDigit(hour) == 2 && countDigit(min) == 2) {
            return hour + ":" + min;
        } else {
            System.out.println("> Invalid input");
        }
        return null;
    }

    private int countDigit(int number) {
        int count = 0;
        String stringNum = Integer.toString(number);

        for (int i = 0; i < stringNum.length(); i++) {
            count++;
        }
        return count;
    }

    public int inputNum() {
        boolean isNotDone = true;
        int tempInt = 0;
        do {
            try {
                tempInt = sc.nextInt();
                isNotDone = false;
            } catch (InputMismatchException e) {
                System.out.println("> Wrong input try again: ");
                sc.nextLine();
            }
        } while (isNotDone);

        return tempInt;
    }
}