// LoaiDienThoai.java
public class LoaiDienThoai {
    private int id;
    private String ten;
    private String moTa;

    public LoaiDienThoai() {}

    public LoaiDienThoai(int id, String ten, String moTa) {
        this.id = id;
        this.ten = ten;
        this.moTa = moTa;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return ten; // để hiển thị trong JComboBox
    }
}
