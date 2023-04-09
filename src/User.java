public class User {
    private String username, password;
    private int balance;

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

    public void setBalance(int balance) {
        this.balance = balance;
    }
}