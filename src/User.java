public class User {
    // Fields
    private String username, password;
    private long balance;
    private Utils utils = new Utils();

    // constructor for initialization
    public User() {
        username = "unknown username";
        password = "no password";
        balance = 0;
    }

    // constructor
    public User(String username, String password, int balance) {
        setUsername(username);
        setPassword(password);
        setBalance(balance);
    }

    // adds user to users arraylist
    public void addUser(String username, String password, int balance) {
        Database.users.add(new User(username, password, balance));
    }

    // finds the users[index] from the users arraylist
    public int findUser(String username) {
        for (int i = 0; i < Database.users.size(); i++) {
            if (Database.users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    // adds charge to the user's account, doesn't let the user charge the account negatively
    public void addBalance(long balance, int index) {
        if (balance >= 0) {
            Database.users.get(index).setBalance(balance);
            System.out.println();
            System.out.println(utils.GREEN_BOLD + "> Balance updated" + utils.RESET);
        } else {
            System.out.println();
            System.out.println(utils.RED_BOLD + "> Enter a valid amount " + utils.RESET);
            addBalance(utils.inputLong(), index);
        }
    }

    // setters and getters
    public void setBalance(long balance) {
        this.balance += balance;
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
}