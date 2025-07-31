import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaiKhoanForm extends JDialog {
    private JTextField txtUser, txtPass, txtName, txtPhone, txtEmail, txtAddress;
    private JRadioButton rbMale, rbFemale;
    private JCheckBox cbAdmin;
    private JButton btnSave, btnCancel;
    private AdminFrame parent;
    private TaiKhoan tk;
    private boolean isNew;

    public TaiKhoanForm(AdminFrame parent, TaiKhoan tk) {
        super(parent, true);
        this.parent = parent;
        this.tk = tk == null ? new TaiKhoan() : tk;
        this.isNew = (tk == null);
        setTitle(isNew ? "Thêm Tài Khoản" : "Sửa Tài Khoản");
        setSize(400, 300);
        setLayout(new GridLayout(9,2));
        add(new JLabel("Tài khoản:")); txtUser = new JTextField(this.tk.getTenTaiKhoan()); add(txtUser);
        add(new JLabel("Mật khẩu:")); txtPass = new JTextField(this.tk.getMatKhau()); add(txtPass);
        add(new JLabel("Họ tên:")); txtName = new JTextField(this.tk.getHoTen()); add(txtName);
        add(new JLabel("Giới tính:")); 
        JPanel p = new JPanel(); rbMale=new JRadioButton("Nam"); rbFemale=new JRadioButton("Nữ");
        ButtonGroup g=new ButtonGroup(); g.add(rbMale); g.add(rbFemale);
        if(this.tk.isGioiTinh()) rbMale.setSelected(true); else rbFemale.setSelected(true);
        p.add(rbMale); p.add(rbFemale); add(p);
        add(new JLabel("SĐT:")); txtPhone=new JTextField(this.tk.getSoDienThoai()); add(txtPhone);
        add(new JLabel("Email:")); txtEmail=new JTextField(this.tk.getEmail()); add(txtEmail);
        add(new JLabel("Địa chỉ:")); txtAddress=new JTextField(this.tk.getDiaChi()); add(txtAddress);
        add(new JLabel("Admin:")); cbAdmin=new JCheckBox(); cbAdmin.setSelected(this.tk.isAdmin()); add(cbAdmin);
        btnSave=new JButton("Lưu"); btnCancel=new JButton("Hủy");
        add(btnSave); add(btnCancel);
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void save() {
        tk.setTenTaiKhoan(txtUser.getText());
        tk.setMatKhau(txtPass.getText());
        tk.setHoTen(txtName.getText());
        tk.setGioiTinh(rbMale.isSelected());
        tk.setSoDienThoai(txtPhone.getText());
        tk.setEmail(txtEmail.getText());
        tk.setDiaChi(txtAddress.getText());
        tk.setAdmin(cbAdmin.isSelected());
        parent.saveTaiKhoan(tk, isNew);
        dispose();
    }
}