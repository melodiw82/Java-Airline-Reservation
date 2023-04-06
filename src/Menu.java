import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);
    public static ArrayList<String> username = new ArrayList<>();
    public static ArrayList<String> password = new ArrayList<>();

    public static void main(String[] args) {
        mainMenu();
        System.out.println("> Enter your command");
        int command = sc.nextInt();
        switch (command) {
            case 1:
                System.out.println("> Enter your username: ");
                String user = sc.next();

                System.out.println("> Enter your password: ");
                String pass = sc.next();

                if (user.equals("Admin") && pass.equals("Admin"))
                {
                    clearScreen();
                    adminMenu();
                } else if (username.contains(user) && password.contains(pass)) {
                    clearScreen();
                    passengerMenu();
                } else {
                    System.out.println("> Invalid username or password");
                }
                break;
            case 2:
                Signup signup = new Signup();
        }

    }

    public static void mainMenu() {
        System.out.println("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                           WELCOME TO AIRLINE RESERVATION SYSTEM
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ..........................MENU OPTIONS........................

                    <1> Sign in
                    <2> Sign up\s""");
    }

    public static void adminMenu() {
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

    public static void passengerMenu() {
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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}