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
public class ChiTietHoaDonNhapDTO {
    private String IDHoaDonNhap,IDNguyenLieu;
    private float SoLuong;
    private float GiaNhap,ThanhTien;
    public ChiTietHoaDonNhapDTO(String IDHoaDonNhap,String IDNguyenLieu,float SoLuong,float GiaNhap,float ThanhTien){
        this.IDHoaDonNhap = IDHoaDonNhap;
        this.IDNguyenLieu = IDNguyenLieu;
        this.SoLuong = SoLuong;
        this.GiaNhap=GiaNhap;
        this.ThanhTien=ThanhTien;
    }

    public ChiTietHoaDonNhapDTO() {
        
    }

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(float ThanhTien) {
        this.ThanhTien = ThanhTien;
    }
    
    public String getIDHoaDonNhap() {
        return IDHoaDonNhap;
    }

    public void setIDHoaDonNhap(String IDHoaDonNhap) {
        this.IDHoaDonNhap = IDHoaDonNhap;
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

    public float getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(float GiaNhap) {
        this.GiaNhap = GiaNhap;
    }
    
}






