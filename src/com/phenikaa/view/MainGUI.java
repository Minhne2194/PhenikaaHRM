package com.phenikaa.view;

import com.phenikaa.database.ConnectDB;
import com.phenikaa.model.*; // Import tất cả model
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Vector;

public class MainGUI extends JFrame {
    
    // Components
    private JTable tableNhanSu;
    private DefaultTableModel tableModel;
    private JComboBox<DonVi> cboDonVi;
    private JComboBox<String> cboLoaiNS;
    
    private JTextField txtMa, txtTen, txtNamSinh, txtLuongCB;
    private JTextField txtHocVi, txtSoTiet, txtDonGia;
    private JTextField txtChucVu, txtNgayCong;
    
    private JPanel panelRieng; 
    private CardLayout cardLayout;

    public MainGUI() {
        setTitle("Quản Lý Nhân Sự - Đại Học Phenikaa");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadDataDonViToComboBox();
        loadDataNhanSuToTable();
    }

    private void initComponents() {
        // --- 1. Panel Nhập liệu ---
        JPanel panelInput = new JPanel(new GridLayout(0, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin Nhân sự"));
        
        panelInput.add(new JLabel("Mã NS:")); txtMa = new JTextField(); panelInput.add(txtMa);
        panelInput.add(new JLabel("Họ tên:")); txtTen = new JTextField(); panelInput.add(txtTen);
        panelInput.add(new JLabel("Năm sinh:")); txtNamSinh = new JTextField(); panelInput.add(txtNamSinh);
        panelInput.add(new JLabel("Lương cứng:")); txtLuongCB = new JTextField(); panelInput.add(txtLuongCB);
        
        panelInput.add(new JLabel("Đơn vị:")); 
        cboDonVi = new JComboBox<>(); 
        panelInput.add(cboDonVi);

        panelInput.add(new JLabel("Loại NS:"));
        String[] loai = {"Giảng Viên", "Nhân Viên"};
        cboLoaiNS = new JComboBox<>(loai);
        panelInput.add(cboLoaiNS);

        // Panel Dynamic
        JPanel panelWrapper = new JPanel(new BorderLayout());
        panelWrapper.add(panelInput, BorderLayout.NORTH);
        
        panelRieng = new JPanel();
        cardLayout = new CardLayout();
        panelRieng.setLayout(cardLayout);

        // Form GV
        JPanel pGV = new JPanel(new GridLayout(0, 2, 5, 5));
        pGV.setBorder(BorderFactory.createTitledBorder("Chi tiết Giảng viên"));
        pGV.add(new JLabel("Học vị:")); txtHocVi = new JTextField(); pGV.add(txtHocVi);
        pGV.add(new JLabel("Số tiết:")); txtSoTiet = new JTextField(); pGV.add(txtSoTiet);
        pGV.add(new JLabel("Đơn giá:")); txtDonGia = new JTextField(); pGV.add(txtDonGia);

        // Form NV
        JPanel pNV = new JPanel(new GridLayout(0, 2, 5, 5));
        pNV.setBorder(BorderFactory.createTitledBorder("Chi tiết Nhân viên"));
        pNV.add(new JLabel("Chức vụ:")); txtChucVu = new JTextField(); pNV.add(txtChucVu);
        pNV.add(new JLabel("Ngày công:")); txtNgayCong = new JTextField(); pNV.add(txtNgayCong);

        panelRieng.add(pGV, "Giảng Viên");
        panelRieng.add(pNV, "Nhân Viên");
        panelWrapper.add(panelRieng, BorderLayout.CENTER);

        cboLoaiNS.addActionListener(e -> {
            String selected = (String) cboLoaiNS.getSelectedItem();
            cardLayout.show(panelRieng, selected);
        });

        // Buttons
        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("Thêm mới");
        JButton btnRefresh = new JButton("Làm mới danh sách");
        btnAdd.addActionListener(e -> themNhanSuVaoDB());
        btnRefresh.addActionListener(e -> loadDataNhanSuToTable());
        panelButtons.add(btnAdd);
        panelButtons.add(btnRefresh);
        panelWrapper.add(panelButtons, BorderLayout.SOUTH);

        // --- 2. Table ---
        String[] columns = {"Mã", "Họ Tên", "Năm Sinh", "Đơn Vị", "Loại", "Chi tiết", "Lương Thực Lĩnh"};
        tableModel = new DefaultTableModel(columns, 0);
        tableNhanSu = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableNhanSu);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelWrapper, scrollPane);
        splitPane.setDividerLocation(350);
        add(splitPane);
    }

