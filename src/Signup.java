import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup {
    private final Utils utils = new Utils();
    private final Conn conn = new Conn();

    // regex to keep track of username and password requirements
    private final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";
    // compiling the regex
    Pattern pattern = Pattern.compile(regex);

    // constructor that opens the signup menu whenever an instance of Signup class is created
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

    // checks if the username and password entered by the user have all requirements, is not duplicated and is valid
    private void check(String newUsername, String newPassword) {
        boolean isMade = false;

        Matcher matcher = pattern.matcher(newUsername);
        Matcher matcher2 = pattern.matcher(newPassword);

        boolean isWrong = false;

        if (((newUsername.length() >= 4) && matcher.matches()) && ((newPassword.length() >= 4) && matcher2.matches())) {
            String query = "INSERT INTO users VALUES('" + newUsername + "', '" + newPassword + "', 0)";
            try {
                conn.statement.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println();
            System.out.printf("%s%n%n", utils.GREEN_BOLD + "> Account created successfully" + utils.RESET);
        } else {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Username or password doesn't match the requirements" + utils.RESET);
            isWrong = true;
        }

        if (isWrong) {
            System.out.println();
            System.out.println(utils.GREY_BOLD + "> Please try again..." + utils.RESET);
        }
        utils.pressEnterToContinue();
    }

    // prints the signup menu
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
}