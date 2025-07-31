import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GioHangDAO {
    public List<GioHang> getByUser(String taiKhoan) throws SQLException {
        List<GioHang> list = new ArrayList<>();
        String sql = "SELECT * FROM GioHang WHERE TaiKhoan=? AND DaDatHang=0";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, taiKhoan);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                GioHang gh = new GioHang();
                gh.setId(rs.getInt("Id"));
                gh.setTaiKhoan(rs.getString("TaiKhoan"));
                gh.setIdDienThoai(rs.getInt("IdDienThoai"));
                gh.setSoLuong(rs.getInt("SoLuong"));
                list.add(gh);
            }
        }
        return list;
    }

    public void add(GioHang gh) throws SQLException {
        String sql = "INSERT INTO GioHang(TaiKhoan, IdDienThoai, DaDatHang, NgayThem, SoLuong) VALUES (?, ?, 0, CURDATE(), ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, gh.getTaiKhoan());
            p.setInt(2, gh.getIdDienThoai());
            p.setInt(3, gh.getSoLuong());
            p.executeUpdate();
        }
    }

    public void updateQuantity(int id, int soLuong) throws SQLException {
        String sql = "UPDATE GioHang SET SoLuong=? WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, soLuong);
            p.setInt(2, id);
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM GioHang WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }
}
