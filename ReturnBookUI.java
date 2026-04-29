import javax.swing.*;
import java.io.*;

public class ReturnBookUI {

    public ReturnBookUI() {

        JFrame frame = new JFrame("Return Book");
        frame.setSize(300,200);
        frame.setLayout(null);

        JLabel idLabel = new JLabel("Book ID");
        idLabel.setBounds(20,40,100,25);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(120,40,140,25);
        frame.add(idField);

        JButton returnBtn = new JButton("Return");
        returnBtn.setBounds(90,90,100,30);
        frame.add(returnBtn);

        returnBtn.addActionListener(e -> {

            try {
                int id = Integer.parseInt(idField.getText());

                File inputFile = new File("issued.txt");

                if (!inputFile.exists()) {
                    JOptionPane.showMessageDialog(frame,
                            "No issued books");
                    return;
                }

                File tempFile = new File("temp.txt");

                BufferedReader br =
                        new BufferedReader(new FileReader(inputFile));

                BufferedWriter bw =
                        new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean found = false;
                long fine = 0;

                while ((line = br.readLine()) != null) {

                    String[] parts = line.split(",", 4);
                    int bookId = Integer.parseInt(parts[0]);

                    if (bookId == id) {
                        found = true;

                        java.time.LocalDate dueDate =
                                java.time.LocalDate.parse(parts[3]);

                        java.time.LocalDate today =
                                java.time.LocalDate.now();

                        long daysLate =
                                java.time.temporal.ChronoUnit.DAYS
                                .between(dueDate, today);

                        if (daysLate > 0)
                            fine = daysLate * 5;

                    } else {
                        bw.write(line);
                        bw.newLine();
                    }
                }

                br.close();
                bw.close();

                inputFile.delete();
                tempFile.renameTo(inputFile);

                if (found) {
                    JOptionPane.showMessageDialog(frame,
                            "Book Returned\nFine: Rs " + fine);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Book not issued");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Error returning book");
            }
        });
frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
}