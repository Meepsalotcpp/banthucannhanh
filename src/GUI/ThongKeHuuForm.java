/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.*;
import DTO.*;
import GUI.GUIMyTable;
import Report.PriceFormatter;
import button.MoreButton;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import button.DateButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
/**
 *
 * @author DELL
 */
public class ThongKeHuuForm {

}

class ThongKeMonAn extends JPanel {
    MonAnBUS qlmaBUS = new MonAnBUS();
    NhanVienBUS qlnvBUS = new NhanVienBUS();
    KhachHangBUS qlkhBUS = new KhachHangBUS();
    HoaDonBUS qlhdBUS = new HoaDonBUS();
    NhaCungCapBUS qlnccBUS = new NhaCungCapBUS();
    HoaDonNhapBUS qlhdnBUS = new HoaDonNhapBUS();
    ChiTietHoaDonBUS qlcthdBUS = new ChiTietHoaDonBUS();
    ChiTietHoaDonNhapBUS qlcthdnBUS = new ChiTietHoaDonNhapBUS();
    NguyenLieuBUS qlnlBUS= new NguyenLieuBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    GUIMyTable tb;

    public ThongKeMonAn() {
        this.setLayout(new BorderLayout());

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel các nút
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Nguyệu liệu nhập", "Món ăn bán ra"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

//        panel tìm kiếm theo thời gian
        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        btnRefresh.addActionListener((ae) -> {
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);

        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thống kê từng cái
        tb = new GUIMyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    
    private void soLuongNguyenLieuNhap() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nguyên liệu", "Tên nguyên liệu", "Mã phiếu nhập", "Tên nhà cung cấp", "Ngày nhập", "Số lượng", "Ðơn giá", "Tổng chi"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);//tìm kiếm theo thời gian

        int tongTatCa = 0;
        float tongTien = 0;
        String donViTinh = "";
        for (NguyenLieuDTO nl : qlnlBUS.getNguyenLieuDTO()) {
            float tongSoLuong = 0;
            float tongtiennguyenlieu = 0;
            tb.addRow(new String[]{nl.getIDNguyenLieu(), nl.getTenNguyenLieu(), "", "", "", "", PriceFormatter.format((float) nl.getDonGia()), ""});
            
            for (HoaDonNhapDTO hdn : qlhdnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                ChiTietHoaDonNhapDTO cthdn = qlcthdnBUS.getChiTiet(hdn.getIDHoaDonNhap(), nl.getIDNguyenLieu());
      
                if (cthdn != null) {
                    donViTinh = String.valueOf(qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getDonViTinh());
                    tb.addRow(new String[]{"", "",
                        cthdn.getIDHoaDonNhap(),
                        qlnccBUS.getNhaCungCapDTO(hdn.getIDNhaCungCap()).getTenNhaCungCap(),
                        String.valueOf(hdn.getNgayNhap()),
                        String.valueOf(cthdn.getSoLuong()+ " " + qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getDonViTinh()),
                        "",
                       PriceFormatter.format((float)cthdn.getSoLuong() * cthdn.getGiaNhap())

                    });
                    tongSoLuong += cthdn.getSoLuong();
                    tongtiennguyenlieu += cthdn.getSoLuong() * cthdn.getGiaNhap();
                }                  
                            
            }

            tb.addRow(new String[]{"", "", "", "", mcd.getKhoangTG(), String.valueOf(tongSoLuong)+ " "+ donViTinh, "",PriceFormatter.format(tongtiennguyenlieu)});
            tb.addRow(new String[]{"", "", "", "", "", "", "", ""});

            tongTatCa += tongSoLuong;
            tongTien += tongtiennguyenlieu;
        }
        tb.addRow(new String[]{"", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)+ " nguyên liệu ","",PriceFormatter.format(tongTien)});
    }

