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
import DTO.ChiTietNguyenLieuDTO;
import DTO.CongThucDTO;
import DTO.MonAnDTO;
import DTO.NguyenLieuDTO;
import Excel.DocExcel;
import Excel.XuatExcel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Nguyen
 */
public class GUICongThuc extends GUIFormContent{
    //Tạo mảng tiêu đề
    public static String array_CongThuc[]={"Mã công thức","Mã món ăn","Mô tả công thức"};
    //Tạo JTable , GUIMyTable kế thừa từ JTable và được chỉnh sửa
    public GUIMyTable table_CongThuc;
    //Tạo Dialog để thêm công thức
    private static JDialog Them_CongThuc;
    //Tạo Dialog để sửa công thức
    private static JDialog Sua;    
    //Phần nhãn bên trong Dialog thêm sửa 
    private JLabel label_CongThuc[] = new JLabel[array_CongThuc.length];
    //Phần textfield của thêm
    private JTextField txt_CongThuc_Them[] = new JTextField[array_CongThuc.length];
    //Phần textfield của sửa
    private JTextField txt_CongThuc_Sua[] = new JTextField[array_CongThuc.length];
    //Phần textfield của thêm nguyên liệu
    private ArrayList<JTextField[]> txt_NguyenLieu_Them;
    //Phần textfield của sửa nguyên liệu
    private ArrayList<JTextField[]> txt_NguyenLieu_Sua;
    //Panel nguyên liệu
    private JPanel pNguyenLieuThem, pNguyenLieuSua;
    //Phần textfield để tìm kiếm
    private JTextField search;
    //Combobox để chọn thuộc tính muốn tìm
    private JComboBox cbSearch;
    //Tạo sẵn đối tượng BUS
    private CongThucBUS BUS = new CongThucBUS();
    //Tạo cờ hiệu cho việc các Dialog có được tắt đúng cách hay không
    //cohieu=0 thì không được bấm ra ngoài. cohieu=1 thì được bấm ra ngoài
    private int cohieu=0;
    
    private JButton ThemMonAn,SuaMonAn;

    private String maNL[];
    private float slNL[];
    private float totalPrice;
    //Danh sách các mã nguyên liệu đã xóa
    private ArrayList<String> maNLDelete;

    // Tạo Menu cho popup menu
    JMenuItem menuThem,menuSua, menuXoa;
    JPopupMenu popup;
    // Lưu dòng đã chọn
    private int selectedRow = -1;
    public GUICongThuc(){
        super();
    }

