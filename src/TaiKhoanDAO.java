// TaiKhoanDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    public List<TaiKhoan> getAll() throws SQLException {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getString("TenTaiKhoan"),
                    rs.getString("MatKhau"),
                    rs.getString("HoTen"),
                    rs.getBoolean("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("DiaChi"),
                    rs.getBoolean("IsAdmin")
                );
                list.add(tk);
            }
        }
        return list;
    }

    public void add(TaiKhoan tk) throws SQLException {
        String sql = "INSERT INTO TaiKhoan "
                   + "(TenTaiKhoan, MatKhau, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, IsAdmin) "
                   + "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, tk.getTenTaiKhoan());
            p.setString(2, tk.getMatKhau());
            p.setString(3, tk.getHoTen());
            p.setBoolean(4, tk.isGioiTinh());
            p.setString(5, tk.getSoDienThoai());
            p.setString(6, tk.getEmail());
            p.setString(7, tk.getDiaChi());
            p.setBoolean(8, tk.isAdmin());
            p.executeUpdate();
        }
    }

    public void update(TaiKhoan tk) throws SQLException {
        String sql = "UPDATE TaiKhoan SET MatKhau=?, HoTen=?, GioiTinh=?, "
                   + "SoDienThoai=?, Email=?, DiaChi=?, IsAdmin=? "
                   + "WHERE TenTaiKhoan=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, tk.getMatKhau());
            p.setString(2, tk.getHoTen());
            p.setBoolean(3, tk.isGioiTinh());
            p.setString(4, tk.getSoDienThoai());
            p.setString(5, tk.getEmail());
            p.setString(6, tk.getDiaChi());
            p.setBoolean(7, tk.isAdmin());
            p.setString(8, tk.getTenTaiKhoan());
            p.executeUpdate();
        }
    }

    public void delete(String tenTaiKhoan) throws SQLException {
        String sql = "DELETE FROM TaiKhoan WHERE TenTaiKhoan=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, tenTaiKhoan);
            p.executeUpdate();
        }
    }
}
