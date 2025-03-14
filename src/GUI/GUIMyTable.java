package GUI;

import DTO.*;
import Excel.MyTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import BUS.CongThucBUS;
import BUS.KhachHangBUS;
import BUS.KhuyenMaiBUS;
import BUS.MonAnBUS;
import BUS.NguyenLieuBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.Tool;
/**
 * MyTable có nhiệm vụ add có row của các table từ database vào table trong GUI
 *
 * */
public class GUIMyTable extends JPanel {

    public JTable tb;
    public DefaultTableModel tbModel;
    public JScrollPane pane;


    // Tạo Menu cho popup menu
    // JMenuItem menuThem,menuSua, menuXoa;
    // JPopupMenu popup;


    public GUIMyTable() {
        setLayout(new BorderLayout());

        tb = new JTable();
        //https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
        tbModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
           //all cells false
           return false;
        }
        
        };
        
        pane = new JScrollPane(tb);
        // setUnitIncrement giúp thanh cuộn mượt mà hơn , số càng nhỏ càng mượt ( chậm )
        pane.getVerticalScrollBar().setUnitIncrement(8);
        
        tb.setFillsViewportHeight(true);
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.setRowHeight(40);
        
        // color
        int bgColor = 235;
        int color = 0;
        tb.getTableHeader().setBackground(new Color(bgColor, bgColor, bgColor));
        tb.getTableHeader().setForeground(new Color(color, color, color));
        tb.setBackground(new Color(bgColor, bgColor, bgColor));
        tb.setForeground(new Color(color, color, color));
        // lệnh này chặn trường hợp chọn 1 lúc nhiều row
        tb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        
        add(pane, BorderLayout.CENTER);
    }
    // hàm thiết lập header với mảng chuỗi
    public void setHeaders(String[] headers) {
        // lệnh tùy biến header theo mảng đổ vào 
        tbModel.setColumnIdentifiers(headers);
        tb.setModel(tbModel);
    }
    // hàm thiết lập header với ArrayList
    public void setHeaders(ArrayList<String> headers) {
        tbModel.setColumnIdentifiers(headers.toArray());
        tb.setModel(tbModel);
    }
    // hàm thiết lập cho dữ liệu căn giữa
    // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
    public void setAlignment(int column, int align) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(align);
        tb.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
    }

    // https://stackoverflow.com/questions/28823670/how-to-sort-jtable-in-shortest-way
    public void setupSort() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tb.getModel());
        tb.setRowSorter(sorter);

        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        for (int i = 0; i < tbModel.getColumnCount(); i++) {
            sortKeys.add(new RowSorter.SortKey(i, SortOrder.ASCENDING));
        }
        sorter.setSortKeys(sortKeys);
    }

    public void addRow(String[] data) {
        tbModel.addRow(data);
    }
    public void addRow(MonAnDTO data)   // Add row món ăn
    {
        addRow(new String[]{
                    data.getIDMonAn(),
                    data.getTenMonAn(),
                    data.getDonViTinh(),
                    String.valueOf(data.getDonGia()),
                    data.getHinhAnh(),
                    data.getLoai(),
                    String.valueOf(data.getSoLuong())
                });
    }
    public void addRow(NguyenLieuDTO data)  // Add row nguyên liệu
    {
        addRow(new String[]{
                    data.getIDNguyenLieu(),
                    data.getTenNguyenLieu(),                   
                    String.valueOf(data.getDonGia()),
                    data.getHinhAnh(),
                    data.getDonViTinh(),
                    String.valueOf(data.getSoLuong())
                });
    }
    public void addRow(KhachHangDTO data) {
        addRow(new String[]{
                    data.getIDKhachHang(),
                    data.getHoKhachHang(),
                    data.getTenKhachHang(),
                    data.getSoDienThoai(),
                    String.valueOf((float)data.getTongChiTieu())
                   
                });
    }
    public void addRow(NhanVienDTO data) {
        addRow(new String[]{
                    data.getIDNhanVien(),
                    data.getHoNhanVien(),
                    data.getTenNhanVien(),
                    data.getGmail(),
                    data.getGioiTinh(),
                    data.getSoDienThoai(),
                    data.getChucVu()
                });
    }
    public void addRow(HoaDonDTO data) {
        NhanVienBUS nvBUS = new NhanVienBUS();
        KhachHangBUS khBUS = new KhachHangBUS();
        KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();
        try {
            nvBUS.docDSNV();
            khBUS.docDSKH();
            kmBUS.docDSKM();
        } catch (Exception ex) {
        }
        String tenNV = nvBUS.getNhanVienDTO(data.getIDNhanVien()).getHoNhanVien()+ " "
        + nvBUS.getNhanVienDTO(data.getIDNhanVien()).getTenNhanVien();
        String tenKH = khBUS.getKhachHangDTO(data.getIDKhachHang()).getHoKhachHang()+ " "
        + khBUS.getKhachHangDTO(data.getIDKhachHang()).getTenKhachHang();
        String tenKM = kmBUS.getKhuyenMaiDTO(data.getIDKhuyenMai()).getTenChuongTrinh();
        addRow(new String[]{
                    data.getIDHoaDon(),
                    tenNV,
                    tenKH,
                    tenKM,
                    String.valueOf(data.getNgayLap()),
                    String.valueOf((float) data.getTienGiamGia()),
                    String.valueOf((float) data.getTongTien()),
                    String.valueOf((float) data.getTienTra()),
                    String.valueOf((float) data.getTienThoi())
                });
    }
    public void addRow(HoaDonNhapDTO data) {
        NhanVienBUS nvBUS = new NhanVienBUS();
        NhaCungCapBUS nccBUS = new NhaCungCapBUS();
        try {
            nvBUS.docDSNV();
            nccBUS.docDSNCC();
        } catch (Exception ex) {
        }
        String tenNV = nvBUS.getNhanVienDTO(data.getIDNhanVien()).getHoNhanVien()+ " "
        + nvBUS.getNhanVienDTO(data.getIDNhanVien()).getTenNhanVien();
        String tenNCC = nccBUS.getNhaCungCapDTO(data.getIDNhaCungCap()).getTenNhaCungCap();
        addRow(new String[]{
                    data.getIDHoaDonNhap(),
                    tenNV,
                    tenNCC,
                    String.valueOf(data.getNgayNhap()),
                    String.valueOf(String.format("%.1f",(float) data.getTongTien()))
                });
    }
    public void addRow(NhaCungCapDTO data) {
        addRow(new String[]{
                    data.getIDNhaCungCap(),
                    data.getTenNhaCungCap(),
                    data.getSoDienThoai(),
                    data.getGmail(),
                    data.getDiaChi()
                });
    }
    public void addRow(TaiKhoanDTO data) {
        addRow(new String[]{
                    data.getTaiKhoan(),
                    data.getIDNhanVien(),
                    data.getIDPhanQuyen(),
                    data.getMatKhau()
                });
    }
    public void addRow(PhanQuyenDTO data) {
        addRow(new String[]{
                    data.getIDPhanQuyen(),
                    data.getTenQuyen(),
                    data.getMoTaQuyen()
                });
    }
    public void addRow(KhuyenMaiDTO data) {
        addRow(new String[]{
                    data.getIDKhuyenMai(),
                    data.getTenChuongTrinh(),
                    String.valueOf((int) data.getTienGiam()),
                    String.valueOf(data.getNgayBatDau()),
                    String.valueOf(data.getNgayKetThuc()),
                    data.getNoiDungGiamGia()                    
                });
    }
    public void addRow(CongThucDTO data) {
        MonAnBUS maBUS = new MonAnBUS();
        try {
            maBUS.docDSMonAn();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu!");
        }
        String tenMonAn = maBUS.getMonAnDTO(data.getIDMonAn()).getTenMonAn();
        addRow(new String[]{
                    data.getIDCongThuc(),
                    data.getIDMonAn(),
                    tenMonAn,
                    data.getMoTaCongThuc()
                });
    }
    public void addRow(ChiTietHoaDonDTO data) {
        MonAnBUS maBUS = new MonAnBUS();
        try {
            maBUS.docDSMonAn();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
            return;
        }
        addRow(new String[]{
                    data.getIDMonAn(),
                    maBUS.getMonAnDTO(data.getIDMonAn()).getTenMonAn(),
                    String.valueOf(data.getSoLuong()),
                    maBUS.getMonAnDTO(data.getIDMonAn()).getDonViTinh(),
                    String.valueOf(data.getDonGia()),
                    String.valueOf((float) data.getThanhTien())
                });
    }
    public void addRow(ChiTietHoaDonNhapDTO data) {
        NguyenLieuBUS nlBUS = new NguyenLieuBUS();
        try {
            nlBUS.docDSNL();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
            return;
        }
        addRow(new String[]{
                    data.getIDNguyenLieu(),
                    nlBUS.getNguyenLieuDTO(data.getIDNguyenLieu()).getTenNguyenLieu(),
                    String.valueOf(data.getSoLuong()),
                    nlBUS.getNguyenLieuDTO(data.getIDNguyenLieu()).getDonViTinh(),
                    String.valueOf(data.getGiaNhap()),
                    String.valueOf(String.format("%.1f",(float) data.getThanhTien()))
                });
    }
    public void addRow(ChiTietNguyenLieuDTO data) {
        addRow(new String[]{
                    data.getIDNguyenLieu(),
                    String.valueOf(data.getSoLuong()),
                    
                });
    }
    public JTable getTable() {
        return tb;
    }

    public DefaultTableModel getModel() {
        return tbModel;
    }

    public void clear() {
        tbModel.setRowCount(0);
    }


    // Hàm thiết lập độ rộng của cột
    // https://www.codejava.net/java-se/swing/setting-column-width-and-row-height-for-jtable
    public void setColumnsWidth(double [] percentages)  // nhập vào mảng
    {

        double total = 0;
        for (int i = 0; i < tb.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < tb.getColumnModel().getColumnCount(); i++) {
            TableColumn column = tb.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (getPreferredSize().width * (percentages[i] / total))); // đổ ra tỉ lệ phầm trăm
        }
    }
    // Hàm thiết lập tự động set size cho cột
    //https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
    public void resizeColumnWidth() {
        final TableColumnModel columnModel = tb.getColumnModel();
        for (int column = 0; column < tb.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < tb.getRowCount(); row++) {
                TableCellRenderer renderer = tb.getCellRenderer(row, column);
                Component comp = tb.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            width += 15;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    // // Khởi tạo popup menu
    // public JPopupMenu createPopUp (int rowIndex, GUIMyTable Table) {

    //     menuThem = new JMenuItem("Thêm");
    //     menuThem.setIcon(new ImageIcon("src/Images/Icon/icons8_add_16px.png"));
    //     menuSua = new JMenuItem("Sửa");
    //     menuSua.setIcon(new ImageIcon("src/Images/Icon/sua3-16.png"));
    //     menuXoa = new JMenuItem("Xóa");
    //     menuXoa.setIcon(new ImageIcon("src/Images/Icon/xoa-16.png"));

    //     popup = new JPopupMenu();
    //     popup.add(menuThem);
    //     popup.addSeparator(); // Ngăn dòng
    //     popup.add(menuSua);
    //     popup.addSeparator();
    //     popup.add(menuXoa);

    //     return popup;
    // }

    // public void ShowMenu(GUIMyTable table){
    //     table.getTable().addMouseListener(new MouseAdapter() {
    //         @Override
    //         public void mouseReleased(MouseEvent me) {
    //             int r = table.getTable().rowAtPoint (me.getPoint ());
    //             if (r >= 0 && r < table.getTable().getRowCount()) {
    //                 table.getTable().setRowSelectionInterval (r, r);
    //             } else  {
    //                 table.getTable().clearSelection ();
    //             }

    //             int rowIndex = table.getTable().getSelectedRow ();
    //             if (rowIndex <0)
    //                 return;
    //             if (me.isPopupTrigger () && me.getComponent () instanceof JTable) {
    //                 JPopupMenu popup = createPopUp (rowIndex, table);
    //                 popup.show (me.getComponent (), me.getX (), me.getY ());
    //             }
    //         }
    //     });
    // }



}




















