    protected JPanel CongCu(){
        JPanel CongCu=new JPanel();
        //Nút thêm
        JButton Them=new JButton("Thêm");
        Them.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/them1-30.png")));
        Them.setFont(new Font("Segoe UI", 0, 14));
        Them.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));        
        Them.setBackground(Color.decode("#90CAF9"));
        Them.setEnabled(false);
        Them.setBounds(350, 0, 70, 40);
        CongCu.add(Them);
        //Nút sửa
        JButton Sua=new JButton("Sửa");
        Sua.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/sua3-30.png")));
        Sua.setFont(new Font("Segoe UI", 0, 14));
        Sua.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        Sua.setBackground(Color.decode("#90CAF9"));
        Sua.setBounds(430, 0, 70, 30);
        Sua.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                Sua_click(evt);
            }
        });
        CongCu.add(Sua);
        //Nút xóa
        JButton Xoa=new JButton("Xóa");
        Xoa.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/delete1-30.png")));
        Xoa.setFont(new Font("Segoe UI", 0, 14));
        Xoa.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        Xoa.setBackground(Color.decode("#90CAF9"));
        Xoa.setBounds(510, 0, 70, 30);
        Xoa.setEnabled(false);
        // Xoa.addMouseListener(new MouseAdapter(){
        //     @Override
        //     public void mousePressed(MouseEvent evt){
        //         Xoa_click(evt);
        //     }
        // });
        CongCu.add(Xoa);
        //Nút nhập excel
        JButton NhapExcel=new JButton("Nhập Excel");
        NhapExcel.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xls-30.png")));
        NhapExcel.setFont(new Font("Segoe UI", 0, 14));
        NhapExcel.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        NhapExcel.setBackground(Color.decode("#90CAF9"));
        NhapExcel.setBounds(590, 0, 100, 30);
        NhapExcel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                NhapExcel_click(evt);
            }
        });
        CongCu.add(NhapExcel);
        //Nút xuất excel
        JButton XuatExcel=new JButton("Xuất Excel");
        XuatExcel.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xls-30.png")));
        XuatExcel.setFont(new Font("Segoe UI", 0, 14));
        XuatExcel.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
        XuatExcel.setBackground(Color.decode("#90CAF9"));
        XuatExcel.setBounds(670, 0, 100, 30);
        XuatExcel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                XuatExcel_click(evt);
            }
        });
        CongCu.add(XuatExcel);
        
        return CongCu;
        
    }

    @Override
    //Tạo Panel chưa Table
    protected JPanel Table(){
        JPanel panel =new JPanel(null);
        //Tạo đối tượng cho table_CongThuc
        table_CongThuc = new GUIMyTable();
        //Tạo tiêu đề bảng
        table_CongThuc.setHeaders(array_CongThuc);
        //Hàm đọc database
        docDB();
        //Set kích thước và vị trí
        table_CongThuc.pane.setPreferredSize(new Dimension(GUImenu.width_content*90/100, 300));        
        table_CongThuc.setBounds(0,0,GUImenu.width_content , 550);
        panel.add(table_CongThuc);

        // Show menu
        ShowMenu(table_CongThuc);

        return panel;
    }
    //Hàm tạo Dialog thêm công thức
    private void Them_Frame() {
        JFrame f = new JFrame();
        //Để cờ hiệu với giá trị 0 với ý nghĩa không được bấm ra khỏi Dialog trừ nút Thoát
        cohieu=0;
        Them_CongThuc = new JDialog(f);
        Them_CongThuc.setLayout(null);
        Them_CongThuc.setSize(500, 500);
        //Set vị trí của Dialog
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Them_CongThuc.setLocationRelativeTo(null);
        //Tắt thanh công cụ mặc định
        Them_CongThuc.setUndecorated(true);
        //Tạo tiêu đề và set hình thức
        JLabel Title = new JLabel("Thêm công thức");
        Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
        Title.setForeground(Color.decode("#FF4081"));
        Title.setBounds(150, 0, 200, 40);
        Them_CongThuc.add(Title);
        int y = 50;
        //Tạo tự động các label và textfield
        for (int i = 0; i < array_CongThuc.length; i++) {
            label_CongThuc[i] = new JLabel(array_CongThuc[i]);
            label_CongThuc[i].setBounds(100, y, 100, 30);
            Them_CongThuc.add(label_CongThuc[i]);

            txt_CongThuc_Them[i]=new JTextField();
            txt_CongThuc_Them[i].setBounds(200, y, 150, 30);
            //Tạo nút để lấy tên ảnh 
            if(i==1)
            {
                ThemMonAn=new JButton();
                ThemMonAn.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
                ThemMonAn.setBorder(BorderFactory.createLineBorder(Color.decode("#90CAF9"), 1));
                ThemMonAn.setBounds(355, y, 30,30);
                Them_CongThuc.add(ThemMonAn);
                ThemMonAn.addActionListener((ActionEvent ae) -> {
                    //Tắt cờ hiệu đi
                    cohieu=1;
                    GUIFormChon a = null;
                    try {
                        a = new GUIFormChon(txt_CongThuc_Them[1],"Món ăn");
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(GUIBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    a.setVisible(true);
                    a.addWindowListener(new WindowAdapter(){
                        @Override
                     public void windowClosed(WindowEvent e){
                            cohieu=0;
                            Them_CongThuc.setVisible(true);
                        }
 
                    });
                });
            }
            
            y += 40;
            Them_CongThuc.add(txt_CongThuc_Them[i]);
        }
        txt_CongThuc_Them[2].setVisible(false);
        label_CongThuc[2].setVisible(false);
        y -= 40;

        txt_NguyenLieu_Them = new ArrayList<>();
        //Panel chứa nguyên liệu
        pNguyenLieuThem = new JPanel();
        pNguyenLieuThem.setLayout(new BoxLayout(pNguyenLieuThem, BoxLayout.Y_AXIS));
//        pNguyenLieuThem.setBounds(100, y, 300, 200);y += 220;
//        pNguyenLieuThem.setBackground(Color.white);
        
        //Tiêu đề thêm chi tiết
        int py = 0;
        JLabel ctnl = new JLabel("Chi tiết nguyên liệu");
        ctnl.setBounds(100, y, 150, 40);py += 40;
        pNguyenLieuThem.add(ctnl);
        addNewRow(pNguyenLieuThem);
        //Tạo nút Thêm chi tiết
        JButton themNL = new JButton("Thêm nguyên liệu");
        themNL.setBackground(Color.decode("#006600"));
        themNL.setForeground(Color.white);
        themNL.setBounds(100, y, 150, 30);y += 40;
        //Sự kiện khi click nút thêm
        themNL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewRow(pNguyenLieuThem);
            }
        });

        Them_CongThuc.add(themNL);
         // Đặt panel vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(pNguyenLieuThem);
        scrollPane.setBounds(50, y, 400, 200);y += 220;
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.white);
        Them_CongThuc.add(scrollPane);
        //Sự kiện khi click
        
        //Tạo nút lưu
        JButton Luu = new JButton("Lưu");
        Luu.setBackground(Color.decode("#90CAF9"));
        Luu.setBounds(100, y, 100, 50);
        //Sự kiện khi click
        Luu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu=1;
                int a=JOptionPane.showConfirmDialog( Them_CongThuc,"Bạn chắc chứ ?" ,"",JOptionPane.YES_NO_OPTION);
                if(a==JOptionPane.YES_OPTION && saveData(false))
                {
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
                    txt_CongThuc_Them[2].setText(txtMota);
                    if(checkTextThem(txt_CongThuc_Them[1].getText(), txt_CongThuc_Them[2].getText()))
                    {
                        CongThucDTO DTO = new CongThucDTO(txt_CongThuc_Them[0].getText(),
                                                    txt_CongThuc_Them[1].getText(),
                                                    txt_CongThuc_Them[2].getText(),
                                                    "Hiện");
                        ChiTietNguyenLieuBUS ctnlBUS = new ChiTietNguyenLieuBUS();
                        try{
                            ctnlBUS.docCTNL();
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                            return;
                        }
                        BUS.them(DTO); //thêm công thức bên BUS đã có thêm vào database
                        for(int i=0; i<maNL.length; i++){
                            ctnlBUS.them(new ChiTietNguyenLieuDTO(txt_CongThuc_Them[0].getText(),maNL[i],slNL[i]));
                        }
                        table_CongThuc.addRow(DTO);                    
                        //clear textfield trong Them
                        for(int i=0;i<array_CongThuc.length;i++)
                        {
                            txt_CongThuc_Them[i].setText("");
                        }
                        if(maNLDelete!=null)
                            maNLDelete.clear();
                        Them_CongThuc.dispose();
                    }
                }
                else
                    cohieu=0;
            }
        });
        Them_CongThuc.add(Luu);
        
        txt_CongThuc_Them[1].setEditable(false);
        //Tạo nút thoát
        JButton Thoat = new JButton("Thoát");
        Thoat.setBackground(Color.decode("#90CAF9"));
        Thoat.setBounds(250, y, 100, 50);
        //Sự kiên khi click
        Thoat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //clear textfield trong Them
                    for(int i=0;i<array_CongThuc.length;i++)
                    {
                        txt_CongThuc_Them[i].setText("");
                    }
                    //Tắt cờ hiệu đi 
                    cohieu=1;
                    if(maNLDelete!=null)
                        maNLDelete.clear();
                //Lệnh này để đóng dialog
                Them_CongThuc.dispose();
            }
        });
        Them_CongThuc.add(Thoat);
        //Chặn việc thao tác ngoài khi chưa tắt dialog gây lỗi phát sinh
        Them_CongThuc.addWindowListener(new WindowAdapter(){
            @Override
            public void windowDeactivated(WindowEvent e){
                if(cohieu==0)
                JOptionPane.showMessageDialog(null, "Vui lòng tắt Dialog khi thao tác");
            }
            @Override
            public void windowActivated(WindowEvent e) {
                // Khi cửa sổ này nhận tiêu điểm, chúng ta không làm gì cả, có thể để trống
            }
        });
        
        CongThucBUS ctBUS = new CongThucBUS();
        try{
            ctBUS.docCT();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
            return;
        }
        String maCongThuc = Tool.tangMa(ctBUS.getMaCongThucCuoi());
        txt_CongThuc_Them[0].setText(maCongThuc);
        txt_CongThuc_Them[0].setEditable(false);
        Them_CongThuc.setVisible(true);

    }
    //Hàm khi ấn Lưu thì lấy dữ liệu của các nguyên liệu
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
                        JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ thông tin");
                        return false;
                    }
                    if(fields[1].getText() == null || fields[1].getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ thông tin");
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
        deleteButton.addActionListener(e -> {
            panel.remove(rowPanel);
            if(maNLDelete == null)
                maNLDelete = new ArrayList<>();
            maNLDelete.add(maField.getText());
            for (int i = 0; i < txt_NguyenLieu_Them.size(); i++) {
                JTextField[] fields = txt_NguyenLieu_Them.get(i);
                if (fields[0] == maField && fields[1] == soLuongField) {
                    txt_NguyenLieu_Them.remove(i);
                    break;
                }
            }
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
    //Hàm tạo Dialog sửa món ăn
    private void Sua_Frame() {
        JFrame f = new JFrame();
        //Để cờ hiệu với giá trị 0 với ý nghĩa không được bấm ra khỏi Dialog trừ nút Thoát
        cohieu=0;
        Sua = new JDialog(f);
        Sua.setLayout(null);
        Sua.setSize(500, 500);
        //Set vị trí của Dialog
        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Sua.setLocationRelativeTo(null);
        Sua.setUndecorated(true);
        //Tạo tiêu đề
        JLabel Title = new JLabel("Sửa công thức");
        Title.setFont(new Font("Time New Roman", Font.BOLD, 21));
        Title.setForeground(Color.decode("#FF4081"));
        Title.setBounds(150, 0, 200, 40);
        Sua.add(Title);
        int y = 50;
        //Tạo tự động các lable và textfield
        for (int i = 0; i < array_CongThuc.length; i++) {
            label_CongThuc[i] = new JLabel(array_CongThuc[i]);
            label_CongThuc[i].setBounds(100, y, 100, 30);
            Sua.add(label_CongThuc[i]);
            txt_CongThuc_Sua[i] = new JTextField();
            txt_CongThuc_Sua[i].setBounds(200, y, 150, 30);
            y += 40;
            Sua.add(txt_CongThuc_Sua[i]);
        }
        label_CongThuc[2].setVisible(false);
        txt_CongThuc_Sua[2].setVisible(false);y -= 40;
        //Panel chứa nguyên liệu
        
        pNguyenLieuSua.setLayout(new BoxLayout(pNguyenLieuSua, BoxLayout.Y_AXIS));
//        pNguyenLieuSua.setBounds(100, y, 300, 200);y += 220;
//        pNguyenLieuSua.setBackground(Color.white);
        
        //Tiêu đề thêm chi tiết
        JLabel ctnl = new JLabel("Chi tiết nguyên liệu");
        ctnl.setBounds(100, y, 150, 40);y += 40;
        Sua.add(ctnl);
        
        
         // Đặt panel vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(pNguyenLieuSua);
        scrollPane.setBounds(50, y, 400, 200);y += 220;
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.white);
        Sua.add(scrollPane);
        //Tạo nút Thêm chi tiết
        JButton SuaNL = new JButton("Thêm nguyên liệu");
        SuaNL.setBackground(Color.decode("#006600"));
        SuaNL.setForeground(Color.white);
        SuaNL.setBounds(100, y, 150, 30);y += 40;
        //Sự kiện khi click nút thêm
        SuaNL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewRow(pNguyenLieuSua);
            }
        });

        Sua.add(SuaNL);
        //Lưu tất cả dữ liệu trên textfield lên database
        JButton Luu = new JButton("Lưu");
        Luu.setBackground(Color.decode("#90CAF9"));
        Luu.setBounds(100, y, 100, 50);
        // Luu.addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mousePressed(MouseEvent evt) {
        //         //Tắt cờ hiệu đi 
        //             cohieu=1;
        //         int a=JOptionPane.showConfirmDialog( Sua,"Bạn chắc chứ ?" ,"",JOptionPane.YES_NO_OPTION);
        //         if(a == JOptionPane.YES_OPTION){
                    // if (checkTextSua(
                    //             txt_CongThuc_Sua[1].getText(),
                    //             txt_CongThuc_Sua[2].getText()
                    //             )) {
        //                     //Chạy hàm để lưu lại việc sửa dữ liệu    
        //                     buttonLuu_Sua();
                            
        //                     //Lệnh này để tắt dialog
        //                     Sua.dispose();
        //                 }
        //         }
        //         else
        //             cohieu=0;
        //     }
        // });
        Luu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu=1;
                int a=JOptionPane.showConfirmDialog( Them_CongThuc,"Bạn chắc chứ ?" ,"",JOptionPane.YES_NO_OPTION);
                if(a==JOptionPane.YES_OPTION && saveData(false))
                {
                    totalPrice = TinhTien();
                    MonAnBUS maBUS = new MonAnBUS();
                    NguyenLieuBUS nlBUS = new NguyenLieuBUS();
                    try{
                        nlBUS.docDSNL();
                        maBUS.docDSMonAn();
                    } catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                        return;
                    }
                    for(int i=0; i<maBUS.dsMonAn.size();i++){
                        if(maBUS.dsMonAn.get(i).getIDMonAn().equals(txt_CongThuc_Sua[1].getText())){
                            maBUS.dsMonAn.get(i).setDonGia(totalPrice);
                            maBUS.sua(maBUS.dsMonAn.get(i),i);
                        }
                    }
                    String txtMota = "";
                    for(int i = 0; i<maNL.length; i++){
                        NguyenLieuDTO nlDTO = nlBUS.getNguyenLieuDTO(maNL[i]);
                        txtMota += String.valueOf(slNL[i]) +" ";
                        txtMota += nlDTO.getDonViTinh() + " ";
                        txtMota += nlDTO.getTenNguyenLieu();
                        if(i != maNL.length-1)
                            txtMota += ", ";
                    }
                    txt_CongThuc_Sua[2].setText(txtMota);
                    if(checkTextSua(
                        txt_CongThuc_Sua[1].getText(),
                        txt_CongThuc_Sua[2].getText()
                        )) 
                    {   
                        CongThucDTO DTO = new CongThucDTO(txt_CongThuc_Sua[0].getText(),
                                                    txt_CongThuc_Sua[1].getText(),
                                                    txt_CongThuc_Sua[2].getText(),
                                                    "Hiện");
                        ChiTietNguyenLieuBUS ctnlBUS = new ChiTietNguyenLieuBUS();
                        try{
                            ctnlBUS.docCTNL();
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                            return;
                        }
                        BUS.sua(DTO, selectedRow); //thêm công thức bên BUS đã có thêm vào database
                        for(int i=0; i<maNL.length; i++){
                            ctnlBUS.sua(new ChiTietNguyenLieuDTO(txt_CongThuc_Sua[0].getText(),maNL[i],slNL[i]));
                        }
                        if(maNLDelete!=null)
                            for(int i=0;i<maNLDelete.size();i++){
                                ctnlBUS.xoa(txt_CongThuc_Sua[0].getText(), maNLDelete.get(i));
                                System.out.println("------------------------------"+maNLDelete.get(i));
                            }
                        table_CongThuc.addRow(DTO);                    
                        //clear textfield trong Them
                        for(int i=0;i<array_CongThuc.length;i++)
                        {
                            txt_CongThuc_Sua[i].setText("");
                        }
                        if(maNLDelete!=null)
                            maNLDelete.clear();
                        pNguyenLieuSua = new JPanel();
                        Sua.dispose();
                    }
                }
                else
                    cohieu=0;
            }
        });
        Sua.add(Luu);
        
        txt_CongThuc_Sua[1].setEditable(false);

        JButton Thoat = new JButton("Thoát");
        Thoat.setBackground(Color.decode("#90CAF9"));
        Thoat.setBounds(250, y, 100, 50);
        Thoat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //Tắt cờ hiệu đi 
                cohieu = 1;
                pNguyenLieuSua = new JPanel();
                if(maNLDelete!=null)
                    maNLDelete.clear();
                Sua.dispose();
            }
        });
        Sua.add(Thoat);
        //Chặn việc thao tác ngoài khi chưa tắt dialog gây lỗi phát sinh
        Sua.addWindowListener(new WindowAdapter(){
            
            @Override
            public void windowDeactivated(WindowEvent e){
                if(cohieu==0)
                    JOptionPane.showMessageDialog(null, "Vui lòng tắt Dialog khi muốn làm thao tác khác");
            }
            public void windowActivated(WindowEvent e){
            }
        });
        Sua.setVisible(true);
        
    }
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
    //Hàm lưu dữ liệu khi sửa
    public void buttonLuu_Sua() {
        int row = table_CongThuc.tb.getSelectedRow();
        int colum = table_CongThuc.tb.getSelectedColumn();
        String maCongThuc = table_CongThuc.tbModel.getValueAt(row, colum).toString();
        //Hỏi để xác nhận việc lưu dữ liệu đã sửa chữa
//        int option = JOptionPane.showConfirmDialog(Sua, "Bạn chắc chắn sửa?", "", JOptionPane.YES_NO_OPTION);
//        if (option == JOptionPane.YES_OPTION) {
            //Sửa dữ liệu trên bảng
            //model là ruột JTable   
            //set tự động giá trị cho model
            for(int j=0;j<array_CongThuc.length;j++)
                table_CongThuc.tbModel.setValueAt(txt_CongThuc_Sua[j].getText(), row, j);
            
            table_CongThuc.tb.setModel(table_CongThuc.tbModel);

            
            //Sửa dữ liệu trong database và arraylist trên bus
            //Tạo đối tượng monAnDTO và truyền dữ liệu trực tiếp thông qua constructor
            CongThucDTO DTO = new CongThucDTO(txt_CongThuc_Sua[0].getText(),
                                                  txt_CongThuc_Sua[1].getText(),
                                                  txt_CongThuc_Sua[2].getText());
            //Tìm vị trí của row cần sửa
            int index = CongThucBUS.timViTri(maCongThuc);
            //Truyền dữ liệu và vị trí vào bus
            BUS.sua(DTO, index);
//        }
    }
    @Override
    protected void Them_click(MouseEvent evt){
        
        Them_Frame();
    }
    //Hàm sự kiện khi click vào nút Sửa
    @Override
    protected void Sua_click(MouseEvent evt) {
        if(pNguyenLieuSua == null)
            pNguyenLieuSua = new JPanel();
        selectedRow = table_CongThuc.tb.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 hàng để sửa");
        } else {
            //Lấy các nguyên liệu của công thức
            ChiTietNguyenLieuBUS ctnlBUS = new ChiTietNguyenLieuBUS();
            try{
                ctnlBUS.docCTNL();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
                return;
            }
            ArrayList<NguyenLieuDTO> nlList = ctnlBUS.timMaCT(table_CongThuc.tb.getValueAt(selectedRow, 0).toString());
            //Hiện Dialog lên và set dữ liệu vào các field
            txt_NguyenLieu_Them = new ArrayList<>();
            for(int j=0;j<nlList.size();j++){
                addNewRow(pNguyenLieuSua);
                JTextField[] fields = txt_NguyenLieu_Them.get(j);
                fields[0].setText(nlList.get(j).getIDNguyenLieu());
                fields[1].setText(String.valueOf(nlList.get(j).getSoLuong()));
            }
            Sua_Frame();
            txt_CongThuc_Sua[0].setEnabled(false);
            //Set tự động giá trị các field
            for(int j=0;j<array_CongThuc.length;j++)
                txt_CongThuc_Sua[j].setText(table_CongThuc.tb.getValueAt(selectedRow, j).toString());
                
        }
    }
    //Hàm sự kiện khi click vào nút xóa
    @Override
    protected void Xoa_click(MouseEvent evt) {       
        int row = table_CongThuc.tb.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hàng muốn xóa");
        } else {       
            int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa?", "", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String maCongThuc = table_CongThuc.tbModel.getValueAt(row, 0).toString();
                //truyền mã công thức vào hàm timViTri ở CongThucBUS 
                int index = CongThucBUS.timViTri(maCongThuc);
                //Xóa hàng ở table
                table_CongThuc.tbModel.removeRow(row);
                //Xóa ở arraylist và đổi chế độ ẩn ở database
                BUS.xoa(maCongThuc, index);
            }
        }

    }
    public void docDB() {
        BUS=new CongThucBUS();
        if(CongThucBUS.CT == null) {
        try {
            BUS.docCT();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GUICongThuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        }
        
        for (CongThucDTO DTO : BUS.CT) {
            if (DTO.getTrangThai().equals("Hiện")) {
                table_CongThuc.addRow(DTO);
                    
            }
        }
    }
    //Hàm khi ấn nút làm mới
    private void LamMoi(){
        table_CongThuc.clear();
        docDB();
    }
    @Override
    protected JPanel TimKiem(){
        JPanel TimKiem=new JPanel(null);
        //Tạo nhãn tìm kiếm 
        JLabel lbsearch=new JLabel("");
        lbsearch.setBorder(new TitledBorder("Tìm kiếm"));
        int x=400;
        //Tạo combobox cho người dùng chọn mục muốn search
        cbSearch = new JComboBox<>(array_CongThuc);
        cbSearch.setBounds(5, 20, 150, 40);
        lbsearch.add(cbSearch);
        
        search=new JTextField();
        //Set mặc định ở ô số 0 trong mảng array_CongThuc
        search.setBorder(new TitledBorder(array_CongThuc[0]));
        search.setBounds(155, 20, 150, 40);
        lbsearch.add(search);
        addDocumentListener(search);
        
        cbSearch.addActionListener((ActionEvent e) -> {
            //với mỗi giá trị của cbSearch thì sẽ set lại tiêu đề search
            search.setBorder(BorderFactory.createTitledBorder(cbSearch.getSelectedItem().toString()));
            search.requestFocus();           
        });
        lbsearch.setBounds(x, 0, 315, 70);
        TimKiem.add(lbsearch);       
        
        //Tạo nút làm mới
        JButton LamMoi=new JButton("Làm mới");
        LamMoi.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/lammoi1-30.png")));
        LamMoi.setFont(new Font("Segoe UI", 0, 14));
        LamMoi.setBorder(BorderFactory.createLineBorder(Color.decode("#BDBDBD"), 1));        
        LamMoi.setBackground(Color.decode("#90CAF9"));
        LamMoi.setBounds(x+=320, 10, 110, 30);
        LamMoi.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                search.setText("");
                LamMoi();
            }
        });
        TimKiem.add(LamMoi);                
        
        JButton ChiTiet=new JButton("Chi tiết");
        ChiTiet.setIcon(new ImageIcon(this.getClass().getResource("/Images/Icon/xemchitiet-30.png")));
        ChiTiet.setFont(new Font("Segoe UI", 0, 14));
        ChiTiet.setBorder(BorderFactory.createLineBorder(Color.decode("#BDBDBD"), 1));        
        ChiTiet.setBackground(Color.decode("#90CAF9"));
        ChiTiet.setBounds(x, 40, 110, 30);
        ChiTiet.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                GUIFormChon a = null;
                int i=table_CongThuc.tb.getSelectedRow();
                if (i == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 công thức");
                    return;
                } 
                String MaCongThuc=String.valueOf(table_CongThuc.tbModel.getValueAt(i,0));
                try {
                    a = new GUIFormChon("Chi tiết công thức",MaCongThuc);
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(GUIBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

            }
        });
        TimKiem.add(ChiTiet);
        
        
        return TimKiem;
    }
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
    public void txtSearchOnChange() {
        table_CongThuc.clear();
        ArrayList<CongThucDTO> arraylist=Tool.searchCT(search.getText(),cbSearch.getSelectedItem().toString() );
        for (CongThucDTO DTO : arraylist) {
            if (DTO.getTrangThai().equals("Hiện")) {
                table_CongThuc.addRow(DTO);
                    
            }
        }
    }
    
    public boolean checkTextThem(String MaMonAn, String moTaCongThuc) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (MaMonAn.equals("")
                || moTaCongThuc.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
        }
