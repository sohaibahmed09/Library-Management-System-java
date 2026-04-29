import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class FineReportUI {

    public FineReportUI() {

        JFrame frame = new JFrame("Fine Report");
        frame.setSize(600,400);

        String[] columns = {
                "Book ID","Student",
                "Due Date","Late Days","Fine"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns,0);

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        try {
            File file = new File("issued.txt");

            if(file.exists()) {

                BufferedReader br =
                        new BufferedReader(new FileReader(file));

                String line;

                while((line = br.readLine()) != null) {

                    String[] parts = line.split(",",4);

                    int bookId = Integer.parseInt(parts[0]);
                    String student = parts[1];

                    java.time.LocalDate dueDate =
                            java.time.LocalDate.parse(parts[3]);

                    java.time.LocalDate today =
                            java.time.LocalDate.now();

                    long daysLate =
                            java.time.temporal.ChronoUnit.DAYS
                            .between(dueDate, today);

                    if(daysLate > 0) {

                        long fine = daysLate * 5;

                        model.addRow(new Object[]{
                                bookId,
                                student,
                                dueDate,
                                daysLate,
                                fine
                        });
                    }
                }

                br.close();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        frame.add(pane);
        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
}
