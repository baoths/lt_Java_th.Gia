// DienThoai.java
public class DienThoai {
    private int id;
    private String ten;
    private String moTa;
    private int soLuong;
    private String urlHinhAnh;
    private int gia;
    private int idLoaiDienThoai;

    public DienThoai() {}

    public DienThoai(int id, String ten, String moTa, int soLuong,
                     String urlHinhAnh, int gia, int idLoaiDienThoai) {
        this.id = id;
        this.ten = ten;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.urlHinhAnh = urlHinhAnh;
        this.gia = gia;
        this.idLoaiDienThoai = idLoaiDienThoai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getUrlHinhAnh() { return urlHinhAnh; }
    public void setUrlHinhAnh(String urlHinhAnh) { this.urlHinhAnh = urlHinhAnh; }

    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    public int getIdLoaiDienThoai() { return idLoaiDienThoai; }
    public void setIdLoaiDienThoai(int idLoaiDienThoai) { this.idLoaiDienThoai = idLoaiDienThoai; }
}
