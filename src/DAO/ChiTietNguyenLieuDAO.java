/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ChiTietNguyenLieuDTO;
import DTO.CongThucDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Phat
 */
public class ChiTietNguyenLieuDAO {
 
    ConnectDB connection = new ConnectDB();
    public ArrayList docCTNL() throws Exception {
        ArrayList<ChiTietNguyenLieuDTO> CTNL = new ArrayList<>() ;
        try {
            String qry = "SELECT * FROM chitietnguyenlieu";
            ResultSet rs = connection.excuteQuery(qry);  
            while (rs.next()) {
                    ChiTietNguyenLieuDTO  ctnl = new ChiTietNguyenLieuDTO();
                    ctnl.setIDCongThuc(rs.getString("IDCongThuc"));
                    ctnl.setIDNguyenLieu(rs.getString("IDNguyenLieu"));
                    ctnl.setSoLuong(rs.getFloat("SoLuong"));
                    CTNL.add(ctnl);
                }
            }
         catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng chi tiết nguyên liệu");
        } 
        return CTNL;   
    }

    public void them(ChiTietNguyenLieuDTO CT) { //cần ghi lại khi qua class khác
        try {
            String qry = "INSERT INTO chitietnguyenlieu values (";
            qry = qry + "'" + CT.getIDCongThuc()+ "'";
            qry = qry + "," + "'" + CT.getIDNguyenLieu()+ "'";
            qry = qry + "," + "'" + CT.getSoLuong()+ "'";
            qry = qry + ")";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {
        }
    }

    public void sua(ChiTietNguyenLieuDTO CT) { 
        try {
            String qry = "Update chitietnguyenlieu Set ";
            qry = qry + "IDCongThuc=" + "'" + CT.getIDCongThuc()+ "'";
            qry = qry + ",IDNguyenLieu=" + "'" + CT.getIDNguyenLieu()+ "'";
            qry = qry + ",Soluong=" + CT.getSoLuong();
            qry = qry+" "+" WHERE IDCongThuc='"+CT.getIDCongThuc()+"' AND IDNguyenLieu ='"+CT.getIDNguyenLieu()+"'" ;
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();

        } catch (Exception ex) {
        }
    }

    public void xoa(String IDCongThuc, String IDNguyenLieu) { 
        try {
            
            String qry = "delete from chitietnguyenlieu ";
            qry = qry + " " + "WHERE IDNguyenLieu='" + IDNguyenLieu+ "' AND IDCongThuc ='"+IDCongThuc+"'";
            connection.getStatement();
            connection.ExecuteUpdate(qry);
            System.out.println(qry);
            connection.closeConnect();
        } catch (Exception ex) {

        }
    }
}