    private void soLuongMonAnBan() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã món ăn", "Tên món ăn", "Mã hóa don", "Tên nhân viên", "Ngày lập", "Số lượng", "Ðơn giá", "Tổng thu"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        int tongTatCa = 0;
        float tongTien = 0;
        String donViTinh = "";
        for (MonAnDTO ma : qlmaBUS.getMonAnDTO()) {
            int tongSoLuong = 0;
            float tongTienHoaDonTungSanPham = 0;
            
            donViTinh = ma.getDonViTinh();
            tb.addRow(new String[]{ma.getIDMonAn(), ma.getTenMonAn(), "", "", "", "", PriceFormatter.format(ma.getDonGia()), ""});

            for (HoaDonDTO hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayTu(), -1, -1)) {
                ChiTietHoaDonDTO cthd = qlcthdBUS.getChiTiet(hd.getIDHoaDon(), ma. getIDMonAn());
                if (cthd != null) {
                    tb.addRow(new String[]{"", "",
                        hd.getIDHoaDon(),
                        qlnvBUS.getNhanVienDTO(hd.getIDNhanVien()).getTenNhanVien(),
                        String.valueOf(hd.getNgayLap()),
                        String.valueOf(cthd.getSoLuong()+ " "+ qlmaBUS.getMonAnDTO(cthd.getIDMonAn()).getDonViTinh()), "", PriceFormatter.format(cthd.getSoLuong() * cthd.getDonGia())
                    });
                    tongSoLuong += cthd.getSoLuong();
                    tongTienHoaDonTungSanPham += cthd.getSoLuong() * cthd.getDonGia();
                }                              
            }

            tb.addRow(new String[]{"", "", "", "", mcd.getKhoangTG(), String.valueOf(tongSoLuong)+ " "+ String.valueOf(donViTinh), "", PriceFormatter.format(tongTienHoaDonTungSanPham)});
            tb.addRow(new String[]{"", "", "", "", "", ""});
            tongTatCa += tongSoLuong;
            tongTien += tongTienHoaDonTungSanPham;
        }

        tb.addRow(new String[]{"", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)+ " món ăn ","",  PriceFormatter.format(tongTien)});

    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Nguyệu liệu nhập")) {
            soLuongNguyenLieuNhap();
        }
        if (cbTieuChi.getSelectedItem().equals("Món ăn bán ra")) {
            soLuongMonAnBan();
        }
    }
}

class ThongKeNhanVien extends JPanel {

    MonAnBUS qlmaBUS = new MonAnBUS();
    NhanVienBUS qlnvBUS = new NhanVienBUS();
    KhachHangBUS qlkhBUS = new KhachHangBUS();
    HoaDonBUS qlhdBUS = new HoaDonBUS();
    NhaCungCapBUS qlnccBUS = new NhaCungCapBUS();
    HoaDonNhapBUS qlhdnBUS = new HoaDonNhapBUS();
    ChiTietHoaDonBUS qlcthdBUS = new ChiTietHoaDonBUS();
    ChiTietHoaDonNhapBUS qlcthdnBUS = new ChiTietHoaDonNhapBUS();
    NguyenLieuBUS qlnlBUS= new NguyenLieuBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    GUIMyTable tb;

