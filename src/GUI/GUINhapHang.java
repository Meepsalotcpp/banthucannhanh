/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.NguyenLieuBUS;
import BUS.HoaDonNhapBUS;
import BUS.ChiTietHoaDonNhapBUS;
import BUS.TaiKhoanBUS;
import BUS.Tool;
import DTO.NguyenLieuDTO;
import DTO.ChiTietHoaDonNhapDTO;
import DTO.HoaDonNhapDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

public class GUINhapHang extends GUIFormBanNhap {
    // Tạo mảng tiêu đề của bảng nguyên liệu
    private static String array_NguyenLieu[] = {"Mã nguyên liệu", "Tên", "Đơn giá", "Hình ảnh", "Loại", "Đơn vị tính", "Số lượng"};
    // Tạo bảng nguyên liệu để nhân viên chọn danh sách và add lên bảng thanh toán
    private GUIMyTable table_NguyenLieu, ThanhToan;
    // Tạo Panel để show thông tin nguyên liệu và để chứa thanh tìm kiếm
    private JPanel Show, TimKiem;
    // Tạo nhãn dùng để chứa hình của thông tin nguyên liệu
    private JLabel lbImage;
    // Tạo các field chứa thông tin nguyên liệu khi chọn
    private JTextField txMaMA, txTenMA, txDonGia, txSoLuong, txLoai;
    private JComboBox<String> cbDonViTinh;
    private String array_DonViTinh[]={"Kg","Quả","Bịch","Lít","Lon"};
    // Tạo các field chứa thông tin hóa đơn khi thanh toán
    private JTextField MaHDN, TongTien, NhaCungCap, NgayNhap, NhanVien;
    // Tạo các nút để phục vụ cho việc thuận tiện khi chọn mã khách hàng hay khuyến mãi
    private JButton ChonNhanVien, ChonNhaCungCap, Khac, btnFileAnh;
    // Tạo field tìm kiếm nguyên liệu
    private JTextField search;
    // Tạo biến chứa tên ảnh
    String tenAnh;

    // Tạo cờ để xem đơn giá có được đặt lần đầu hay không
    boolean isFirstDonGia = false, isFirstNL = false;

    // Tạo Menu cho popup menu
    JMenuItem menuThem, menuXoa;
    JPopupMenu popupThem, popupXoa;

    public GUINhapHang() {
        super();
    }

