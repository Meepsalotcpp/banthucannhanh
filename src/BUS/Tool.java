/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.*;
import GUI.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Tool {

    public static String IDNhanVienHienHanh = "";

    public static boolean isNumber(String number) { // kt có phải là số không
        try {
            if (Double.parseDouble(number) < 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean haveSpace(String string) {
        if (string.contains(" ")) {
            return true;
        }
        return false;
    }

    public static boolean haveNumber(String string) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static boolean isGmail(String gmail) {
        Pattern pattern = Pattern.compile("^[\\w.+\\-]+@gmail\\.com$");
        Matcher matcher = pattern.matcher(gmail);
        if (matcher.find()) {
            return true;
        }
        return false;

    }

    public static boolean ngayBDTruocNgayKT(String ngayBatDau, String ngayKetThuc) {
        LocalDate ngayTruoc = LocalDate.parse(ngayBatDau);
        LocalDate ngaySau = LocalDate.parse(ngayKetThuc);
        if (ngayTruoc.isBefore(ngaySau)) {
            return true;
        }
        return false;
    }

    public static boolean isTenThousandToOneMil(String donGia) {
        try {
            if (Integer.parseInt(donGia) > 1000000 || Integer.parseInt(donGia) < 0) {
                return false;
            }

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLength50(String ten) {
        if (ten.length() > 50 || ten.length() < 1) {
            return false;
        }
        return true;
    }

    public static boolean isOneToOneThousand(String donGia) {

        try {
            if (Integer.parseInt(donGia) > 1000 || Integer.parseInt(donGia) < 1) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isHinhAnh(String hinhAnh) {
        if (hinhAnh.contains(".png") || hinhAnh.contains(".jpg")) {
            return true;
        }
        return false;
    }

    public static boolean isSpecialChar(String specialChar) { // kiểm tra có chứa ký tự đặc biệt k
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9 ]*$");
        Matcher matcher = pattern.matcher(specialChar);
        if (matcher.find()) {
            return true;
        }
        return false;

    }

    public static boolean isName(String name) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9 ]");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return false;
        }
        return true;

    }

    public static boolean isTongTien(String name) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9. ]");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return false;
        }
        return true;

    }

    public static boolean isCongThuc(String congThuc) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9, ]");
        Matcher matcher = pattern.matcher(congThuc);
        if (matcher.find()) {
            return false;
        }
        return true;

    }

    public static boolean isPhoneNumber(String number) // số điện thoại có số đầu là 03 2-9 || 05 678 || 07 06-9 09
                                                       // 0-44-6
    {
        Pattern pattern = Pattern.compile(
                "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$");
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()) {
            return true;
        }
        return false;

    }

    public static boolean isMa(String ma) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(ma);
        if (matcher.find()) {
            return false;
        }
        return true;

    }

    public static boolean isDate(String date) {
        Pattern pattern = Pattern.compile("\\d{1,2}[-|/]\\d{1,2}[-|/]\\d{4}");
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            return true;
        }
        return false;

    }

    public static String chuanHoa(String string) {
        string = string.trim();
        string = string.replaceAll("\\s+", " ");
        return string;
    }

    public static boolean isDuplicateMaMonAn(String string) // kiểm tra trùng nhau hay không
    {
        for (int i = 0; i < MonAnBUS.dsMonAn.size(); i++) {
            if (MonAnBUS.dsMonAn.get(i).getIDMonAn().contains(string))
                return true;
        }
        return false;
    }

    public static boolean isDuplicateMaNhanVien(String string) // kiểm tra trùng nhau hay không
    {
        for (int i = 0; i < NhanVienBUS.dsnv.size(); i++) {
            if (NhanVienBUS.dsnv.get(i).getIDNhanVien().contains(string)
                    && NhanVienBUS.dsnv.get(i).getTrangThai().equals("Hiện"))
                return true;
        }
        return false;
    }

    public static String chuanHoaDanhTuRieng(String string) {
        string = chuanHoa(string);
        String temp[] = string.split(" ");
        string = ""; // ? ^-^
        for (int i = 0; i < temp.length; i++) {
            string += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) //
            {
                string += " ";
            }
        }
        return string;
    }

    public static ImageIcon showIcon(int width, int height, String fileName) {
        ImageIcon icon;
        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
            return icon;
        } catch (IOException e) {
            return null;
        }
    }

    private static final char[] SOURCE_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự', };

    private static final char[] DESTINATION_CHARACTERS = { 'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u', };

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    public static String removeAccent(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    /*
     * Original code of ArrayList searchMA:
     * 
     * public static ArrayList<MonAnDTO> searchMA(String value, double donGiaTu,
     * double donGiaDen, int soLuongTu, int soLuongDen) { //có thay đổi
     * // phương pháp tìm từ arraylist
     * ArrayList<MonAnDTO> result = new ArrayList<>();
     * //Tìm theo tên
     * MonAnBUS.dsMonAn.forEach((MonAnDTO) -> {
     * if ( MonAnDTO.getTenMonAn().toLowerCase().contains(value.toLowerCase()) &&
     * MonAnDTO.getTrangThai().equals("Hiện") //Tìm kiếm theo chuỗi thường
     * || removeAccent(MonAnDTO.getTenMonAn().toLowerCase()).contains(removeAccent(
     * value.toLowerCase())) && MonAnDTO.getTrangThai().equals("Hiện") )//Tìm kiếm
     * theo chữ VN
     * {
     * result.add(MonAnDTO);
     * }
     * });
     * 
     * for (int i = result.size() - 1; i >= 0; i--)
     * {
     * MonAnDTO monAn = result.get(i);
     * double donGia = monAn.getDonGia();
     * int soLuong = monAn.getSoLuong();
     * Boolean donGiaKhongThoa = (donGiaTu != -1 && donGia < donGiaTu ) ||
     * (donGiaDen != -1 && donGia > donGiaDen);
     * Boolean soLuongKhongThoa = (soLuongTu != -1 && soLuong < soLuongTu ) ||
     * (soLuongDen != -1 && soLuong > soLuongDen );
     * if (donGiaKhongThoa || soLuongKhongThoa)
     * {
     * result.remove(i);
     * }
     * }
     * return result;
     * }
     */
    public static ArrayList<MonAnDTO> searchMA(String value, double donGiaTu, double donGiaDen) { // có thay đổi
        // phương pháp tìm từ arraylist
        ArrayList<MonAnDTO> result = new ArrayList<>();
        // Tìm theo tên
        MonAnBUS.dsMonAn.forEach((MonAnDTO) -> {
            if (MonAnDTO.getTenMonAn().toLowerCase().contains(value.toLowerCase())
                    && MonAnDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                    || removeAccent(MonAnDTO.getTenMonAn().toLowerCase()).contains(removeAccent(value.toLowerCase()))
                            && MonAnDTO.getTrangThai().equals("Hiện"))// Tìm kiếm theo chữ VN
            {
                result.add(MonAnDTO);
            }
        });

        for (int i = result.size() - 1; i >= 0; i--) {
            MonAnDTO monAn = result.get(i);
            double donGia = monAn.getDonGia();
            float soLuong = monAn.getSoLuong();
            Boolean donGiaKhongThoa = (donGiaTu != -1 && donGia < donGiaTu) || (donGiaDen != -1 && donGia > donGiaDen);
            if (donGiaKhongThoa) {
                result.remove(i);
            }
        }
        return result;
    }

    public static ArrayList<NguyenLieuDTO> searchNL(String value, double donGiaTu, double donGiaDen, int soLuongTu,
            int soLuongDen) { // có thay đổi

        // phương pháp tìm từ arraylist
        ArrayList<NguyenLieuDTO> result = new ArrayList<>();

        NguyenLieuBUS.dsnl.forEach((NguyenLieuDTO) -> {
            if (NguyenLieuDTO.getTenNguyenLieu().toLowerCase().contains(value.toLowerCase())
                    && NguyenLieuDTO.getTrangThai().equals("Hiện")
                    || Tool.removeAccent(NguyenLieuDTO.getTenNguyenLieu().toLowerCase()).contains(
                            removeAccent(value.toLowerCase())) && NguyenLieuDTO.getTrangThai().equals("Hiện")) {
                result.add(NguyenLieuDTO);
            }
        });

        for (int i = result.size() - 1; i >= 0; i--) {
            NguyenLieuDTO monAn = result.get(i);
            double donGia = monAn.getDonGia();
            float soLuong = monAn.getSoLuong();
            Boolean donGiaKhongThoa = (donGiaTu != -1 && donGia < donGiaTu) || (donGiaDen != -1 && donGia > donGiaDen);
            Boolean soLuongKhongThoa = (soLuongTu != -1 && soLuong < soLuongTu)
                    || (soLuongDen != -1 && soLuong > soLuongDen);
            if (donGiaKhongThoa || soLuongKhongThoa) {
                result.remove(i);
            }
        }
        return result;
    }

    public static ArrayList<CongThucDTO> searchCT(String value, String type) {
        // phương pháp tìm từ arraylist
        ArrayList<CongThucDTO> result = new ArrayList<>();
        if (type.equals("Mã công thức"))
            // Tìm theo tên
            CongThucBUS.CT.forEach((CongThucDTO) -> {
                if (CongThucDTO.getIDCongThuc().toLowerCase().contains(value.toLowerCase())
                        && CongThucDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(CongThucDTO);
                }
            });
        if (type.equals("Mã món ăn"))
            // Tìm theo tên
            CongThucBUS.CT.forEach((CongThucDTO) -> {
                if (CongThucDTO.getIDMonAn().toLowerCase().contains(value.toLowerCase())
                        && CongThucDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(CongThucDTO);
                }
            });
        if (type.equals("Tên món ăn")){
            // Tìm theo tên
            MonAnBUS ma = new MonAnBUS();
            try {
                ma.docDSMonAn();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu");
            }
            CongThucBUS.CT.forEach((CongThucDTO) -> {
                String tenMA = ma.getMonAnDTO(CongThucDTO.getIDMonAn()).getTenMonAn().toLowerCase();
                if (tenMA.contains(value.toLowerCase())
                        && CongThucDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(CongThucDTO);
                }
            });
        }
        if (type.equals("Mô tả công thức"))
            // Tìm theo tên
            CongThucBUS.CT.forEach((CongThucDTO) -> {
                if (CongThucDTO.getMoTaCongThuc().toLowerCase().contains(value.toLowerCase())
                        && CongThucDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(CongThucDTO.getMoTaCongThuc().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && CongThucDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                // kiếm
                                                                                                                // theo
                                                                                                                // chữ
                                                                                                                // VN
                {
                    result.add(CongThucDTO);
                }
            });

        return result;
    }

    public static ArrayList<KhuyenMaiDTO> searchKM(String value, String tuNgayBatDau, String denNgayKetThuc,
            double tuTienGiam, double denTienGiam, String type) {
        // phương pháp tìm từ arraylist
        ArrayList<KhuyenMaiDTO> result = new ArrayList<>();
        if (type.equals("Mã khuyến mãi"))
            // Tìm theo tên
            KhuyenMaiBUS.dskm.forEach((KhuyenMaiDTO) -> {
                if (KhuyenMaiDTO.getIDKhuyenMai().toLowerCase().contains(value.toLowerCase())
                        && KhuyenMaiDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(KhuyenMaiDTO);
                }
            });
        if (type.equals("Tên chương trình"))
            // Tìm theo tên
            KhuyenMaiBUS.dskm.forEach((KhuyenMaiDTO) -> {
                if (KhuyenMaiDTO.getTenChuongTrinh().toLowerCase().contains(value.toLowerCase())
                        && KhuyenMaiDTO.getTrangThai().equals("Hiện")// Tìm kiếm theo chuỗi thường
                        || removeAccent(KhuyenMaiDTO.getTenChuongTrinh().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && KhuyenMaiDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                 // kiếm
                                                                                                                 // theo
                                                                                                                 // chữ
                                                                                                                 // VN
                {
                    result.add(KhuyenMaiDTO);
                }
            });
        if (type.equals("Nội dung"))
            // Tìm theo tên
            KhuyenMaiBUS.dskm.forEach((KhuyenMaiDTO) -> {
                if (KhuyenMaiDTO.getNoiDungGiamGia().toLowerCase().contains(value.toLowerCase())
                        && KhuyenMaiDTO.getTrangThai().equals("Hiện")// Tìm kiếm theo chuỗi thường
                        || removeAccent(KhuyenMaiDTO.getNoiDungGiamGia().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && KhuyenMaiDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                 // kiếm
                                                                                                                 // theo
                                                                                                                 // chữ
                                                                                                                 // VN
                {
                    result.add(KhuyenMaiDTO);
                }
            });

        for (int i = result.size() - 1; i >= 0; i--) {
            KhuyenMaiDTO khuyenMai = result.get(i);
            double tienGiam = khuyenMai.getTienGiam();
            Boolean tienGiamKhongThoa = (tuTienGiam != -1 && tienGiam < tuTienGiam)
                    || (denTienGiam != -1 && tienGiam > denTienGiam);
            if (tienGiamKhongThoa) {
                result.remove(i);
            }

        }

        for (int i = result.size() - 1; i >= 0; i--) {
            KhuyenMaiDTO khuyenMai = result.get(i);
            LocalDate ngayBatDau = khuyenMai.getNgayBatDau();
            LocalDate ngayKetThuc = khuyenMai.getNgayKetThuc();
            Boolean ngayBatDauKhongThoa = (!(tuNgayBatDau).equals("")
                    && ngayBatDau.isBefore(LocalDate.parse(tuNgayBatDau)));
            Boolean ngayKetThucKhongThoa = (!(denNgayKetThuc).equals("")
                    && ngayKetThuc.isAfter(LocalDate.parse(denNgayKetThuc)));
            if (ngayBatDauKhongThoa || ngayKetThucKhongThoa) {
                result.remove(i);
            }

        }

        return result;
    }

    public static ArrayList<KhachHangDTO> searchKH(String value, double tuTongChiTieu, double denTongChiTieu,
            String type) {
        // phương pháp tìm từ arraylist
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        if (type.equals("Mã khách hàng"))
            // Tìm theo tên
            KhachHangBUS.dskh.forEach((KhachHangDTO) -> {
                if (KhachHangDTO.getIDKhachHang().toLowerCase().contains(value.toLowerCase())
                        && KhachHangDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(KhachHangDTO);
                }
            });
        if (type.equals("Họ"))
            // Tìm theo tên
            KhachHangBUS.dskh.forEach((KhachHangDTO) -> {
                if (KhachHangDTO.getHoKhachHang().toLowerCase().contains(value.toLowerCase())
                        && KhachHangDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(KhachHangDTO.getHoKhachHang().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && KhachHangDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                 // kiếm
                                                                                                                 // theo
                                                                                                                 // chữ
                                                                                                                 // VN
                {
                    result.add(KhachHangDTO);
                }
            });
        if (type.equals("Tên"))
            // Tìm theo tên
            KhachHangBUS.dskh.forEach((KhachHangDTO) -> {
                if (KhachHangDTO.getTenKhachHang().toLowerCase().contains(value.toLowerCase())
                        && KhachHangDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(KhachHangDTO.getTenKhachHang().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && KhachHangDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                 // kiếm
                                                                                                                 // theo
                                                                                                                 // chữ
                                                                                                                 // VN
                {
                    result.add(KhachHangDTO);
                }
            });

        if (type.equals("SĐT"))
            // Tìm theo tên
            KhachHangBUS.dskh.forEach((KhachHangDTO) -> {
                if (KhachHangDTO.getSoDienThoai().toLowerCase().contains(value.toLowerCase())
                        && KhachHangDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(KhachHangDTO);
                }
            });
        for (int i = result.size() - 1; i >= 0; i--) {
            KhachHangDTO khachHang = result.get(i);
            double tongChiTieu = khachHang.getTongChiTieu();
            Boolean chiTieuKhongThoa = (tuTongChiTieu != -1 && tongChiTieu < tuTongChiTieu)
                    || (denTongChiTieu != -1 && tongChiTieu > denTongChiTieu);
            if (chiTieuKhongThoa) {
                result.remove(i);
            }

        }

        return result;
    }

    public static ArrayList<NhanVienDTO> searchNV(String value, String type) {
        // phương pháp tìm từ arraylist
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        if (type.equals("Mã nhân viên"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getIDNhanVien().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(NhanVienDTO);
                }
            });
        if (type.equals("Họ"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getHoNhanVien().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(NhanVienDTO.getHoNhanVien().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && NhanVienDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                // kiếm
                                                                                                                // theo
                                                                                                                // chữ
                                                                                                                // VN
                {
                    result.add(NhanVienDTO);
                }
            });
        if (type.equals("Tên"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getTenNhanVien().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(NhanVienDTO.getTenNhanVien().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && NhanVienDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                // kiếm
                                                                                                                // theo
                                                                                                                // chữ
                                                                                                                // VN
                {
                    result.add(NhanVienDTO);
                }
            });
        if (type.equals("Gmail"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getGmail().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường

                {
                    result.add(NhanVienDTO);
                }
            });
        if (type.equals("Giới tính"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getGioiTinh().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(NhanVienDTO.getGioiTinh().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && NhanVienDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                // kiếm
                                                                                                                // theo
                                                                                                                // chữ
                                                                                                                // VN
                {
                    result.add(NhanVienDTO);
                }
            });
        if (type.equals("SĐT"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getSoDienThoai().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(NhanVienDTO);
                }
            });
        if (type.equals("Chức vụ"))
            // Tìm theo tên
            NhanVienBUS.dsnv.forEach((NhanVienDTO) -> {
                if (NhanVienDTO.getChucVu().toLowerCase().contains(value.toLowerCase())
                        && NhanVienDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(NhanVienDTO.getChucVu().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && NhanVienDTO.getTrangThai().equals("Hiện"))

                {
                    result.add(NhanVienDTO);
                }
            });

        return result;
    }

    public static ArrayList<NhaCungCapDTO> searchNCC(String value, String type) {
        // phương pháp tìm từ arraylist
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        if (type.equals("Mã nhà cung cấp"))
            // Tìm theo tên
            NhaCungCapBUS.dsncc.forEach((NhaCungCapDTO) -> {
                if (NhaCungCapDTO.getIDNhaCungCap().toLowerCase().contains(value.toLowerCase())
                        && NhaCungCapDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(NhaCungCapDTO);
                }
            });
        if (type.equals("Tên"))
            // Tìm theo tên
            NhaCungCapBUS.dsncc.forEach((NhaCungCapDTO) -> {
                if (NhaCungCapDTO.getTenNhaCungCap().toLowerCase().contains(value.toLowerCase())
                        && NhaCungCapDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(NhaCungCapDTO.getTenNhaCungCap().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && NhaCungCapDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                  // kiếm
                                                                                                                  // theo
                                                                                                                  // chữ
                                                                                                                  // VN
                {
                    result.add(NhaCungCapDTO);
                }
            });

        if (type.equals("SĐT"))
            // Tìm theo tên
            NhaCungCapBUS.dsncc.forEach((NhaCungCapDTO) -> {
                if (NhaCungCapDTO.getSoDienThoai().toLowerCase().contains(value.toLowerCase())
                        && NhaCungCapDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(NhaCungCapDTO);
                }
            });
        if (type.equals("Gmail"))
            // Tìm theo tên
            NhaCungCapBUS.dsncc.forEach((NhaCungCapDTO) -> {
                if (NhaCungCapDTO.getGmail().toLowerCase().contains(value.toLowerCase())
                        && NhaCungCapDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường

                {
                    result.add(NhaCungCapDTO);
                }
            });
        if (type.equals("Địa chỉ"))
            // Tìm theo tên
            NhaCungCapBUS.dsncc.forEach((NhaCungCapDTO) -> {
                if (NhaCungCapDTO.getDiaChi().toLowerCase().contains(value.toLowerCase())
                        && NhaCungCapDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(NhaCungCapDTO.getDiaChi().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && NhaCungCapDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                  // kiếm
                                                                                                                  // theo
                                                                                                                  // chữ
                                                                                                                  // VN
                {
                    result.add(NhaCungCapDTO);
                }
            });

        return result;
    }

    public static ArrayList<TaiKhoanDTO> searchTK(String value, String type) {
        // phương pháp tìm từ arraylist
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        if (type.equals("Tài khoản"))
            // Tìm theo tài khoản
            TaiKhoanBUS.dstk.forEach((TaiKhoanDTO) -> {
                if (TaiKhoanDTO.getTaiKhoan().toLowerCase().contains(value.toLowerCase())
                        && TaiKhoanDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(TaiKhoanDTO);
                }
            });
        if (type.equals("Mã nhân viên"))
            // Tìm theo mã nhân viên
            TaiKhoanBUS.dstk.forEach((TaiKhoanDTO) -> {
                if (TaiKhoanDTO.getIDNhanVien().toLowerCase().contains(value.toLowerCase())
                        && TaiKhoanDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường

                {
                    result.add(TaiKhoanDTO);
                }
            });

        if (type.equals("Mã quyền"))
            // Tìm theo tên
            TaiKhoanBUS.dstk.forEach((TaiKhoanDTO) -> {
                if (TaiKhoanDTO.getIDPhanQuyen().toLowerCase().contains(value.toLowerCase())
                        && TaiKhoanDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(TaiKhoanDTO);
                }
            });
        if (type.equals("Mật khẩu"))
            // Tìm theo mật khẩu
            TaiKhoanBUS.dstk.forEach((TaiKhoanDTO) -> {
                if (TaiKhoanDTO.getMatKhau().toLowerCase().contains(value.toLowerCase())
                        && TaiKhoanDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường

                {
                    result.add(TaiKhoanDTO);
                }
            });

        return result;
    }

    public static ArrayList<PhanQuyenDTO> searchPQ(String value, String type) {
        // phương pháp tìm từ arraylist
        ArrayList<PhanQuyenDTO> result = new ArrayList<>();
        if (type.equals("Mã quyền"))
            // Tìm theo tên
            PhanQuyenBUS.dspq.forEach((PhanQuyenDTO) -> {
                if (PhanQuyenDTO.getIDPhanQuyen().toLowerCase().contains(value.toLowerCase())
                        && PhanQuyenDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(PhanQuyenDTO);
                }
            });
        if (type.equals("Tên quyền"))
            // Tìm theo tên
            PhanQuyenBUS.dspq.forEach((PhanQuyenDTO) -> {
                if (PhanQuyenDTO.getTenQuyen().toLowerCase().contains(value.toLowerCase())
                        && PhanQuyenDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                        || removeAccent(PhanQuyenDTO.getTenQuyen().toLowerCase()).contains(
                                removeAccent(value.toLowerCase())) && PhanQuyenDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                 // kiếm
                                                                                                                 // theo
                                                                                                                 // chữ
                                                                                                                 // VN
                {
                    result.add(PhanQuyenDTO);
                }
            });

        if (type.equals("Mô tả quyền"))
            // Tìm theo tên
            PhanQuyenBUS.dspq.forEach((PhanQuyenDTO) -> {
                if (PhanQuyenDTO.getMoTaQuyen().toLowerCase().contains(value.toLowerCase())
                        && PhanQuyenDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(PhanQuyenDTO);
                }
            });

        return result;

    }

    public static ArrayList<MonAnDTO> searchBH(String value) {
        // phương pháp tìm từ arraylist
        ArrayList<MonAnDTO> result = new ArrayList<>();

        // Tìm theo tên
        MonAnBUS.dsMonAn.forEach((MonAnDTO) -> {
            if (MonAnDTO.getTenMonAn().toLowerCase().contains(value.toLowerCase())
                    && MonAnDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                    || removeAccent(MonAnDTO.getTenMonAn().toLowerCase()).contains(removeAccent(value.toLowerCase()))
                            && MonAnDTO.getTrangThai().equals("Hiện"))// Tìm kiếm theo chữ VN
            {
                result.add(MonAnDTO);
            }
        });

        return result;
    }

    public static ArrayList<NguyenLieuDTO> searchNH(String value) {
        // phương pháp tìm từ arraylist
        ArrayList<NguyenLieuDTO> result = new ArrayList<>();

        // Tìm theo tên
        NguyenLieuBUS.dsnl.forEach((NguyenLieuDTO) -> {
            if (NguyenLieuDTO.getTenNguyenLieu().toLowerCase().contains(value.toLowerCase())
                    && NguyenLieuDTO.getTrangThai().equals("Hiện") // Tìm kiếm theo chuỗi thường
                    || removeAccent(NguyenLieuDTO.getTenNguyenLieu().toLowerCase())
                            .contains(removeAccent(value.toLowerCase())) && NguyenLieuDTO.getTrangThai().equals("Hiện"))// Tìm
                                                                                                                        // kiếm
                                                                                                                        // theo
                                                                                                                        // chữ
                                                                                                                        // VN
            {
                result.add(NguyenLieuDTO);
            }
        });

        return result;
    }

    public static boolean docDB() throws Exception {
        try {
            ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();
            ChiTietHoaDonNhapBUS cthdnBUS = new ChiTietHoaDonNhapBUS();
            ChiTietNguyenLieuBUS ctnlBUS = new ChiTietNguyenLieuBUS();
            CongThucBUS ctBUS = new CongThucBUS();
            HoaDonBUS hdBUS = new HoaDonBUS();
            HoaDonNhapBUS hdnBUS = new HoaDonNhapBUS();
            KhachHangBUS khBUS = new KhachHangBUS();
            KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();
            MonAnBUS maBUS = new MonAnBUS();
            NguyenLieuBUS nlBUS = new NguyenLieuBUS();
            NhaCungCapBUS nccBUS = new NhaCungCapBUS();
            NhanVienBUS nvBUS = new NhanVienBUS();
            PhanQuyenBUS pqBUS = new PhanQuyenBUS();
            TaiKhoanBUS tkBUS = new TaiKhoanBUS();
            cthdBUS.docCTHD();
            cthdnBUS.docCTHDN();
            ctnlBUS.docCTNL();
            ctBUS.docCT();
            hdBUS.docHD();
            hdnBUS.docHDN();
            khBUS.docDSKH();
            kmBUS.docDSKM();
            maBUS.docDSMonAn();
            nlBUS.docDSNL();
            nccBUS.docDSNCC();
            nvBUS.docDSNV();
            pqBUS.docDSPQ();
            tkBUS.docDSTK();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Hàm lấy ngày hiện tại
    public static LocalDate getNgayLap() {
        LocalDate ngayLap = java.time.LocalDate.now();
        return ngayLap;
    }

    // Hàm lấy giờ hiện tại
    public static LocalTime getGioLap() {
        LocalTime gioLap = java.time.LocalTime.now();
        return gioLap;
    }

    // Hàm tăng mã tự động khi thêm mới
    public static String tangMa(String ma) {
        // VD: HD12
        if (ma != null) {
            String maSo = ma.substring(2);// lấy 12
            String maChu = ma.substring(0, 2); // lấy HD
            int maSo1 = Integer.parseInt(maSo) + 1;// tăng 12+1
            if (maSo1 < 10) {
                String maMoi = maChu + "0" + maSo1;
                return maMoi;
            } else {
                String maMoi = maChu + maSo1;
                return maMoi;
            }

        }
        return null;
    }

    // Hàm tăng mã đặt biệt khi có đến 3 kí tự

    /**
     * this function is being edited (19/2/2025 - 20h)
     * @param ma: store the ID of latest supplier => If there are not any suppliers, it is null
     * The return value at the end of the funciton is changed
     * @param maMoi: new supplier's ID
     * Database store supplier's ID like "NCC1, NCC2,...", not "NCC01, NCC02" => maMoi is changed
     */
    public static String tangMa3(String ma) {
        // VD: NCC12
        if (ma != null) {
            String maChu = ma.substring(0, 3); // lấy NCC
            String maSo = ma.substring(3);// lấy 12
            int maSo1 = Integer.parseInt(maSo) + 1;// tăng 12+1
            if (maSo1 < 10) {
                String maMoi = maChu + "0" + maSo1;
                return maMoi;
            } else {
                String maMoi = maChu + maSo1;
                return maMoi;
            }
        }
        return null;
    }

    // mới thêm luôn
    public static ArrayList<HoaDonNhapDTO> searchHDN(String value, String tuNgayNhap, String denNgayNhap,
            double tuTongTien, double denTongTien, String type) {
        ArrayList<HoaDonNhapDTO> result = new ArrayList<>();
        if (type.equals("Mã hóa đơn")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            HoaDonNhapBUS.dshdn.forEach((HoaDonNhapDTO) -> {
                if (HoaDonNhapDTO.getIDHoaDonNhap().toLowerCase().contains(value.toLowerCase())
                        && HoaDonNhapDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonNhapDTO);
                }
            });
        }
        if (type.equals("Tên nhân viên")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            NhanVienBUS nvBUS = new NhanVienBUS();
            try {
                nvBUS.docDSNV();
            } catch (Exception ex) {
            }
            HoaDonNhapBUS.dshdn.forEach((HoaDonNhapDTO) -> {
                String tenNV = nvBUS.getNhanVienDTO(HoaDonNhapDTO.getIDNhanVien()).getHoNhanVien()+ " "
                + nvBUS.getNhanVienDTO(HoaDonNhapDTO.getIDNhanVien()).getTenNhanVien();
                if (tenNV.toLowerCase().contains(value.toLowerCase())
                        && HoaDonNhapDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonNhapDTO);
                }
            });
        }
        if (type.equals("Tên nhà cung cấp")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            NhaCungCapBUS nccBUS = new NhaCungCapBUS();
            try {
                nccBUS.docDSNCC();
            } catch (Exception ex) {
            }
            HoaDonNhapBUS.dshdn.forEach((HoaDonNhapDTO) -> {
                String tenNCC = nccBUS.getNhaCungCapDTO(HoaDonNhapDTO.getIDNhaCungCap()).getTenNhaCungCap();
                if (tenNCC.toLowerCase().contains(value.toLowerCase())
                        && HoaDonNhapDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonNhapDTO);
                }
            });
        }

        for (int i = result.size() - 1; i >= 0; i--) {
            HoaDonNhapDTO hoaDonNhap = result.get(i);
            double tongTien = hoaDonNhap.getTongTien();
            Boolean donGiaKhongThoa = (tuTongTien != -1 && tongTien < tuTongTien)
                    || (denTongTien != -1 && tongTien > denTongTien);
            if (donGiaKhongThoa) {
                result.remove(i);
            }

        }

        for (int i = result.size() - 1; i >= 0; i--) {
            HoaDonNhapDTO hoaDonNhap = result.get(i);
            LocalDate ngayNhap = hoaDonNhap.getNgayNhap();
            Boolean ngayNhapKhongThoa = (!(tuNgayNhap).equals("") && ngayNhap.isBefore(LocalDate.parse(tuNgayNhap)))
                    || (!(denNgayNhap).equals("") && ngayNhap.isAfter(LocalDate.parse(denNgayNhap)));
            if (ngayNhapKhongThoa) {
                result.remove(i);
            }

        }

        return result;
    }

    public static ArrayList<HoaDonDTO> searchHD(String value, String tuNgayLap, String denNgayLap, double tuTongTien,
            double denTongTien, String type) {
        ArrayList<HoaDonDTO> result = new ArrayList<>();
        if (type.equals("Mã hóa đơn")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            HoaDonBUS.HD.forEach((HoaDonDTO) -> {
                if (HoaDonDTO.getIDHoaDon().toLowerCase().contains(value.toLowerCase())
                        && HoaDonDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonDTO);
                }
            });
        }
        if (type.equals("Mã nhân viên")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            HoaDonBUS.HD.forEach((HoaDonDTO) -> {
                if (HoaDonDTO.getIDNhanVien().toLowerCase().contains(value.toLowerCase())
                        && HoaDonDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonDTO);
                }
            });
        }
        if (type.equals("Mã khách hàng")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            HoaDonBUS.HD.forEach((HoaDonDTO) -> {
                if (HoaDonDTO.getIDKhachHang().toLowerCase().contains(value.toLowerCase())
                        && HoaDonDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonDTO);
                }
            });
        }
        if (type.equals("Mã khuyến mãi")) {
            // duyệt xem mã giống mã nhập vào thì thêm vào arraylist result
            HoaDonBUS.HD.forEach((HoaDonDTO) -> {
                if (HoaDonDTO.getIDKhuyenMai().toLowerCase().contains(value.toLowerCase())
                        && HoaDonDTO.getTrangThai().equals("Hiện")) // Tìm kiếm theo chuỗi thường
                {
                    result.add(HoaDonDTO);
                }
            });
        }

        for (int i = result.size() - 1; i >= 0; i--) {
            HoaDonDTO hoaDon = result.get(i);
            double tongTien = hoaDon.getTongTien();
            Boolean donGiaKhongThoa = (tuTongTien != -1 && tongTien < tuTongTien)
                    || (denTongTien != -1 && tongTien > denTongTien);
            if (donGiaKhongThoa) {
                result.remove(i);
            }

        }

        for (int i = result.size() - 1; i >= 0; i--) {
            HoaDonDTO hoaDon = result.get(i);
            LocalDate ngayLap = hoaDon.getNgayLap();
            Boolean ngayNhapKhongThoa = (!(tuNgayLap).equals("") && ngayLap.isBefore(LocalDate.parse(tuNgayLap)))
                    || (!(denNgayLap).equals("") && ngayLap.isAfter(LocalDate.parse(denNgayLap)));
            if (ngayNhapKhongThoa) {
                result.remove(i);
            }

        }

        return result;
    }

}
