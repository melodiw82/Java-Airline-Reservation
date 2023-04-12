import java.util.Scanner;

public class User {
    private String username, password;
    private int balance;

    private static final Scanner sc = new Scanner(System.in);

    public User() {
        username = "unknown username";
        password = "no password";
        balance = 0;
    }

    public User(String username, String password, int balance) {
        setUsername(username);
        setPassword(password);
        setBalance(balance);
    }

    public void addUser(String username, String password, int balance) {
        Database.users.add(new User(username, password, balance));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int balance, int index) {
        if (balance >= 0) {
            Database.users.get(index).setBalance(balance);
            System.out.println(Signup.GREEN_BOLD + "> Charge added to account" + Signup.RESET);
        } else {
            System.out.println("> Enter a valid amount ");
            addBalance(sc.nextInt(), index);
        }
    }

    public void setBalance(int balance) {
        this.balance += balance;
    }

    public int findUser(String username) {
        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}