import javax.swing.*;
import java.io.FileWriter;

public class AddBookUI {

    public AddBookUI() {

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

                FileWriter fw = new FileWriter("books.txt", true);
                fw.write(id + "," +
                        nameField.getText() + "," +
                        authorField.getText() + "\n");
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