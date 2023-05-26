import java.io.IOException;
import java.io.RandomAccessFile;

public class FileWriter {
    // fix size for writing in the file
    public final int FIX_SIZE = 15;

    /**
     * this method takes a string and extends it to fix size using String builder class
     * @param str input string
     * @return a fixed size string
     **/
    public String fixStringToWrite(String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < FIX_SIZE) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    /**
     * this method turns an integer into string and extends it to the fixed size to be used in file writing
     * @param integer input int
     * @return a fixed sized string
     */
    public String fixIntToWrite(int integer) {
        StringBuilder temp = new StringBuilder(Integer.toString(integer));
        while (temp.length() < FIX_SIZE) {
            temp.append(" ");
        }
        return temp.toString();
    }

    /**
     * this method reads said number of characters from anywhere in the file
     * @param randFile random access file which you want to read from
     * @param seek position of the file pointer
     * @param chars how many characters you want to read from the file
     * @return an array of bytes
     */
    public byte[] readCharsFromFile(RandomAccessFile randFile, int seek, int chars) throws IOException {
        randFile.seek(seek);
        byte[] bytes = new byte[chars];
        randFile.read(bytes);

        return bytes;
    }

    /**
     * his method checks if two strings (on will be read from the file) are equal
     * @param randFile given random access file
     * @param str the string you want to check
     * @param seek position you want ot read from
     * @return if the String read from the file and given String are equal, it will return true; otherwise it would return false
     */
    public boolean equalString(RandomAccessFile randFile, String str, int seek) throws IOException {
        String temp = new String(readCharsFromFile(randFile, seek, FIX_SIZE));

        return temp.trim().equals(str);
    }

    /**
     * this method parses the string which will be read from file to an integer
     * @param randFile given random access file
     * @param seek position of file pointer
     * @return integer value of string
     */
    public int StringToInt(RandomAccessFile randFile, int seek) throws IOException {
        randFile.seek(seek);

        String integer = new String(readCharsFromFile(randFile, seek, FIX_SIZE));
        return Integer.parseInt(integer.trim());
    }
}