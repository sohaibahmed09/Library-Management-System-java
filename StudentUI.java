import javax.swing.*;

public class StudentUI {

    public StudentUI(String studentName) {

        JFrame frame = new JFrame("Student Dashboard");
        frame.setSize(400,350);
        frame.setLayout(null);

        JLabel welcome = new JLabel("Welcome: " + studentName);
        welcome.setBounds(120,10,200,25);
        frame.add(welcome);

        JButton searchBtn = new JButton("Search Books");
        searchBtn.setBounds(110,50,160,30);
        frame.add(searchBtn);

        JButton availableBtn = new JButton("Available Books");
        availableBtn.setBounds(110,100,160,30);
        frame.add(availableBtn);

        JButton issuedBtn = new JButton("My Issued Books");
        issuedBtn.setBounds(110,150,160,30);
        frame.add(issuedBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(110,200,160,30);
        frame.add(logoutBtn);

        // actions
        searchBtn.addActionListener(e -> new SearchBookUI());

        availableBtn.addActionListener(e -> new AvailableBooksUI());

        issuedBtn.addActionListener(e ->
                new StudentIssuedBooksUI(studentName));

        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new LoginUI();
        });

        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
} 