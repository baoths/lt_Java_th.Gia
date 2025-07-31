import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Đăng nhập");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 5, 5));

        add(new JLabel("Tài khoản:"));
        txtUser = new JTextField(); add(txtUser);

        add(new JLabel("Mật khẩu:"));
        txtPass = new JPasswordField(); add(txtPass);

        JButton btnLogin = new JButton("Đăng nhập");
        add(new JPanel()); // placeholder
        add(btnLogin);

        btnLogin.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        try {
            TaiKhoanDAO dao = new TaiKhoanDAO();
            for (TaiKhoan tk : dao.getAll()) {
                if (tk.getTenTaiKhoan().equals(user) && tk.getMatKhau().equals(pass)) {
                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        if (tk.isAdmin()) new AdminFrame().setVisible(true);
                        else new UserFrame(tk).setVisible(true);
                    });
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}