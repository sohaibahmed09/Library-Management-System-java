import javax.swing.*;
import java.io.*;
import java.util.*;

public class ReturnBookUI {

    JFrame frame;
    JComboBox<String> issuedBooks;

    public ReturnBookUI() {

        frame = new JFrame("Return Book");
        frame.setSize(400,200);
        frame.setLayout(null);

        issuedBooks = new JComboBox<>();
        issuedBooks.setBounds(50, 40, 250, 25);
        frame.add(issuedBooks);

        JButton returnBtn = new JButton("Return Book");
        returnBtn.setBounds(100, 90, 150, 30);
        frame.add(returnBtn);

        loadIssuedBooks();

        returnBtn.addActionListener(e -> returnBook());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadIssuedBooks() {
        try {
            issuedBooks.removeAllItems();

            BufferedReader br = new BufferedReader(new FileReader("issued.txt"));
            String line;

            while((line = br.readLine()) != null) {
                issuedBooks.addItem(line); // full line
            }

            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
        try {
            String selected = (String) issuedBooks.getSelectedItem();

            if(selected == null) return;

            File inputFile = new File("issued.txt");
            File tempFile = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while((line = br.readLine()) != null) {
                if(!line.equals(selected)) {
                    bw.write(line);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            JOptionPane.showMessageDialog(frame, "Book Returned");

            loadIssuedBooks(); // refresh

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}