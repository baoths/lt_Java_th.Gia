// GioHang.java
public class GioHang {
    private int id;
    private String taiKhoan;
    private int idDienThoai;
    private int soLuong;

    public GioHang() {}

    public GioHang(int id, String taiKhoan, int idDienThoai, int soLuong) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.idDienThoai = idDienThoai;
        this.soLuong = soLuong;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(String taiKhoan) { this.taiKhoan = taiKhoan; }

    public int getIdDienThoai() { return idDienThoai; }
    public void setIdDienThoai(int idDienThoai) { this.idDienThoai = idDienThoai; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}
