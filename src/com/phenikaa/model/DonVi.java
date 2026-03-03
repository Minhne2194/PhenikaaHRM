package com.phenikaa.model;

// Lớp trừu tượng Đơn Vị
public abstract class DonVi {
    protected String maDonVi;
    protected String tenDonVi;

    public DonVi(String maDonVi, String tenDonVi) {
        this.maDonVi = maDonVi;
        this.tenDonVi = tenDonVi;
    }
    public String getMaDonVi() { return maDonVi; }
    public String getTenDonVi() { return tenDonVi; }
    public abstract String moTaNhiemVu();
    
    @Override
    public String toString() { return tenDonVi; } 
}

// Các lớp con (bạn có thể tách ra file riêng hoặc để chung file này nhưng bỏ từ khóa public)
class Truong extends DonVi {
    public Truong(String ma, String ten) { super(ma, ten); }
    @Override public String moTaNhiemVu() { return "Quản lý đào tạo đa ngành"; }
}

class Khoa extends DonVi {
    public Khoa(String ma, String ten) { super(ma, ten); }
    @Override public String moTaNhiemVu() { return "Giảng dạy & NCKH"; }
}

class PhongBan extends DonVi {
    public PhongBan(String ma, String ten) { super(ma, ten); }
    @Override public String moTaNhiemVu() { return "Hành chính & Vận hành"; }
}