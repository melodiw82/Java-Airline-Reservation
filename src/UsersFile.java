import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UsersFile extends FileWriter {
    public final File user;
    public RandomAccessFile userRand;

    /**
     * constructor to generate a random access file
     */
    public UsersFile() {
        user = new File("user.text");
        try {
            userRand = new RandomAccessFile(user, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes the user information in the file and moves to the next line
     *
     * @param username username of the user
     * @param password password of the user that matches the requirements
     * @param balance  default value of balance is zero when the account is created
     */
    public void writeUserInFile(String username, String password, int balance) throws IOException {
        userRand = new RandomAccessFile(user, "rw");

        if (user.exists()) {
            userRand.seek(userRand.length());
            try {
                userRand.writeBytes(fixStringToWrite(username));
                userRand.writeBytes(fixStringToWrite(password));
                userRand.writeBytes(fixIntToWrite(balance));
                userRand.writeBytes("\n");
                System.out.println("> Data Successfully Saved");
            } catch (IOException e) {
                System.out.println("> Error writing File...");
                e.printStackTrace();
            }
        }

        userRand.close();
    }

    /**
     * this method is used for signing up
     *
     * @param username username of the user
     * @param password password of the user
     * @return if the information matches the saved info in the file; method will return true, else it would return false
     */
    public boolean findUser(String username, String password) throws IOException {
        userRand = new RandomAccessFile(user, "r");

        for (int i = 0; i < userRand.length(); i += ((3 * FIX_SIZE) + 1)) {
            boolean user = equalString(userRand, username, i);
            boolean pass = equalString(userRand, password, i + FIX_SIZE);

            if (user && pass) {
                userRand.close();
                return true;
            }
        }
        userRand.close();
        return false;
    }

    /**
     * finds the user index using username
     *
     * @param username username of the user
     * @return index of the line in which given user is, if user doesn't exist it will return -1
     */
    public int findUser(String username) throws IOException {
        userRand = new RandomAccessFile(user, "r");

        for (int i = 0; i < userRand.length(); i += ((3 * FIX_SIZE) + 1)) {
            userRand.seek(i);

            String user = new String(readCharsFromFile(userRand, i, FIX_SIZE));

            if (user.trim().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * used in the change password field, acts as set password in default version
     *
     * @param index    user index
     * @param password new password of the user
     */
    public void updatePassword(int index, String password) throws IOException {
        userRand = new RandomAccessFile(user, "rw");

        userRand.seek(index + FIX_SIZE);
        userRand.writeBytes(fixStringToWrite(password));

        userRand.close();
    }

    /**
     * changes balance of user, acts as setBalance in default version
     *
     * @param index   user index
     * @param balance new balance
     */
    public void updateBalance(int index, int balance) throws IOException {
        userRand = new RandomAccessFile(user, "rw");

        userRand.seek(index + (2 * FIX_SIZE));
        userRand.writeBytes(fixIntToWrite(balance));

        userRand.close();
    }

    public boolean duplicateUser(String username) throws IOException {
        userRand = new RandomAccessFile(user, "r");

        for (int i = 0; i < userRand.length(); i += ((3 * FIX_SIZE) + 1)) {
            userRand.seek(i);

            String user = new String(readCharsFromFile(userRand, i, FIX_SIZE));

            if (user.trim().equals(username)) {
                return true;
            }
        }

        return false;
    }
}