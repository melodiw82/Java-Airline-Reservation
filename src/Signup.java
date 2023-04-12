import java.util.Scanner;
import java.util.regex.*;

public class Signup {
    public static final String RESET = "\033[0m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\u001B[32m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String TEXT_ITALIC = "\033[3m";
    private static final Scanner sc = new Scanner(System.in);
    private static int countUser = 0;
    private static final User user = new User();

    public static final String regex = "^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + ".+$";
    static Pattern pattern = Pattern.compile(regex);

    public Signup() {
        Menu.clearScreen();
        System.out.println("\n" + CYAN_BOLD +
                """
                        ::::::::::::::::::::::::::::::::::::::::
                                       SIGN UP
                        ::::::::::::::::::::::::::::::::::::::::
                          """ + RESET);

        System.out.println(TEXT_ITALIC + RED_BOLD + """
                ::::::::::::::::::::::::::::::::::::::::::::::::::::
                username and password must contain the followings:
                >> Capital letters
                >> Small letters
                >> numbers
                >> with the length of at least 4 digits
                ::::::::::::::::::::::::::::::::::::::::::::::::::::""" + RESET);
        System.out.println();
        System.out.println("> Enter your username: ");
        String newUsername = sc.next();

        System.out.println("> Enter your password: ");
        String newPassword = sc.next();

        check(newUsername, newPassword);
    }

    private void check(String newUsername, String newPassword) {
        Matcher matcher = pattern.matcher(newUsername);
        Matcher matcher2 = pattern.matcher(newPassword);

        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(newUsername)) {
                System.out.println(">" + RED_BOLD + "Duplicated username..." + RESET);
                System.out.println("> Please try again");
                Menu.pressEnterToContinue();
                Signup signup = new Signup();
                break;
            }
        }

        if ((newUsername.length() >= 4) && matcher.matches()) {
            System.out.printf("%s%n%n", GREEN_BOLD + "> Username added successfully" + RESET);
        } else {
            System.out.println(">" + RED_BOLD + "Invalid username..." + RESET);
            System.out.println("> Please try again");
            Menu.pressEnterToContinue();
            Signup signup = new Signup();
        }

        boolean isDuplicate = false;
        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getPassword().equals(newPassword)) {
                System.out.println(">" + RED_BOLD + "Duplicated password..." + RESET);
                System.out.println("> Please try again");
                Menu.pressEnterToContinue();
                Signup signup = new Signup();
            }
        }
        if (!isDuplicate) {
            if ((newPassword.length() >= 4) && matcher2.matches()) {
                user.addUser(newUsername, newPassword, 0);
                countUser++;
                System.out.printf("%s%n", GREEN_BOLD + "> Account created successfully" + RESET);
            } else {
                System.out.println(">" + RED_BOLD + "Invalid password..." + RESET);
                System.out.println("> Please try again ");
                Menu.pressEnterToContinue();
                Signup signup = new Signup();
            }
        }
    }
}

//  public Student findStudent(int studentId) {
//        for (int i = 0; i < students.length; i++) {
//            if (students[i].getStudentId() == studentId) {
//                return students[i];
//            }
//        }
//        return null;
//    }
//use this in project