    public ThongKeNhanVien() {
        this.setLayout(new BorderLayout());

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel tieu chi
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Tổng tiền", "Món ăn"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        btnRefresh.addActionListener((ae) -> {
            try {
                qlmaBUS.docDSMonAn();
                qlnvBUS.docDSNV();
                qlhdBUS.docHD();
                qlcthdBUS.docCTHD();
            } catch (Exception ex) {
                Logger.getLogger(ThongKeNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            }            
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);

        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new GUIMyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    public void tongTienTungNhanVien_searchOnChange() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nhân viên", "Tên nhân viên", "Mã hóa đơn", "Ngày lập", "Tổng tiền hóa đơn"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        float tongTatCa = 0;
        for (NhanVienDTO nv : qlnvBUS.getNhanVienDTO()) {
            float tongTien = 0;
            tb.addRow(new String[]{nv.getIDNhanVien(), nv.getTenNhanVien(), "", ""});

            for (HoaDonDTO hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (nv.getIDNhanVien().equals(hd.getIDNhanVien())) {
                    tb.addRow(new String[]{"", "",
                        hd.getIDHoaDon(),
                        String.valueOf(hd.getNgayLap()),
                        PriceFormatter.format((float) hd.getTongTien())
                    });
                    tongTien += hd.getTongTien();
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), PriceFormatter.format(tongTien)});
            tb.addRow(new String[]{"", "", "", "", ""});

            tongTatCa += tongTien;
        }
        tb.addRow(new String[]{"", "", "", "Tổng thu", PriceFormatter.format(tongTatCa)});
    }

    public void MonAnCuaTungNhanVien_searchOnChange() {
        tb.setHeaders(new String[]{"Mã nhân viên", "Tên nhân viên", "Mã hóa đơn", "Ngày lập", "Mã món ăn", "Tên món ăn", "Số lượng "});
        tb.clear();

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        int tongTatCa = 0;
        
        for (NhanVienDTO nv : qlnvBUS.getNhanVienDTO()) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{nv.getIDNhanVien(), nv.getTenNhanVien(), "", "", "", "", ""});

            for (HoaDonDTO hd : qlhdBUS.search("Mã nhân viên", nv.getIDNhanVien(), mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) { // tương đối -> sai
                tb.addRow(new String[]{"", "", hd.getIDHoaDon(), String.valueOf(hd.getNgayLap()), "", "", ""});

                for (ChiTietHoaDonDTO cthd : qlcthdBUS.search("Mã hóa đơn", hd.getIDHoaDon(), -1, -1, -1, -1)) { // tương đối -> sai
                    tongSoLuong += cthd.getSoLuong();
                    tb.addRow(new String[]{"", "", "", "",
                        cthd.getIDMonAn(),
                        qlmaBUS.getMonAnDTO(cthd.getIDMonAn()).getTenMonAn(),
                        String.valueOf(cthd.getSoLuong())+" " +qlmaBUS.getMonAnDTO(cthd.getIDMonAn()).getDonViTinh()
                    });
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "Tổng số món ăn", String.valueOf(tongSoLuong)+" món ăn"});
            tb.addRow(new String[]{"", "", "", "", "", "",""});

            tongTatCa += tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "", "Tổng bán ra", String.valueOf(tongTatCa)+" món ăn"});
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Tổng tiền")) {
            tongTienTungNhanVien_searchOnChange();
        }
        if (cbTieuChi.getSelectedItem().equals("Món ăn")) {
            MonAnCuaTungNhanVien_searchOnChange();
        }
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }
}

class ThongKeKhachHang extends JPanel {

    MonAnBUS qlmaBUS = new MonAnBUS();
    NhanVienBUS qlnvBUS = new NhanVienBUS();
    KhachHangBUS qlkhBUS = new KhachHangBUS();
    HoaDonBUS qlhdBUS = new HoaDonBUS();
    NhaCungCapBUS qlnccBUS = new NhaCungCapBUS();
    HoaDonNhapBUS qlhdnBUS = new HoaDonNhapBUS();
    ChiTietHoaDonBUS qlcthdBUS = new ChiTietHoaDonBUS();
    ChiTietHoaDonNhapBUS qlcthdnBUS = new ChiTietHoaDonNhapBUS();
    NguyenLieuBUS qlnlBUS= new NguyenLieuBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    GUIMyTable tb;

