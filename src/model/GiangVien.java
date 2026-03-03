package model;

public class GiangVien extends CanBoPhenikaa implements NghienCuuKhoaHoc {
    private final String khoa;
    private final String trinhDo;
    private final int soTietDay;
    private final int soBaiBaoISI;

    public GiangVien(String maCB, String hoTen, int namSinh, double heSoLuong, 
                     String khoa, String trinhDo, int soTietDay, int soBaiBaoISI) {
        super(maCB, hoTen, namSinh, heSoLuong);
        this.khoa = khoa;
        this.trinhDo = trinhDo;
        this.soTietDay = soTietDay;
        this.soBaiBaoISI = soBaiBaoISI;
    }

    @Override
    public double tinhThuongNghienCuu() {
        return this.soBaiBaoISI * 5000000; 
    }

    @Override
    public double tinhLuong() {
        double phuCapTrinhDo = trinhDo.equalsIgnoreCase("Tiến sĩ") ? 3000000 : 1000000;
        double luongGiangDay = soTietDay * 120000; 
        return (heSoLuong * LUONG_CO_SO) + luongGiangDay + phuCapTrinhDo + tinhThuongNghienCuu();
    }

    @Override
    public String getDonViCongTac() {
        return "Khoa: " + this.khoa;
    }

    public String getKhoa() { return khoa; }
    public String getTrinhDo() { return trinhDo; }
    public int getSoTietDay() { return soTietDay; }
    public int getSoBaiBaoISI() { return soBaiBaoISI; }
}