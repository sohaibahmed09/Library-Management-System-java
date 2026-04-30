import javax.swing.*;
import java.io.*;

public class LoginUI {

    JFrame frame;

    public LoginUI() {

        frame = new JFrame("Library Login");
        frame.setSize(350, 250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== LABELS =====
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        userLabel.setBounds(30, 30, 100, 25);
        passLabel.setBounds(30, 70, 100, 25);

        frame.add(userLabel);
        frame.add(passLabel);

        // ===== FIELDS =====
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        userField.setBounds(120, 30, 150, 25);
        passField.setBounds(120, 70, 150, 25);

        frame.add(userField);
        frame.add(passField);

        // ===== LOGIN BUTTON =====
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 120, 100, 30);
        frame.add(loginBtn);

        // ===== ACTION =====
        loginBtn.addActionListener(e -> {

            String username = userField.getText();
            String password = new String(passField.getPassword());

            boolean found = false;
            String role = "";

            try {
                File file = new File("users.txt");

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(frame, "users.txt not found!");
                    return;
                }

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {

                    String[] data = line.split("\\|");

                    if (data.length < 3) continue;

                    String fileUser = data[0];
                    String filePass = data[1];
                    String fileRole = data[2];

                    if (fileUser.equals(username) && filePass.equals(password)) {
                        found = true;
                        role = fileRole;
                        break;
                    }
                }

                br.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (found) {

                JOptionPane.showMessageDialog(frame, "Login Successful! Role: " + role);

                frame.dispose();

                // ===== ROLE BASED REDIRECT =====
                if (role.equalsIgnoreCase("ADMIN")) {

                    new AdminDashboardUI();

                } else {

                    // ✅ FIXED: pass STRING, not object
                    new StudentDashboardUI(username);
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials!");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}