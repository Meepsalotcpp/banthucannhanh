/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.KhuyenMaiBUS;
import BUS.MonAnBUS;
import BUS.NguyenLieuBUS;
import BUS.NhanVienBUS;
import BUS.Tool;
import DAO.ConnectDB;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhuyenMaiDTO;
import DTO.MonAnDTO;
import DTO.NguyenLieuDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Nguyen
 */
// Class này được kế thừa từ GUIFormBanNhap , các bố cục đã được sắp xếp sẵn ở
// bên đó
public class GUIBanHang extends GUIFormBanNhap {
    // Tạo mảng tiêu đề của bảng món ăn
    private static String array_MonAn[] = { "Mã món ăn", "Tên món", "Đơn vị tính", "Giá", "Hình ảnh", "Loại" };
    // Tạo bảng món ăn để nhân viên chọn danh sách món và add lên bảng thanh toán
    private GUIMyTable table_MonAn, ThanhToan;
    // Tạo Panel để show thông tin món ăn và để chứa thanh tìm kiếm
    private JPanel Show, TimKiem;
    // Tạo nhãn dùng để chứa hình của thông tin món ăn
    private JLabel lbImage;
    // Tạo các field chứa thông tin món ăn khi chọn
    private JTextField txMaMA, txTenMA, txDonGia, txSoLuong;
    // Tạo các field chứa thông tin hóa đơn khi thanh toán
    private JTextField MaHD, TongTien, SDT, KhachHang, tenKhachHang, NgayLap, NhanVien, tenNhanVien, KhuyenMai, tenKhuyenMai, TienTra, TienThoi;
    // Tạo các nút để phục vụ cho việc thuận tiện khi chọn mã khách hàng hay khuyến
    // mãi
    private JButton KiemTra, ChonNhanVien, ChonKhachHang, ChonKhuyenMai;
    // Tạo field tìm kiếm món ăn
    private JTextField search;
    // Tạo ComboBox để chọn tiêu chí tìm kiếm
    private JComboBox cbSearch;

    // Tạo Menu cho popup menu
    JMenuItem menuThem, menuXoa;
    JPopupMenu popupThem, popupXoa;

    GUIMyTable menu;

    public GUIBanHang() {
        super();

    }

    @Override
    protected JPanel panelDanhSach() {
        JPanel panel = new JPanel(null);
        // Thanh tìm kiếm món ăn
        TimKiem = TimKiem();
        TimKiem.setBounds(0, 0, GUImenu.width_content * 50 / 100, 80);
        panel.add(TimKiem);
        // Bảng món ăn
        JPanel MonAn = Table();
        MonAn.setBounds(0, 85, GUImenu.width_content * 50 / 100, 300);
        panel.add(MonAn);
        // Show thông tin món ăn khi click vào
        Show = Show();
        Show.setBounds(0, 390, GUImenu.width_content * 50 / 100, 370);
        panel.add(Show);

        // Hiển thị menu thêm
        ShowMenuOnlyThem(table_MonAn);

        return panel;
    }

    // Tạo bảng món ăn
    private JPanel Table() {
        table_MonAn = new GUIMyTable();
        table_MonAn.setHeaders(array_MonAn);
        docDB();
        table_MonAn.pane.setPreferredSize(new Dimension(GUImenu.width_content * 50 / 100, 300));
        double[] tilerow = { 15, 30, 18, 12, 0, 15, 10 }; // set tỉ lệ các cột, tổng các phần tử = 100
        table_MonAn.tb.getColumnModel().getColumn(4).setMinWidth(0); // Ẩn cột hình ảnh
        table_MonAn.tb.getColumnModel().getColumn(4).setMaxWidth(0);
        table_MonAn.tb.getColumnModel().getColumn(4).setWidth(0);
        table_MonAn.setColumnsWidth(tilerow);
        return table_MonAn;
    }

