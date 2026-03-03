package controller;

import model.CanBoPhenikaa;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuanLyCanBo {
    private List<CanBoPhenikaa> danhSach;

    public QuanLyCanBo() {
        danhSach = new ArrayList<>();
    }

    public void themCanBo(CanBoPhenikaa cb) {
        danhSach.add(cb);
    }

    public void xoaCanBo(int index) {
        if (index >= 0 && index < danhSach.size()) {
            danhSach.remove(index);
        }
    }

    public void capNhatCanBo(int index, CanBoPhenikaa cbMoi) {
        if (index >= 0 && index < danhSach.size()) {
            danhSach.set(index, cbMoi);
        }
    }

    public List<CanBoPhenikaa> layDanhSach() {
        return danhSach;
    }
    
    public boolean kiemTraTonTai(String maCB) {
        for (CanBoPhenikaa cb : danhSach) {
            if (cb.getMaCB().equalsIgnoreCase(maCB)) {
                return true;
            }
        }
        return false;
    }

    public List<CanBoPhenikaa> timKiemTheoMa(String maCB) {
        List<CanBoPhenikaa> ketQua = new ArrayList<>();
        for (CanBoPhenikaa cb : danhSach) {
            if (cb.getMaCB().equalsIgnoreCase(maCB.trim())) {
                ketQua.add(cb);
            }
        }
        return ketQua;
    }

    public void sapXepTheoLuong() {
        Collections.sort(danhSach, (CanBoPhenikaa cb1, CanBoPhenikaa cb2) -> Double.compare(cb2.tinhLuong(), cb1.tinhLuong()));
    }

    public double tinhTongQuyLuong() {
        double tong = 0;
        for (CanBoPhenikaa cb : danhSach) {
            tong += cb.tinhLuong();
        }
        return tong;
    }

    public void luuFile(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(danhSach);
        }
    }

    public void taoDuLieuMau() {
        danhSach.clear();
        
        danhSach.add(new model.GiangVien("GV001", "Nguyễn Văn An", 1980, 3.5, "CNTT", "Tiến sĩ", 120, 3));
        danhSach.add(new model.GiangVien("GV002", "Trần Thị Bích", 1985, 2.8, "Kinh Tế", "Thạc sĩ", 80, 1));
        
        danhSach.add(new model.NhanVienHC("NV001", "Lê Hoàng", 1990, 2.34, "Phòng Đào tạo", "Chuyên viên", 24));
        danhSach.add(new model.NhanVienHC("NV002", "Phạm Văn Dũng", 1978, 4.0, "Phòng Tổ chức", "Trưởng phòng", 26));
    }
    
    @SuppressWarnings("unchecked")
    public void docFile(String fileName) throws IOException, ClassNotFoundException {
        File f = new File(fileName);
        if(!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            danhSach = (List<CanBoPhenikaa>) ois.readObject();
        }
    }
}