//        else if(!MonAnBUS.timMaMonAn(MaMonAn)) {
//            JOptionPane.showMessageDialog(null, "Mã món ăn không tồn tại");
//            txt_CongThuc_Them[1].requestFocus();
//        }
         else {
            return true;

        }
        return false;
    }
    
    public boolean checkTextSua(String MaMonAn, String moTaCongThuc) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Segoe UI", 0, 20)));
        if (MaMonAn.equals("")
                || moTaCongThuc.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
            return false;
        }
        else if(totalPrice == 0) {
            JOptionPane.showMessageDialog(null, "Phải có ít nhất 1 nguyên liệu");
            return false;
        }
//        else if(!MonAnBUS.timMaMonAn(MaMonAn)) {
//            JOptionPane.showMessageDialog(null, "Mã món ăn không tồn tại");
//            txt_CongThuc_Them[1].requestFocus();
//        }
         else {
            return true;

        }
    }
    
    @Override
    protected void XuatExcel_click(MouseEvent evt){
        new XuatExcel().xuatFileExcelCongThuc();
    }
    @Override
    protected void NhapExcel_click(MouseEvent evt){
        new DocExcel().docFileExcelCongThuc();
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
    // popup.add(menuThem);
    popup.addSeparator(); // Ngăn dòng
    popup.add(menuSua);
    popup.addSeparator();
    popup.add(menuXoa);

    // menuThem.addMouseListener(new MouseAdapter() {
    //     @Override
    //     public void mousePressed(MouseEvent evt){
    //         Them_click(evt);
    //     }
    // });

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






















































