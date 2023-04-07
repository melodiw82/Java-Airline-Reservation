import java.util.Scanner;
import java.util.regex.*;

public class Signup {
    private static final String RESET = "\033[0m";
    private static final String RED_BOLD = "\033[1;31m";
    private static final String GREEN_BOLD = "\033[1;32m";
    private static final String CYAN_BOLD = "\033[1;36m";
    private static final String TEXT_ITALIC = "\033[3m";
    private static final Scanner sc = new Scanner(System.in);

    private final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";
    Pattern pattern = Pattern.compile(regex);

    public Signup() {
        System.out.println("\n" + CYAN_BOLD +
                "                                                                            \n" +
                "                                                                            \n" +
                "                                                                            \n" +
                "              ,--,                                               ,-.----.   \n" +
                "            ,--.'|                   ,---,                  ,--, \\    /  \\  \n" +
                "  .--.--.   |  |,     ,----._,.  ,-+-. /  |               ,'_ /| |   :    | \n" +
                " /  /    '  `--'_    /   /  ' / ,--.'|'   |          .--. |  | : |   | .\\ : \n" +
                "|  :  /`./  ,' ,'|  |   :     ||   |  ,\"' |        ,'_ /| :  . | .   : |: | \n" +
                "|  :  ;_    '  | |  |   | .\\  .|   | /  | |        |  ' | |  . . |   |  \\ : \n" +
                " \\  \\    `. |  | :  .   ; ';  ||   | |  | |        |  | ' |  | | |   : .  | \n" +
                "  `----.   \\'  : |__'   .   . ||   | |  |/         :  | : ;  ; | :     |`-' \n" +
                " /  /`--'  /|  | '.'|`---`-'| ||   | |--'          '  :  `--'   \\:   : :    \n" +
                "'--'.     / ;  :    ;.'__/\\_: ||   |/              :  ,      .-./|   | :    \n" +
                "  `--'---'  |  ,   / |   :    :'---'                `--`----'    `---'.|    \n" +
                "             ---`-'   \\   \\  /                                     `---`    \n" +
                "                       `--`-'                                               \n" + RESET);

        System.out.println();
        System.out.println(TEXT_ITALIC + RED_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::
                username and password must contain the followings:
                >> Capital letters
                >> Small letters
                >> numbers
                >> with the length of at least 4 digits
                ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + RESET);
        System.out.println("> Enter your username: ");
        setUsername(sc.nextLine());
        System.out.println("> Enter your password: ");
        setPassword(sc.nextLine());
    }

    private void setUsername(String newUsername) {
        Matcher matcher = pattern.matcher(newUsername);

        if (Menu.username.contains(newUsername)) {
            System.out.println(">" + RED_BOLD + "Duplicated username..." + RESET);
            System.out.println("> Enter another username: ");
            this.setUsername(sc.nextLine());
        } else if ((newUsername.length() >= 4) && matcher.matches()) {
            Menu.username.add(newUsername);
            System.out.printf("%s%n%n", GREEN_BOLD + "> Username added successfully" + RESET);
        } else {
            System.out.println(">" + RED_BOLD + "Invalid username..." + RESET);
            System.out.println("> Enter a valid username: ");
            this.setUsername(sc.nextLine());
        }
    }

    private void setPassword(String newPassword) {
        Matcher matcher = pattern.matcher(newPassword);

        if (Menu.password.contains(newPassword)) {
            System.out.println(">" + RED_BOLD + "Duplicated password..." + RESET);
            System.out.println("> Enter another password: ");
            this.setPassword(sc.next());
        } else if ((newPassword.length() >= 4) && matcher.matches()) {
            Menu.password.add(newPassword);
            System.out.printf("%s%n", GREEN_BOLD + "> Account created successfully" + RESET);
        } else {
            System.out.println(">" + RED_BOLD + "Invalid password..." + RESET);
            System.out.println("> Enter a valid password: ");
            this.setPassword(sc.nextLine());
        }
    }
}