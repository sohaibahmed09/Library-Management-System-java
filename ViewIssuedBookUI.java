import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ViewIssuedBookUI {

    public ViewIssuedBookUI(String role, String userName) {

        JFrame frame = new JFrame("Issued Books");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ===== TABLE =====
        String[] columns = {"Book ID", "Student", "Issue Date", "Due Date", "Fine"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);

        File file = new File("issued.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "issued.txt not found!");
            frame.setVisible(true);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length < 4) continue;

                String bookId = data[0];
                String student = data[1];
                String issueDate = data[2];
                String dueDateStr = data[3];

                // ===== ROLE FILTER =====
                if (!role.equalsIgnoreCase("ADMIN")) {
                    if (!student.equalsIgnoreCase(userName)) continue;
                }

                // ===== FINE CALCULATION =====
                String fine = "0";

                try {
                    LocalDate dueDate = LocalDate.parse(dueDateStr);
                    LocalDate today = LocalDate.now();

                    long daysLate = ChronoUnit.DAYS.between(dueDate, today);

                    if (daysLate > 0) {
                        fine = String.valueOf(daysLate * 5);
                    }

                } catch (Exception e) {
                    // ignore invalid date
                }

                model.addRow(new Object[]{
                        bookId,
                        student,
                        issueDate,
                        dueDateStr,
                        fine
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error reading issued books!");
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}