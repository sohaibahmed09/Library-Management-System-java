import javax.swing.*;
import java.io.*;
import java.time.LocalDate;

public class IssueBookUI {

    JFrame frame;
    JComboBox<String> genreFilter;
    JComboBox<String> bookDropdown;
    JTextField userField;

    public IssueBookUI() {

        frame = new JFrame("Issue Book");
        frame.setSize(450,300);
        frame.setLayout(null);

        // ✅ Genre Dropdown
        String[] genres = {
                "All", "Fantasy", "Science", "Horror",
                "Romance", "History", "Programming", "Education"
        };

        genreFilter = new JComboBox<>(genres);
        genreFilter.setBounds(20, 20, 150, 25);
        frame.add(genreFilter);

        // ✅ Book Dropdown
        bookDropdown = new JComboBox<>();
        bookDropdown.setBounds(200, 20, 200, 25);
        frame.add(bookDropdown);

        // ✅ User Name
        JLabel userLabel = new JLabel("User Name:");
        userLabel.setBounds(20, 70, 100, 25);
        frame.add(userLabel);

        userField = new JTextField();
        userField.setBounds(120, 70, 200, 25);
        frame.add(userField);

        // ✅ Issue Button
        JButton issueBtn = new JButton("Issue Book");
        issueBtn.setBounds(120, 120, 150, 30);
        frame.add(issueBtn);

        // ✅ Initial Load
        loadBooksByGenre("All");

        // ✅ Filter by Genre
        genreFilter.addActionListener(e -> {
            String selectedGenre = genreFilter.getSelectedItem().toString();
            loadBooksByGenre(selectedGenre);
        });

        // ✅ Issue Button Logic
        issueBtn.addActionListener(e -> {
            try {
                String selectedBook = (String) bookDropdown.getSelectedItem();
                String user = userField.getText();

                if(selectedBook == null || user.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter all details");
                    return;
                }

                // 🔥 Prevent duplicate issue
                if(isBookIssued(selectedBook)) {
                    JOptionPane.showMessageDialog(frame, "Book already issued!");
                    return;
                }

                String date = LocalDate.now().toString(); // ✅ Issue Date

                FileWriter fw = new FileWriter("issued.txt", true);
                fw.write(selectedBook + "|" + user + "|" + date + "\n");
                fw.close();

                JOptionPane.showMessageDialog(frame, "Book Issued Successfully");

                // Refresh dropdown
                loadBooksByGenre(genreFilter.getSelectedItem().toString());

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ✅ Load Books Based on Genre (AND remove issued books)
    private void loadBooksByGenre(String selectedGenre) {
        try {
            bookDropdown.removeAllItems();

            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;

            while((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if(data.length < 4) continue;

                String bookName = data[1];
                String genre = data[3];

                if((selectedGenre.equals("All") || genre.equalsIgnoreCase(selectedGenre))
                        && !isBookIssued(bookName)) {

                    bookDropdown.addItem(bookName);
                }
            }

            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Check if Book Already Issued
    private boolean isBookIssued(String bookName) {
        try {
            File file = new File("issued.txt");

            if(!file.exists()) return false;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if(data.length >= 1) {
                    String issuedBook = data[0];

                    if(issuedBook.equalsIgnoreCase(bookName)) {
                        br.close();
                        return true;
                    }
                }
            }

            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}