package model;

import java.io.Serializable;

public abstract class CanBoPhenikaa implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String maCB;
    protected String hoTen;
    protected int namSinh;
    protected double heSoLuong;
    protected final double LUONG_CO_SO = 1800000;

    public CanBoPhenikaa(String maCB, String hoTen, int namSinh, double heSoLuong) {
        this.maCB = maCB;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.heSoLuong = heSoLuong;
    }

    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public int getNamSinh() { return namSinh; }
    public void setNamSinh(int namSinh) { this.namSinh = namSinh; }

    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = heSoLuong; }

    public abstract double tinhLuong();
    public abstract String getDonViCongTac();
}