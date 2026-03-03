package com.phenikaa.model;

public class GiangVien extends NhanSu {
    private String hocVi;
    private int soTiet;
    private double donGia;

    public GiangVien(){
    }
    
    public GiangVien(String ma, String ten, int nam, double luong, DonVi dv, String hv, int st, double dg) {
        super(ma, ten, nam, luong, dv);
        this.hocVi = hv; this.soTiet = st; this.donGia = dg;
    }
    @Override public double tinhLuong() { return luongCoBan + (soTiet * donGia); }
    public String getHocVi() { return hocVi; }
    public int getSoTiet() { return soTiet; }
}