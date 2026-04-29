import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Library Login");
        frame.setSize(350,250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(60,40,100,25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(150,40,140,25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(60,80,100,25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(150,80,140,25);
        frame.add(passText);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(125,130,100,30);
        frame.add(loginBtn);

        loginBtn.addActionListener(e -> {

            String username = userText.getText();
            String password = new String(passText.getPassword());

            User user = loginService.login(username, password);

            if(user != null && user.getRole().equals("ADMIN")) {
                frame.dispose();
              if(user.getRole().equals("ADMIN")) {
         new AdminUI();
           } else {
               new StudentUI(user.getUsername());
              }
            } else {
                JOptionPane.showMessageDialog(frame,"Invalid login");
            }
        });

        frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
}