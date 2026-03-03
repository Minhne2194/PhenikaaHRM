package com.phenikaa.model;

public class NhanVien extends NhanSu {
    private String chucVu;
    private int ngayCong;
    
    public NhanVien(){
    }

    public NhanVien(String ma, String ten, int nam, double luong, DonVi dv, String cv, int nc) {
        super(ma, ten, nam, luong, dv);
        this.chucVu = cv; this.ngayCong = nc;
    }
    @Override public double tinhLuong() { 
        double phuCap = chucVu.equalsIgnoreCase("Truong phong") ? 5000000 : 1000000;
        return (luongCoBan * ngayCong / 26) + phuCap; 
    }
    public String getChucVu() { return chucVu; }
    public int getNgayCong() { return ngayCong; }
}