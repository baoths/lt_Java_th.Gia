import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DienThoaiForm extends JDialog {
    private JTextField txtName, txtDesc, txtQty, txtImage, txtPrice;
    private JComboBox<LoaiDienThoai> cboLoai;
    private JButton btnSave, btnCancel;
    private AdminFrame parent;
    private DienThoai dt;
    private boolean isNew;
    private LoaiDienThoaiDAO ltDAO = new LoaiDienThoaiDAO();

    public DienThoaiForm(AdminFrame parent, DienThoai dt) {
        super(parent, true);
        this.parent = parent;
        this.dt = (dt == null ? new DienThoai() : dt);
        this.isNew = (dt == null);
        setTitle(isNew ? "Thêm Điện thoại" : "Sửa Điện thoại");
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("Tên:"));
        txtName = new JTextField(this.dt.getTen()); add(txtName);

        add(new JLabel("Mô tả:"));
        txtDesc = new JTextField(this.dt.getMoTa()); add(txtDesc);

        add(new JLabel("Số lượng:"));
        txtQty = new JTextField(String.valueOf(this.dt.getSoLuong())); add(txtQty);

        add(new JLabel("Hình ảnh URL:"));
        txtImage = new JTextField(this.dt.getUrlHinhAnh()); add(txtImage);

        add(new JLabel("Giá:"));
        txtPrice = new JTextField(String.valueOf(this.dt.getGia())); add(txtPrice);

        add(new JLabel("Loại:"));
        cboLoai = new JComboBox<>();
        try {
            for (LoaiDienThoai lt : ltDAO.getAll()) cboLoai.addItem(lt);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Lỗi tải loại: " + e.getMessage()); }
        if (!isNew) {
            for (int i = 0; i < cboLoai.getItemCount(); i++) {
                if (cboLoai.getItemAt(i).getId() == this.dt.getIdLoaiDienThoai()) {
                    cboLoai.setSelectedIndex(i);
                    break;
                }
            }
        }
        add(cboLoai);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        try {
            dt.setTen(txtName.getText().trim());
            dt.setMoTa(txtDesc.getText().trim());
            dt.setSoLuong(Integer.parseInt(txtQty.getText().trim()));
            dt.setUrlHinhAnh(txtImage.getText().trim());
            dt.setGia(Integer.parseInt(txtPrice.getText().trim()));
            dt.setIdLoaiDienThoai(((LoaiDienThoai) cboLoai.getSelectedItem()).getId());
            parent.saveDT(dt, isNew);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng và giá phải là số nguyên!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu: " + ex.getMessage());
        }
    }
}