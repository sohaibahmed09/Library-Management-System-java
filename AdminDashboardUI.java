import javax.swing.*;
import java.io.*;

public class AdminDashboardUI {

    JFrame frame;

    public AdminDashboardUI() {

        frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 380);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ====== LABELS ======
        JLabel totalBooksLabel = new JLabel("Total Books: ");
        totalBooksLabel.setBounds(30, 30, 300, 25);
        frame.add(totalBooksLabel);

        JLabel issuedBooksLabel = new JLabel("Issued Books: ");
        issuedBooksLabel.setBounds(30, 60, 300, 25);
        frame.add(issuedBooksLabel);

        JLabel availableBooksLabel = new JLabel("Available Books: ");
        availableBooksLabel.setBounds(30, 90, 300, 25);
        frame.add(availableBooksLabel);

        JLabel fineLabel = new JLabel("Total Fine: ");
        fineLabel.setBounds(30, 120, 300, 25);
        frame.add(fineLabel);

        // ====== BUTTONS ======
        JButton addBtn = new JButton("Add Book");
        JButton viewBtn = new JButton("View Books");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");
        JButton issuedBtn = new JButton("Issued Books");

        addBtn.setBounds(30, 170, 150, 30);
        viewBtn.setBounds(200, 170, 150, 30);
        issueBtn.setBounds(30, 210, 150, 30);
        returnBtn.setBounds(200, 210, 150, 30);
        issuedBtn.setBounds(100, 250, 180, 30);

        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(issueBtn);
        frame.add(returnBtn);
        frame.add(issuedBtn);

        // ====== ACTIONS ======
        addBtn.addActionListener(e -> new AddBookUI());
        viewBtn.addActionListener(e -> new ViewBooksUI());
        issueBtn.addActionListener(e -> new IssueBookUI());
        returnBtn.addActionListener(e -> new ReturnBookUI());

        // ✅ FIXED LINE (MAIN ERROR SOLVED)
        issuedBtn.addActionListener(e ->
                new ViewIssuedBookUI("ADMIN", "ALL")
        );

        // ====== LOAD DASHBOARD DATA ======
        loadDashboardData(totalBooksLabel, issuedBooksLabel, availableBooksLabel, fineLabel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ====== LOAD DASHBOARD DATA ======
    private void loadDashboardData(JLabel totalBooksLabel,
                                   JLabel issuedBooksLabel,
                                   JLabel availableBooksLabel,
                                   JLabel fineLabel) {

        try {
            int totalBooks = 0;
            int issuedBooks = 0;
            long totalFine = 0;

            // ===== TOTAL BOOKS =====
            File booksFile = new File("books.txt");
            if (booksFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(booksFile));
                while (br.readLine() != null) {
                    totalBooks++;
                }
                br.close();
            }

            // ===== ISSUED BOOKS + FINE =====
            File issuedFile = new File("issued.txt");

            if (issuedFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(issuedFile));
                String line;

                while ((line = br.readLine()) != null) {

                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split("\\|");

                    if (parts.length < 4) continue;

                    issuedBooks++;

                    try {
                        java.time.LocalDate dueDate =
                                java.time.LocalDate.parse(parts[3]);

                        java.time.LocalDate today =
                                java.time.LocalDate.now();

                        long daysLate =
                                java.time.temporal.ChronoUnit.DAYS
                                        .between(dueDate, today);

                        if (daysLate > 0) {
                            totalFine += daysLate * 5;
                        }

                    } catch (Exception ex) {
                        // ignore invalid date
                    }
                }

                br.close();
            }

            int availableBooks = totalBooks - issuedBooks;

            // ===== UPDATE UI =====
            totalBooksLabel.setText("Total Books: " + totalBooks);
            issuedBooksLabel.setText("Issued Books: " + issuedBooks);
            availableBooksLabel.setText("Available Books: " + availableBooks);
            fineLabel.setText("Total Fine: Rs " + totalFine);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading dashboard");
        }
    }
}