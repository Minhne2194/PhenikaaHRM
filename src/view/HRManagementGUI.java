package view;

import controller.QuanLyCanBo;
import model.CanBoPhenikaa;
import model.GiangVien;
import model.NhanVienHC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HRManagementGUI extends JFrame {
    private QuanLyCanBo quanLy;
    private DefaultTableModel tableModel;
    private JTable table;
    
    private String loaiQuanLy;
    private List<CanBoPhenikaa> danhSachHienTai;
    
    private JTextField txtMaCB, txtHoTen, txtNamSinh, txtHeSoLuong;
    private JComboBox<String> cmbLoaiCB;
    
    private JPanel panelGV;
    private JTextField txtKhoa, txtSoTiet, txtSoBaiBao;
    private JComboBox<String> cmbTrinhDoGV;

    private JPanel panelNV;
    private JTextField txtPhongBan, txtSoNgayCong;
    private JComboBox<String> cmbChucVuNV;

    private JPanel panelDynamic;
    private CardLayout cardLayout;

    public HRManagementGUI(String loaiQuanLy) {
        this.loaiQuanLy = loaiQuanLy;
        this.danhSachHienTai = new ArrayList<>();
        quanLy = new QuanLyCanBo();

        try {
            quanLy.docFile("NhanSuPhenikaa.dat");
        } catch (IOException | ClassNotFoundException e) {
        }

        setTitle("Quản lý " + loaiQuanLy + " - Đại học Phenikaa");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(taoPanelNhapLieu(), BorderLayout.NORTH);
        add(taoPanelBangDuLieu(), BorderLayout.CENTER);
        add(taoPanelChucNang(), BorderLayout.SOUTH);
        
        capNhatBang();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thoatUngDung();
            }
        });
    }

    private JPanel taoPanelNhapLieu() {
        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết hồ sơ nhân sự"));

        JPanel pnlChung = new JPanel(new GridLayout(0, 4, 20, 15));
        pnlChung.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        pnlChung.add(new JLabel("Mã Cán bộ:")); txtMaCB = new JTextField(); pnlChung.add(txtMaCB);
        pnlChung.add(new JLabel("Họ và Tên:")); txtHoTen = new JTextField(); pnlChung.add(txtHoTen);
        pnlChung.add(new JLabel("Năm sinh:")); txtNamSinh = new JTextField(); pnlChung.add(txtNamSinh);
        pnlChung.add(new JLabel("Hệ số lương:")); txtHeSoLuong = new JTextField(); pnlChung.add(txtHeSoLuong);

        JPanel pnlLoai = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlLoai.add(new JLabel("Loại cán bộ: "));
        cmbLoaiCB = new JComboBox<>(new String[]{"Giảng viên", "Nhân viên Hành chính"});
        cmbLoaiCB.setSelectedItem(loaiQuanLy);
        cmbLoaiCB.setEnabled(false);
        pnlLoai.add(cmbLoaiCB);

        cardLayout = new CardLayout();
        panelDynamic = new JPanel(cardLayout);
        panelDynamic.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        panelGV = new JPanel(new GridLayout(0, 4, 20, 15));
        panelGV.add(new JLabel("Khoa công tác:")); txtKhoa = new JTextField(); panelGV.add(txtKhoa);
        panelGV.add(new JLabel("Trình độ:")); cmbTrinhDoGV = new JComboBox<>(new String[]{"Thạc sĩ", "Tiến sĩ"}); panelGV.add(cmbTrinhDoGV);
        panelGV.add(new JLabel("Số tiết dạy:")); txtSoTiet = new JTextField(); panelGV.add(txtSoTiet);
        panelGV.add(new JLabel("Số bài ISI:")); txtSoBaiBao = new JTextField(); panelGV.add(txtSoBaiBao);

        panelNV = new JPanel(new GridLayout(0, 4, 20, 15));
        panelNV.add(new JLabel("Phòng ban:")); txtPhongBan = new JTextField(); panelNV.add(txtPhongBan);
        panelNV.add(new JLabel("Chức vụ:")); cmbChucVuNV = new JComboBox<>(new String[]{"Chuyên viên", "Phó phòng", "Trưởng phòng"}); panelNV.add(cmbChucVuNV);
        panelNV.add(new JLabel("Số ngày công:")); txtSoNgayCong = new JTextField(); panelNV.add(txtSoNgayCong);
        panelNV.add(new JLabel("")); panelNV.add(new JLabel(""));

        panelDynamic.add(panelGV, "Giảng viên");
        panelDynamic.add(panelNV, "Nhân viên Hành chính");
        cardLayout.show(panelDynamic, loaiQuanLy);

        pnlTop.add(pnlChung);
        pnlTop.add(pnlLoai);
        pnlTop.add(panelDynamic);

        return pnlTop;
    }

    private JPanel taoPanelBangDuLieu() {
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Danh sách " + loaiQuanLy));

        String[] cols = {"Mã CB", "Họ Tên", "Năm sinh", "Phân loại", "Đơn vị/Phòng", "Tổng Lương (VNĐ)"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) hienThiChiTietLenForm(row);
            }
        });

        return pnlCenter;
    }

    private JPanel taoPanelChucNang() {
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        
        JButton btnQuayLai = new JButton("Quay lại Menu");
        JButton btnThem = new JButton("Thêm mới");
        JButton btnSua = new JButton("Cập nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTim = new JButton("Tìm kiếm");
        JButton btnSapXep = new JButton("Sắp xếp lương");
        JButton btnNhap = new JButton("Nhập File");
        JButton btnLuu = new JButton("Xuất File");
        JButton btnTongLuong = new JButton("Thống kê");
        JButton btnLamMoi = new JButton("Làm mới");

        pnlSouth.add(btnQuayLai);
        pnlSouth.add(btnThem); pnlSouth.add(btnSua); pnlSouth.add(btnXoa);
        pnlSouth.add(btnTim); pnlSouth.add(btnSapXep);
        pnlSouth.add(btnNhap); pnlSouth.add(btnLuu); 
        pnlSouth.add(btnTongLuong); pnlSouth.add(btnLamMoi);

        btnQuayLai.addActionListener(e -> {
            try { quanLy.luuFile("NhanSuPhenikaa.dat"); } catch (Exception ex) {}
            new MainMenu().setVisible(true);
            dispose();
        });

        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnNhap.addActionListener(e -> xuLyDocFile());
        btnLuu.addActionListener(e -> xuLyLuuFile());
        btnLamMoi.addActionListener(e -> { xoaTrangForm(); capNhatBang(); });
        
        btnTongLuong.addActionListener(e -> {
            double tong = 0;
            for (CanBoPhenikaa cb : danhSachHienTai) {
                tong += cb.tinhLuong();
            }
            JOptionPane.showMessageDialog(this, 
                String.format("Tổng quỹ lương khối %s: %,.0f VNĐ", loaiQuanLy, tong),
                "Thống kê", JOptionPane.INFORMATION_MESSAGE);
        });

        btnTim.addActionListener(e -> {
            String tuKhoa = JOptionPane.showInputDialog(this, "Nhập mã " + loaiQuanLy + " cần tìm:");
            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                List<CanBoPhenikaa> listTim = quanLy.timKiemTheoMa(tuKhoa);
                List<CanBoPhenikaa> ketQuaLoc = new ArrayList<>();
                for (CanBoPhenikaa cb : listTim) {
                    if (loaiQuanLy.equals("Giảng viên") && cb instanceof GiangVien) ketQuaLoc.add(cb);
                    else if (loaiQuanLy.equals("Nhân viên Hành chính") && cb instanceof NhanVienHC) ketQuaLoc.add(cb);
                }
                
                if (ketQuaLoc.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hồ sơ phù hợp!");
                } else {
                    hienThiDanhSach(ketQuaLoc);
                }
            } else {
                capNhatBang();
            }
        });

        btnSapXep.addActionListener(e -> {
            quanLy.sapXepTheoLuong();
            capNhatBang();
            JOptionPane.showMessageDialog(this, "Đã sắp xếp theo lương giảm dần!");
        });

        return pnlSouth;
    }

    private CanBoPhenikaa layDuLieuTuForm() throws NumberFormatException {
        String ma = txtMaCB.getText();
        String ten = txtHoTen.getText();
        if(ma.isEmpty() || ten.isEmpty()) throw new NumberFormatException("Thiếu thông tin");
        int nam = Integer.parseInt(txtNamSinh.getText());
        double heSo = Double.parseDouble(txtHeSoLuong.getText());

        if (loaiQuanLy.equals("Giảng viên")) {
            String khoa = txtKhoa.getText();
            String trinhDo = (String) cmbTrinhDoGV.getSelectedItem();
            int tiet = Integer.parseInt(txtSoTiet.getText());
            int baiBao = Integer.parseInt(txtSoBaiBao.getText());
            return new GiangVien(ma, ten, nam, heSo, khoa, trinhDo, tiet, baiBao);
        } else {
            String phong = txtPhongBan.getText();
            String chucVu = (String) cmbChucVuNV.getSelectedItem();
            int ngay = Integer.parseInt(txtSoNgayCong.getText());
            return new NhanVienHC(ma, ten, nam, heSo, phong, chucVu, ngay);
        }
    }

    private void xuLyThem() {
        try {
            if (quanLy.kiemTraTonTai(txtMaCB.getText())) {
                JOptionPane.showMessageDialog(this, "Mã này đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            CanBoPhenikaa cb = layDuLieuTuForm();
            quanLy.themCanBo(cb);
            capNhatBang();
            xoaTrangForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLySua() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            return;
        }
        try {
            CanBoPhenikaa cbCu = danhSachHienTai.get(row);
            int originalIndex = quanLy.layDanhSach().indexOf(cbCu);
            
            CanBoPhenikaa cbMoi = layDuLieuTuForm();
            quanLy.capNhatCanBo(originalIndex, cbMoi);
            capNhatBang();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu khi sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXoa() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CanBoPhenikaa cbCu = danhSachHienTai.get(row);
            int originalIndex = quanLy.layDanhSach().indexOf(cbCu);
            quanLy.xoaCanBo(originalIndex);
            
            capNhatBang();
            xoaTrangForm();
        }
    }

    private void hienThiDanhSach(List<CanBoPhenikaa> list) {
        this.danhSachHienTai = list;
        tableModel.setRowCount(0);
        for (CanBoPhenikaa cb : list) {
            String loai = (cb instanceof GiangVien) ? "Giảng viên" : "Nhân viên HC";
            tableModel.addRow(new Object[]{
                    cb.getMaCB(), cb.getHoTen(), cb.getNamSinh(), 
                    loai, cb.getDonViCongTac(), 
                    String.format("%,.0f", cb.tinhLuong())
            });
        }
    }

    private void capNhatBang() {
        List<CanBoPhenikaa> locDanhSach = new ArrayList<>();
        for (CanBoPhenikaa cb : quanLy.layDanhSach()) {
            if (loaiQuanLy.equals("Giảng viên") && cb instanceof GiangVien) {
                locDanhSach.add(cb);
            } else if (loaiQuanLy.equals("Nhân viên Hành chính") && cb instanceof NhanVienHC) {
                locDanhSach.add(cb);
            }
        }
        hienThiDanhSach(locDanhSach);
    }

    private void hienThiChiTietLenForm(int index) {
        if (index < 0 || index >= danhSachHienTai.size()) return;

        CanBoPhenikaa cb = danhSachHienTai.get(index);
        txtMaCB.setText(cb.getMaCB());
        txtHoTen.setText(cb.getHoTen());
        txtNamSinh.setText(String.valueOf(cb.getNamSinh()));
        txtHeSoLuong.setText(String.valueOf(cb.getHeSoLuong()));

        if (cb instanceof GiangVien giangVien) {
            GiangVien gv = giangVien;
            txtKhoa.setText(gv.getKhoa());
            cmbTrinhDoGV.setSelectedItem(gv.getTrinhDo());
            txtSoTiet.setText(String.valueOf(gv.getSoTietDay()));
            txtSoBaiBao.setText(String.valueOf(gv.getSoBaiBaoISI()));
        } else {
            NhanVienHC nv = (NhanVienHC) cb;
            txtPhongBan.setText(nv.getPhongBan());
            cmbChucVuNV.setSelectedItem(nv.getChucVu());
            txtSoNgayCong.setText(String.valueOf(nv.getSoNgayCong()));
        }
    }

    private void xuLyDocFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file dữ liệu để nhập (.dat)");
        
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToRead = fileChooser.getSelectedFile();
            try {
                quanLy.docFile(fileToRead.getAbsolutePath());
                capNhatBang();
                JOptionPane.showMessageDialog(this, "Tải dữ liệu từ file thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file!\nChi tiết: " + ex.getMessage(), "Lỗi Nhập File", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyLuuFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file dữ liệu (.dat)");
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            if (!filePath.toLowerCase().endsWith(".dat")) {
                filePath += ".dat";
            }
            
            try {
                quanLy.luuFile(filePath);
                JOptionPane.showMessageDialog(this, "Lưu file thành công tại:\n" + filePath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi lưu file: " + ex.getMessage(), "Lỗi Xuất File", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaTrangForm() {
        txtMaCB.setText(""); txtHoTen.setText(""); 
        txtNamSinh.setText(""); txtHeSoLuong.setText("");
        txtKhoa.setText(""); txtSoTiet.setText(""); txtSoBaiBao.setText("");
        txtPhongBan.setText(""); txtSoNgayCong.setText("");
        table.clearSelection();
    }

    private void thoatUngDung() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn lưu dữ liệu hệ thống trước khi thoát?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                quanLy.luuFile("NhanSuPhenikaa.dat"); 
            } catch (Exception e) {}
            System.exit(0);
        } else if (confirm == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}