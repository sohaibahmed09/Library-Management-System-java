import java.io.*;
import java.util.*;

public class loginService {

    public static User login(String username, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(username) && data[1].equals(password)) {
                    return new User(data[0], data[1], data[2]);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading users file");
        }

        return null;
    }
}