    // Đọc dữ liệu bảng món ăn
    public void docDB() {
        MonAnBUS monAnBus = new MonAnBUS();
        if (MonAnBUS.dsMonAn == null) {
            try {
                monAnBus.docDSMonAn();
            } catch (Exception ex) {
                Logger.getLogger(GUIMonAn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (MonAnDTO monAnDTO : MonAnBUS.dsMonAn) {
            if (monAnDTO.getTrangThai().equals("Hiện")) {
                table_MonAn.addRow(monAnDTO);

            }
        }
    }

    // Thanh tìm kiếm món ăn
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
    private void addDocumentListener(JTextField tx) {
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
        table_MonAn.clear();
        ArrayList<MonAnDTO> arraylist = Tool.searchBH(search.getText());
        for (MonAnDTO DTO : arraylist) {
            if (DTO.getTrangThai().equals("Hiện")) {
                table_MonAn.addRow(DTO);

            }
        }
    }

    // Show thông tin món ăn
    private JPanel Show() {
        JPanel panel = new JPanel(null);
        JPanel ChiTiet = new JPanel(null);

        ChiTiet.setBounds(300, 0, 500, 300);
        lbImage = new JLabel();
        lbImage.setBackground(Color.yellow);
        lbImage.setBounds(0, 0, 300, 300);

        txMaMA = new JTextField();
        txTenMA = new JTextField();
        txDonGia = new JTextField();
        txSoLuong = new JTextField();

        // border
        txMaMA.setBorder(BorderFactory.createTitledBorder("Mã món ăn"));
        txTenMA.setBorder(BorderFactory.createTitledBorder("Tên món ăn"));
        txDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        // disable
        txMaMA.setEditable(false);
        txTenMA.setEditable(false);
        txDonGia.setEditable(false);
        txSoLuong.setEditable(true);
        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaMA.setFont(f);
        txTenMA.setFont(f);
        txDonGia.setFont(f);
        txSoLuong.setFont(f);
        // setsize

        txMaMA.setBounds(50, 0, 200, 40);
        txTenMA.setBounds(50, 50, 200, 40);
        txDonGia.setBounds(50, 100, 200, 40);
        txSoLuong.setBounds(50, 150, 200, 40);
        // add to panel
        ChiTiet.add(txMaMA);
        ChiTiet.add(txTenMA);
        ChiTiet.add(txDonGia);
        ChiTiet.add(txSoLuong);

        // Sự kiện khi click vào các row
        table_MonAn.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                String id = String.valueOf(table_MonAn.tbModel.getValueAt(table_MonAn.tb.getSelectedRow(), 0));
                if (id != null) {
                    showInfo(id);

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

    // Hàm hiển thị ảnh và show thông tin
    private void showInfo(String id) {
        // https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        if (id != null) {
            // show hình
            for (MonAnDTO ds : MonAnBUS.dsMonAn) {
                if (ds.getIDMonAn().equals(id)) {
                    int w = lbImage.getWidth();
                    int h = lbImage.getHeight();
                    ImageIcon img = new ImageIcon(getClass().getResource("/Images/MonAn/" + ds.getHinhAnh()));
                    Image imgScaled = img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    lbImage.setIcon(new ImageIcon(imgScaled));

                    // show info
                    txMaMA.setText(ds.getIDMonAn());
                    txTenMA.setText(ds.getTenMonAn());
                    txDonGia.setText(String.valueOf(ds.getDonGia()));
                    txSoLuong.setText("1");
                    return;
                }
            }
        }
    }

    @Override
    // Ghi đè để lấy vị trí và tạo panel thông tin hóa đơn
    protected JPanel panelThongTin() {
        JPanel panel = new JPanel(null);

        MaHD = new JTextField();
        TongTien = new JTextField();
        SDT = new JTextField();
        KiemTra = new JButton("Kiểm tra");
        KhachHang = new JTextField();
        tenKhachHang = new JTextField();
        NgayLap = new JTextField();
        NhanVien = new JTextField();
        tenNhanVien = new JTextField();
        ChonNhanVien = new JButton();
        ChonKhachHang = new JButton();
        KhuyenMai = new JTextField();
        tenKhuyenMai = new JTextField();
        ChonKhuyenMai = new JButton();
        TienTra = new JTextField();
        TienThoi = new JTextField();
        
        // border
        MaHD.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        TongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
        SDT.setBorder(BorderFactory.createTitledBorder("SDT khách hàng"));
        tenKhachHang.setBorder(BorderFactory.createTitledBorder("Khách hàng"));
        NgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập"));
        tenNhanVien.setBorder(BorderFactory.createTitledBorder("Nhân viên"));
        tenKhuyenMai.setBorder(BorderFactory.createTitledBorder("Khuyến mãi"));
        TienTra.setBorder(BorderFactory.createTitledBorder("Tiền trả"));
        TienThoi.setBorder(BorderFactory.createTitledBorder("Tiền thối"));
        ChonNhanVien.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        // ChonNhanVien.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"),
        // 1));
        ChonKhachHang.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        // ChonKhachHang.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"),
        // 1));
        ChonKhuyenMai.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        ChonKhuyenMai.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        // disable
        MaHD.setEditable(false);
        TongTien.setEditable(false);
        tenKhachHang.setEditable(false);
        KhachHang.setVisible(false);
        NgayLap.setEditable(false);
        NhanVien.setVisible(false);
        tenNhanVien.setEditable(false);
        tenKhuyenMai.setEditable(false);
        KhuyenMai.setVisible(false);
        TienThoi.setEditable(false);
        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        MaHD.setFont(f);
        TongTien.setFont(f);
        SDT.setFont(f);
        KiemTra.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        tenKhachHang.setFont(f);
        NgayLap.setFont(f);
        tenNhanVien.setFont(f);
        tenKhuyenMai.setFont(f);
        TienTra.setFont(f);
        TienThoi.setFont(f);
        // setsize
        int y = 20;
        MaHD.setBounds(10, y, 200, 40);
        TongTien.setBounds(300, y, 200, 40);
        y += 50;
        SDT.setBounds(10, y, 200, 40);
        SDT.setBackground(Color.white);
        SDT.setOpaque(true);
        KiemTra.setBounds(210, y + 10, 90, 30);
        KiemTra.setBackground(Color.green);
        KiemTra.setOpaque(true);

        // ChonKhachHang.setBounds(210, y+10, 30, 30);
        tenNhanVien.setBounds(300, y, 200, 40);
        y += 50;
        // ChonNhanVien.setBounds(500, y+10, 30, 30);y+=50;
        tenKhachHang.setBounds(10, y, 200, 40);
        tenKhuyenMai.setBounds(300, y, 200, 40);
        ChonKhuyenMai.setBounds(500, y + 10, 30, 30);
        y += 50;
        NgayLap.setBounds(10, y, 200, 40);
        TienTra.setBounds(300, y, 200, 40);
        TienThoi.setBounds(510, y, 200, 40);
        ChonNhanVien.setEnabled(false);
        // set giá trị cho bảng thông tin
        // Chạy các hàm tự động
        String ngayLap = Tool.getNgayLap().toString(); // set ngày
        NgayLap.setText(ngayLap);
        NhanVien.setText(Tool.IDNhanVienHienHanh);
        NhanVienBUS nvBUS = new NhanVienBUS();
        try {
            nvBUS.docDSNV();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu!");
        }
        tenNhanVien.setText(nvBUS.getNhanVienDTO(Tool.IDNhanVienHienHanh).getHoNhanVien() + " "+
            nvBUS.getNhanVienDTO(Tool.IDNhanVienHienHanh).getTenNhanVien());
        if (HoaDonBUS.getMaHoaDonCuoi() != null) {
            String maHD = Tool.tangMa(HoaDonBUS.getMaHoaDonCuoi());
            MaHD.setText(maHD);
        }

        // add to panel
        panel.add(MaHD);
        panel.add(TongTien);
        panel.add(SDT);
        panel.add(KiemTra);
        panel.add(KhachHang);
        panel.add(tenKhachHang);
        panel.add(NgayLap);
        panel.add(NhanVien);
        panel.add(tenNhanVien);
        panel.add(KhuyenMai);
        panel.add(tenKhuyenMai);
        panel.add(ChonKhachHang);
        panel.add(ChonKhuyenMai);
        panel.add(TienTra);
        panel.add(TienThoi);
        // Tạo sự kiện khi ấn vào kiểm tra số điện thoại thì hiện mã khách hàng hoặc
        // thêm khách hàng
        KiemTra.addActionListener((ae) -> {

            try {
                String sdt = SDT.getText();
                if (!Tool.isPhoneNumber(sdt)) {
                    JOptionPane.showMessageDialog(null, "Số điện thoại không đúng định dạng");
                    return;
                } else {
                    GUIKhachHang khGUI = new GUIKhachHang();
                    KhachHangBUS khBUS = new KhachHangBUS();
                    khBUS.docDSKH();
                    for (int i = 0; i < khBUS.dskh.size(); i++) {
                        if (khBUS.dskh.get(i).getSoDienThoai().equals(sdt)) {
                            KhachHang.setText(khBUS.dskh.get(i).getIDKhachHang());
                            tenKhachHang.setText(khBUS.dskh.get(i).getHoKhachHang() + " " +
                            khBUS.dskh.get(i).getTenKhachHang());
                            return;
                        }
                    }
                    int result = JOptionPane.showConfirmDialog(null, "Khách hàng không tồn tại, thêm khách hàng mới?",
                            "Thêm khách hàng", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        String maKH = khGUI.Them_Frame(sdt);
                        KhachHang.setText(maKH);
                        khBUS.docDSKH();
                        if(khBUS.getKhachHangDTO(maKH) != null)
                        SDT.setText(khBUS.getKhachHangDTO(maKH).getSoDienThoai());
                        tenKhachHang.setText(khBUS.getKhachHangDTO(maKH).getHoKhachHang() + " " +
                        khBUS.getKhachHangDTO(maKH).getTenKhachHang());
                    } else
                        return;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Tạo sự kiện khi ấn vào nút thì hiện cửa sổ chọn khách hàng nếu người dùng
        // không nhớ mã khách hàng
        ChonKhachHang.addActionListener((ae) -> {

            GUIFormChon a = null;
            try {
                a = new GUIFormChon(KhachHang, "Khách hàng");
            } catch (Exception ex) {
                Logger.getLogger(GUIBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            a.setVisible(true);
        });
        // Tạo sự kiện khi ấn vào nút thì hiện cửa sổ chọn khuyến mãi nếu người dùng
        // không nhớ mã khuyến mãi
        ChonKhuyenMai.addActionListener((ae) -> {

            GUIFormChon a = null;
            try {
                a = new GUIFormChon(KhuyenMai, "Khuyến mãi");
            } catch (Exception ex) {
                Logger.getLogger(GUIBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            a.setVisible(true);
            a.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();
                    try {
                        kmBUS.docDSKM();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                        return;
                    }
                    tenKhuyenMai.setText(kmBUS.getKhuyenMaiDTO(String.valueOf(KhuyenMai.getText())).getTenChuongTrinh());
                    TinhTien();
                }

            });

        });
        //Lắng nghe sự kiện nhập liệu vào ô tiền trả để tính tiền thối
        TienTra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try{
                    float tongTien;
                    float tienTra;
                    if(TongTien.getText() == null || TongTien.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Vui lòng thêm 1 sản phẩm trước khi nhập tiền trả");
                        TienThoi.setText("");
                        TienTra.setText("");
                        return;
                    }
                    else{
                        if(TienTra.getText() != null && !TienTra.getText().isEmpty()){
                            tongTien = Float.parseFloat(String.valueOf(TongTien.getText()));
                            tienTra = Float.parseFloat(String.valueOf(TienTra.getText()));
                            TienThoi.setText(String.format("%.1f",tienTra - tongTien));
                        }
                        else return;
                    }
                }catch(NumberFormatException ne){
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số");
                    TienThoi.setText("");
                    TienTra.setText("");
                    return;
                }
            }
        });

        return panel;
    }

    @Override
    // Hàm này tạo bảng chứa các món ăn order
    protected JPanel panelThanhToan() {
        JPanel panel = new JPanel();

        ThanhToan = new GUIMyTable();
        ThanhToan.setHeaders(new String[] { "Mã món", "Tên món", "Giá", "Loại", "Số lượng" });// chỗ này bỏ hình ảnh và
                                                                                              // đơn vị tính vì không
                                                                                              // cần

        ThanhToan.pane.setPreferredSize(new Dimension(GUImenu.width_content * 49 / 100, 300));
        double[] tilerow = { 15, 30, 18, 12, 15, 10 }; // set tỉ lệ các cột, tổng các phần tử = 100
        ThanhToan.setColumnsWidth(tilerow);

        panel.add(ThanhToan);

        ShowMenuOnlyXoa(ThanhToan);

        return panel;
    }

    // Hàm này xử lý việc ấn thêm món khi khách order
    private NguyenLieuBUS nlBUS = new NguyenLieuBUS();
    private ArrayList<NguyenLieuDTO> nlList = nlBUS.dsnl;

    private void Them_click(MouseEvent e) {
        try {
            int i = table_MonAn.tb.getSelectedRow();
            String sl = txSoLuong.getText().trim();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để thêm");
            } else {
                if (sl.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Số lượng không bỏ trống");
                    return;
                }

                int a = Integer.parseInt(sl); // so luong trong o 
                if (a <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
                    return;
                } else {
                    System.out.print("\n");
                    ConnectDB connection = new ConnectDB();
                    // Truy vấn SQL
                    String query = "SELECT ctnl.IDNguyenLieu, ctnl.Soluong " +
                            "FROM chitietnguyenlieu AS ctnl " +
                            "JOIN congthuc AS ct ON ct.IDCongthuc = ctnl.IDCongThuc " +
                            "JOIN nguyenlieu AS nl ON nl.IDNguyenLieu = ctnl.IDNguyenLieu " +
                            "WHERE ct.IDMonAn = '" + table_MonAn.tbModel.getValueAt(i, 0) + "'";

                    connection.getStatement();
                    ResultSet resultSet = connection.excuteQuery(query);

                    while (resultSet.next()) {
                        for (NguyenLieuDTO nl : nlList) {
                            if (nl.getIDNguyenLieu().equals(resultSet.getString("IDNguyenLieu"))) {
                                if (nl.getSoLuong() - a * resultSet.getInt("Soluong") < 0) {
                                    JOptionPane.showMessageDialog(null, "Không đủ nguyên liệu");
                                    return;
                                } else
                                    nl.setSoLuong(nl.getSoLuong() - a * resultSet.getInt("Soluong"));
                            }
                        }
                    }
                    connection.closeConnect();
                    System.out.print("\n");
                    for (int j = 0; j < ThanhToan.tbModel.getRowCount(); j++) {
                        if (ThanhToan.tbModel.getValueAt(j, 0) == table_MonAn.tbModel.getValueAt(i, 0)) {
                            int SlTrongThanhToan = a
                                    + Integer.valueOf(String.valueOf(ThanhToan.tbModel.getValueAt(j, 4)));
                            ThanhToan.tbModel.setValueAt(SlTrongThanhToan, j, 4);
                            TinhTien();
                            return;
                        }
                    }
                    ThanhToan.addRow(new String[] {
                            String.valueOf(table_MonAn.tbModel.getValueAt(i, 0)),
                            String.valueOf(table_MonAn.tbModel.getValueAt(i, 1)),
                            String.valueOf(table_MonAn.tbModel.getValueAt(i, 3)),
                            String.valueOf(table_MonAn.tbModel.getValueAt(i, 5)),
                            String.valueOf(a)
                    });
                    TinhTien();

                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Số lượng không đúng");
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối dữ liệu");
            System.out.println(exc);
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
        try {
            int i = ThanhToan.tb.getSelectedRow();
            String sl = ThanhToan.tb.getValueAt(i, 4).toString();
            float a = Float.parseFloat(sl);
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để xóa");
            } else {
                int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa?", "", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ConnectDB connection = new ConnectDB();
                    // Truy vấn SQL
                    String query = "SELECT ctnl.IDNguyenLieu, ctnl.Soluong " +
                            "FROM chitietnguyenlieu AS ctnl " +
                            "JOIN congthuc AS ct ON ct.IDCongthuc = ctnl.IDCongThuc " +
                            "JOIN nguyenlieu AS nl ON nl.IDNguyenLieu = ctnl.IDNguyenLieu " +
                            "WHERE ct.IDMonAn = '" + ThanhToan.tb.getValueAt(i, 0).toString() + "'";

                    connection.getStatement();
                    ResultSet resultSet = connection.excuteQuery(query);

                    while (resultSet.next()) {
                        for (NguyenLieuDTO nl : nlList) {
                            if (nl.getIDNguyenLieu().equals(resultSet.getString("IDNguyenLieu"))) {
                                nl.setSoLuong(nl.getSoLuong() + a * resultSet.getFloat("Soluong"));
                            }
                        }
                    }
                    connection.closeConnect();
                    ThanhToan.tbModel.removeRow(i);
                    TinhTien();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối dữ liệu");
            System.out.println(ex);
        }
    }

    // Hàm xử lý khi ấn vào nút thanh toán nằm ở thanh công cụ
    private void ThanhToan_click(MouseEvent e) {
        // Ràng buộc dữ liệu
        if (checkText(MaHD.getText(),
                TongTien.getText(),
                KhachHang.getText(),
                NgayLap.getText(),
                NhanVien.getText(),
                ThanhToan.tb.getRowCount(),
                TienTra.getText(),
                TienThoi.getText()
            )) {
                    if (KhuyenMai.getText().equals(""))
                    {
                        KhuyenMai.setText("KM01");
                    }
                    else if (!checkPromotionValidity(KhuyenMai.getText())) {
                        JOptionPane.showMessageDialog(null, "Khuyến mãi không hợp lệ hoặc đã hết hạn");
                        return;
                    }
            // Tạo đối tượng cho HoaDonBUS để chuẩn bị cho việc ghi vào arraylist và
            // database
            HoaDonBUS hdbus = new HoaDonBUS();
            // Tạo đối tượng cho ChiTietHoaDonBUS để chuẩn bị cho việc ghi vào arraylist và
            // database
            ChiTietHoaDonBUS cthdbus = new ChiTietHoaDonBUS();
            // Tạo biến và xác định số tiền giảm trong mỗi hóa đơn
            float TienKhuyenMai = 0;
            for (KhuyenMaiDTO DTO : KhuyenMaiBUS.dskm)
                if (DTO.getIDKhuyenMai().equals(KhuyenMai.getText())) {
                    TienKhuyenMai = DTO.getTienGiam();
                }
            // Tạo DTO và truyền dữ liệu trực tiếp thông qua constructor
            HoaDonDTO hdDTO = new HoaDonDTO(MaHD.getText(),
                    NhanVien.getText(),
                    KhachHang.getText(),
                    KhuyenMai.getText(),
                    LocalDate.parse(NgayLap.getText()),
                    TienKhuyenMai,
                    Float.parseFloat(TongTien.getText()),
                    Float.parseFloat(TienTra.getText()),
                    Float.parseFloat(TienThoi.getText()),
                    "Hiện");
            // Thêm vào hóa đơn
            hdbus.them(hdDTO);
            // Tạo hàm duyệt vì cần thêm nhiều chi tiết hóa đơn
            for (int i = 0; i < ThanhToan.tb.getRowCount(); i++) {
                String mamonan = String.valueOf(ThanhToan.tbModel.getValueAt(i, 0));
                float soluong = Float.parseFloat(String.valueOf(ThanhToan.tbModel.getValueAt(i, 4)));
                float dongia = Float.valueOf(String.valueOf(ThanhToan.tbModel.getValueAt(i, 2)));
                float thanhtien = dongia * soluong;
                // Tạo DTO và truyền dữ liệu trực tiếp thông qua constructor
                ChiTietHoaDonDTO ctDTO = new ChiTietHoaDonDTO(MaHD.getText(), mamonan, soluong, dongia, thanhtien);
                // Thêm vào chi tiết hóa đơn
                cthdbus.them(ctDTO);
                // Cập nhật lại số lượng
                for (int j = 0; j < nlList.size(); j++) {
                    nlBUS.sua(nlList.get(j), j);
                }
            }
            // Clear
            JOptionPane.showMessageDialog(null, "Thanh toán thành công");
            MaHD.setText(Tool.tangMa(HoaDonBUS.getMaHoaDonCuoi()));
            SDT.setText("");
            KhachHang.setText("");
            tenKhachHang.setText("");
            KhuyenMai.setText("");
            tenKhuyenMai.setText("");
            NgayLap.setText(Tool.getNgayLap().toString()); // set ngày
            TongTien.setText("");
            TienTra.setText("");
            TienThoi.setText("");
            ThanhToan.clear();
            try {
                LamMoi();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    // Ràng buộc dữ liệu
    // Thứ tự truyền vào lần lượt trùng với các thứ tự ô text
    public boolean checkText(String checkMaHD, String checkTien, String checkMaKH, String checkNgay, String checkMaNV,
             int somonan, String checkTienTra, String checkTienThoi) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (checkMaHD.equals("")
                || checkTien.equals("")
                || checkMaKH.equals("")
                || checkNgay.equals("")
                || checkMaNV.equals("")
                || checkTienTra.equals("")
                || checkTienThoi.equals("")
                ) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        } else if (somonan == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn");
        } else if (Float.parseFloat(checkTienThoi) < 0) {
            JOptionPane.showMessageDialog(null, "Tiền trả không đủ");
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
                float soluong = Float.parseFloat(String.valueOf(ThanhToan.tbModel.getValueAt(i, 4)));
                float dongia = Float.valueOf(String.valueOf(ThanhToan.tbModel.getValueAt(i, 2)));
                thanhtien += dongia * soluong;

            }
            for (KhuyenMaiDTO DTO : KhuyenMaiBUS.dskm) {
                if (KhuyenMai.getText().equals(DTO.getIDKhuyenMai()))
                    thanhtien -= DTO.getTienGiam();
            }
            // Trường hợp có mã khuyến mãi và mua đồ có giá thấp hơn
            if (thanhtien < 0)
                thanhtien = 0;
            TongTien.setText(String.valueOf(thanhtien));
        } else {
            TongTien.setText("0");
        }
    }

    private boolean checkPromotionValidity(String promotionID) {
        KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();
        return kmBUS.isPromotionValid(promotionID);
    }

    // Hàm khi ấn nút làm mới
    private void LamMoi() throws SQLException, Exception {
        table_MonAn.clear();
        MonAnBUS BUS = new MonAnBUS();
        try{
            BUS.docDSMonAn();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
        }
        for (MonAnDTO DTO : BUS.dsMonAn) {
             if (DTO.getTrangThai().equals("Hiện")) {
                 table_MonAn.addRow(DTO);
                }
        }
    }

    // Hiển thị menu thêm
    public void ShowMenuOnlyThem(GUIMyTable table) {
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
                    JPopupMenu popupTMenu = createPopUpThem(rowIndex, table);
                    popupThem.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
    }

    // Khởi tạo popup menu thêm
    public JPopupMenu createPopUpThem(int rowIndex, GUIMyTable Table) {

        menuThem = new JMenuItem("Thêm");
        menuThem.setIcon(new ImageIcon("src/Images/Icon/icons8_add_16px.png"));

        popupThem = new JPopupMenu();
        menuThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
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
                Xoa_click(evt);
            }
        });

        return popupXoa;
    }

}
