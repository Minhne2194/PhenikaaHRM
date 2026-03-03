package model;

public class NhanVienHC extends CanBoPhenikaa {
    private final String phongBan;
    private final String chucVu;
    private final int soNgayCong;

    public NhanVienHC(String maCB, String hoTen, int namSinh, double heSoLuong, 
                      String phongBan, String chucVu, int soNgayCong) {
        super(maCB, hoTen, namSinh, heSoLuong);
        this.phongBan = phongBan;
        this.chucVu = chucVu;
        this.soNgayCong = soNgayCong;
    }

    @Override
    public double tinhLuong() {
        double phuCapChucVu = chucVu.equalsIgnoreCase("Trưởng phòng") ? 4000000 : 1000000;
        double luongNgayCong = soNgayCong * 250000; 
        return (heSoLuong * LUONG_CO_SO) + luongNgayCong + phuCapChucVu;
    }

    @Override
    public String getDonViCongTac() {
        return "Phòng: " + this.phongBan;
    }

    public String getPhongBan() { return phongBan; }
    public String getChucVu() { return chucVu; }
    public int getSoNgayCong() { return soNgayCong; }
}