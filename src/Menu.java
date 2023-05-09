import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final AdminMenu adminMenu = new AdminMenu();
    private final PassengerMenu passengerMenu = new PassengerMenu();
    private final Utils utils = new Utils();
    private final Conn conn = new Conn();

    // keeps track of the user's username when signing in
    public static String currentUsername;

    // executes the menu
    public void menuExecution() {
        utils.clearScreen();
        mainMenu();
        System.out.println();
        System.out.println("> Enter your command: ");
        int command = utils.inputNum();

        while (command != 3) {
            switch (command) {
                case 1:
                    utils.clearScreen();
                    signIn();
                    break;
                case 2:
                    Signup signup = new Signup();
                    break;
                default:
                    System.out.println("> Invalid input");
                    System.out.println("> Please try again");
            }
            utils.clearScreen();
            mainMenu();
            System.out.println();
            System.out.println("> Enter your command:");
            command = utils.inputNum();
        }
    }

    // signs in the admin and user
    private void signIn() {
        signInMenu();
        System.out.println("> Enter your username: ");
        currentUsername = sc.next();
        System.out.println("> Enter your password: ");
        String pass = sc.next();

        boolean isValid = false;
        if (currentUsername.equals("Admin") && pass.equals("Admin")) {
            isValid = true;
            utils.clearScreen();
            adminMenu.adminMenuExe();
        }

        try {
            PreparedStatement statement = conn.connection.prepareStatement("SELECT username, password from users where username = ? AND  password = ?");
            statement.setString(1, currentUsername);
            statement.setString(2, pass);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                isValid = true;
                utils.clearScreen();
                passengerMenu.passengerMenuExe();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!isValid) {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Invalid username or password" + utils.RESET);
            System.out.println(utils.GREY_BOLD + "> If you don't hava an account, create one in sign up menu..." + utils.RESET);
            utils.pressEnterToContinue();
        }
    }

    // prints menus
    private void mainMenu() {
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                           WELCOME TO AIRLINE RESERVATION SYSTEM
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ..........................MENU OPTIONS........................

                    <1> Sign in
                    <2> Sign up
                    <3> Exit\s""" + utils.RESET);
    }

    private void signInMenu() {
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                               SIGN IN
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }
}