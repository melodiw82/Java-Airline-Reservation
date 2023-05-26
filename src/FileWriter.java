import java.io.IOException;
import java.io.RandomAccessFile;

public class FileWriter {
    public final int FIX_SIZE = 15;

    public String fixStringToWrite(String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < FIX_SIZE) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    public String fixIntToWrite(int integer) {
        StringBuilder temp = new StringBuilder(Integer.toString(integer));
        while (temp.length() < FIX_SIZE) {
            temp.append(" ");
        }
        return temp.toString();
    }

    public byte[] readCharsFromFile(RandomAccessFile randFile, int seek, int chars) throws IOException {
        randFile.seek(seek);
        byte[] bytes = new byte[chars];
        randFile.read(bytes);

        return bytes;
    }

    public boolean equalString(RandomAccessFile randFile, String str, int seek) throws IOException {
        String temp = new String(readCharsFromFile(randFile, seek, FIX_SIZE));

        return temp.trim().equals(str);
    }

    public int StringToInt(RandomAccessFile randFile, int seek) throws IOException {
        randFile.seek(seek);

        String integer = new String(readCharsFromFile(randFile, seek, FIX_SIZE));
        return Integer.parseInt(integer.trim());
    }
}