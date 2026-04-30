import javax.swing.*;

public class StudentDashboardUI {

    JFrame frame;
    String studentName;

    public StudentDashboardUI(String studentName) {

        this.studentName = studentName;

        frame = new JFrame("Student Dashboard");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== WELCOME LABEL =====
        JLabel label = new JLabel("Welcome, " + studentName);
        label.setBounds(30, 30, 300, 30);
        frame.add(label);

        // ===== BUTTONS =====
        JButton viewBooksBtn = new JButton("View Books");
        JButton myIssuedBtn = new JButton("My Issued Books");

        viewBooksBtn.setBounds(100, 90, 180, 30);
        myIssuedBtn.setBounds(100, 140, 180, 30);

        frame.add(viewBooksBtn);
        frame.add(myIssuedBtn);

        // ===== ACTIONS =====

        // View all books
        viewBooksBtn.addActionListener(e -> new ViewBooksUI());

        // ✅ FIXED CALL (matches your ViewIssuedBookUI(String, String))
        myIssuedBtn.addActionListener(e ->
                new ViewIssuedBookUI("STUDENT", studentName)
        );

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}