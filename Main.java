import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // ✅ Start GUI application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI(); // Start from Login UI
            }
        });
    }
}