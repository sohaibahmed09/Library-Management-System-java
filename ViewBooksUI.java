import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class ViewBooksUI {

    public ViewBooksUI() {

        JFrame frame = new JFrame("View Books");
        frame.setSize(500,400);

        String[] column = {"ID","Name","Author"};
        DefaultTableModel model = new DefaultTableModel(column,0);

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        try {
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;

            while((line = br.readLine()) != null) {
                String[] data = line.split(",",3);
                model.addRow(data);
            }

            br.close();

        } catch(Exception e){}

        frame.add(pane);
       frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
}
