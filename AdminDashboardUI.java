import javax.swing.*;
import java.io.*;

public class AdminDashboardUI {

    public AdminDashboardUI() {

        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(350,300);
        frame.setLayout(null);

        JLabel totalBooksLabel = new JLabel("Total Books: ");
        totalBooksLabel.setBounds(30,30,250,25);
        frame.add(totalBooksLabel);

        JLabel issuedBooksLabel = new JLabel("Issued Books: ");
        issuedBooksLabel.setBounds(30,70,250,25);
        frame.add(issuedBooksLabel);

        JLabel availableBooksLabel = new JLabel("Available Books: ");
        availableBooksLabel.setBounds(30,110,250,25);
        frame.add(availableBooksLabel);

        JLabel fineLabel = new JLabel("Total Fine: ");
        fineLabel.setBounds(30,150,250,25);
        frame.add(fineLabel);

        try {
            int totalBooks = 0;
            int issuedBooks = 0;
            long totalFine = 0;

            // count total books
            BufferedReader br1 =
                    new BufferedReader(new FileReader("books.txt"));

            while (br1.readLine() != null)
                totalBooks++;

            br1.close();

            // issued books + fine
            File file = new File("issued.txt");

            if(file.exists()) {

                BufferedReader br2 =
                        new BufferedReader(new FileReader(file));

                String line;

                while((line = br2.readLine()) != null) {

                    issuedBooks++;

                    String[] parts = line.split(",",4);

                    java.time.LocalDate dueDate =
                            java.time.LocalDate.parse(parts[3]);

                    java.time.LocalDate today =
                            java.time.LocalDate.now();

                    long daysLate =
                            java.time.temporal.ChronoUnit.DAYS
                            .between(dueDate, today);

                    if(daysLate > 0)
                        totalFine += daysLate * 5;
                }

                br2.close();
            }

            int availableBooks = totalBooks - issuedBooks;

            totalBooksLabel.setText("Total Books: " + totalBooks);
            issuedBooksLabel.setText("Issued Books: " + issuedBooks);
            availableBooksLabel.setText("Available Books: " + availableBooks);
            fineLabel.setText("Total Fine: Rs " + totalFine);

        } catch(Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
} 
