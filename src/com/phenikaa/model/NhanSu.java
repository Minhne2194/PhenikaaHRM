package com.phenikaa.model;

public abstract class NhanSu {
    protected String maNhanSu, hoTen;
    protected int namSinh;
    protected double luongCoBan;
    protected DonVi donViCongTac;
    
    public NhanSu() {
    }

    public NhanSu(String ma, String ten, int nam, double luong, DonVi dv) {
        this.maNhanSu = ma; this.hoTen = ten; this.namSinh = nam;
        this.luongCoBan = luong; this.donViCongTac = dv;
    }
    public abstract double tinhLuong();
    
    // Getters
    public String getMa() { return maNhanSu; }
    public String getTen() { return hoTen; }
    public int getNam() { return namSinh; }
    public String getTenDonVi() { return donViCongTac.getTenDonVi(); }
    public double getLuongCoBan() { return luongCoBan; }

    public void setMaNS(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setHoTen(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNamSinh(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}