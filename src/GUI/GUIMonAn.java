/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.ChiTietNguyenLieuBUS;
import BUS.CongThucBUS;
import BUS.MonAnBUS;
import BUS.NguyenLieuBUS;
import BUS.Tool;
import DAO.MonAnDAO;
import DTO.ChiTietNguyenLieuDTO;
import DTO.CongThucDTO;
import DTO.MonAnDTO;
import DTO.NguyenLieuDTO;
import Excel.DocExcel;
import Excel.XuatExcel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Nguyen
 */
//Kế thừa từ 1 mẫu bố cục là GUIFormContent
public class GUIMonAn extends GUIFormContent {
    //Panel chứa phần món ăn và phần công thức
    private JPanel pThem_MonAn, pThem_CongThuc;
    //Nút lấy tên ảnh
    private JButton btnFileAnh;
    //Tạo mảng tiêu đề
    public static String array_MonAn[] = {"Mã", "Tên món", "Đơn vị tính", "Giá", "Hình ảnh", "Loại"};
    //Tạo JTable , GUIMyTable kế thừa từ JTable và được chỉnh sửa
    private GUIMyTable table_MonAn;
    //Panel chứa phần show thông tin món ăn
    private JPanel Show;
    //Tạo Dialog để thêm món ăn
    private static JDialog Them_MonAn;
    //Tạo Dialog để sửa món ăn
    private static JDialog Sua;
    //Phần nhãn bên trong Dialog thêm sửa 
    private JLabel label_MonAn[] = new JLabel[array_MonAn.length];
    //Phần textfield của thêm
    private JTextField txt_MonAn_Them[] = new JTextField[array_MonAn.length];
    //Phần textfield của sửa
    private JTextField txt_MonAn_Sua[] = new JTextField[array_MonAn.length];
    //Tạo nhãn chứa hình ảnh
    private JLabel lbImage;
    //field thông tin khi click row
    private JTextField txMaMA, txTenMA, txDonGia;
    //Các textfield trong thanh tìm kiếm
    public JTextField Ten, Tu_DonGia, Den_DonGia;
//    public JTextField Tu_SoLuong, Den_SoLuong;
    //Tạo đối tượng cho BUS
    MonAnBUS BUS = new MonAnBUS();
    //Tạo cờ hiệu cho việc các Dialog có được tắt đúng cách hay không
    private int cohieu = 0;
    private JComboBox cbDonViTinh_Them,cbDonViTinh_Sua;
    private String array_DonViTinh[]={"Dĩa","Phần","Ly"};
    private JComboBox cbLoai_Them,cbLoai_Sua;
    private String array_Loai[]={"Món chính","Món phụ","Nước uống"};

    // Tạo Menu cho popup menu
    JMenuItem menuThem,menuSua, menuXoa;
    JPopupMenu popup;
 
    //Tạo các đối tượng cho công thức
    public static String array_CongThuc[]={"Mã công thức","Mô tả công thức"};
    //Tạo JTable , GUIMyTable kế thừa từ JTable và được chỉnh sửa
    public GUIMyTable table_CongThuc;
    //Tạo Dialog để thêm công thức
    private static JDialog Them_CongThuc;
    //Phần nhãn bên trong Dialog thêm sửa 
    private JLabel label_CongThuc[] = new JLabel[array_CongThuc.length];
    //Phần textfield của thêm
    private JTextField txt_CongThuc_Them[] = new JTextField[array_CongThuc.length];
    //Phần textfield của thêm nguyên liệu
    private ArrayList<JTextField[]> txt_NguyenLieu_Them;
    //Panel nguyên liệu
    private JPanel pNguyenLieuThem;
    //Tạo sẵn đối tượng BUS
    private CongThucBUS ctBUS = new CongThucBUS();
    
    private JButton ThemMonAn;

    private String maNL[];
    private float slNL[];


    public GUIMonAn() {
        super();
    }

    @Override
    //Tạo Panel chưa Table
    protected JPanel Table() {
        JPanel panel = new JPanel(null);
        //Tạo đối tượng cho table_MonAn
        table_MonAn = new GUIMyTable();
        //Tạo tiêu đề bảng
        table_MonAn.setHeaders(array_MonAn);
        //Hàm đọc database
        docDB();
        //Set kích thước và vị trí
        table_MonAn.pane.setPreferredSize(new Dimension(GUImenu.width_content * 90 / 100, 300));
        table_MonAn.tb.getColumnModel().getColumn(4).setMinWidth(0); // Ẩn cột hình ảnh
        table_MonAn.tb.getColumnModel().getColumn(4).setMaxWidth(0);
        table_MonAn.tb.getColumnModel().getColumn(4).setWidth(0);
        table_MonAn.setBounds(0, 0, GUImenu.width_content, 300);
        panel.add(table_MonAn);

        //Tạo phần show thông tin
        Show = Show();
        Show.setBounds(0, 300, GUImenu.width_content, 300);
        panel.add(Show);

        // Show menu
        ShowMenu(table_MonAn);

        return panel;
    }

