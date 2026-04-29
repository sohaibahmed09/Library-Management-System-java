import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;

public class AvailableBooksUI {

    public AvailableBooksUI() {

        JFrame frame = new JFrame("Available Books");
        frame.setSize(500,400);

        String[] col = {"ID","Name","Author"};
        DefaultTableModel model = new DefaultTableModel(col,0);

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        try {

            Set<String> issued = new HashSet<>();

            File file = new File("issued.txt");
            if(file.exists()) {
                BufferedReader br =
                        new BufferedReader(new FileReader(file));

                String line;
                while((line = br.readLine()) != null) {
                    issued.add(line.split(",")[0]);
                }
                br.close();
            }

            BufferedReader br2 =
                    new BufferedReader(new FileReader("books.txt"));

            String line;

            while((line = br2.readLine()) != null) {

                String[] data = line.split(",",3);

                if(!issued.contains(data[0]))
                    model.addRow(data);
            }

            br2.close();

        } catch(Exception e){}

        frame.add(pane);
        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
} 