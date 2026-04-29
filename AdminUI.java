import javax.swing.*;
import java.awt.event.*;

public class AdminUI {

    public AdminUI() {

        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(400,400);
        frame.setLayout(null);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBounds(120,30,150,30);
        frame.add(addBtn);

        JButton viewBtn = new JButton("View Books");
        viewBtn.setBounds(120,70,150,30);
        frame.add(viewBtn);

        JButton issueBtn = new JButton("Issue Book");
        issueBtn.setBounds(120,110,150,30);
        frame.add(issueBtn);

        JButton returnBtn = new JButton("Return Book");
        returnBtn.setBounds(120,150,150,30);
        frame.add(returnBtn);

        JButton fineBtn = new JButton("Fine Report");
        fineBtn.setBounds(120,190,150,30);
        frame.add(fineBtn);

        // actions
        addBtn.addActionListener(e -> new AddBookUI());
        viewBtn.addActionListener(e -> new ViewBooksUI());
        issueBtn.addActionListener(e -> new IssueBookUI());
        returnBtn.addActionListener(e -> new ReturnBookUI());
        fineBtn.addActionListener(e -> new FineReportUI());
        JButton dashboardBtn = new JButton("Dashboard");
dashboardBtn.setBounds(120,230,150,30);
frame.add(dashboardBtn);

dashboardBtn.addActionListener(e -> new AdminDashboardUI());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
