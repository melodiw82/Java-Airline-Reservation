import java.util.Scanner;

public class User {
    private String username, password;
    private long balance;

    private final Scanner sc = new Scanner(System.in);
    private final Utils utils = new Utils();

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

    public long getBalance() {
        return balance;
    }

    public void addBalance(int balance, int index) {
        if (balance >= 0) {
            Database.users.get(index).setBalance(balance);
            System.out.println(utils.GREEN_BOLD + "> Charge added to account" + utils.RESET);
        } else {
            System.out.println("> Enter a valid amount ");
            addBalance(utils.inputNum(), index);
        }
    }

    public void setBalance(long balance) {
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