import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class AdminFrame extends JFrame {
    private JTabbedPane tabs;
    private JTable tblTaiKhoan, tblLoai, tblDienThoai;
    private DefaultTableModel modelTaiKhoan, modelLoai, modelDT;

    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();
    private LoaiDienThoaiDAO ltDAO = new LoaiDienThoaiDAO();
    private DienThoaiDAO dtDAO = new DienThoaiDAO();

    public AdminFrame() {
        setTitle("Quản trị hệ thống");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel với Logout
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });
        top.add(btnLogout);
        add(top, BorderLayout.NORTH);

        // TabbedPane cho CRUD
        tabs = new JTabbedPane();
        initTaiKhoanTab();
        initLoaiTab();
        initDienThoaiTab();
        add(tabs, BorderLayout.CENTER);

        // Load dữ liệu ban đầu
        loadTaiKhoan();
        loadLoai();
        loadDienThoai();
    }

    private JPanel createCrudPanel(JTable table, Runnable onLoad, Runnable onAdd, Runnable onEdit, Runnable onDel) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel btns = new JPanel();
        btns.add(new JButton(new AbstractAction("Thêm") { public void actionPerformed(ActionEvent e) { onAdd.run(); } }));
        btns.add(new JButton(new AbstractAction("Sửa") { public void actionPerformed(ActionEvent e) { onEdit.run(); } }));
        btns.add(new JButton(new AbstractAction("Xóa") { public void actionPerformed(ActionEvent e) { onDel.run(); } }));
        btns.add(new JButton(new AbstractAction("Tải lại") { public void actionPerformed(ActionEvent e) { onLoad.run(); } }));
        p.add(btns, BorderLayout.SOUTH);
        return p;
    }

    // -- Tab TaiKhoan --
    private void initTaiKhoanTab() {
        modelTaiKhoan = new DefaultTableModel(new String[]{"Tài khoản","Mật khẩu","Họ tên","Giới tính","ĐT","Email","Địa chỉ","Admin"},0);
        tblTaiKhoan = new JTable(modelTaiKhoan);
        JPanel pnl = createCrudPanel(tblTaiKhoan, this::loadTaiKhoan, ()->showTaiKhoanForm(null), ()->{
			try {
				showTaiKhoanForm(getSelectedTaiKhoan());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, this::deleteTaiKhoan);
        tabs.add("Tài khoản", pnl);
    }

    private void loadTaiKhoan() {
        try {
            modelTaiKhoan.setRowCount(0);
            for (TaiKhoan tk : tkDAO.getAll()) {
                modelTaiKhoan.addRow(new Object[]{tk.getTenTaiKhoan(), tk.getMatKhau(), tk.getHoTen(), tk.isGioiTinh()?"Nam":"Nữ", tk.getSoDienThoai(), tk.getEmail(), tk.getDiaChi(), tk.isAdmin()});
            }
        } catch (Exception e) { showError(e); }
    }

    private TaiKhoan getSelectedTaiKhoan() throws SQLException {
        int r = tblTaiKhoan.getSelectedRow(); if (r<0) return null;
        String key = (String)modelTaiKhoan.getValueAt(r,0);
        return tkDAO.getAll().stream().filter(t->t.getTenTaiKhoan().equals(key)).findFirst().orElse(null);
    }

    private void showTaiKhoanForm(TaiKhoan tk) {
        new TaiKhoanForm(this, tk).setVisible(true);
    }

    public void saveTaiKhoan(TaiKhoan tk, boolean isNew) {
        try {
            if (isNew) tkDAO.add(tk); else tkDAO.update(tk);
            loadTaiKhoan();
        } catch (Exception e) { showError(e); }
    }

    private void deleteTaiKhoan() {
        int r = tblTaiKhoan.getSelectedRow(); if(r<0) return;
        String key = (String)modelTaiKhoan.getValueAt(r,0);
        try { tkDAO.delete(key); loadTaiKhoan(); } catch(Exception e){showError(e);}    }

    // -- Tab LoaiDienThoai --
    private void initLoaiTab() {
        modelLoai = new DefaultTableModel(new String[]{"ID","Tên","Mô tả"},0);
        tblLoai = new JTable(modelLoai);
        JPanel pnl = createCrudPanel(tblLoai, this::loadLoai, ()->showLoaiForm(null), ()->{
			try {
				showLoaiForm(getSelectedLoai());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, this::deleteLoai);
        tabs.add("Loại điện thoại", pnl);
    }

    private void loadLoai() {
        try {
            modelLoai.setRowCount(0);
            for (LoaiDienThoai lt: ltDAO.getAll()) modelLoai.addRow(new Object[]{lt.getId(), lt.getTen(), lt.getMoTa()});
        } catch (Exception e) { showError(e); }
    }

    private LoaiDienThoai getSelectedLoai() throws SQLException {
        int r = tblLoai.getSelectedRow(); if(r<0) return null;
        int id = (Integer)modelLoai.getValueAt(r,0);
        return ltDAO.getAll().stream().filter(l->l.getId()==id).findFirst().orElse(null);
    }

    private void showLoaiForm(LoaiDienThoai lt) {
        new LoaiForm(this, lt).setVisible(true);
    }

    public void saveLoai(LoaiDienThoai lt, boolean isNew) {
        try {
            if(isNew) ltDAO.add(lt); else ltDAO.update(lt);
            loadLoai();
        } catch (Exception e) { showError(e); }
    }

    private void deleteLoai() {
        int r = tblLoai.getSelectedRow(); if(r<0) return;
        int id = (Integer)modelLoai.getValueAt(r,0);
        try { ltDAO.delete(id); loadLoai(); } catch(Exception e){showError(e);}    }

    // -- Tab DienThoai --
    private void initDienThoaiTab() {
        modelDT = new DefaultTableModel(new String[]{"ID","Tên","Mô tả","SL","Hình","Giá","Loại"},0);
        tblDienThoai = new JTable(modelDT);
        JPanel pnl = createCrudPanel(tblDienThoai, this::loadDienThoai, ()->showDTForm(null), ()->{
			try {
				showDTForm(getSelectedDT());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, this::deleteDT);
        tabs.add("Điện thoại", pnl);
    }

    private void loadDienThoai() {
        try {
            modelDT.setRowCount(0);
            for (DienThoai dt: dtDAO.getAll()) modelDT.addRow(new Object[]{dt.getId(), dt.getTen(), dt.getMoTa(), dt.getSoLuong(), dt.getUrlHinhAnh(), dt.getGia(), dt.getIdLoaiDienThoai()});
        } catch (Exception e) { showError(e); }
    }

    private DienThoai getSelectedDT() throws SQLException {
        int r = tblDienThoai.getSelectedRow(); if(r<0) return null;
        int id = (Integer)modelDT.getValueAt(r,0);
        return dtDAO.getAll().stream().filter(d->d.getId()==id).findFirst().orElse(null);
    }

    private void showDTForm(DienThoai dt) {
        new DienThoaiForm(this, dt).setVisible(true);
    }

    public void saveDT(DienThoai dt, boolean isNew) {
        try {
            if(isNew) dtDAO.add(dt); else dtDAO.update(dt);
            loadDienThoai();
        } catch (Exception e) { showError(e); }
    }

    private void deleteDT() {
        int r = tblDienThoai.getSelectedRow(); if(r<0) return;
        int id = (Integer)modelDT.getValueAt(r,0);
        try { dtDAO.delete(id); loadDienThoai(); } catch(Exception e){showError(e);}    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}