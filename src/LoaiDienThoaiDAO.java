import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiDienThoaiDAO {
    public List<LoaiDienThoai> getAll() throws SQLException {
        List<LoaiDienThoai> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiDienThoai";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                LoaiDienThoai lt = new LoaiDienThoai();
                lt.setId(rs.getInt("Id"));
                lt.setTen(rs.getString("Ten"));
                lt.setMoTa(rs.getString("MoTa"));
                list.add(lt);
            }
        }
        return list;
    }

    public void add(LoaiDienThoai lt) throws SQLException {
        String sql = "INSERT INTO LoaiDienThoai(Ten, MoTa) VALUES (?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, lt.getTen());
            p.setString(2, lt.getMoTa());
            p.executeUpdate();
        }
    }

    public void update(LoaiDienThoai lt) throws SQLException {
        String sql = "UPDATE LoaiDienThoai SET Ten=?, MoTa=? WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, lt.getTen());
            p.setString(2, lt.getMoTa());
            p.setInt(3, lt.getId());
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM LoaiDienThoai WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }
}
