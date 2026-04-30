import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class ViewBooksUI {

    JFrame frame;
    JTable table;
    DefaultTableModel model;
    JComboBox<String> genreFilter;

    public ViewBooksUI() {

        frame = new JFrame("View Books");
        frame.setSize(500,400);
        frame.setLayout(null);

        // ✅ Genre Dropdown
        String[] genres = {
    "Fantasy",
    "Science",
    "Horror",
    "Romance",
    "History",
    "Programming",   
    "Education"      
};
        genreFilter = new JComboBox<>(genres);
        genreFilter.setBounds(20, 10, 150, 25);
        frame.add(genreFilter);

        // ✅ Table
        String[] column = {"ID","Name","Author","Genre"};
        model = new DefaultTableModel(column,0);

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 50, 440, 280);
        frame.add(pane);

        // ✅ Load all books initially
        loadBooks("All");

        // ✅ Dropdown filter
        genreFilter.addActionListener(e -> {
            String selectedGenre = genreFilter.getSelectedItem().toString();
            loadBooks(selectedGenre);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ✅ ONLY ONE method (outside constructor)
    private void loadBooks(String selectedGenre) {
        try {
            model.setRowCount(0);

            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;

            while((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if(data.length < 4) continue;

                String genre = data[3];

                if(selectedGenre.equals("All") || genre.equalsIgnoreCase(selectedGenre)) {
                    model.addRow(new Object[]{
                            data[0],
                            data[1],
                            data[2],
                            data[3]
                    });
                }
            }

            br.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}