    @Override
    protected JPanel panelDanhSach() {
        JPanel panel = new JPanel(null);
        // Thanh tìm kiếm nguyên liệu
        TimKiem = TimKiem();
        TimKiem.setBounds(0, 0, GUImenu.width_content * 50 / 100, 80);
        panel.add(TimKiem);
        // Bảng nguyên liệu
        JPanel NguyenLieu = Table();
        NguyenLieu.setBounds(0, 85, GUImenu.width_content * 50 / 100, 300);
        panel.add(NguyenLieu);
        Khac = new JButton("Khác");
        Khac.setBounds(GUImenu.width_content * 50 / 100 - 100, 390, 80, 30);
        Khac.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        Khac.setBackground(Color.GREEN);
        Khac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    NguyenLieuBUS nlBUS = new NguyenLieuBUS();
                    nlBUS.docDSNL();
                    isFirstNL = true;
                    boolean hasMa = false;
                    txMaMA.setText(Tool.tangMa(nlBUS.getMaMonAnCuoi()));
                    do {
                        hasMa = false;
                        for (int i = 0; i < ThanhToan.tb.getRowCount(); i++) {
                            String maNL = String.valueOf(ThanhToan.tbModel.getValueAt(i, 0));
                            if (txMaMA.getText().equals(maNL))
                                hasMa = true;
                        }
                        if (hasMa && ThanhToan.tb.getRowCount() != 0) {
                            txMaMA.setText(Tool.tangMa(txMaMA.getText()));
                        }
                    } while (hasMa && ThanhToan.tb.getRowCount() != 0);

                    txLoai.setText("");
                    txTenMA.setText("");
                    txDonGia.setText("");
                    txSoLuong.setText("");
                    tenAnh = "";
                    lbImage.setIcon(new ImageIcon());

                    txTenMA.setEditable(true);
                    txLoai.setEditable(true);
                    txDonGia.setEditable(true);
                    txSoLuong.setEditable(true);
                    cbDonViTinh.setEnabled(true);
                    btnFileAnh.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu");
                }
            }
        });

        panel.add(Khac);
        // Show thông tin nguyên liệu khi click vào
        Show = Show();
        Show.setBounds(0, 390, GUImenu.width_content * 50 / 100, 370);
        panel.add(Show);

        ShowMenuOnlyThem(table_NguyenLieu);

        return panel;
    }

    // Tạo bảng nguyên liệu
    private JPanel Table() {
        table_NguyenLieu = new GUIMyTable();
        table_NguyenLieu.setHeaders(array_NguyenLieu);
        docDB();
        table_NguyenLieu.pane.setPreferredSize(new Dimension(GUImenu.width_content * 50 / 100, 300));
        double[] tilerow = { 20, 23, 13, 0, 12, 15, 12 }; // set tỉ lệ các cột, tổng các phần tử = 100
        table_NguyenLieu.tb.getColumnModel().getColumn(3).setMinWidth(0); // Ẩn cột hình ảnh
        table_NguyenLieu.tb.getColumnModel().getColumn(3).setMaxWidth(0);
        table_NguyenLieu.tb.getColumnModel().getColumn(3).setWidth(0);
        table_NguyenLieu.setColumnsWidth(tilerow);
        System.out.println("Table initialized and headers set");
        return table_NguyenLieu;
    }

    // Đọc dữ liệu bảng nguyên liệu
    public void docDB() {
        NguyenLieuBUS monAnBus = new NguyenLieuBUS();
        if (NguyenLieuBUS.dsnl == null) {
            try {
                monAnBus.docDSNL();
            } catch (Exception ex) {
                Logger.getLogger(GUINguyenLieu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (NguyenLieuDTO monAnDTO : NguyenLieuBUS.dsnl) {
            if (monAnDTO.getTrangThai().equals("Hiện")) {
                table_NguyenLieu.addRow(monAnDTO);
            }
        }
    }

    // Thanh tìm kiếm nguyên liệu
    private JPanel TimKiem() {
        JPanel TimKiem = new JPanel(null);

        JLabel lbsearch = new JLabel("");
        lbsearch.setBorder(new TitledBorder("Tìm kiếm"));

        search = new JTextField();
        search.setBorder(new TitledBorder("Tên"));
        search.setBounds(5, 20, 200, 40);
        lbsearch.add(search);
        addDocumentListener(search);

        lbsearch.setBounds(200, 0, 215, 70);
        TimKiem.add(lbsearch);

        JButton LamMoi = new JButton("Làm mới");
        LamMoi.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        LamMoi.setFont(new Font("Segoe UI", 0, 14));
        LamMoi.setBorder(BorderFactory.createLineBorder(Color.decode("#BDBDBD"), 1));
        LamMoi.setBackground(Color.decode("#90CAF9"));
        LamMoi.setBounds(450, 10, 110, 30);
        LamMoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //clear hết các text tìm kiếm
                search.setText("");
                //gọi hàm làm mới
                try {
                    LamMoi();
                } catch (Exception e) {
                    //TODO: handle exception
                }
            }

        });
        TimKiem.add(LamMoi);
        return TimKiem;
    }

    // để cho hàm tìm kiếm
    private void addDocumentListener(JTextField tx) { // để cho hàm tìm kiếm
        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        tx.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txtSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txtSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txtSearchOnChange();
            }

        });
    }

    // xóa ruột của table và đổ lên những kết quả tìm kiếm được
    public void txtSearchOnChange() {
        table_NguyenLieu.clear();
        ArrayList<NguyenLieuDTO> arraylist = Tool.searchNH(search.getText());
        for (NguyenLieuDTO DTO : arraylist) {
            if (DTO.getTrangThai().equals("Hiện")) {
                table_NguyenLieu.addRow(DTO);

            }
        }
    }

    // Show thông tin nguyên liệu
    private JPanel Show() {
        JPanel panel = new JPanel(null);
        JPanel ChiTiet = new JPanel(null);

        btnFileAnh = new JButton();
        btnFileAnh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/hinhanh-30.png")));
        btnFileAnh.addActionListener((ae) -> {
            btnFileAnh_Click();
        });
        btnFileAnh.setBounds(300, 250, 40, 40);
        panel.add(btnFileAnh);

        ChiTiet.setBounds(300, 0, 500, 300);
        lbImage = new JLabel();
        lbImage.setBackground(Color.yellow);
        lbImage.setBounds(0, 0, 300, 300);

        txMaMA = new JTextField();
        txTenMA = new JTextField();
        txDonGia = new JTextField();
        txLoai = new JTextField();
        txSoLuong = new JTextField();
        if (array_DonViTinh == null)
            array_DonViTinh = new String[] { "Kg", "Quả", "Bịch" };
        cbDonViTinh = new JComboBox<>(array_DonViTinh);

        // border
        txMaMA.setBorder(BorderFactory.createTitledBorder("Mã nguyên liệu"));
        txTenMA.setBorder(BorderFactory.createTitledBorder("Tên nguyên liệu"));
        txLoai.setBorder(BorderFactory.createTitledBorder("Loại"));
        txDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        cbDonViTinh.setBorder(BorderFactory.createTitledBorder("Đơn vị tính"));
        // disable
        txMaMA.setEditable(false);
        txTenMA.setEditable(false);
        txLoai.setEditable(false);
        txDonGia.setEditable(false);
        txSoLuong.setEditable(true);
        cbDonViTinh.setEditable(false);
        cbDonViTinh.setEnabled(false);
        btnFileAnh.setEnabled(false);
        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaMA.setFont(f);
        txTenMA.setFont(f);
        txLoai.setFont(f);
        txDonGia.setFont(f);
        txSoLuong.setFont(f);
        cbDonViTinh.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        // setsize

        txMaMA.setBounds(70, 0, 200, 40);
        txTenMA.setBounds(70, 50, 200, 40);
        txLoai.setBounds(70, 100, 200, 40);
        txDonGia.setBounds(70, 150, 200, 40);
        txSoLuong.setBounds(70, 200, 200, 40);
        cbDonViTinh.setBounds(70, 250, 200, 45);
        // add to panel
        ChiTiet.add(txMaMA);
        ChiTiet.add(txTenMA);
        ChiTiet.add(txLoai);
        ChiTiet.add(txDonGia);
        ChiTiet.add(txSoLuong);
        ChiTiet.add(cbDonViTinh);

        // Sự kiện khi click vào các row
        table_NguyenLieu.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                String id = String
                        .valueOf(table_NguyenLieu.tbModel.getValueAt(table_NguyenLieu.tb.getSelectedRow(), 0));
                if (id != null) {
                    showInfo(id);
                    isFirstDonGia = false;
                }
            }
        });

        JButton Them = new JButton("Thêm");
        Them.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/them1-30.png")));
        Them.setFont(new Font("Segoe UI", 0, 14));
        Them.setBackground(Color.decode("#90CAF9"));

        Them.setBounds(0, 310, GUImenu.width_content * 50 / 100, 40);
        // Sự kiện khi bấm nút thêm
        Them.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                Them_click(evt);
            }
        });
        panel.add(Them);
        panel.add(lbImage);
        panel.add(ChiTiet);
        return panel;
    }

    // Hàm khi ấn thêm hình ảnh
    private void btnFileAnh_Click() {
        // Tạo cửa sổ chọn file
        JDialog Them = new JDialog();
        Them.setLayout(null);
        Them.setSize(500, 500);
        // Set vị trí của Dialog
        Them.setLocationRelativeTo(null);
        // Tắt thanh công cụ mặc định
        Them.setUndecorated(true);
        // Tạo tiêu đề và set hình thức
        JLabel Title = new JLabel("Thêm nguyên liệu");
        Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
        Title.setForeground(Color.decode("#FF4081"));
        Title.setBounds(150, 0, 200, 40);
        Them.add(Title);
        FileDialog fd = new FileDialog(Them);
        fd.setVisible(true);
        String filename = fd.getFile();
        String directory = fd.getDirectory();
        if (filename != null) {
            try {
                // Lấy đường dẫn file gốc
                File sourceFile = new File(directory + filename);
                
                // Đường dẫn đến thư mục lưu ảnh (trong src/Images/MonAn/)
                File destFolder = new File("src/Images/MonAn/");
                if (!destFolder.exists()) destFolder.mkdirs(); // Tạo thư mục nếu chưa có

                // Đường dẫn file đích
                File destFile = new File(destFolder, filename);

                // Sao chép file vào thư mục đích
                Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sao chép ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            tenAnh = filename;
            int w = lbImage.getWidth();
            int h = lbImage.getHeight();
            ImageIcon img = new ImageIcon(getClass().getResource("/Images/MonAn/" + filename));
            Image imgScaled = img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
            lbImage.setIcon(new ImageIcon(imgScaled));
        }
    }

    // Hàm hiển thị ảnh và show thông tin
    private void showInfo(String id) {
        // https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        if (id != null) {
            // show hình
            for (NguyenLieuDTO ds : NguyenLieuBUS.dsnl) {
                if (ds.getIDNguyenLieu().equals(id)) {
                    int w = lbImage.getWidth();
                    int h = lbImage.getHeight();
                    ImageIcon img = new ImageIcon(getClass().getResource("/Images/MonAn/" + ds.getHinhAnh()));
                    Image imgScaled = img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    lbImage.setIcon(new ImageIcon(imgScaled));

                    // show info
                    txMaMA.setText(ds.getIDNguyenLieu());
                    txTenMA.setText(ds.getTenNguyenLieu());
                    txLoai.setText(ds.getLoai());
                    txDonGia.setText(String.valueOf(ds.getDonGia()));
                    txSoLuong.setText("1");
                    cbDonViTinh.setSelectedItem(ds.getDonViTinh());
                    tenAnh = ds.getHinhAnh();

                    txTenMA.setEditable(false);
                    txLoai.setEditable(false);
                    txDonGia.setEditable(false);
                    cbDonViTinh.setEditable(false);
                    cbDonViTinh.setEnabled(false);
                    btnFileAnh.setEnabled(false);

                    if (ds.getDonGia() == 0) {
                        txDonGia.setEditable(true);
                        isFirstDonGia = true;
                    } else {
                        isFirstDonGia = false;
                    }
                    return;
                }
            }
        }
    }

    @Override
    // Ghi đè để lấy vị trí và tạo panel thông tin hóa đơn nhập
    protected JPanel panelThongTin() {
        JPanel panel = new JPanel(null);

        MaHDN = new JTextField();
        TongTien = new JTextField();
        NhaCungCap = new JTextField();
        NgayNhap = new JTextField();
        NhanVien = new JTextField();
        ChonNhanVien = new JButton();
        ChonNhaCungCap = new JButton();
        // border
        MaHDN.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn nhập"));
        TongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
        NhaCungCap.setBorder(BorderFactory.createTitledBorder("Nhà cung cấp"));
        NgayNhap.setBorder(BorderFactory.createTitledBorder("Ngày nhập"));
        NhanVien.setBorder(BorderFactory.createTitledBorder("Nhân viên"));
        ChonNhanVien.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        ChonNhanVien.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        ChonNhaCungCap.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        ChonNhaCungCap.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        // disable
        MaHDN.setEditable(false);
        TongTien.setEditable(false);
        NhaCungCap.setEditable(false);
        NgayNhap.setEditable(false);
        NhanVien.setEditable(false);
        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        MaHDN.setFont(f);
        TongTien.setFont(f);
        NhaCungCap.setFont(f);
        NgayNhap.setFont(f);
        NhanVien.setFont(f);
        // setsize
        int y = 20;
        MaHDN.setBounds(10, y, 200, 40);
        TongTien.setBounds(300, y, 200, 40);
        y += 50;
        NhaCungCap.setBounds(10, y, 200, 40);
        ChonNhaCungCap.setBounds(210, y + 10, 30, 30);
        NhanVien.setBounds(300, y, 200, 40);
        ChonNhanVien.setBounds(500, y + 10, 30, 30);
        y += 50;
        NgayNhap.setBounds(10, y, 200, 40);
        // add to panel
        ChonNhanVien.setEnabled(false);
        panel.add(MaHDN);
        panel.add(TongTien);
        panel.add(NhaCungCap);
        panel.add(NgayNhap);
        panel.add(NhanVien);
        panel.add(ChonNhanVien);
        panel.add(ChonNhaCungCap);

        // set tăng mã tự động
        String maHoaDonNhap = Tool.tangMa3(HoaDonNhapBUS.getMaHoaDonNhapCuoi());
        MaHDN.setText(maHoaDonNhap);
        // Lấy mã nhân viên từ tài khoản đã đăng nhập
        NhanVien.setText(Tool.IDNhanVienHienHanh);
        String ngayNhap = Tool.getNgayLap().toString(); // set ngày
        NgayNhap.setText(ngayNhap);
        // kết thúc thêm mới
        // Tạo sự kiện khi ấn vào nút thì hiện cửa sổ chọn nhà cung cấp nếu người dùng
        // không nhớ mã nhà cung cấp
        ChonNhaCungCap.addActionListener((ae) -> {
            GUIFormChon a = null;
            try {
                a = new GUIFormChon(NhaCungCap, "Nhà cung cấp");
            } catch (Exception ex) {
                Logger.getLogger(GUINhapHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            a.setVisible(true);

        });
        return panel;
    }

    @Override
    // Hàm này tạo bảng chứa các nguyên liệu cần nhập
    protected JPanel panelThanhToan() {
        JPanel panel = new JPanel();
        ThanhToan = new GUIMyTable();
        ThanhToan.setHeaders(new String[] { "Mã nguyên liệu", "Tên nguyên liệu", "Giá", "Loại", "Số lượng",
                "Đơn vị tính", "Tên hình ảnh" });
        ThanhToan.pane.setPreferredSize(new Dimension(GUImenu.width_content * 49 / 100, 300));
        double[] tilerow = { 20, 23, 15, 12, 15, 15, 0 }; // set tỉ lệ các cột, tổng các phần tử = 100
        ThanhToan.tb.getColumnModel().getColumn(6).setMaxWidth(0); // Ẩn cột "Tên hình ảnh"
        ThanhToan.tb.getColumnModel().getColumn(6).setMinWidth(0);
        ThanhToan.setColumnsWidth(tilerow);
        panel.add(ThanhToan);

        ShowMenuOnlyXoa(ThanhToan);

        return panel;
    }

    // Hàm này xử lý việc ấn thêm nguyên liệu
    private void Them_click(MouseEvent e) {
        try {
            int i = table_NguyenLieu.tb.getSelectedRow();
            String sl = txSoLuong.getText();
            String dg = txDonGia.getText();
            if (isFirstNL) {
                String ten = txTenMA.getText();
                String loai = txLoai.getText();
                if (ten.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tên không bỏ trống");
                    return;
                }
                if (loai.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Loại không bỏ trống");
                    return;
                }
                if (tenAnh == null) {
                    JOptionPane.showMessageDialog(null, "Ảnh không bỏ trống");
                    return;
                } else if (tenAnh.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ảnh không bỏ trống");
                    return;
                }
            }
            if (i == -1 && !isFirstNL) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để thêm");
                return;
            } else {
                if (sl.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Số lượng không bỏ trống");
                    return;
                }
                float b = Float.parseFloat(dg);
                if (isFirstDonGia) {
                    if (dg.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Đơn giá không bỏ trống");
                        return;
                    }
                    if (b <= 0) {
                        JOptionPane.showMessageDialog(null, "Đơn giá phải lớn hơn 0");
                        return;
                    }
                    isFirstDonGia = false;
                }
                int a = Integer.parseInt(sl);
                if (a <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
                    return;
                } else {
                    for (int j = 0; j < ThanhToan.tbModel.getRowCount(); j++) {
                        if (ThanhToan.tbModel.getValueAt(j, 0).equals(txMaMA.getText())) {
                            int SlTrongThanhToan = a
                                    + Integer.valueOf(String.valueOf(ThanhToan.tbModel.getValueAt(j, 4)));

                            ThanhToan.tbModel.setValueAt(SlTrongThanhToan, j, 4);
                            TinhTien();
                            return;

                        }
                    }
                    ThanhToan.addRow(new String[] {
                            String.valueOf(txMaMA.getText()),
                            String.valueOf(txTenMA.getText()),
                            String.valueOf(b),
                            String.valueOf(txLoai.getText()),
                            // String.valueOf(table_NguyenLieu.tbModel.getValueAt(i, 4)),
                            String.valueOf(a),
                            String.valueOf(cbDonViTinh.getSelectedItem().toString()),
                            String.valueOf(tenAnh),
                    });
                    TinhTien();

                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi định dạng");
        }
    }

    @Override
    // Hàm tạo nút xóa món đã đặt và nút thanh toán hóa đơn
    protected JPanel panelCongCu() {
        JPanel panel = new JPanel(null);
        // Nút xóa
        JButton Xoa = new JButton("Xóa");
        Xoa.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/delete1-30.png")));
        Xoa.setFont(new Font("Segoe UI", 0, 14));
        Xoa.setBackground(Color.decode("#90CAF9"));

        Xoa.setBounds(0, 0, GUImenu.width_content * 25 / 100, 40);
        Xoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                Xoa_click(evt);
            }
        });
        panel.add(Xoa);
        // Nút thanh toán
        JButton btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/thanhtoan-30.png")));
        btnThanhToan.setFont(new Font("Segoe UI", 0, 14));
        btnThanhToan.setBackground(Color.decode("#90CAF9"));
        btnThanhToan.setBounds(GUImenu.width_content * 25 / 100, 0, GUImenu.width_content * 25 / 100, 40);
        btnThanhToan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                ThanhToan_click(evt);
            }
        });
        panel.add(btnThanhToan);

        return panel;
    }

    // Hàm xử lý khi ấn vào nút xóa nằm ở thanh công cụ
    private void Xoa_click(MouseEvent e) {
        int i = ThanhToan.tb.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để xóa");
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa?", "", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {

                ThanhToan.tbModel.removeRow(i);
                TinhTien();
            }
        }
    }

    // Hàm xử lý khi ấn vào nút thanh toán nằm ở thanh công cụ
    private void ThanhToan_click(MouseEvent e) {
        // Ràng buộc dữ liệu
        if (checkText(MaHDN.getText(),
                TongTien.getText(),
                NhaCungCap.getText(),
                NgayNhap.getText(),
                NhanVien.getText(),
                ThanhToan.tb.getRowCount())) {
            // Tạo đối tượng cho HoaDonBUS để chuẩn bị cho việc ghi vào arraylist và
            // database
            HoaDonNhapBUS hdnbus = new HoaDonNhapBUS();
            // Tạo đối tượng cho ChiTietHoaDonBUS để chuẩn bị cho việc ghi vào arraylist và
            // database
            ChiTietHoaDonNhapBUS cthdnbus = new ChiTietHoaDonNhapBUS();

            // Tạo DTO và truyền dữ liệu trực tiếp thông qua constructor
            HoaDonNhapDTO hdDTO = new HoaDonNhapDTO(MaHDN.getText(),
                    NhanVien.getText(),
                    NhaCungCap.getText(),
                    LocalDate.parse(NgayNhap.getText()),
                    Double.parseDouble(TongTien.getText()),
                    "Hiện");
            // Thêm vào hóa đơn nhập
            hdnbus.them(hdDTO);
            // Tạo hàm duyệt vì cần thêm nhiều chi tiết hóa đơn nhập
            NguyenLieuBUS nlBUS = new NguyenLieuBUS();
            try {
                nlBUS.docDSNL();
                for (int i = 0; i < ThanhToan.tb.getRowCount(); i++) {
                    for (int j = 0; j < nlBUS.dsnl.size(); j++) {
                        if (!nlBUS.hasID(ThanhToan.tbModel.getValueAt(i, 0).toString())) {
                            float price = Float.parseFloat(ThanhToan.tbModel.getValueAt(i, 2).toString())
                                    * (110.0f / 100);
                            nlBUS.them(new NguyenLieuDTO(
                                    ThanhToan.tbModel.getValueAt(i, 0).toString(),
                                    ThanhToan.tbModel.getValueAt(i, 1).toString(),
                                    ThanhToan.tbModel.getValueAt(i, 5).toString(),
                                    price,
                                    ThanhToan.tbModel.getValueAt(i, 6).toString(),
                                    ThanhToan.tbModel.getValueAt(i, 3).toString(),
                                    Float.parseFloat(ThanhToan.tbModel.getValueAt(i, 4).toString()),
                                    "Hiện"));
                        }
                    }
                    String manguyenlieu = String.valueOf(ThanhToan.tbModel.getValueAt(i, 0));
                    float soluong = Float.parseFloat(String.valueOf(ThanhToan.tbModel.getValueAt(i, 4)));
                    float dongia = Float.valueOf(String.valueOf(ThanhToan.tbModel.getValueAt(i, 2)));
                    float thanhtien = dongia * soluong;
                    // Tạo DTO và truyền dữ liệu trực tiếp thông qua constructor
                    ChiTietHoaDonNhapDTO ctDTO = new ChiTietHoaDonNhapDTO(MaHDN.getText(), manguyenlieu, soluong,
                            dongia, thanhtien);
                    // Thêm vào chi tiết hóa đơn
                    cthdnbus.them(ctDTO);
                    cthdnbus.trusoluong(ctDTO);
                }
                // Clear
                String maHoaDonNhap = Tool.tangMa3(HoaDonNhapBUS.getMaHoaDonNhapCuoi());
                MaHDN.setText(maHoaDonNhap);
                NhaCungCap.setText("");
                NgayNhap.setText(Tool.getNgayLap().toString());
                TongTien.setText("0");
                ThanhToan.clear();
                LamMoi();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
            }
        }
    }

    // Ràng buộc dữ liệu
    // Thứ tự truyền vào lần lượt trùng với các thứ tự ô text
    public boolean checkText(String checkMaHDN, String checkTien, String checkMaNCC, String checkNgay, String checkMaNV,
            int songuyenlieu) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (checkMaHDN.equals("")
                || checkTien.equals("")
                || checkMaNCC.equals("")
                || checkNgay.equals("")
                || checkMaNV.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        } else if (songuyenlieu == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nguyên liệu");
        } else {
            return true;

        }
        return false;
    }

    // Hàm tính tiền khi thanh toán
    public void TinhTien() {
        if (ThanhToan.tb.getRowCount() != 0) {
            float thanhtien = 0;
            for (int i = 0; i < ThanhToan.tb.getRowCount(); i++) {

                int soluong = Integer.parseInt(String.valueOf(ThanhToan.tbModel.getValueAt(i, 4)));
                float dongia = Float.valueOf(String.valueOf(ThanhToan.tbModel.getValueAt(i, 2)));
                thanhtien += dongia * soluong;

            }
            TongTien.setText(String.valueOf(thanhtien));
        } else {
            TongTien.setText("0");
        }
    }

    // Hàm khi ấn nút làm mới
    private void LamMoi() {
        table_NguyenLieu.clear();
        for (NguyenLieuDTO DTO : NguyenLieuBUS.dsnl) {
            if (DTO.getTrangThai().equals("Hiện")) {
                table_NguyenLieu.addRow(DTO);
            }
        }
    }

    // Hiển thị menu thêm
    public void ShowMenuOnlyThem(GUIMyTable table) {
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                System.out.println("Mouse released event detected");
                int r = table.getTable().rowAtPoint(me.getPoint());
                if (r >= 0 && r < table.getTable().getRowCount()) {
                    table.getTable().setRowSelectionInterval(r, r);
                } else {
                    table.getTable().clearSelection();
                }

                int rowIndex = table.getTable().getSelectedRow();
                if (rowIndex < 0)
                    return;
                if (me.isPopupTrigger() && me.getComponent() instanceof JTable) {
                    System.out.println("Right-click detected on row: " + rowIndex);
                    JPopupMenu popupTMenu = createPopUpThem(rowIndex, table);
                    popupThem.show(me.getComponent(), me.getX(), me.getY());
                } else if (me.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Right-click detected but not a popup trigger");
                } else {
                    System.out.println("Popup trigger not detected. Button: " + me.getButton());
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger() && me.getComponent() instanceof JTable) {
                    System.out.println("Popup trigger detected on mousePressed");
                }
            }
        });
        System.out.println("Mouse listener attached to table");
    }

    // Khởi tạo popup menu thêm
    public JPopupMenu createPopUpThem(int rowIndex, GUIMyTable Table) {

        menuThem = new JMenuItem("Thêm");
        menuThem.setIcon(new ImageIcon("src/Images/Icon/icons8_add_16px.png"));

        popupThem = new JPopupMenu();
        menuThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                System.out.println("Thêm menu item clicked");
                Them_click(evt);
            }
        });

        popupThem.add(menuThem);
        return popupThem;
    }

    // Hiển thị menu xóa
    public void ShowMenuOnlyXoa(GUIMyTable table) {
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                int r = table.getTable().rowAtPoint(me.getPoint());
                if (r >= 0 && r < table.getTable().getRowCount()) {
                    table.getTable().setRowSelectionInterval(r, r);
                } else {
                    table.getTable().clearSelection();
                }

                int rowIndex = table.getTable().getSelectedRow();
                if (rowIndex < 0)
                    return;
                if (me.isPopupTrigger() && me.getComponent() instanceof JTable) {
                    System.out.println("Right-click detected on row: " + rowIndex);
                    JPopupMenu popupXoa = createPopUpXoa(rowIndex, table);
                    popupXoa.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
    }

    // Khởi tạo popup menu Xoa
    public JPopupMenu createPopUpXoa(int rowIndex, GUIMyTable Table) {

        menuXoa = new JMenuItem("Xóa");
        menuXoa.setIcon(new ImageIcon("src/Images/Icon/xoa-16.png"));

        popupXoa = new JPopupMenu();
        popupXoa.add(menuXoa);

        menuXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                System.out.println("Xóa menu item clicked");
                Xoa_click(evt);
            }
        });

        return popupXoa;
    }
}
