import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoaiForm extends JDialog {
    private JTextField txtName, txtDesc;
    private JButton btnSave, btnCancel;
    private AdminFrame parent;
    private LoaiDienThoai lt;
    private boolean isNew;

    public LoaiForm(AdminFrame parent, LoaiDienThoai lt) {
        super(parent,true);
        this.parent=parent;
        this.lt = lt==null? new LoaiDienThoai():lt;
        this.isNew = lt==null;
        setTitle(isNew?"Thêm Loại":"Sửa Loại");
        setSize(300,200);
        setLayout(new GridLayout(3,2));
        add(new JLabel("Tên:")); txtName=new JTextField(this.lt.getTen()); add(txtName);
        add(new JLabel("Mô tả:")); txtDesc=new JTextField(this.lt.getMoTa()); add(txtDesc);
        btnSave=new JButton("Lưu"); btnCancel=new JButton("Hủy");
        add(btnSave); add(btnCancel);
        btnSave.addActionListener(e->save());
        btnCancel.addActionListener(e->dispose());
    }

    private void save() {
        lt.setTen(txtName.getText());
        lt.setMoTa(txtDesc.getText());
        parent.saveLoai(lt,isNew);
        dispose();
    }
}