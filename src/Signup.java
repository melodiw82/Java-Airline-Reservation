import java.util.Scanner;
import java.util.regex.*;

public class Signup {
    public static final String RESET = "\033[0m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    private static final Scanner sc = new Scanner(System.in);

    private final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";
    Pattern pattern = Pattern.compile(regex);

    public Signup() {
        System.out.printf("%n%s%n", ">> Username must contain" + RED_BOLD + " capital " + RESET + "letters," + RED_BOLD + " small " + RESET + "letters and" + RED_BOLD + " numbers " + RESET + "with the length of at least" + RED_BOLD + " 4 digits" + RESET + ": ");
        System.out.println("> Enter your username: ");
        setUsername(sc.nextLine());
        System.out.printf("%n%s%n", ">> Password must contain" + RED_BOLD + " capital " + RESET + "letters," + RED_BOLD + " small " + RESET + "letters and" + RED_BOLD + " numbers " + RESET + "with the length of at least" + RED_BOLD + " 4 digits" + RESET + ": ");
        System.out.println("> Enter your password: ");
        setPassword(sc.nextLine());
    }

    private void setUsername(String newUsername) {
        Matcher matcher = pattern.matcher(newUsername);

        if (Menu.username.contains(newUsername)) {
            System.out.println("> Duplicated username...");
            System.out.println("> Enter a valid username: ");
            this.setUsername(sc.nextLine());
        } else if ((newUsername.length() >= 4) && matcher.matches()) {
            Menu.username.add(newUsername);
            System.out.printf("%s%n%n", GREEN_BOLD + "> Username added successfully" + RESET);
        } else {
            System.out.println("> Invalid username...");
            System.out.println("> Enter a valid username: ");
            this.setUsername(sc.nextLine());
        }
    }

    private void setPassword(String newPassword) {
        Matcher matcher = pattern.matcher(newPassword);

        if (Menu.password.contains(newPassword)) {
            System.out.println("> Duplicated password...");
            System.out.println("> Enter a valid password: ");
            this.setPassword(sc.next());
        } else if ((newPassword.length() >= 4) && matcher.matches()) {
            Menu.password.add(newPassword);
            System.out.printf("%s%n%n", GREEN_BOLD + "> Account created successfully" + RESET);
        } else {
            System.out.println("> Invalid password...");
            System.out.println("> Enter a valid password: ");
            this.setPassword(sc.nextLine());
        }
    }
}