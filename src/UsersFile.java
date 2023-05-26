import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UsersFile extends FileWriter {
    private final File user;
    public RandomAccessFile userRand;

    public UsersFile() {
        user = new File("user.text");
        try {
            userRand = new RandomAccessFile(user, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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

    public boolean findUser(String username, String password) throws IOException {
        userRand = new RandomAccessFile(user, "r");

        for (int i = 0; i < userRand.length(); i += 46) {
            boolean user = equalString(userRand, username, i);
            boolean pass = equalString(userRand, password, i + 15);

            if (user && pass) {
                return true;
            }
        }
        return false;
    }
}
