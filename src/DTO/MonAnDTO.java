package DTO;
//main
/**
 *
 * @author Nguyen
 */
public class MonAnDTO {
    private String  IDMonAn,TenMonAn,DonViTinh,HinhAnh,Loai,TrangThai;
    private float DonGia;
    private float SoLuong;
    public MonAnDTO(String IDMonAn, String TenMonAn, String DonViTinh, float DonGia, String HinhAnh, String Loai, String TrangThai) {
        this.IDMonAn = IDMonAn;
        this.TenMonAn = TenMonAn;
        this.DonViTinh = DonViTinh;
        this.HinhAnh = HinhAnh;
        this.Loai = Loai;
        this.TrangThai = TrangThai;
        this.DonGia = DonGia;
    }
    //Mới của Nhân
    public MonAnDTO(String IDMonAn, String TenMonAn, String DonViTinh, float DonGia, String HinhAnh, String Loai) {
        this.IDMonAn = IDMonAn;
        this.TenMonAn = TenMonAn;
        this.DonViTinh = DonViTinh;
        this.DonGia = DonGia;
        this.HinhAnh = HinhAnh;
        this.Loai = Loai;
    }

    public MonAnDTO()
    {
        
    }
    public String getIDMonAn() {
        return IDMonAn;
    }

    public void setIDMonAn(String IDMonAn) {
        this.IDMonAn = IDMonAn;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String TenMonAn) {
        this.TenMonAn = TenMonAn;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float DonGia) {
        this.DonGia = DonGia;
    }

    public float getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(float SoLuong) {
        this.SoLuong = SoLuong;
    }

    

    
}



