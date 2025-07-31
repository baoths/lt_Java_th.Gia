import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatHangDAO {
    public List<DatHang> getByUser(String taiKhoan) throws SQLException {
        List<DatHang> list = new ArrayList<>();
        String sql = "SELECT * FROM DatHang WHERE TaiKhoan=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, taiKhoan);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    list.add(new DatHang(
                        rs.getInt("Id"),
                        rs.getString("TaiKhoan"),
                        rs.getInt("IdDienThoai"),
                        rs.getInt("TrangThai"),
                        rs.getDate("NgayTao"),
                        rs.getInt("TongTien")
                    ));
                }
            }
        }
        return list;
    }

    public void placeOrder(String taiKhoan) throws SQLException {
        String insert = "INSERT INTO DatHang(TaiKhoan, IdDienThoai, TrangThai, NgayTao, TongTien) "
                      + "SELECT TaiKhoan, IdDienThoai, 1, CURDATE(), d.Gia * g.SoLuong "
                      + "FROM GioHang g JOIN DienThoai d ON g.IdDienThoai=d.Id "
                      + "WHERE g.TaiKhoan=? AND g.DaDatHang=0";
        String updateCart = "UPDATE GioHang SET DaDatHang=1 WHERE TaiKhoan=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p1 = c.prepareStatement(insert);
             PreparedStatement p2 = c.prepareStatement(updateCart)) {
            c.setAutoCommit(false);
            p1.setString(1, taiKhoan);
            p1.executeUpdate();
            p2.setString(1, taiKhoan);
            p2.executeUpdate();
            c.commit();
        }
    }

    public void updateStatus(int id, int trangThai) throws SQLException {
        String sql = "UPDATE DatHang SET TrangThai=? WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, trangThai);
            p.setInt(2, id);
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DatHang WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }

    /**
     * Chuyển trạng thái số (1-3) thành chuỗi hiển thị
     */
    public static String formatStatus(int st) {
        switch (st) {
            case 1: return "Chưa thanh toán";
            case 2: return "Đã thanh toán";
            case 3: return "Đã huỷ";
            default: return "Không xác định";
        }
    }
}
