/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author HP
 */
public class ChiTietNguyenLieuDTO {
    private String IDCongThuc,IDNguyenLieu;
    private float SoLuong;
    public ChiTietNguyenLieuDTO(String IDCongThuc,String IDNguyenLieu,float SoLuong){
        this.IDCongThuc = IDCongThuc;
        this.IDNguyenLieu = IDNguyenLieu;
        this.SoLuong = SoLuong;
    }

    public ChiTietNguyenLieuDTO() {
        
    }

    public String getIDCongThuc() {
        return IDCongThuc;
    }

    public void setIDCongThuc(String IDCongThuc) {
        this.IDCongThuc = IDCongThuc;
    }

    public String getIDNguyenLieu() {
        return IDNguyenLieu;
    }

    public void setIDNguyenLieu(String IDNguyenLieu) {
        this.IDNguyenLieu = IDNguyenLieu;
    }

    public float getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(float SoLuong) {
        this.SoLuong = SoLuong;
    }
    
}



