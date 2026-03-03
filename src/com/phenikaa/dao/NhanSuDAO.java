package com.phenikaa.dao;

import com.phenikaa.database.ConnectDB;
import com.phenikaa.model.GiangVien;
import com.phenikaa.model.NhanSu;
import com.phenikaa.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanSuDAO {
    
    public List<NhanSu> getAllNhanSu() {
        // Đã thêm dấu chấm phẩy ở đây
        List<NhanSu> dsNhanSu = new ArrayList<>(); 
        String sql = "SELECT * FROM NhanSu"; 
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
        while (rs.next()) {
            NhanSu ns = null;
            String loai = rs.getString("loai_ns"); 
            if ("Giảng Viên".equalsIgnoreCase(loai)) {
                ns = new GiangVien();
            } else {
                ns = new NhanVien();
            }
            if (ns != null) {
                ns.setMaNS(rs.getString("ma_ns"));
                ns.setHoTen(rs.getString("ho_ten"));
                ns.setNamSinh(rs.getInt("nam_sinh"));
                dsNhanSu.add(ns);
            }
        }
        } catch (Exception e) {
        }
        return dsNhanSu;
    }
}