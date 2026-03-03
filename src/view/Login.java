package view;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private final JButton btnLogin;
    private final JButton btnExit;

    public Login() {
        setTitle("Đăng nhập - QLNS Đại học Phenikaa");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        panelInput.add(new JLabel("Tài khoản:"));
        txtUsername = new JTextField();
        panelInput.add(txtUsername);
        panelInput.add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        panelInput.add(txtPassword);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");
        panelButtons.add(btnLogin);
        panelButtons.add(btnExit);

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ NHÂN SỰ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        add(lblTitle, BorderLayout.NORTH);
        add(panelInput, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                dispose();
                new MainMenu().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}