    //Hàm tạo Dialog thêm món ăn
    private void Them_Frame() {
        JFrame f = new JFrame();
        //Để cờ hiệu với giá trị 0 với ý nghĩa không được bấm ra khỏi Dialog trừ nút Thoát
        cohieu = 0;
        Them_MonAn = new JDialog(f);
        Them_MonAn.setLayout(null);
        Them_MonAn.setSize(1000, 500);
        //Set vị trí của Dialog
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Them_MonAn.setLocationRelativeTo(null);
        //Tắt thanh công cụ mặc định
        Them_MonAn.setUndecorated(true);

        
        //Tạo tiêu đề và set hình thức

        JLabel Title = new JLabel("Thêm món ăn");
        Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
        Title.setForeground(Color.decode("#FF4081"));
        Title.setBounds(450, 0, 200, 40);
        Them_MonAn.add(Title);
        //Tạo panel chứa phần món ăn
        pThem_MonAn = new JPanel();
        pThem_MonAn.setLayout(null);
        pThem_MonAn.setBounds(0, 50, 500, 340);
        int y = 0;
        //Tạo tự động các label và textfield
        for (int i = 0; i < array_MonAn.length; i++) {
            label_MonAn[i] = new JLabel(array_MonAn[i]);
            label_MonAn[i].setBounds(100, y, 100, 30);
            pThem_MonAn.add(label_MonAn[i]);
            //Tạo combobox
            if(i==2)
            {
                cbDonViTinh_Them=new JComboBox(array_DonViTinh);
                cbDonViTinh_Them.setBounds(200, y, 150, 30);
                pThem_MonAn.add(cbDonViTinh_Them);
                y+=40;
                continue;
            }
            if(i==5)
            {
                cbLoai_Them=new JComboBox(array_Loai);
                cbLoai_Them.setBounds(200, y, 150, 30);
                pThem_MonAn.add(cbLoai_Them);
                y+=40;
                continue;
            }
            txt_MonAn_Them[i] = new JTextField();
            txt_MonAn_Them[i].setBounds(200, y, 150, 30);
            //Tạo nút để lấy tên ảnh 
            if (i == 4) {
                btnFileAnh = new JButton();
                btnFileAnh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/hinhanh-30.png")));
                btnFileAnh.addActionListener((ae) -> {
                    btnFileAnh_Click("Thêm");
                });
                btnFileAnh.setBounds(360, y, 40, 40);
                pThem_MonAn.add(btnFileAnh);
            }
            y += 40;
            pThem_MonAn.add(txt_MonAn_Them[i]);
        }
        
        txt_MonAn_Them[3].setText("0");
        txt_MonAn_Them[3].setEditable(false); // không cho người dùng nhập giá
        txt_MonAn_Them[4].setEditable(false); // không cho người dùng nhập hình ảnh
        Them_MonAn.add(pThem_MonAn);


        //Phần công thức
        pThem_CongThuc = new JPanel();
        pThem_CongThuc.setLayout(null);
        pThem_CongThuc.setBounds(500, 50, 500, 340);
        int yct = 0;
        //Set vị trí của Dialog
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        //Tắt thanh công cụ mặc định
        //Tạo tiêu đề và set hình thức
        //Tạo tự động các label và textfield
        for (int i = 0; i < array_CongThuc.length; i++) {
            label_CongThuc[i] = new JLabel(array_CongThuc[i]);
            label_CongThuc[i].setBounds(100, yct, 100, 30);
            pThem_CongThuc.add(label_CongThuc[i]);

            txt_CongThuc_Them[i]=new JTextField();
            txt_CongThuc_Them[i].setBounds(200, yct, 150, 30);
            
            yct += 40;
            pThem_CongThuc.add(txt_CongThuc_Them[i]);
        }
        txt_CongThuc_Them[1].setVisible(false);
        label_CongThuc[1].setVisible(false);
        yct -= 40;

        txt_NguyenLieu_Them = new ArrayList<>();
        //Panel chứa nguyên liệu
        pNguyenLieuThem = new JPanel();
        pNguyenLieuThem.setLayout(new BoxLayout(pNguyenLieuThem, BoxLayout.Y_AXIS));
//        pNguyenLieuThem.setBounds(100, y, 300, 200);y += 220;
//        pNguyenLieuThem.setBackground(Color.white);
        
        //Tiêu đề thêm chi tiết
        JLabel ctnl = new JLabel("Chi tiết nguyên liệu");
        ctnl.setBounds(100, yct, 150, 40);yct+=40;
        pThem_CongThuc.add(ctnl);
        addNewRow(pNguyenLieuThem);
         // Đặt panel vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(pNguyenLieuThem);
        scrollPane.setBounds(50, yct, 400, 200);yct += 210;
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.white);
        pThem_CongThuc.add(scrollPane);

        Them_MonAn.add(pThem_CongThuc);
        