    public ThongKeKhachHang() {
        this.setLayout(new BorderLayout());

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel tieu chi
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Tổng tiền", "Món ăn đã đặt"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        btnRefresh.addActionListener((ae) -> {
            try {
                qlmaBUS.docDSMonAn();
                qlcthdBUS.docCTHD();
                qlhdBUS.docHD();
                qlkhBUS.docDSKH();
            } catch (Exception ex) {
                Logger.getLogger(ThongKeKhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);

        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new GUIMyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    //Thong ke tong tien hoa don cua tung khach hang
    public void tongTienTungKhachHang_searchOnChange() {
        tb.setHeaders(new String[]{"Mã khách hàng", "Tên khách hàng", "Mã hóa đơn", "Ngày lập", "Tổng tiền hóa đơn"});
        tb.clear();

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        float tongTatCa = 0;
        for (KhachHangDTO kh : qlkhBUS.getKhachHangDTO()) {
            float tongTien = 0;
            tb.addRow(new String[]{kh.getIDKhachHang(), kh.getTenKhachHang(), "", "", ""});

            for (HoaDonDTO hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (kh.getIDKhachHang().equals(hd.getIDKhachHang())) {
                    tb.addRow(new String[]{"", "",
                        hd.getIDHoaDon(),
                        String.valueOf(hd.getNgayLap()),
                        PriceFormatter.format((float) hd.getTongTien())
                    });
                    tongTien += hd.getTongTien();
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), PriceFormatter.format(tongTien)});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongTien;
        }

        tb.addRow(new String[]{"", "", "", "Tổng thanh toán", PriceFormatter.format(tongTatCa)});
    }

    //Thong ke san pham va so luong mua cua tung khach hang
    public void MonAnCuaTungKhachHang_searchOnChange() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã khách hàng", "Tên khách hàng", "Mã hóa đơn", "Ngày lập", "Mã món ăn", "Tên món ăn", "Số lượng "});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        int tongTatCa = 0;
        //sai ở search hóa đơn bus
        for (KhachHangDTO kh : qlkhBUS.getKhachHangDTO()) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{kh.getIDKhachHang(), kh.getTenKhachHang(), "", "", "", "", ""});

            for (HoaDonDTO hd : qlhdBUS.search("Mã khách hàng", kh.getIDKhachHang(), mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) { // tương đối -> sai 
                tb.addRow(new String[]{"", "", hd.getIDHoaDon(), String.valueOf(hd.getNgayLap()), "", "", ""});

                for (ChiTietHoaDonDTO cthd : qlcthdBUS.search("Mã hóa đơn", hd.getIDHoaDon(), -1, -1, -1, -1)) { // tương đối -> sai
                    tongSoLuong += cthd.getSoLuong();
                    tb.addRow(new String[]{"", "", "", "",
                        cthd.getIDMonAn(),
                        qlmaBUS.getMonAnDTO(cthd.getIDMonAn()).getTenMonAn(),
                        String.valueOf(cthd.getSoLuong() + " "+ qlmaBUS.getMonAnDTO(cthd.getIDMonAn()).getDonViTinh())
                    });
                }
            }
             tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "Số phần món ăn", String.valueOf(tongSoLuong)+ " món ăn "});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)+ " món ăn "});
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Tổng tiền")) {
            tongTienTungKhachHang_searchOnChange();
        }
        if (cbTieuChi.getSelectedItem().equals("Món ăn đã đặt")) {
            MonAnCuaTungKhachHang_searchOnChange();
        }
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }
}

class ThongKeNhaCungCap extends JPanel {

    MonAnBUS qlmaBUS = new MonAnBUS();
    NhanVienBUS qlnvBUS = new NhanVienBUS();
    KhachHangBUS qlkhBUS = new KhachHangBUS();
    HoaDonBUS qlhdBUS = new HoaDonBUS();
    NhaCungCapBUS qlnccBUS = new NhaCungCapBUS();
    HoaDonNhapBUS qlhdnBUS = new HoaDonNhapBUS();
    ChiTietHoaDonBUS qlcthdBUS = new ChiTietHoaDonBUS();
    ChiTietHoaDonNhapBUS qlcthdnBUS = new ChiTietHoaDonNhapBUS();
    NguyenLieuBUS qlnlBUS= new NguyenLieuBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    GUIMyTable tb;
    JButton btnRefresh = new JButton("Làm mới");

