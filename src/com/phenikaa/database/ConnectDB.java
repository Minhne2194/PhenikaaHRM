package com.phenikaa.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectDB {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/phenikaa_hrm";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối CSDL!\n" + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
}