        //Tạo nút Thêm chi tiết
        JButton themNL = new JButton("Thêm nguyên liệu");
        themNL.setBackground(Color.decode("#006600"));
        themNL.setForeground(Color.white);
        themNL.setBounds(100, yct, 150, 30);yct += 120;
        //Sự kiện khi click nút thêm
        themNL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData(false);
                addNewRow(pNguyenLieuThem);
            }
        });

        pThem_CongThuc.add(themNL);
        //Tạo nút lưu
        JButton Luu = new JButton("Lưu");
        Luu.setBackground(Color.decode("#90CAF9"));
        Luu.setBounds(350, yct, 100, 50);
        //Sự kiện khi click
        Luu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cohieu=1;
                int a = JOptionPane.showConfirmDialog(Them_MonAn, "Bạn chắc chứ ?", "", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION && saveData(false)) {
                    txt_MonAn_Them[3].setText(String.valueOf(TinhTien()));
                    //Kiểm tra luồng dữ liệu 
                    if (checkTextThem(txt_MonAn_Them[0].getText(),
                            txt_MonAn_Them[1].getText(),
                            cbDonViTinh_Them.getSelectedItem().toString(),
                            txt_MonAn_Them[3].getText(),
                            txt_MonAn_Them[4].getText(),
                            cbLoai_Them.getSelectedItem().toString()) &&
                            checkTextThem(txt_MonAn_Them[0].getText(),txt_CongThuc_Them[1].getText())
                            ) {
                        //Tạo đối tượng và truyền dữ liệu trực tiếp vào 
                        MonAnDTO DTO = new MonAnDTO(txt_MonAn_Them[0].getText(),
                                txt_MonAn_Them[1].getText(),
                                cbDonViTinh_Them.getSelectedItem().toString(),
                                Float.parseFloat(txt_MonAn_Them[3].getText()),
                                txt_MonAn_Them[4].getText(),
                                cbLoai_Them.getSelectedItem().toString(),
                                "Hiện");
                        //Gọi hàm thêm ở bus và truyền đối tượng DTO vào
                        BUS.them(DTO);
                        //Thêm vào table
                        table_MonAn.addRow(DTO);
                    
                        NguyenLieuBUS nlBUS = new NguyenLieuBUS();
                        try{
                            nlBUS.docDSNL();
                        } catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                            return;
                        }
                        String txtMota = "";
                        for(int i = 0; i<txt_NguyenLieu_Them.size(); i++){
                            NguyenLieuDTO nlDTO = nlBUS.getNguyenLieuDTO(txt_NguyenLieu_Them.get(i)[0].getText());
                            txtMota += String.valueOf(slNL[i]) +" ";
                            txtMota += nlDTO.getDonViTinh() + " ";
                            txtMota += nlDTO.getTenNguyenLieu();
                            if(i != maNL.length-1)
                                txtMota += ", ";
                        }
                        txt_CongThuc_Them[1].setText(txtMota);
                        CongThucDTO ctDTO = new CongThucDTO(txt_CongThuc_Them[0].getText(),
                                                    txt_MonAn_Them[0].getText(),
                                                    txt_CongThuc_Them[1].getText(),
                                                    "Hiện");
                        ChiTietNguyenLieuBUS ctnlBUS = new ChiTietNguyenLieuBUS();
                        try{
                            ctnlBUS.docCTNL();
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                            return;
                        }
                        ctBUS.them(ctDTO); //thêm công thức bên BUS đã có thêm vào database
                        for(int i=0; i<maNL.length; i++){
                            ctnlBUS.them(new ChiTietNguyenLieuDTO(txt_CongThuc_Them[0].getText(),maNL[i],slNL[i]));
                        }                
                        //clear textfield trong Them
                        for(int i=0;i<array_CongThuc.length;i++)
                        {
                            txt_CongThuc_Them[i].setText("");
                        }
                        
                        //clear textfield trong Them_frame
                        clearThem_Frame();
                        
                        //Lệnh này để đóng dialog
                        Them_MonAn.dispose();
                    
                    }
                }
                else
                    cohieu=0;
            }
        });
        Them_MonAn.add(Luu);
        //Tạo nút thoát
        JButton Thoat = new JButton("Thoát");
        Thoat.setBackground(Color.decode("#90CAF9"));
        Thoat.setBounds(550, yct, 100, 50);
        //Sự kiên khi click lưu
        Thoat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //Clear textfield
                clearThem_Frame();
                //Tắt cờ hiệu đi 
                cohieu = 1;
                //Lệnh này để đóng dialog
                Them_MonAn.dispose();
            }
        });

        Them_MonAn.add(Thoat);
        //Chặn việc thao tác ngoài khi chưa tắt dialog gây lỗi phát sinh
        Them_MonAn.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                if (cohieu == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng tắt Dialog khi muốn làm thao tác khác");
                }
            }

        });

        String maMonAn = Tool.tangMa(MonAnBUS.getMaMonAnCuoi()); //lấy mã tự động
        txt_MonAn_Them[0].setEditable(false);
        txt_MonAn_Them[0].setText(maMonAn); //set mã lên textfield
        Them_MonAn.setVisible(true);

        try{
            ctBUS.docCT();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
            return;
        }
        String maCongThuc = Tool.tangMa(ctBUS.getMaCongThucCuoi());
        txt_CongThuc_Them[0].setText(maCongThuc);
        txt_CongThuc_Them[0].setEditable(false);

    }
    //Thêm hàng ghi nguyên liệu
    private void addNewRow(JPanel panel) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField maField = new JTextField(5);
        JTextField soLuongField = new JTextField(5);
        JButton deleteButton = new JButton("Xóa");
        JButton ChonNguyenLieu = new JButton();
        
        ChonNguyenLieu.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        ChonNguyenLieu.setSize(40,40);
        
        maField.setEditable(false);
        ChonNguyenLieu.addActionListener((ae) -> {
            cohieu = 1;
            GUIFormChon a = null;
            try {
                a = new GUIFormChon(maField,"Nguyên liệu");
            } catch (Exception ex) {
                Logger.getLogger(GUIBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            a.setVisible(true);
        });
        // Thêm DocumentListener để kiểm tra khi nội dung thay đổi
        maField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    for (int i = 0; i < txt_NguyenLieu_Them.size(); i++) {
                        if (maField.getText().equals(txt_NguyenLieu_Them.get(i)[0].getText()) && maField != txt_NguyenLieu_Them.get(i)[0] ) {
                            maField.setText("");  // ✅ Đặt text trống một cách an toàn
                            JOptionPane.showMessageDialog(null, "Nguyên liệu đã tồn tại");
                            break;
                        }
                    }
                });
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });
        soLuongField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    txt_MonAn_Them[3].setText(String.valueOf(TinhTien()));
                });
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });

        deleteButton.addActionListener(e -> {
            panel.remove(rowPanel);
            for (int i = 0; i < txt_NguyenLieu_Them.size(); i++) {
                JTextField[] fields = txt_NguyenLieu_Them.get(i);
                if (fields[0] == maField && fields[1] == soLuongField) {
                    txt_NguyenLieu_Them.remove(i);
                    break;
                }
            }
            txt_MonAn_Them[3].setText(String.valueOf(TinhTien()));
            panel.revalidate();
            panel.repaint();
            saveData(true);
        });

        rowPanel.add(new JLabel("Mã:"));
        rowPanel.add(maField);
        rowPanel.add(ChonNguyenLieu);
        rowPanel.add(new JLabel("Số lượng:"));
        rowPanel.add(soLuongField);
        rowPanel.add(deleteButton);

        panel.add(rowPanel, panel.getComponentCount());
        txt_NguyenLieu_Them.add(new JTextField[]{maField, soLuongField}); // Lưu vào danh sách

        panel.revalidate();
        panel.repaint();
    }
    private boolean saveData(boolean isDelete) {
        int size = txt_NguyenLieu_Them.size(); // Số lượng nguyên liệu hiện tại
        maNL = new String[size];
        slNL = new float[size];

        for (int j = 0; j < size; j++) {
            JTextField[] fields = txt_NguyenLieu_Them.get(j);
            if(isDelete){
                
            }
            else{
                if(fields[0].getText()== null || fields[0].getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ mã nguyên liệu");
                    return false;
                }
                if(fields[1].getText() == null || fields[1].getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ số lượng nguyên liệu");
                    return false;
                }
                try{
                    slNL[j] = Float.parseFloat(fields[1].getText());  // Lấy số lượng
                }catch(NumberFormatException ne){
                    JOptionPane.showMessageDialog(null,"Số lượng không đúng định dạng");
                    return false;
                }
            }
            if(fields[0].getText()!= null && !fields[0].getText().isEmpty())
                maNL[j] = fields[0].getText();  // Lấy mã nguyên liệu
            if(fields[1].getText() != null && !fields[1].getText().isEmpty())
                slNL[j] = Float.parseFloat(fields[1].getText());  // Lấy số lượng
        }
        return true;

}
    //Hàm tính giá tiền món ăn
    private float TinhTien(){
        float price = 0;
        saveData(false);
        if(txt_NguyenLieu_Them.size()>0){
            for(int i=0; i<txt_NguyenLieu_Them.size();i++){
                NguyenLieuBUS nlBUS = new NguyenLieuBUS();
                try{
                    nlBUS.docDSNL();
                    NguyenLieuDTO nlDTO = nlBUS.getNguyenLieuDTO(txt_NguyenLieu_Them.get(i)[0].getText().toString());
                    if(nlDTO!=null){
                        price += Float.parseFloat(txt_NguyenLieu_Them.get(i)[1].getText()) * nlDTO.getDonGia();
                    }
                    else return 0;
                } catch(NumberFormatException ne){
                    return 0;
                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                    return 0;
                }
            }
        }
        return price;
    }
    //Hàm tạo Dialog sửa món ăn
    private void Sua_Frame() {
        JFrame f = new JFrame();
        //Để cờ hiệu với giá trị 0 với ý nghĩa không được bấm ra khỏi Dialog trừ nút Thoát
        cohieu = 0;
        Sua = new JDialog(f);
        Sua.setLayout(null);
        Sua.setSize(500, 500);
        //Set vị trí của Dialog
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Sua.setLocationRelativeTo(null);
        Sua.setUndecorated(true);
        //Tạo tiêu đề
        JLabel Title = new JLabel("Sửa món ăn");
        Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
        Title.setForeground(Color.decode("#FF4081"));
        Title.setBounds(150, 0, 200, 40);
        Sua.add(Title);
        int y = 50;
        //Tạo tự động các lable và textfield
        for (int i = 0; i < array_MonAn.length; i++) {
            label_MonAn[i] = new JLabel(array_MonAn[i]);
            label_MonAn[i].setBounds(100, y, 100, 30);
            Sua.add(label_MonAn[i]);
            //Tạo combobox
            if(i==2)
            {
                cbDonViTinh_Sua=new JComboBox(array_DonViTinh);
                cbDonViTinh_Sua.setBounds(200, y, 150, 30);
                Sua.add(cbDonViTinh_Sua);
                y+=40;
                continue;
            }
            //Tạo combobox
            if(i==5)
            {
                cbLoai_Sua=new JComboBox(array_Loai);
                cbLoai_Sua.setBounds(200, y, 150, 30);
                Sua.add(cbLoai_Sua);
                y+=40;
                continue;
            }
            txt_MonAn_Sua[i] = new JTextField();
            txt_MonAn_Sua[i].setBounds(200, y, 150, 30);
            //Tạo nút để lấy file ảnh
            if (i == 4) {
                btnFileAnh = new JButton();
                btnFileAnh.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/hinhanh-30.png")));
                btnFileAnh.addActionListener((ae) -> {
                    btnFileAnh_Click("Sửa");
                });
                btnFileAnh.setBounds(360, y, 40, 40);
                Sua.add(btnFileAnh);
            }

            y += 40;
            Sua.add(txt_MonAn_Sua[i]);
        }
        
        txt_MonAn_Sua[3].setEditable(false); // không cho người dùng nhập giá
        txt_MonAn_Sua[4].setEditable(false); // không cho người dùng nhập hình ảnh
        //Lưu tất cả dữ liệu trên textfield lên database
        JButton Luu = new JButton("Lưu");
        Luu.setBackground(Color.decode("#90CAF9"));
        Luu.setBounds(100, y, 100, 50);
        Luu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu = 1;
                int a = JOptionPane.showConfirmDialog(Sua, "Bạn chắc chứ ?", "", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    //Chạy hàm checkText để ràng buộc dữ liệu 
                    if (checkTextSua(txt_MonAn_Sua[0].getText(),
                            txt_MonAn_Sua[1].getText(),
                            cbDonViTinh_Sua.getSelectedItem().toString(),
                            txt_MonAn_Sua[3].getText(),
                            txt_MonAn_Sua[4].getText(),
                            cbLoai_Sua.getSelectedItem().toString())) {
                        //Chạy hàm để lưu lại việc sửa dữ liệu    
                        buttonLuu_Sua();
                        
                        //Lệnh này để tắt dialog
                        Sua.dispose();
                    }
                }
                else
                    cohieu=0;
            }
        });
        Sua.add(Luu);

        JButton Thoat = new JButton("Thoát");
        Thoat.setBackground(Color.decode("#90CAF9"));
        Thoat.setBounds(250, y, 100, 50);
        Thoat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu = 1;
                //Lệnh này để tắt dialog
                Sua.dispose();
            }
        });

        Sua.add(Thoat);
        //Chặn việc thao tác ngoài khi chưa tắt dialog gây lỗi phát sinh
        Sua.addWindowListener(new WindowAdapter() {

            @Override
            public void windowDeactivated(WindowEvent e) {
                if (cohieu == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng tắt Dialog khi muốn làm thao tác khác");
                }
            }

        });

        Sua.setVisible(true);

    }

    //Viết đè hàm tìm kiếm
    @Override
    protected JPanel TimKiem() {
        JPanel TimKiem = new JPanel(null);

        JLabel lbTen = new JLabel("");
        lbTen.setBorder(new TitledBorder("Tìm kiếm"));
        //Tìm kiếm theo tên
        Ten = new JTextField();
        Ten.setBorder(new TitledBorder("Tên"));
        Ten.setBounds(5, 20, 200, 40);
        lbTen.add(Ten);
        //Gọi sự kiện khi tìm kiếm với Ten
        addDocumentListener(Ten);
        lbTen.setBounds(300, 0, 215, 70);
        TimKiem.add(lbTen);

        //Tìm kiếm theo đơn giá
        JLabel DonGia = new JLabel("");
        DonGia.setBorder(new TitledBorder("Đơn giá"));

        Tu_DonGia = new JTextField();
        Tu_DonGia.setBorder(new TitledBorder("Từ"));
        Tu_DonGia.setBounds(5, 20, 100, 40);
        DonGia.add(Tu_DonGia);
        //Gọi sự kiện khi tìm kiếm với Ten Tu_DonGia
        addDocumentListener(Tu_DonGia);

        Den_DonGia = new JTextField();
        Den_DonGia.setBorder(new TitledBorder("Đến"));
        Den_DonGia.setBounds(105, 20, 100, 40);
        DonGia.add(Den_DonGia);
        //Gọi sự kiện khi tìm kiếm với Den_DonGia
        addDocumentListener(Den_DonGia);

        DonGia.setBounds(520, 0, 215, 70);
        TimKiem.add(DonGia);

        //Tìm kiếm theo số lượng
//        JLabel SoLuong = new JLabel("");
//        SoLuong.setBorder(new TitledBorder("Số lượng"));
        
          // check so luong de tim kiem thuc an

//        Tu_SoLuong = new JTextField();
//        Tu_SoLuong.setBorder(new TitledBorder("Từ"));
//        Tu_SoLuong.setBounds(5, 20, 100, 40);
//        SoLuong.add(Tu_SoLuong);
//        //Gọi sự kiện khi tìm kiếm với Tu_SoLuong
//        addDocumentListener(Tu_SoLuong);
//
//        Den_SoLuong = new JTextField();
//        Den_SoLuong.setBorder(new TitledBorder("Đến"));
//        Den_SoLuong.setBounds(105, 20, 100, 40);
//        SoLuong.add(Den_SoLuong);
//        //Gọi sự kiện khi tìm kiếm với Den_SoLuong
//        addDocumentListener(Den_SoLuong);

//        SoLuong.setBounds(740, 0, 215, 70);
//        TimKiem.add(SoLuong);
        //Nút làm mới
        JButton LamMoi = new JButton("Làm mới");
        LamMoi.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        LamMoi.setFont(new Font("Segoe UI", 0, 14));
        LamMoi.setBorder(BorderFactory.createLineBorder(Color.decode("#BDBDBD"), 1));
        LamMoi.setBackground(Color.decode("#90CAF9"));
        LamMoi.setBounds(965, 10, 110, 30);
        LamMoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //clear hết các text tìm kiếm
                Ten.setText("");
                Tu_DonGia.setText("");
                Den_DonGia.setText("");
//                Tu_SoLuong.setText("");
//                Den_SoLuong.setText("");
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

    public void docDB() {
        //Nếu dsMonAn vẫn chưa được đọc thì chạy hàm đọc
        if (MonAnBUS.dsMonAn == null) {
            try {
                BUS.docDSMonAn();
            } catch (Exception ex) {
                Logger.getLogger(GUIMonAn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Chỉ hiện những món ăn ở trạng thái hiện , trạng thái ẩn là khi xóa
        for (MonAnDTO monAnDTO : MonAnBUS.dsMonAn) {
            if (monAnDTO.getTrangThai().equals("Hiện")) {
                table_MonAn.addRow(monAnDTO);

            }
        }
    }

    @Override
    protected void Them_click(MouseEvent evt) {
        Them_Frame();
    }

    //Ràng buộc dữ liệu
    //Thứ tự truyền vào lần lượt trùng với các thứ tự ô text
    public boolean checkTextThem(String MaMonAn, String TenMonAn, String DonViTinh, String DonGia, String HinhAnh, String Loai) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (MaMonAn.equals("")
                || TenMonAn.equals("")
                || DonViTinh.equals("")
                || HinhAnh.equals("")
                || Loai.equals("")
                ) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        } else if( Float.parseFloat(DonGia) ==0){
            JOptionPane.showMessageDialog(null, "Phải có ít nhất 1 nguyên liệu");
        } else if (!Tool.isName(Tool.removeAccent(TenMonAn))) {
            JOptionPane.showMessageDialog(null, "Tên món ăn không được chứa ký tự đặc biệt");
            txt_MonAn_Them[1].requestFocus();
        } else if (!Tool.isLength50(TenMonAn)) {
            JOptionPane.showMessageDialog(null, "Tên món ăn không được quá 50 ký tự");
            txt_MonAn_Them[1].requestFocus();
        }
        
        else if (!Tool.isName(Tool.removeAccent(DonViTinh))) {
            JOptionPane.showMessageDialog(null, "Đơn vị tính không được chứa ký tự đặc biệt");
            txt_MonAn_Them[2].requestFocus();
        } else if (!Tool.isLength50(DonViTinh)) {
            JOptionPane.showMessageDialog(null, "Đơn vị tính không được quá 50 ký tự");
            txt_MonAn_Them[2].requestFocus();
        }
        
        else if (!Tool.isNumber(DonGia)) {
            JOptionPane.showMessageDialog(null, "Đơn giá phải là số nguyên dương");
            txt_MonAn_Them[3].requestFocus();
        }
        
        else if (!Tool.isHinhAnh(HinhAnh)) {
            JOptionPane.showMessageDialog(null, "Hình ảnh phải được định dạng là : *.jpg hoặc *.png ");
            txt_MonAn_Them[4].requestFocus();
        }
        
        else if (!Tool.isName(Tool.removeAccent(Loai))) {
            JOptionPane.showMessageDialog(null, "Loại không được chứa ký tự đặc biệt");
            txt_MonAn_Them[5].requestFocus();
        } else if (!Tool.isLength50(Loai)) {
            JOptionPane.showMessageDialog(null, "Loại không được quá 50 ký tự");
            txt_MonAn_Them[5].requestFocus();
        } else {
            return true;

        }
        return false;
    }
    //Check text cua cong thuc
    public boolean checkTextThem(String MaMonAn, String moTaCongThuc) {
//         UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
//         if (MaMonAn.equals("")
//                 || moTaCongThuc.equals("")) {
//             JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
//         }
// //        else if(!MonAnBUS.timMaMonAn(MaMonAn)) {
// //            JOptionPane.showMessageDialog(null, "Mã món ăn không tồn tại");
// //            txt_CongThuc_Them[1].requestFocus();
// //        }
//          else {
            return true;

        // }
        // return false;
    }
    
    public boolean checkTextSua(String MaMonAn, String TenMonAn, String DonViTinh, String DonGia, String HinhAnh, String Loai) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (MaMonAn.equals("")
                || TenMonAn.equals("")
                || DonViTinh.equals("")
                || HinhAnh.equals("")
                || Loai.equals("") ) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        } else if( Float.parseFloat(DonGia) ==0){
            JOptionPane.showMessageDialog(null, "Phải có ít nhất 1 nguyên liệu");
        } else if (!Tool.isName(Tool.removeAccent(TenMonAn))) {
            JOptionPane.showMessageDialog(null, "Tên món ăn không được chứa ký tự đặc biệt");
            txt_MonAn_Sua[1].requestFocus();
        } else if (!Tool.isLength50(TenMonAn)) {
            JOptionPane.showMessageDialog(null, "Tên món ăn không được quá 50 ký tự");
            txt_MonAn_Sua[1].requestFocus();
        }
        
        else if (!Tool.isName(Tool.removeAccent(DonViTinh))) {
            JOptionPane.showMessageDialog(null, "Đơn vị tính không được chứa ký tự đặc biệt");
            txt_MonAn_Sua[2].requestFocus();
        } else if (!Tool.isLength50(DonViTinh)) {
            JOptionPane.showMessageDialog(null, "Đơn vị tính không được quá 50 ký tự");
            txt_MonAn_Sua[2].requestFocus();
        }
        
        else if (!Tool.isNumber(DonGia)) {
            JOptionPane.showMessageDialog(null, "Đơn giá phải là số nguyên dương");
            txt_MonAn_Sua[3].requestFocus();
        } else if (!Tool.isName((DonGia))) {
            JOptionPane.showMessageDialog(null, "Đơn giá không được chứa ký tự đặc biệt");
            txt_MonAn_Sua[3].requestFocus();
        } else if (!Tool.isTenThousandToOneMil(DonGia)) {
            JOptionPane.showMessageDialog(null, "Đơn giá phải nằm trong khoảng 10.000 đến 1.000.000");
            txt_MonAn_Sua[3].requestFocus();
        }
        
        else if (!Tool.isHinhAnh(HinhAnh)) {
            JOptionPane.showMessageDialog(null, "Hình ảnh phải được định dạng là : *.jpg hoặc *.png ");
            txt_MonAn_Sua[4].requestFocus();
        }
        
        else if (!Tool.isName(Tool.removeAccent(Loai))) {
            JOptionPane.showMessageDialog(null, "Loại không được chứa ký tự đặc biệt");
            txt_MonAn_Sua[5].requestFocus();
        } else if (!Tool.isLength50(Loai)) {
            JOptionPane.showMessageDialog(null, "Loại không được quá 50 ký tự");
            txt_MonAn_Sua[5].requestFocus();
        } else {
            return true;

        }
        return false;
    }

    //Hàm lưu dữ liệu khi sửa
    public void buttonLuu_Sua() {
        int row = table_MonAn.tb.getSelectedRow();
        int colum = table_MonAn.tb.getSelectedColumn();
        String maMonAn = table_MonAn.tbModel.getValueAt(row, colum).toString();
            //Sửa dữ liệu trên bảng
            //model là ruột JTable   
            //set tự động giá trị cho model
            for (int j = 0; j < array_MonAn.length; j++) {
                if(j!=2&&j!=5)
                    table_MonAn.tbModel.setValueAt(txt_MonAn_Sua[j].getText(), row, j);
                else if(j==2)
                    table_MonAn.tbModel.setValueAt(cbDonViTinh_Sua.getSelectedItem().toString(), row, j);
                else if(j==5)
                    table_MonAn.tbModel.setValueAt(cbLoai_Sua.getSelectedItem().toString(), row, j);
            }

            table_MonAn.tb.setModel(table_MonAn.tbModel);

            //Sửa dữ liệu trong database và arraylist trên bus
            //Tạo đối tượng monAnDTO và truyền dữ liệu trực tiếp thông qua constructor
            MonAnDTO DTO = new MonAnDTO(txt_MonAn_Sua[0].getText(),
                    txt_MonAn_Sua[1].getText(),
                    cbDonViTinh_Sua.getSelectedItem().toString(),
                    Integer.parseInt(txt_MonAn_Sua[3].getText()),
                    txt_MonAn_Sua[4].getText(),
                    cbLoai_Sua.getSelectedItem().toString() );
            //Tìm vị trí của row cần sửa
            int index = MonAnBUS.timViTri(DTO.getIDMonAn());
            //Truyền dữ liệu và vị trí vào bus
            BUS.sua(DTO, index);

        // MonAnBUS bus=new MonAnBUS();
        //  for(MonAnDTO DTO:MonAnBUS.dsMonAn)
        //  {
        //      if(ctHD.getIDMonAn().equals(DTO.getIDMonAn()))
        //      {
        //          int i=MonAnBUS.timViTri(DTO.getIDMonAn());
        //          DTO.setSoLuong(DTO.getSoLuong()-ctHD.getSoLuong());
        //          MonAnBUS.dsMonAn.set(i, DTO);
        //          bus.sua(DTO, i);
        //          return;
        //      }
        //  }
    }

    //Clear textfield
    public void clearThem_Frame() {
        for (int i = 0; i < GUIMonAn.array_MonAn.length; i++) {
            if(i!=2&&i!=5)
            txt_MonAn_Them[i].setText("");
        }
    }

    //Hàm sự kiện khi click vào nút Sửa
    @Override
    protected void Sua_click(MouseEvent evt) {

        int i = table_MonAn.tb.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để sửa");
        } else {
            //Hiện Dialog lên và set dữ liệu vào các field
            Sua_Frame();
            txt_MonAn_Sua[0].setEnabled(false);
            //Set tự động giá trị các field
            for (int j = 0; j < array_MonAn.length; j++) {
                if(j!=2&&j!=5)
                    txt_MonAn_Sua[j].setText(table_MonAn.tb.getValueAt(i, j).toString());
                else if(j==2)
                {
                    int k;
                    for(k=0;k<array_DonViTinh.length;k++)
                        if(table_MonAn.tb.getValueAt(i, j).toString().equals(array_DonViTinh[k]))
                            cbDonViTinh_Sua.setSelectedIndex(k);
                }
                else if(j==5)
                {
                    int k;
                    for(k=0;k<array_Loai.length;k++)
                        if(table_MonAn.tb.getValueAt(i, j).toString().equals(array_Loai[k]))
                            cbLoai_Sua.setSelectedIndex(k);
                }
            }

        }
    }

    //Hàm sự kiện khi click vào nút xóa
    @Override
    protected void Xoa_click(MouseEvent evt) {
        int row = table_MonAn.tb.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hàng muốn xóa");
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa?", "", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String maMonAn = table_MonAn.tbModel.getValueAt(row, 0).toString();
                //truyền mã món ăn vào hàm timViTri ở MonAnBUS 
                int index = MonAnBUS.timViTri(maMonAn);
                //Xóa hàng ở table
                table_MonAn.tbModel.removeRow(row);
                //Xóa ở arraylist và đổi chế độ ẩn ở database
                BUS.xoa(maMonAn, index);
            }
        }

    }

    //Show thông tin món ăn
    private JPanel Show() {
        //Panel chung
        JPanel panel = new JPanel(null);
        //Panel chứa các text thông tin món ăn
        JPanel ChiTiet = new JPanel(null);

        ChiTiet.setBounds(500, 0, 500, 300);
        //Nhãn dùng để hiện hình ảnh
        lbImage = new JLabel();
        lbImage.setBackground(Color.yellow);
        lbImage.setBounds(200, 0, 300, 300);

        //Các textfield thông tin
        txMaMA = new JTextField();
        txTenMA = new JTextField();
        txDonGia = new JTextField();

        // Tạo border có tiêu đề
        txMaMA.setBorder(BorderFactory.createTitledBorder("Mã món ăn"));
        txTenMA.setBorder(BorderFactory.createTitledBorder("Tên món ăn"));
        txDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
//         Set các textfield không edit được
        txMaMA.setEditable(false);
        txTenMA.setEditable(false);
        txDonGia.setEditable(false);
        // Set font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaMA.setFont(f);
        txTenMA.setFont(f);
        txDonGia.setFont(f);
        //set size

        txMaMA.setBounds(50, 0, 200, 40);
        txTenMA.setBounds(50, 50, 200, 40);
        txDonGia.setBounds(50, 100, 200, 40);
        // add to panel
        ChiTiet.add(txMaMA);
        ChiTiet.add(txTenMA);
        ChiTiet.add(txDonGia);

        // event
        table_MonAn.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                int selectedRow = table_MonAn.tb.getSelectedRow(); 
                if (selectedRow != -1) {  // Kiểm tra xem có hàng nào được chọn không
                    String id = String.valueOf(table_MonAn.tbModel.getValueAt(selectedRow, 0));
                    showInfo(id);
                } else {
                    System.out.println("Không có hàng nào được chọn!");
                }
            }
        });

        panel.add(lbImage);
        panel.add(ChiTiet);
        return panel;
    }
    //Hàm show thông tin của món ăn
    private void showInfo(String id) {
        // https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        if (id != null) {
            // show hình
            for (MonAnDTO ds : MonAnBUS.dsMonAn) {
                if (ds.getIDMonAn().equals(id)) {
                    //Lấy chiều dài và chiều cao của nhãn lbImage
                    int w = lbImage.getWidth();
                    int h = lbImage.getHeight();
                    //Hàm xử lý khi đưa ảnh lên
                    ImageIcon img = new ImageIcon(getClass().getResource("/Images/MonAn/" + ds.getHinhAnh()));
                    Image imgScaled = img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    lbImage.setIcon(new ImageIcon(imgScaled));

                    // show info                   
                    txMaMA.setText(ds.getIDMonAn());
                    txTenMA.setText(ds.getTenMonAn());
                    txDonGia.setText(String.valueOf(ds.getDonGia()));
                    return;
                }
            }
        }
    }

    //Hành động khi ấn nút FileAnh
    private void btnFileAnh_Click(String type) {
        //bật lên 1 cửa sổ mới nên cần gán giá trị 1
        cohieu = 1;
        if (type.equals("Thêm")) {
            //Tạo cửa sổ chọn file
            FileDialog fd = new FileDialog(Them_MonAn);
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
                txt_MonAn_Them[4].setText(filename);

            }
        }
        if (type.equals("Sửa")) {
            //Tạo cửa sổ chọn file
            FileDialog fd = new FileDialog(Sua);
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
                txt_MonAn_Sua[4].setText(filename);

            }
        }
        //đã thực hiện xong thì set lại giá trị 0
        cohieu = 0;
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

    //Hàm tìm kiếm mỗi khi thao tác trên field
    public void txtSearchOnChange() {
//        int soLuong1 = -1, soLuong2 = -1;
        double donGia1 = -1, donGia2 = -1;
        //Ràng buộc
//        try {
//            soLuong1 = Integer.parseInt(Tu_SoLuong.getText());
//            Tu_SoLuong.setForeground(Color.black);
//        } catch (NumberFormatException e) {
//            Tu_SoLuong.setForeground(Color.red);
//        }
//
//        try {
//            soLuong2 = Integer.parseInt(Den_SoLuong.getText());
//            Den_SoLuong.setForeground(Color.black);
//        } catch (NumberFormatException e) {
//            Den_SoLuong.setForeground(Color.red);
//        }

        try {
            donGia1 = Double.parseDouble(Tu_DonGia.getText());
            Tu_DonGia.setForeground(Color.black);
        } catch (NumberFormatException e) {
            Tu_DonGia.setForeground(Color.red);
        }

        try {
            donGia2 = Double.parseDouble(Den_DonGia.getText());
            Den_DonGia.setForeground(Color.black);
        } catch (NumberFormatException e) {
            Den_DonGia.setForeground(Color.red);
        }

        //Đẩy dữ liệu đi và nhận lại danh sách đúng với field tìm kiếm
        // original: setDataToTable(Tool.searchMA(Ten.getText(), donGia1, donGia2, soLuong1, soLuong2), table_MonAn);
        setDataToTable(Tool.searchMA(Ten.getText(), donGia1, donGia2), table_MonAn); //chưa sửa xong hỏi Nguyên cái Textfield
    }

    //Set dữ liệu lên lại table
    private void setDataToTable(ArrayList<MonAnDTO> monAnDTO, GUIMyTable myTable) {
        myTable.clear();
        for (MonAnDTO monAn : monAnDTO) {
            table_MonAn.addRow(monAn);
        }
    }

    @Override
    protected void XuatExcel_click(MouseEvent evt) {
        new XuatExcel().xuatFileExcelMonAn();

    }

    @Override
    protected void NhapExcel_click(MouseEvent evt) {
        new DocExcel().docFileExcelMonAn();

    }

    //Hàm khi ấn nút làm mới
    private void LamMoi() throws SQLException, Exception {
        table_MonAn.clear();
        for (MonAnDTO DTO : MonAnBUS.dsMonAn) {
             if (DTO.getTrangThai().equals("Hiện")) {
                 table_MonAn.addRow(DTO);
             }
         }


         try {
             ArrayList<MonAnDTO> dsma = new ArrayList<MonAnDTO>();
             dsma = MonAnBUS.ReturnListMonAn();
             for(MonAnDTO DTO : dsma){
                 table_MonAn.addRow(DTO);
             }  
         } catch (Exception e) {
             e.printStackTrace();
         }
      
//        table_MonAn.clear();
//        ArrayList <MonAnDTO> dsmn = new ArrayList<MonAnDTO>();
//        dsmn = MonAnDAO.docDB();
//        for (int j = 0; j < dsmn.size(); j++) {
//            System.out.println(dsmn.get(j).getTrangThai());
//        }

    }

    // Khởi tạo popup menu
    public JPopupMenu createPopUp (int rowIndex, GUIMyTable Table) {

        menuThem = new JMenuItem("Thêm");
        menuThem.setIcon(new ImageIcon("src/Images/Icon/icons8_add_16px.png"));
        menuSua = new JMenuItem("Sửa");
        menuSua.setIcon(new ImageIcon("src/Images/Icon/sua3-16.png"));
        menuXoa = new JMenuItem("Xóa");
        menuXoa.setIcon(new ImageIcon("src/Images/Icon/xoa-16.png"));

        popup = new JPopupMenu();
        popup.add(menuThem);
        popup.addSeparator(); // Ngăn dòng
        popup.add(menuSua);
        popup.addSeparator();
        popup.add(menuXoa);

        menuThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt){
                Them_click(evt);
            }
        });

        menuSua.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                Sua_click(evt);
            }
        });

        menuXoa.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                Xoa_click(evt);
            }
        });

        return popup;
    }

    public void ShowMenu(GUIMyTable table){
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                int r = table.getTable().rowAtPoint (me.getPoint ());
                if (r >= 0 && r < table.getTable().getRowCount()) {
                    table.getTable().setRowSelectionInterval (r, r);
                } else  {
                    table.getTable().clearSelection ();
                }

                int rowIndex = table.getTable().getSelectedRow ();
                if (rowIndex <0)
                    return;
                if (me.isPopupTrigger () && me.getComponent () instanceof JTable) {
                    JPopupMenu popup = createPopUp (rowIndex, table);
                    popup.show (me.getComponent (), me.getX (), me.getY ());
                }
            }
        });
    }


}




