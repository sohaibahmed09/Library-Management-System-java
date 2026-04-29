import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class StudentIssuedBooksUI {

    public StudentIssuedBooksUI(String student) {

        JFrame frame = new JFrame("My Issued Books");
        frame.setSize(500,400);

        String[] col = {"Book ID","Student","Issue Date","Due Date"};
        DefaultTableModel model = new DefaultTableModel(col,0);

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        try {
            BufferedReader br =
                    new BufferedReader(new FileReader("issued.txt"));

            String line;

            while((line = br.readLine()) != null) {

                String[] data = line.split(",",4);

                if(data[1].equalsIgnoreCase(student))
                    model.addRow(data);
            }

            br.close();

        } catch(Exception e){}

        frame.add(pane);
        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
}
