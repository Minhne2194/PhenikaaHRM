package com.phenikaa.dao;

import com.phenikaa.model.TaiKhoan;
import com.phenikaa.database.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAO {
    
    // Hàm kiểm tra đăng nhập
    public TaiKhoan kiemTraDangNhap(String tenDangNhap, String matKhau) {
        TaiKhoan tk = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Lấy kết nối từ class ConnectDB của bạn
            conn = ConnectDB.getConnection(); 
            
            if (conn != null) {
                String sql = "SELECT * FROM taikhoan WHERE TenDangNhap = ? AND MatKhau = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, tenDangNhap);
                ps.setString(2, matKhau);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    tk = new TaiKhoan();
                    tk.setTenDangNhap(rs.getString("TenDangNhap"));
                    tk.setMatKhau(rs.getString("MatKhau"));
                    tk.setVaiTro(rs.getString("VaiTro"));
                }
            }
        } catch (SQLException e) {
        } finally {
            // Đóng kết nối để giải phóng bộ nhớ
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(conn != null) conn.close();
            } catch (SQLException ex) {
            }
        }
        
        return tk;
    }
}