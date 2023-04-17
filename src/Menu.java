import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final Flight flight = new Flight();
    private final AdminMenu adminMenu = new AdminMenu();
    private final PassengerMenu passengerMenu = new PassengerMenu();
    private final Utils utils = new Utils();

    public static String currentUsername;

    public void menuExecution() {
        utils.clearScreen();
        mainMenu();
        System.out.println();
        System.out.println("> Enter your command: ");
        int command = sc.nextInt();

        while (true) {
            switch (command) {
                case 1 -> {
                    utils.clearScreen();
                    signIn();
                }
                case 2 -> {
                    Signup signup = new Signup();
                }
                default -> throw new InputMismatchException("Unexpected value: " + command);
            }
            utils.clearScreen();
            mainMenu();
            command = sc.nextInt();
        }
    }

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
        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(currentUsername) && Database.users.get(i).getPassword().equals(pass)) {
                isValid = true;
                utils.clearScreen();
                passengerMenu.passengerMenuExe();
            }
        }
        if (!isValid) {
            System.out.println("> Invalid username or password");
            System.out.println("> If you don't hava an account, create one in sign up menu...");
            utils.pressEnterToContinue();
        }
    }

    private void mainMenu() {
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                           WELCOME TO AIRLINE RESERVATION SYSTEM
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ..........................MENU OPTIONS........................

                    <1> Sign in
                    <2> Sign up\s""" + utils.RESET);
    }
    private void signInMenu() {
        System.out.println(utils.CYAN_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::
                               SIGN IN
                ::::::::::::::::::::::::::::::::::::::::
                  """ + utils.RESET);
    }
}