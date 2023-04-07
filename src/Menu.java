import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);
    public static ArrayList<String> username = new ArrayList<>();
    public static ArrayList<String> password = new ArrayList<>();
    private static final String CYAN_BOLD = "\033[1;36m";
    private static final String RESET = "\033[0m";

    public static void main(String[] args) {
        menuExecution();
    }

    public static void menuExecution() {
        clearScreen();
        mainMenu();
        System.out.println();
        System.out.println("> Enter your command: ");
        int command = sc.nextInt();

        while (true) {
            switch (command) {
                case 1 -> {
                    clearScreen();
                    signIn();
                }
                case 2 -> {
                    clearScreen();
                    Signup signup = new Signup();
                    pressEnterToContinue();
                    menuExecution();
                }
            }
        }
    }

    private static void signIn() {
        System.out.println(CYAN_BOLD + """

                                                                                               \s
                                                                                               \s
                                                                                               \s
                              ,--,                                   ,--,                      \s
                            ,--.'|                   ,---,         ,--.'|         ,---,        \s
                  .--.--.   |  |,     ,----._,.  ,-+-. /  |        |  |,      ,-+-. /  |       \s
                 /  /    '  `--'_    /   /  ' / ,--.'|'   |        `--'_     ,--.'|'   |       \s
                |  :  /`./  ,' ,'|  |   :     ||   |  ,"' |        ,' ,'|   |   |  ,"' |       \s
                |  :  ;_    '  | |  |   | .\\  .|   | /  | |        '  | |   |   | /  | |       \s
                 \\  \\    `. |  | :  .   ; ';  ||   | |  | |        |  | :   |   | |  | |       \s
                  `----.   \\'  : |__'   .   . ||   | |  |/         '  : |__ |   | |  |/        \s
                 /  /`--'  /|  | '.'|`---`-'| ||   | |--'          |  | '.'||   | |--'         \s
                '--'.     / ;  :    ;.'__/\\_: ||   |/              ;  :    ;|   |/             \s
                  `--'---'  |  ,   / |   :    :'---'               |  ,   / '---'              \s
                             ---`-'   \\   \\  /                      ---`-'                     \s
                                       `--`-'                                                  \s
                """ + RESET);
        System.out.println("> Enter your username: ");
        String user = sc.next();
        System.out.println("> Enter your password: ");
        String pass = sc.next();
        if (user.equals("Admin") && pass.equals("Admin")) {
            clearScreen();
            adminMenu();
        } else if (username.contains(user) && password.contains(pass)) {
            clearScreen();
            passengerMenu();
        } else {
            System.out.println("> Invalid username or password");
            System.out.println("> If you don't hava an account, create one in sign up menu...");
            pressEnterToContinue();
            menuExecution();
        }
    }

    private static void mainMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                           WELCOME TO AIRLINE RESERVATION SYSTEM
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ..........................MENU OPTIONS........................

                    <1> Sign in
                    <2> Sign up\s""");
    }

    private static void adminMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::
                           Admin MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                 ......................................
                    <1> Add
                    <2> Update
                    <3> Remove
                    <4> Flight schedules
                    <0> Sign out""");
    }

    private static void passengerMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::
                         PASSENGER MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                 ......................................
                    <1> Change password
                    <2> Search flight tickets
                    <3> Booking ticket
                    <4> Ticket cancellation
                    <5> Booked tickets
                    <6> Add charge
                    <0> Sign out""");
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    private static void pressEnterToContinue() {
        System.out.printf("%n%s%n", "Press Enter key to continue...");
        try {
            sc.nextLine();
            sc.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}