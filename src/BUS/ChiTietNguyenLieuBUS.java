/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ChiTietNguyenLieuDAO;
import DAO.CongThucDAO;
import DTO.ChiTietNguyenLieuDTO;
import DTO.CongThucDTO;
import DTO.NguyenLieuDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class ChiTietNguyenLieuBUS {
   public static ArrayList<ChiTietNguyenLieuDTO> dsctnl;
   public ChiTietNguyenLieuBUS()
    {
        
    }
    public  void  docCTNL() throws Exception 
    {
        ChiTietNguyenLieuDAO ctnl = new ChiTietNguyenLieuDAO();
        if (dsctnl == null) {
            dsctnl = new ArrayList<>();
        }
        dsctnl = ctnl.docCTNL(); // đọc dữ liệu từ database
    }
    
    //Tìm mã công thức trả về danh sách nguyên liệu
    
    public ArrayList<NguyenLieuDTO> timMaCT(String id){
        try{
            NguyenLieuBUS nlBUS = new NguyenLieuBUS();
            nlBUS.docDSNL();
            ArrayList<NguyenLieuDTO> dsnl = new ArrayList<>();
            for(int i=0; i<dsctnl.size(); i++){
                if(dsctnl.get(i).getIDCongThuc().equals(id)){
                    NguyenLieuDTO nl = nlBUS.getNguyenLieuDTO(dsctnl.get(i).getIDNguyenLieu());
                    nl.setSoLuong(dsctnl.get(i).getSoLuong());
                    dsnl.add(nl);
                }
            }
            return dsnl;
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
        }
        return null;
    }

    public void them(ChiTietNguyenLieuDTO CTNLDTO) //cần ghi lại khi qua class khác
    {
        ChiTietNguyenLieuDAO ctnl = new ChiTietNguyenLieuDAO();
        ctnl.them(CTNLDTO);//ghi vào database
        if (dsctnl != null)
        dsctnl.add(CTNLDTO);//cập nhật arraylist
    }

    public void sua(ChiTietNguyenLieuDTO CTNLDTO) 
    {
        
        boolean hasNL = false;
        for(int i=0;i<dsctnl.size();i++){
            ChiTietNguyenLieuDTO ctnlDTO = dsctnl.get(i);
            if(ctnlDTO.getIDCongThuc().equals(CTNLDTO.getIDCongThuc()) && ctnlDTO.getIDNguyenLieu().equals(CTNLDTO.getIDNguyenLieu()))
                hasNL = true;
        }
        if(hasNL){
            ChiTietNguyenLieuDAO ctnl = new ChiTietNguyenLieuDAO();
            ctnl.sua(CTNLDTO);
        }
        else{
            them(CTNLDTO);
        }
    }

    public void xoa(String IDCT, String IDNL) 
    {
        ChiTietNguyenLieuDAO data = new ChiTietNguyenLieuDAO();
        data.xoa(IDCT, IDNL); // update trạng thái lên database
    }
}




