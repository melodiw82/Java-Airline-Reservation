import java.util.ArrayList;
import java.util.Scanner;

public class Signup {
    public static final String RESET = "\033[0m";
    public static final String RED_BOLD = "\033[1;31m";
    private static final Scanner sc = new Scanner(System.in);

    private static final ArrayList<String> username = new ArrayList<>();
    private static final ArrayList<String> password = new ArrayList<>();

    public Signup() {
        System.out.printf("%n%s%n", ">> Username must contain" + RED_BOLD + " capital " + RESET + "letters," + RED_BOLD + " small " + RESET + "letters and" + RED_BOLD + " numbers " + RESET + "with the length of at least" + RED_BOLD + " 4 digits" + RESET + ": ");
        System.out.println("> Enter your username: ");
        setUsername(sc.nextLine());
        System.out.println("> Enter your password: ");
        setPassword(sc.nextLine());
    }

    public void setUsername(String newUsername) {
        if (username.contains(newUsername)) {
            System.out.println("> Duplicated username...");
            System.out.println("> Enter a valid username: ");
            this.setUsername(sc.nextLine());
        } else if ((newUsername.length() > 4) && (newUsername.matches("[a-z]")) && (newUsername.matches("[A-Z]") && newUsername.matches("[0-9]"))) {
            username.add(newUsername);
        }
    }

    public void setPassword(String newPassword) {
        if (password.contains(newPassword)) {
            System.out.println("> Duplicated password...");
            System.out.println("> Enter a valid password: ");
            this.setPassword(sc.next());
        } else {
            password.add(newPassword);
        }
    }
}