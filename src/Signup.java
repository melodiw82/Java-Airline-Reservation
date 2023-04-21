import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup {
    private static int countUser = 0;
    private final User user = new User();
    private final Utils utils = new Utils();

    private final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";
    Pattern pattern = Pattern.compile(regex);

    public Signup() {
        utils.clearScreen();
        signupMenu();

        System.out.println();
        System.out.println("> Enter your username: ");
        Scanner sc = new Scanner(System.in);
        String newUsername = sc.next();

        System.out.println("> Enter your password: ");
        String newPassword = sc.next();

        check(newUsername, newPassword);
    }

    private void signupMenu() {
        System.out.println("\n" + utils.CYAN_BOLD +
                """
                        ::::::::::::::::::::::::::::::::::::::::
                                       SIGN UP
                        ::::::::::::::::::::::::::::::::::::::::
                          """ + utils.RESET);

        System.out.println(utils.TEXT_ITALIC + utils.RED_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::
                username and password must contain the followings:
                >> Capital letters
                >> Small letters
                >> numbers
                >> with the length of at least 4 digits
                ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + utils.RESET);
    }

    private void check(String newUsername, String newPassword) {
        Matcher matcher = pattern.matcher(newUsername);
        Matcher matcher2 = pattern.matcher(newPassword);

        boolean isWrong = false;
        boolean isDuplicated = false;

        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(newUsername)) {
                System.out.println();
                System.out.println("> " + utils.RED_BOLD + "Duplicated username..." + utils.RESET);
                isWrong = true;
                isDuplicated = true;
                break;
            }
        }

        if (!isDuplicated) {
            if ((newUsername.length() >= 4) && matcher.matches()) {
                System.out.println();
                System.out.printf("%s%n%n", utils.GREEN_BOLD + "> Username added successfully" + utils.RESET);
            } else {
                System.out.println();
                System.out.println("> " + utils.RED_BOLD + "Invalid username..." + utils.RESET);
                isWrong = true;
            }
        }

        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getPassword().equals(newPassword)) {
                System.out.println();
                System.out.println("> " + utils.RED_BOLD + "Duplicated password..." + utils.RESET);
                isWrong = true;
                isDuplicated = true;
                break;
            }
        }
        if (!isDuplicated) {
            if ((newPassword.length() >= 4) && matcher2.matches()) {
                user.addUser(newUsername, newPassword, 0);
                countUser++;
                System.out.println();
                System.out.printf("%s%n", utils.GREEN_BOLD + "> Account created successfully" + utils.RESET);
            } else {
                System.out.println();
                System.out.println("> " + utils.RED_BOLD + "Invalid password..." + utils.RESET);
                isWrong = true;
            }
        }

        if (isWrong) {
            System.out.println();
            System.out.println("> Please try again...");
        }
        utils.pressEnterToContinue();
    }
}