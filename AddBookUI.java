import javax.swing.*;
import java.io.FileWriter;

public class AddBookUI {

    public AddBookUI() {
        JLabel genreLabel = new JLabel("Genre:");
String[] genres = {
    "Fantasy",
    "Science",
    "Horror",
    "Romance",
    "History",
    "Programming",   
    "Education"      
};
JComboBox<String> genreCombo = new JComboBox<>(genres);

        JFrame frame = new JFrame("Add Book");
        frame.setSize(300,250);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Book Name");
        nameLabel.setBounds(20,30,100,25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120,30,140,25);
        frame.add(nameField);

        JLabel authorLabel = new JLabel("Author");
        authorLabel.setBounds(20,70,100,25);
        frame.add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(120,70,140,25);
        frame.add(authorField);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(90,120,100,30);
        frame.add(addBtn);

        addBtn.addActionListener(e -> {
    try {
        int id = BookIdGenerator.getNextId();

        String name = nameField.getText();
        String author = authorField.getText();
        String genre = genreCombo.getSelectedItem().toString(); // ✅ NEW

        FileWriter fw = new FileWriter("books.txt", true);

        // ✅ UPDATED LINE (adds genre)
        fw.write(id + "|" + name + "|" + author + "|" + genre + "\n");

        fw.close();

        JOptionPane.showMessageDialog(frame,"Book Added");

    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
 frame.setLocationRelativeTo(null);
frame.setVisible(true);
        
        
    }
}