    public ThongKeNhaCungCap() {
        this.setLayout(new BorderLayout());

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel tieu chi
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Số lượng nguyên liệu", "Tổng thành tiền"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        btnRefresh.addActionListener((ae) -> {
            try {
                qlmaBUS.docDSMonAn();
                qlhdnBUS.docHDN();
                qlcthdnBUS.docCTHDN();
                qlnccBUS.docDSNCC();
            } catch (Exception ex) {
                Logger.getLogger(ThongKeNhaCungCap.class.getName()).log(Level.SEVERE, null, ex);
            }
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);
        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new GUIMyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }
        
    private void tongTienThanhToan() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Mã hóa đơn nhập", "Ngày lập", "Mã nguyên liệu", "Tên nguyên liệu", "Đơn giá", "Số lượng", "Thành tiền"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        float tongTatCa = 0;
        for(NhaCungCapDTO ncc: qlnccBUS.getNhaCungCapDTO()) {
            float tongTien = 0;
            tb.addRow(new String[]{ncc.getIDNhaCungCap(), ncc.getTenNhaCungCap(), "", "", "", "", "", "", ""});
            for (HoaDonNhapDTO hdn : qlhdnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (hdn.getIDNhaCungCap().equals(ncc.getIDNhaCungCap())) {
                    tb.addRow(new String[]{"", "", hdn.getIDHoaDonNhap(), String.valueOf(hdn.getNgayNhap()), "", "", "", ""});

                    for (ChiTietHoaDonNhapDTO cthdn : qlcthdnBUS.search("Mã hóa đơn nhập", hdn.getIDHoaDonNhap())) {
                        tongTien += cthdn.getSoLuong() * cthdn.getGiaNhap();
                        tb.addRow(new String[]{"", "", "", "",
                            cthdn.getIDNguyenLieu(),
                            String.valueOf(qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getTenNguyenLieu()),
                            PriceFormatter.format(cthdn.getGiaNhap()),
                            String.valueOf(cthdn.getSoLuong()+ " " + qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getDonViTinh()),
                            PriceFormatter.format(cthdn.getSoLuong() * cthdn.getGiaNhap())});
                    }
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "", "", "", PriceFormatter.format(tongTien)});
            tongTatCa += tongTien;
        }
        tb.addRow(new String[]{"", "", "", "", "", "", "","",""});
        
        tb.addRow(new String[]{"", "", "", "", "", "", "", "Tổng tiền:", PriceFormatter.format(tongTatCa)});
    }

    private void soLuongNguyenLieuCungCap() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Mã hóa đơn nhập", "Ngày lập", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        int tongTatCa = 0;
        for(NhaCungCapDTO ncc: qlnccBUS.getNhaCungCapDTO()) {           
            int tongSoLuong = 0;
            tb.addRow(new String[]{ncc.getIDNhaCungCap(), ncc.getTenNhaCungCap(), "", "", "", "", ""});
            for (HoaDonNhapDTO hdn : qlhdnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (hdn.getIDNhaCungCap().equals(ncc.getIDNhaCungCap())) {
                    tb.addRow(new String[]{"", "", hdn.getIDHoaDonNhap(), String.valueOf(hdn.getNgayNhap()), "", "", ""});

                    for (ChiTietHoaDonNhapDTO cthdn : qlcthdnBUS.search("Mã hóa đơn nhập", hdn.getIDHoaDonNhap())) {
                        tongSoLuong += cthdn.getSoLuong();
                        tb.addRow(new String[]{"", "", "", "",
                            cthdn.getIDNguyenLieu(),
                            qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getTenNguyenLieu(),
                            String.valueOf(cthdn.getSoLuong()+ " " + qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getDonViTinh())
                        });
                    }
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "", String.valueOf(tongSoLuong)+ " nguyên liệu "});
            tongTatCa+=tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "", "", ""});
        tb.addRow(new String[]{"", "", "", "", "", "Tổng tất cả:", String.valueOf(tongTatCa)+ " nguyên liệu "});
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Số lượng nguyên liệu")) {
            soLuongNguyenLieuCungCap();
        }
        if (cbTieuChi.getSelectedItem().equals("Tổng thành tiền")) {
            tongTienThanhToan();
        }
    }
    
}


