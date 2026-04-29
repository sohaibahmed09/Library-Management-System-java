import java.io.*;

public class BookIdGenerator {

    public static int getNextId() {
        int id = 1000;

        try {
            File file = new File("book_id.txt");

            if (!file.exists()) {
                FileWriter fw = new FileWriter(file);
                fw.write("1000");
                fw.close();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            br.close();

            id = Integer.parseInt(line);
            id++;

            FileWriter fw = new FileWriter(file);
            fw.write(String.valueOf(id));
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();   // IMPORTANT (show real error)
        }

        return id;
    }
}