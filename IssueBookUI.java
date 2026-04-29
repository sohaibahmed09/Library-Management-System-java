import javax.swing.*;
import java.io.*;

public class IssueBookUI {

    public IssueBookUI() {

        JFrame frame = new JFrame("Issue Book");
        frame.setSize(300,250);
        frame.setLayout(null);

        JLabel idLabel = new JLabel("Book ID");
        idLabel.setBounds(20,30,100,25);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(120,30,140,25);
        frame.add(idField);

        JLabel studentLabel = new JLabel("Student");
        studentLabel.setBounds(20,70,100,25);
        frame.add(studentLabel);

        JTextField studentField = new JTextField();
        studentField.setBounds(120,70,140,25);
        frame.add(studentField);

        JButton issueBtn = new JButton("Issue");
        issueBtn.setBounds(90,120,100,30);
        frame.add(issueBtn);

        issueBtn.addActionListener(e -> {

            try {
                int bookId = Integer.parseInt(idField.getText());
                String student = studentField.getText();

                // check already issued
                File file = new File("issued.txt");
                if(file.exists()) {
                    BufferedReader br =
                        new BufferedReader(new FileReader(file));

                    String line;

                    while((line = br.readLine()) != null) {
                        String[] parts = line.split(",",4);

                        if(Integer.parseInt(parts[0]) == bookId) {
                            JOptionPane.showMessageDialog(frame,
                                    "Book already issued");
                            br.close();
                            return;
                        }
                    }

                    br.close();
                }

                java.time.LocalDate issueDate =
                        java.time.LocalDate.now();

                java.time.LocalDate dueDate =
                        issueDate.plusDays(7);

                FileWriter fw =
                        new FileWriter("issued.txt", true);

                fw.write(bookId + "," +
                        student + "," +
                        issueDate + "," +
                        dueDate + "\n");

                fw.close();

                JOptionPane.showMessageDialog(frame,
                        "Book Issued\nDue Date: " + dueDate);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,"Error");
            }
        });

        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
}