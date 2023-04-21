import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private AdminMenu adminMenu = new AdminMenu();
    private PassengerMenu passengerMenu = new PassengerMenu();
    private Utils utils = new Utils();

    public static String currentUsername;

    public void menuExecution() {
        utils.clearScreen();
        mainMenu();
        System.out.println();
        System.out.println("> Enter your command: ");
        int command = utils.inputNum();

        while (true) {
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
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Invalid username or password" + utils.RESET);
            System.out.println(utils.GREY_BOLD + "> If you don't hava an account, create one in sign up menu..." + utils.RESET);
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