class ThongKeBieuDo extends JPanel{
    private int histogramHeight = 200;
    private int barWidth = 50;
    private int barGap = 10;

    private JPanel barPanel;
    private JPanel labelPanel;

    private List<Bar> bars = new ArrayList<Bar>();
    MonAnBUS qlmaBUS = new MonAnBUS();
    NhanVienBUS qlnvBUS = new NhanVienBUS();
    KhachHangBUS qlkhBUS = new KhachHangBUS();
    HoaDonBUS qlhdBUS = new HoaDonBUS();
    NhaCungCapBUS qlnccBUS = new NhaCungCapBUS();
    HoaDonNhapBUS qlhdnBUS = new HoaDonNhapBUS();
    ChiTietHoaDonBUS qlcthdBUS = new ChiTietHoaDonBUS();
    ChiTietHoaDonNhapBUS qlcthdnBUS = new ChiTietHoaDonNhapBUS();
    NguyenLieuBUS qlnlBUS= new NguyenLieuBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    GUIMyTable tb;

    public ThongKeBieuDo() {
        this.setLayout(new BorderLayout());

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel các nút
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        // cbTieuChi = new JComboBox(new String[]{"Nguyệu liệu nhập", "Món ăn bán ra"});
        // cbTieuChi.addActionListener((ae) -> {
        //     cbSearchOnChange();
        // });
        // plTieuchi.add(cbTieuChi);

//        panel tìm kiếm theo thời gian
        // JPanel plKhoangNgay1 = new JPanel();
        // txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        // addDocumentListener(txKhoangNgayTu);
        // plKhoangNgay1.add(txKhoangNgayTu);
        // plKhoangNgay1.add(dPicker1);
        // JPanel plKhoangNgay2 = new JPanel();
        // txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        // addDocumentListener(txKhoangNgayDen);
        // plKhoangNgay2.add(txKhoangNgayDen);
        // plKhoangNgay2.add(dPicker2);

        // btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        // btnRefresh.addActionListener((ae) -> {
        //     txKhoangNgayTu.setText("");
        //     txKhoangNgayDen.setText("");
        //     dPicker1.setDate(null);
        //     dPicker2.setDate(null);
        //     cbSearchOnChange();
        // });

        // plTieuchi.add(plKhoangNgay1);
        // plTieuchi.add(plKhoangNgay2);
        // plTieuchi.add(btnRefresh);

        // this.add(plTieuchi, BorderLayout.NORTH);

        // //Table thống kê từng cái
        // tb = new GUIMyTable();
        // cbSearchOnChange();
        // this.add(tb, BorderLayout.CENTER);
    }
    private void HistogramPanel()
    {
        setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );

        barPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        barPanel.setBorder( compound );

        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        labelPanel.setBorder( new EmptyBorder(5, 10, 0, 10) );

        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }

    public void addHistogramColumn(String label, int value, Color color)
    {
        Bar bar = new Bar(label, value, color);
        bars.add( bar );
    }

    public void layoutHistogram()
    {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;

        for (Bar bar: bars)
            maxValue = Math.max(maxValue, bar.getValue());

        for (Bar bar: bars)
        {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = (bar.getValue() * histogramHeight) / maxValue;
            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);
            label.setIcon( icon );
            barPanel.add( label );

            JLabel barLabel = new JLabel( bar.getLabel() );
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add( barLabel );
        }
    }

    private class Bar
    {
        private String label;
        private int value;
        private Color color;

        public Bar(String label, int value, Color color)
        {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        public String getLabel()
        {
            return label;
        }

        public int getValue()
        {
            return value;
        }

        public Color getColor()
        {
            return color;
        }
    }

    private class ColorIcon implements Icon
    {
        private int shadow = 3;

        private Color color;
        private int width;
        private int height;

        public ColorIcon(Color color, int width, int height)
        {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth()
        {
            return width;
        }

        public int getIconHeight()
        {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            g.setColor(color);
            g.fillRect(x, y, width - shadow, height);
            g.setColor(Color.GRAY);
            g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }

    private static void createAndShowGUI()
    {
        ThongKeBieuDo panel = new ThongKeBieuDo();
        panel.addHistogramColumn("A", 350, Color.RED);
        panel.addHistogramColumn("B", 690, Color.YELLOW);
        panel.addHistogramColumn("C", 510, Color.BLUE);
        panel.addHistogramColumn("D", 570, Color.ORANGE);
        panel.addHistogramColumn("E", 180, Color.MAGENTA);
        panel.addHistogramColumn("F", 504, Color.CYAN);
        panel.addHistogramColumn("F", 504, Color.CYAN);
        panel.addHistogramColumn("F", 504, Color.CYAN);
        panel.addHistogramColumn("F", 504, Color.CYAN);
        panel.addHistogramColumn("F", 504, Color.CYAN);
        panel.HistogramPanel();
        panel.layoutHistogram();

        JFrame frame = new JFrame("Histogram Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( panel );
        frame.setLocationByPlatform( true );
        frame.pack();
        frame.setVisible( true );
    }
    
    // public static void main(String[] args)
    // {
    //     EventQueue.invokeLater(new Runnable()
    //     {
    //         public void run()
    //         {
    //             createAndShowGUI();
    //         }
    //     });
    // }
} 




























// private void soLuongNguyenLieuCungCap() {
//     tb.clear();
//     tb.setHeaders(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Mã hóa đơn nhập", "Ngày lập", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng"});

//     MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

//     int tongTatCa = 0;
//     for(NhaCungCapDTO ncc: qlnccBUS.getNhaCungCapDTO()) {           
//         int tongSoLuong = 0;
//         tb.addRow(new String[]{ncc.getIDNhaCungCap(), ncc.getTenNhaCungCap(), "", "", "", "", ""});
//         for (HoaDonNhapDTO hdn : qlhdnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
//             if (hdn.getIDNhaCungCap().equals(ncc.getIDNhaCungCap())) {
//                 tb.addRow(new String[]{"", "", hdn.getIDHoaDonNhap(), String.valueOf(hdn.getNgayNhap()), "", "", ""});

//                 for (ChiTietHoaDonNhapDTO cthdn : qlcthdnBUS.search("Mã hóa đơn nhập", hdn.getIDHoaDonNhap())) {
//                     tongSoLuong += cthdn.getSoLuong();
//                     tb.addRow(new String[]{"", "", "", "",
//                         cthdn.getIDNguyenLieu(),
//                         qlnlBUS.getNguyenLieuDTO(cthdn.getIDNguyenLieu()).getTenNguyenLieu(),
//                         String.valueOf(cthdn.getSoLuong()+ " phần ")
//                     });
//                 }
//             }
//         }
//         tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "", String.valueOf(tongSoLuong)+ " phần "});
//         tongTatCa+=tongSoLuong;
//     }
//     tb.addRow(new String[]{"", "", "", "", "", "", ""});
//     tb.addRow(new String[]{"", "", "", "", "", "Tổng tất cả:", String.valueOf(tongTatCa)+ " phần "});
// }

// private void addDocumentListener(JTextField txField) {
//     txField.getDocument().addDocumentListener(new DocumentListener() {
//         @Override
//         public void changedUpdate(DocumentEvent e) {
//             cbSearchOnChange();
//         }

//         @Override
//         public void removeUpdate(DocumentEvent e) {
//             cbSearchOnChange();
//         }

//         @Override
//         public void insertUpdate(DocumentEvent e) {
//             cbSearchOnChange();
//         }
//     });
// }