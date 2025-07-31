// DatHang.java
import java.util.Date;

public class DatHang {
    private int id;
    private String taiKhoan;
    private int idDienThoai;
    private int trangThai;
    private Date ngayTao;
    private int tongTien;

    public DatHang() {}

    public DatHang(int id, String taiKhoan, int idDienThoai,
                   int trangThai, Date ngayTao, int tongTien) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.idDienThoai = idDienThoai;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(String taiKhoan) { this.taiKhoan = taiKhoan; }

    public int getIdDienThoai() { return idDienThoai; }
    public void setIdDienThoai(int idDienThoai) { this.idDienThoai = idDienThoai; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }

    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }

    public int getTongTien() { return tongTien; }
    public void setTongTien(int tongTien) { this.tongTien = tongTien; }
}
