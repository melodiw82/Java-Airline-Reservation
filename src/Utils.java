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
}
