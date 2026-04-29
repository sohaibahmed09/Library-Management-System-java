import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class SearchBookUI {

    public SearchBookUI() {

        JFrame frame = new JFrame("Search Books");
        frame.setSize(550,400);
        frame.setLayout(null);

        JLabel label = new JLabel("Search:");
        label.setBounds(20,20,60,25);
        frame.add(label);

        JTextField searchField = new JTextField();
        searchField.setBounds(80,20,250,25);
        frame.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(350,20,100,25);
        frame.add(searchBtn);

        String[] columns = {"ID","Name","Author"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20,60,500,280);

        frame.add(pane);

        searchBtn.addActionListener(e -> {

            model.setRowCount(0); // clear table

            String keyword =
                    searchField.getText().toLowerCase();

            try {
                BufferedReader br =
                        new BufferedReader(
                                new FileReader("books.txt"));

                String line;

                while((line = br.readLine()) != null) {

                    String[] data = line.split(",",3);

                    String name = data[1].toLowerCase();
                    String author = data[2].toLowerCase();

                    if(name.contains(keyword)
                      || author.contains(keyword)) {

                        model.addRow(data);
                    }
                }

                br.close();

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
} 