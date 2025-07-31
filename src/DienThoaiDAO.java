import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DienThoaiDAO {
    public List<DienThoai> getAll() throws SQLException {
        List<DienThoai> list = new ArrayList<>();
        String sql = "SELECT * FROM DienThoai";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                DienThoai dt = new DienThoai();
                dt.setId(rs.getInt("Id"));
                dt.setTen(rs.getString("Ten"));
                dt.setMoTa(rs.getString("MoTa"));
                dt.setSoLuong(rs.getInt("SoLuong"));
                dt.setUrlHinhAnh(rs.getString("UrlHinhAnh"));
                dt.setGia(rs.getInt("Gia"));
                dt.setIdLoaiDienThoai(rs.getInt("IdLoaiDienThoai"));
                list.add(dt);
            }
        }
        return list;
    }

    public void add(DienThoai dt) throws SQLException {
        String sql = "INSERT INTO DienThoai(Ten, MoTa, SoLuong, UrlHinhAnh, Gia, IdLoaiDienThoai) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, dt.getTen());
            p.setString(2, dt.getMoTa());
            p.setInt(3, dt.getSoLuong());
            p.setString(4, dt.getUrlHinhAnh());
            p.setInt(5, dt.getGia());
            p.setInt(6, dt.getIdLoaiDienThoai());
            p.executeUpdate();
        }
    }

    public void update(DienThoai dt) throws SQLException {
        String sql = "UPDATE DienThoai SET Ten=?, MoTa=?, SoLuong=?, UrlHinhAnh=?, Gia=?, IdLoaiDienThoai=? WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, dt.getTen());
            p.setString(2, dt.getMoTa());
            p.setInt(3, dt.getSoLuong());
            p.setString(4, dt.getUrlHinhAnh());
            p.setInt(5, dt.getGia());
            p.setInt(6, dt.getIdLoaiDienThoai());
            p.setInt(7, dt.getId());
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DienThoai WHERE Id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }
}
