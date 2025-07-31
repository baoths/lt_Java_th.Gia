import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserFrame extends JFrame {
    private TaiKhoan user;
    private JComboBox<LoaiDienThoai> cboLoai;
    private JTextField txtSearch;
    private JTable tblCart, tblOrders;
    private DefaultTableModel modelCart, modelOrder;
    private JPanel pnlStoreGrid;

    private LoaiDienThoaiDAO ltDAO = new LoaiDienThoaiDAO();
    private DienThoaiDAO dtDAO = new DienThoaiDAO();
    private GioHangDAO ghDAO = new GioHangDAO();
    private DatHangDAO dhDAO = new DatHangDAO();

    public UserFrame(TaiKhoan user) {
        this.user = user;
        setTitle("Cửa hàng - " + user.getHoTen());
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        JPanel left = new JPanel();
        cboLoai = new JComboBox<>(); txtSearch = new JTextField(20); JButton btnFilter = new JButton("Lọc/Tìm");
        left.add(new JLabel("Loại:")); left.add(cboLoai);
        left.add(new JLabel("Tìm:")); left.add(txtSearch); left.add(btnFilter);
        top.add(left, BorderLayout.WEST);
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> { dispose(); SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true)); });
        top.add(btnLogout, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // Tab Cửa hàng
        JPanel storePanel = new JPanel(new BorderLayout());
        pnlStoreGrid = new JPanel(new GridLayout(0, 4, 10, 10));
        pnlStoreGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(pnlStoreGrid);
        storePanel.add(scrollPane, BorderLayout.CENTER);
        tabs.add("Cửa hàng", storePanel);

        // Tab Giỏ hàng
        modelCart = new DefaultTableModel(new String[]{"ID","Tên SP","SL","Giá","Thành tiền"},0);
        tblCart = new JTable(modelCart);
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.add(new JScrollPane(tblCart), BorderLayout.CENTER);
        JPanel cartBtn = new JPanel();
        cartBtn.add(new JButton(new AbstractAction("Xóa khỏi giỏ") { public void actionPerformed(ActionEvent e){ removeFromCart(); }}));
        cartBtn.add(new JButton(new AbstractAction("- SL") { public void actionPerformed(ActionEvent e){ changeQty(-1); }}));
        cartBtn.add(new JButton(new AbstractAction("+ SL") { public void actionPerformed(ActionEvent e){ changeQty(+1); }}));
        cartBtn.add(new JButton(new AbstractAction("Thanh toán") { public void actionPerformed(ActionEvent e){ placeOrder(); }}));
        cartPanel.add(cartBtn, BorderLayout.SOUTH);
        tabs.add("Giỏ hàng", cartPanel);

        // Tab Đơn hàng
        modelOrder = new DefaultTableModel(new String[]{"ID","Tên SP","SL","TT","Ngày"},0);
        tblOrders = new JTable(modelOrder);
        tabs.add("Đơn hàng", new JScrollPane(tblOrders));

        add(tabs, BorderLayout.CENTER);

        initLoai(); loadProducts(null, ""); loadCart(); loadOrders();
        btnFilter.addActionListener(e -> loadProducts((LoaiDienThoai)cboLoai.getSelectedItem(), txtSearch.getText()));
    }

    private void initLoai() {
        cboLoai.removeAllItems();
        cboLoai.addItem(new LoaiDienThoai(0,"Tất cả",""));
        try { for(LoaiDienThoai lt: ltDAO.getAll()) cboLoai.addItem(lt); }
        catch(Exception e){ JOptionPane.showMessageDialog(this, "Lỗi tải loại: " + e.getMessage()); }
    }

    private void loadProducts(LoaiDienThoai lt, String kw) {
        pnlStoreGrid.removeAll();
        try {
            List<DienThoai> list = dtDAO.getAll();
            for (DienThoai dt : list) {
                boolean matchType = (lt == null || lt.getId() == 0 || dt.getIdLoaiDienThoai() == lt.getId());
                boolean matchKeyword = (kw == null || dt.getTen().toLowerCase().contains(kw.toLowerCase()));
                if (matchType && matchKeyword) {
                    JPanel card = new JPanel();
                    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    card.setPreferredSize(new Dimension(200, 260));

                    JLabel imgLabel = new JLabel();
                    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    try {
                        ImageIcon icon = new ImageIcon(dt.getUrlHinhAnh());
                        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        imgLabel.setIcon(new ImageIcon(scaled));
                    } catch(Exception ex) {
                        imgLabel.setText("Không có ảnh");
                    }

                    JLabel lblName = new JLabel(dt.getTen(), SwingConstants.CENTER);
                    lblName.setFont(new Font("Arial", Font.BOLD, 13));
                    lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

                    JLabel lblPrice = new JLabel("Giá: " + dt.getGia() + "đ", SwingConstants.CENTER);
                    lblPrice.setFont(new Font("Arial", Font.PLAIN, 12));
                    lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

                    JButton btnAdd = new JButton("Thêm vào giỏ");
                    btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btnAdd.setFont(new Font("Arial", Font.PLAIN, 12));
                    btnAdd.addActionListener(e -> {
                        try { ghDAO.add(new GioHang(0, user.getTenTaiKhoan(), dt.getId(), 1)); loadCart(); }
                        catch(Exception ex2){ JOptionPane.showMessageDialog(this, "Lỗi thêm giỏ hàng: " + ex2.getMessage()); }
                    });

                    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    card.add(Box.createVerticalStrut(5));
                    card.add(imgLabel);
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                    infoPanel.add(lblName);
                    infoPanel.add(lblPrice);
                    infoPanel.add(btnAdd);
                    infoPanel.setOpaque(false);
                    infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    card.add(Box.createVerticalStrut(5));
                    card.add(infoPanel);
                    card.add(Box.createVerticalGlue());
                    pnlStoreGrid.add(card);
                }
            }
            pnlStoreGrid.revalidate();
            pnlStoreGrid.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + e.getMessage());
        }
    }

    private void loadCart() {
        modelCart.setRowCount(0);
        try {
            for(GioHang gh: ghDAO.getByUser(user.getTenTaiKhoan())){
                DienThoai d = dtDAO.getAll().stream().filter(x->x.getId()==gh.getIdDienThoai()).findFirst().orElse(null);
                if(d!=null) modelCart.addRow(new Object[]{gh.getId(), d.getTen(), gh.getSoLuong(), d.getGia(), gh.getSoLuong()*d.getGia()});
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(this, "Lỗi tải giỏ hàng: " + e.getMessage()); }
    }

    private void loadOrders() {
        modelOrder.setRowCount(0);
        try {
            for(DatHang dh: dhDAO.getByUser(user.getTenTaiKhoan())){
                DienThoai d = dtDAO.getAll().stream().filter(x->x.getId()==dh.getIdDienThoai()).findFirst().orElse(null);
                if(d!=null) modelOrder.addRow(new Object[]{dh.getId(), d.getTen(), dh.getTongTien()/d.getGia(), DatHangDAO.formatStatus(dh.getTrangThai()), dh.getNgayTao()});
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(this, "Lỗi tải đơn hàng: " + e.getMessage()); }
    }

    private void removeFromCart() {
        int r = tblCart.getSelectedRow(); if(r<0) return;
        int id = (Integer)modelCart.getValueAt(r,0);
        try{ ghDAO.delete(id); loadCart(); }catch(Exception e){ JOptionPane.showMessageDialog(this, "Lỗi xóa giỏ hàng: " + e.getMessage()); }
    }

    private void changeQty(int delta) {
        int r = tblCart.getSelectedRow(); if(r<0) return;
        int id = (Integer)modelCart.getValueAt(r,0);
        int qty = (Integer)modelCart.getValueAt(r,2) + delta; if(qty<1) return;
        try{ ghDAO.updateQuantity(id, qty); loadCart(); }catch(Exception e){ JOptionPane.showMessageDialog(this, "Lỗi cập nhật số lượng: " + e.getMessage()); }
    }

    private void placeOrder() {
        try{ dhDAO.placeOrder(user.getTenTaiKhoan()); loadCart(); loadOrders(); }catch(Exception e){ JOptionPane.showMessageDialog(this, "Lỗi thanh toán: " + e.getMessage()); }
    }
}