    private void loadDataDonViToComboBox() {
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM donvi")) {
             
            cboDonVi.removeAllItems();
            while (rs.next()) {
                String ma = rs.getString("ma_don_vi");
                String ten = rs.getString("ten_don_vi");
                String loai = rs.getString("loai_don_vi");
                
                // Ở đây nếu bạn tách file class con thì phải new đúng class con
                // Để đơn giản ta dùng lớp nặc danh hoặc tạo instance dựa trên logic
                DonVi dv = new DonVi(ma, ten) {
                    @Override public String moTaNhiemVu() { return ""; }
                };
                cboDonVi.addItem(dv);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải Đơn vị: " + e.getMessage());
        }
    }

    private void loadDataNhanSuToTable() {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        String sql = "SELECT ns.*, dv.ten_don_vi FROM nhansu ns JOIN donvi dv ON ns.ma_don_vi = dv.ma_don_vi";
        
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                DonVi dv = new DonVi(rs.getString("ma_don_vi"), rs.getString("ten_don_vi")) {
                    @Override public String moTaNhiemVu() { return ""; }
                };

                String loaiNS = rs.getString("loai_nhan_su");
                NhanSu ns;
                String chiTiet;

                if ("GiangVien".equalsIgnoreCase(loaiNS)) {
                    ns = new GiangVien(
                        rs.getString("ma_nhan_su"), rs.getString("ho_ten"), 
                        rs.getInt("nam_sinh"), rs.getDouble("luong_co_ban"), dv,
                        rs.getString("hoc_vi"), rs.getInt("so_tiet_day"), rs.getDouble("don_gia_tiet")
                    );
                    chiTiet = "Học vị: " + ((GiangVien)ns).getHocVi();
                } else {
                    ns = new NhanVien(
                        rs.getString("ma_nhan_su"), rs.getString("ho_ten"), 
                        rs.getInt("nam_sinh"), rs.getDouble("luong_co_ban"), dv,
                        rs.getString("chuc_vu"), rs.getInt("so_ngay_cong")
                    );
                    chiTiet = "Chức vụ: " + ((NhanVien)ns).getChucVu();
                }

                Vector<Object> row = new Vector<>();
                row.add(ns.getMa());
                row.add(ns.getTen());
                row.add(ns.getNam());
                row.add(ns.getTenDonVi());
                row.add(loaiNS);
                row.add(chiTiet);
                row.add(df.format(ns.tinhLuong()));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
        }
    }

    private void themNhanSuVaoDB() {
        String sql = "INSERT INTO nhansu VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, txtMa.getText());
            pstmt.setString(2, txtTen.getText());
            pstmt.setInt(3, Integer.parseInt(txtNamSinh.getText()));
            pstmt.setDouble(4, Double.parseDouble(txtLuongCB.getText()));
            
            DonVi selectedDv = (DonVi) cboDonVi.getSelectedItem();
            pstmt.setString(5, selectedDv.getMaDonVi());
            
            String loai = (String) cboLoaiNS.getSelectedItem();
            if ("Giảng Viên".equals(loai)) {
                pstmt.setString(6, "GiangVien");
                pstmt.setString(7, txtHocVi.getText());
                pstmt.setInt(8, Integer.parseInt(txtSoTiet.getText()));
                pstmt.setDouble(9, Double.parseDouble(txtDonGia.getText()));
                pstmt.setString(10, null);
                pstmt.setObject(11, null);
            } else {
                pstmt.setString(6, "NhanVien");
                pstmt.setString(7, null);
                pstmt.setObject(8, null);
                pstmt.setObject(9, null);
                pstmt.setString(10, txtChucVu.getText());
                pstmt.setInt(11, Integer.parseInt(txtNgayCong.getText()));
            }
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadDataNhanSuToTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginForm = new LoginGUI(); 
            loginForm.setLocationRelativeTo(null); 
            loginForm.setVisible(true); 
        });
    }
}