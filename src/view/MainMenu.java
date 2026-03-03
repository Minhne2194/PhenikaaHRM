package view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Hệ thống Quản lý Nhân sự Phenikaa");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("CHỌN PHÂN HỆ QUẢN LÝ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlButtons = new JPanel(new GridLayout(2, 1, 15, 15));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        
        JButton btnGiangVien = new JButton("Quản lý Giảng viên");
        btnGiangVien.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton btnNhanVien = new JButton("Quản lý Nhân viên Hành chính");
        btnNhanVien.setFont(new Font("Arial", Font.BOLD, 16));

        pnlButtons.add(btnGiangVien);
        pnlButtons.add(btnNhanVien);
        add(pnlButtons, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel();
        JButton btnLogout = new JButton("Đăng xuất");
        pnlBottom.add(btnLogout);
        add(pnlBottom, BorderLayout.SOUTH);

        btnGiangVien.addActionListener(e -> {
            new HRManagementGUI("Giảng viên").setVisible(true);
            dispose();
        });

        btnNhanVien.addActionListener(e -> {
            new HRManagementGUI("Nhân viên Hành chính").setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> {
            System.exit(0);
        });
    }
}