-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 12, 2022 lúc 10:14 AM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `banthucannhanhver01`
--

-- create schema banthucannhanhver01;
-- use banthucannhanhver01;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `IDHoaDon` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDMonAn` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `SoLuong` int(15) UNSIGNED NOT NULL,
  `DonGia` float UNSIGNED NOT NULL,
  `ThanhTien` float UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`IDHoaDon`, `IDMonAn`, `SoLuong`, `DonGia`, `ThanhTien`) VALUES
('HD01', 'MA01', 1, 25000, 25000),
('HD02', 'MA02', 3, 20000, 60000),
('HD03', 'MA02', 2, 20000, 40000),
('HD04', 'MA01', 1, 25000, 25000),
('HD05', 'MA07', 1, 40000, 40000),
('HD06', 'MA05', 2, 18000, 36000),
('HD07', 'MA02', 1, 20000, 20000),
('HD07', 'MA06', 2, 40000, 80000);




-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadonnhap`
--

CREATE TABLE `chitiethoadonnhap` (
  `IDHoaDonNhap` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDNguyenLieu` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `SoLuong` float UNSIGNED NOT NULL,
  `GiaNhap` float UNSIGNED NOT NULL,
  `ThanhTien` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadonnhap`
--

INSERT INTO `chitiethoadonnhap` (`IDHoaDonNhap`, `IDNguyenLieu`, `SoLuong`, `GiaNhap`, `ThanhTien`) VALUES
-- Hóa đơn nhập 01
('HDN01', 'NL01', 10, 20000, 200000),   -- Gạo
('HDN01', 'NL02', 10, 70000, 700000),   -- Thịt bò
('HDN01', 'NL03', 40, 50000, 2000000),  -- Thịt heo
('HDN01', 'NL04', 101, 4000, 404000),   -- Trứng
('HDN01', 'NL05', 21, 40000, 840000),   -- Rau củ
-- Hóa đơn nhập 02
('HDN02', 'NL06', 40, 70000, 2800000),  -- Thịt gà
('HDN02', 'NL07', 7, 30000, 210000),    -- Kim chi
('HDN02', 'NL08', 30, 15000, 450000),   -- Gia vị ướp
('HDN02', 'NL09', 100, 40000, 4000000), -- Mì
('HDN02', 'NL10', 123, 40000, 4920000), -- Xúc xích
-- Hóa đơn nhập 03
('HDN03', 'NL11', 52, 12000, 624000),   -- Bột mì
('HDN03', 'NL12', 20, 14000, 280000),   -- Bột chiên giòn
('HDN03', 'NL13', 30, 50000, 1500000),  -- Cá
('HDN03', 'NL14', 3, 21000, 63000),     -- Men nở
('HDN03', 'NL15', 13, 90000, 1170000),  -- Tôm
('HDN03', 'NL16', 41, 120000, 4920000), -- Mực
-- Hóa đơn nhập 04
('HDN04', 'NL17', 21, 65000, 1365000),  -- Phô mai
('HDN04', 'NL18', 14, 60000, 840000),   -- Thịt nguội
('HDN04', 'NL19', 100, 20000, 2000000), -- Nấm
('HDN04', 'NL20', 100, 25000, 2500000), -- Bột gạo
('HDN04', 'NL21', 10, 15000, 150000),   -- Hương vị đào
('HDN04', 'NL22', 10, 15000, 150000),   -- Hương vị dâu
('HDN04', 'NL23', 10, 15000, 150000),   -- Hương vị socola
('HDN04', 'NL24', 10, 15000, 150000),   -- Hương vị việt quất
('HDN04', 'NL26', 20, 40000, 800000),   -- Whipping cream
('HDN04', 'NL27', 40, 7000, 280000),    -- Khoai môn
('HDN04', 'NL28', 38, 10000, 380000),   -- Khoai tây
('HDN04', 'NL29', 28, 5000, 140000),   -- Cà chua
-- Hóa đơn nhập 05
('HDN05', 'NL30', 10, 250000, 2500000),  -- Bột cà phê
('HDN05', 'NL31', 20, 85000, 1700000),  -- Sữa đặc
('HDN05', 'NL32', 30, 32000, 960000),   -- Sữa tươi
('HDN05', 'NL33', 50, 20000, 1000000),  -- Nước đá viên
('HDN05', 'NL34', 100, 10000, 1000000), -- CocaCola
('HDN05', 'NL35', 50, 2200, 110000),    -- Nestea
('HDN05', 'NL36', 3, 260000, 780000),   -- Siro Blue Curacao
('HDN05', 'NL37', 50, 10000, 500000),   -- Sprite
('HDN05', 'NL38', 10, 120000, 1200000), -- Nước cốt chanh
('HDN05', 'NL39', 100, 1000, 100000),   -- Trà túi lọc
('HDN05', 'NL40', 50, 2000, 100000),    -- Trà đào
('HDN05', 'NL41', 20, 7000, 140000),    -- Quả đào
('HDN05', 'NL42', 50, 1500, 75000),     -- Trà vải
('HDN05', 'NL43', 20, 1500, 30000);     -- Quả vải


-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietnguyenlieu`
--

CREATE TABLE `chitietnguyenlieu` (
  `IDCongThuc` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDNguyenLieu` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `SoLuong` float UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietnguyenlieu`
--

INSERT INTO `chitietnguyenlieu` (`IDCongThuc`, `IDNguyenLieu`, `SoLuong`) VALUES
('CT01', 'NL01', 0.2), -- gạo
('CT01', 'NL02', 0.15), -- thịt bò
('CT01', 'NL04', 1), -- trứng
('CT01', 'NL05', 0.1), -- rau củ
('CT02', 'NL01', 0.2), -- gạo
('CT02', 'NL02', 0.15), -- thịt bò
('CT02', 'NL05', 0.1), -- rau củ
('CT03', 'NL01', 0.2), -- gạo
('CT03', 'NL04', 2), -- trứng
('CT03', 'NL05', 0.05), -- rau củ
('CT04', 'NL01', 0.2), -- gạo
('CT04', 'NL06', 0.2), -- thịt gà
('CT04', 'NL05', 0.035), -- rau củ
('CT05', 'NL01', 0.2), -- gạo
('CT05', 'NL03', 0.15), -- thịt heo
('CT05', 'NL07', 0.1), -- kim chi
('CT05', 'NL05', 0.05), -- rau củ
('CT06', 'NL06', 0.5), -- thịt gà
('CT06', 'NL08', 0.078), -- gia vị ướp
('CT07', 'NL06', 0.5), -- thịt gà
('CT07', 'NL08', 0.078), -- gia vị ướp
('CT07', 'NL07', 0.01), -- ớt
('CT08', 'NL06', 0.15), -- thịt gà
('CT08', 'NL08', 0.078), -- gia vị ướp
('CT08', 'NL05', 0.05), -- đậu hà lan
('CT09', 'NL09', 0.15), -- mì
('CT09', 'NL06', 0.15), -- thịt gà
('CT09', 'NL05', 0.05), -- rau củ
('CT10', 'NL09', 0.1), -- mì
('CT10', 'NL03', 0.15), -- thịt heo
('CT10', 'NL05', 0.05), -- rau củ
('CT11', 'NL09', 0.1), -- mì
('CT11', 'NL04', 1), -- trứng
('CT11', 'NL10', 0.07), -- xúc xích
('CT11', 'NL05', 0.078), -- rau củ
('CT12', 'NL11', 0.02), -- bột mì
('CT12', 'NL12', 0.02), -- bột chiên giòn
('CT12', 'NL13', 0.15), -- cá
('CT13', 'NL11', 0.25), -- bột mì
('CT13', 'NL15', 0.05), -- tôm
('CT13', 'NL16', 0.05), -- mực
('CT13', 'NL05', 0.15), -- rau và phô mai
('CT14', 'NL11', 0.25), -- bột mì
('CT14', 'NL14', 0.005), -- men nở
('CT14', 'NL05', 0.15), -- rau củ và phô mai
('CT15', 'NL11', 0.25), -- bột mì
('CT15', 'NL19', 0.05), -- nấm
('CT15', 'NL18', 0.1), -- thịt nguội và phô mai
('CT16', 'NL09', 0.2), -- mì
('CT16', 'NL29', 0.15), -- cà chua và thịt heo 
('CT17', 'NL11', 0.05), -- bột mì
('CT17', 'NL20', 0.01), -- bột gạo
('CT17', 'NL21', 0.15), -- đào
('CT18', 'NL11', 0.1), -- bột mì
('CT18', 'NL20', 0.03), -- bột gạo
('CT18', 'NL06', 0.15), -- thịt gà
('CT19', 'NL11', 0.1), -- bột mì
('CT19', 'NL12', 0.05), -- bột chiên giòn
('CT19', 'NL06', 0.15), -- thịt gà
('CT20', 'NL26', 0.05), -- whipping cream
('CT20', 'NL22', 0.05), -- dâu
('CT21', 'NL26', 0.05), -- whipping cream
('CT21', 'NL23', 0.05), -- socola
('CT22', 'NL26', 0.05), -- whipping cream
('CT22', 'NL24', 0.05), -- việt quất
('CT23', 'NL12', 0.04), -- bột chiên giòn
('CT23', 'NL28', 0.15), -- khoai tây
('CT24', 'NL11', 0.02), -- bột mì
('CT24', 'NL20', 0.02), -- bột gạo
('CT24', 'NL27', 0.1), -- khoai môn
('CT24', 'NL17', 0.03), -- phô mai
('CT25', 'NL11', 0.02), -- bột mì
('CT25', 'NL12', 0.03), -- bột chiên giòn
('CT25', 'NL16', 0.15), -- mực
('CT26', 'NL11', 0.03), -- bột mì
('CT26', 'NL12', 0.03), -- bột chiên
('CT26', 'NL17', 0.05), -- phô mai
('CT26', 'NL04', 1), -- trứng
('CT27', 'NL30', 0.025), -- bột cafe
('CT27', 'NL33', 0.1), -- nước đá viên
('CT28', 'NL30', 0.025),
('CT28', 'NL31', 0.015), -- sữa đặc
('CT28', 'NL32', 0.07), -- sữa tươi
('CT28', 'NL33', 0.1), -- nước đá viên
('CT29', 'NL34', 1), -- CocaCola
('CT29', 'NL33', 0.1), -- nước đá viên
('CT30', 'NL35', 1), -- Nestea
('CT30', 'NL33', 0.1), -- nước đá viên
('CT31', 'NL36', 0.03), -- Siro Blue Curacao
('CT31', 'NL37', 1), -- Sprite
('CT31', 'NL38', 0.01), -- Nước cốt chanh
('CT31', 'NL33', 0.1), -- Nước đá viên
('CT32', 'NL39', 1), -- Trà túi lọc
('CT32', 'NL38', 0.015), -- Nước cốt chanh
('CT32', 'NL33', 0.1), -- nước đá viên
('CT33', 'NL39', 1), -- Trà túi lọc
('CT33', 'NL33', 0.1), -- nước đá viên
('CT34', 'NL40', 1), -- Trà đào
('CT34', 'NL41', 0.5), -- Quả đào
('CT34', 'NL33', 0.1), -- nước đá viên
('CT35', 'NL42', 1), -- Trà vải
('CT35', 'NL43', 2), -- Quả vải
('CT35', 'NL33', 0.1); -- nước đá viên


-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `congthuc`
--

CREATE TABLE `congthuc` (
  `IDCongThuc` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDMonAn` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `MoTaCongThuc` text COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `congthuc`
--

INSERT INTO `congthuc` (`IDCongThuc`, `IDMonAn`, `MoTaCongThuc`, `TrangThai`) VALUES
('CT01', 'MA01', '0.2 Kg Gạo, 0.15 Kg Thịt bò, 1 Quả Trứng, 0.1 Kg Rau củ', 'Hiện'),
('CT02', 'MA02', '0.2 Kg Gạo, 0.15 Kg Thịt bò, 0.1 Kg Rau củ', 'Hiện'),
('CT03', 'MA03', '0.2 Kg Gạo, 2 Quả Trứng, 0.05 Kg Rau củ', 'Hiện'),
('CT04', 'MA04', '0.2 Kg Gạo, 0.2 Kg Thịt gà, 0.035 Kg Rau củ', 'Hiện'),
('CT05', 'MA05', '0.2 Kg Gạo, 0.15 Kg Thịt heo, 0.1 Kg Kim chi, 0.05 Kg Rau củ', 'Hiện'),
('CT06', 'MA06', '0.5 Kg Thịt gà, 0.078 Kg Gia vị ướp', 'Hiện'),
('CT07', 'MA07', '0.5 Kg Thịt gà, 0.01 Kg Ớt, 0.078 Kg Gia vị ướp', 'Hiện'),
('CT08', 'MA08', '0.15 Kg Thịt gà, 0.05 Kg Đậu hà lan, 0.078 Kg Gia vị ướp', 'Hiện'),
('CT09', 'MA09', '0.15 Kg Mì, 0.15 Kg Thịt gà, 0.05 Kg Rau củ', 'Hiện'),
('CT10', 'MA10', '0.1 Kg Mì, 0.15 Kg Thịt heo, 0.05 Kg Rau củ', 'Hiện'),
('CT11', 'MA11', '0.1 Kg Mì, 1 Quả Trứng, 0.07 Kg Xúc xích, 0.078 Kg Rau củ', 'Hiện'),
('CT12', 'MA12', '0.02 Kg Bột mì, 0.02 Kg Bột chiên giòn, 0.15 Kg Cá', 'Hiện'),
('CT13', 'MA13', '0.25 Kg Bột mì, 0.05 Kg Tôm, 0.05 Kg Mực, 0.15 Kg Rau và phô mai', 'Hiện'),
('CT14', 'MA14', '0.25 Kg Bột mì, 0.005 Kg Men nở, 0.15 Kg Rau củ và phô mai', 'Hiện'),
('CT15', 'MA15', '0.25 Kg Bột mì, 0.05 Kg Nấm, 0.1 Kg Thịt nguội và phô mai', 'Hiện'),
('CT16', 'MA16', '0.2 Kg Mì, 0.15 Kg Cà chua và thịt heo', 'Hiện'),
('CT17', 'MA17', '0.05 Kg Bột mì, 0.01 Kg Bột gạo, 0.15 Kg Đào', 'Hiện'),
('CT18', 'MA18', '0.1 Kg Bột mì, 0.03 Kg Bột gạo, 0.15 Kg Thịt gà', 'Hiện'),
('CT19', 'MA19', '0.1 Kg Bột mì, 0.05 Kg Bột chiên giòn, 0.15 Kg Thịt gà', 'Hiện'),
('CT20', 'MA20', '0.05 Kg Whipping cream, 0.05 Kg Dâu', 'Hiện'),
('CT21', 'MA21', '0.05 Kg Whipping cream, 0.05 Kg Socola', 'Hiện'),
('CT22', 'MA22', '0.05 Kg Whipping cream, 0.05 Kg Việt quất', 'Hiện'),
('CT23', 'MA23', '0.04 Kg Bột chiên giòn, 0.15 Kg Khoai tây', 'Hiện'),
('CT24', 'MA24', '0.02 Kg Bột mì và bột gạo, 0.1 Kg Khoai môn, 0.03 Kg Phô mai', 'Hiện'),
('CT25', 'MA25', '0.02 Kg Bột mì, 0.03 Kg Bột chiên giòn, 0.15 Kg Mực', 'Hiện'),
('CT26', 'MA26', '0.03 Kg Bột mì và bột chiên, 0.05 Kg Phô mai, 1 Quả Trứng', 'Hiện'),
('CT27', 'MA27', '0.025 Kg Bột cafe, 0.1 Kg Nước đá', 'Hiện'),
('CT28', 'MA28', '0.025 Kg Bột cafe, 0.015 Lít Sữa đặc, 0.07 Lít Sữa tươi, 0.1 Kg Nước đá', 'Hiện'),
('CT29', 'MA29', '1 Lon CocaCola, 0.1 Kg Nước đá', 'Hiện'),
('CT30', 'MA30', '1 Bịch Nestea 0.1 Kg Nước đá', 'Hiện'),
('CT31', 'MA31', '0.03 Lít Siro Blue Curacao, 1 Lon Sprite, 0.01 Lít Nước cốt chanh, 0.1 Kg Nước đá', 'Hiện'),
('CT32', 'MA32', '1 Bịch Trà túi lọc, 0.015 Lít Nước cốt chanh, 0.1 Kg Nước đá', 'Hiện'),
('CT33', 'MA33', '1 Bịch Trà túi lọc, 0.1 Kg Nước đá', 'Hiện'),
('CT34', 'MA34', '1 Bịch Trà đào, 0.5 Quả Đào, 0.1 Kg Nước đá', 'Hiện'),
('CT35', 'MA35', '1 Bịch Trà vải, 2 Quả Vải, 0.1 Kg Nước đá', 'Hiện');


-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `IDHoaDon` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDNhanVien` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDKhachHang` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `IDKhuyenMai` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NgayLap` date NOT NULL,
  `TienGiamGia` float UNSIGNED NOT NULL,
  `TongTien` float UNSIGNED NOT NULL,
  `TienTra` float UNSIGNED NOT NULL,
  `TienThoi` float UNSIGNED NOT NULL,
  `TrangThai` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`IDHoaDon`, `IDNhanVien`, `IDKhachHang`, `IDKhuyenMai`, `NgayLap`, `TienGiamGia`, `TongTien`, `TienTra`, `TienThoi`, `TrangThai`) VALUES
('HD01', 'NV01', 'KH03', 'KM01', '2020-05-11', 0, 25000, 25000, 0, 'Hiện'),
('HD02', 'NV01', 'KH01', 'KM01', '2021-05-08', 0, 60000, 70000, 10000, 'Hiện'),
('HD03', 'NV01', 'KH02', 'KM01', '2022-05-08', 0, 40000, 50000, 10000, 'Hiện'),
('HD04', 'NV01', 'KH01', 'KM01', '2022-05-04', 0, 25000, 30000, 5000, 'Hiện'),
('HD05', 'NV01', 'KH10', 'KM01', '2022-05-01', 0, 40000, 50000, 10000, 'Hiện'),
('HD06', 'NV01', 'KH05', 'KM01', '2022-06-23', 0, 36000, 40000, 4000, 'Hiện'),
('HD07', 'NV01', 'KH04', 'KM01', '2022-04-30', 0, 100000, 100000, 0, 'Hiện');


-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadonnhap`
--

CREATE TABLE `hoadonnhap` (
  `IDHoaDonNhap` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `IDNhanVien` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `IDNhaCungCap` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `NgayNhap` date NOT NULL,
  `TongTien` float UNSIGNED NOT NULL,
  `TrangThai` varchar(10) NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `hoadonnhap`
--

INSERT INTO `hoadonnhap` (`IDHoaDonNhap`, `IDNhanVien`, `IDNhaCungCap`, `NgayNhap`, `TongTien`, `TrangThai`) VALUES
('HDN01', 'NV01', 'NCC02', '2022-05-06', 2643000, 'Hiện'),
('HDN02', 'NV01', 'NCC04', '2022-03-23', 2727000, 'Hiện'),
('HDN03', 'NV01', 'NCC01', '2022-06-10', 2582000, 'Hiện'),
('HDN04', 'NV01', 'NCC06', '2022-07-15', 1585000, 'Hiện'),
('HDN05', 'NV01', 'NCC05', '2022-07-15', 10195000, 'Hiện');


-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `IDKhachHang` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `HoKhachHang` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TenKhachHang` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SoDienThoai` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TongChiTieu` float UNSIGNED NOT NULL,
  `TrangThai` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`IDKhachHang`, `HoKhachHang`, `TenKhachHang`, `SoDienThoai`, `TongChiTieu`, `TrangThai`) VALUES
('KH01', 'Lê', 'Lợi', '0985444326', 200000, 'Hiện'),
('KH02', 'Phan Bội', 'A', '0903121212', 5000000, 'Hiện'),
('KH03', 'Lê Văn', 'Long', '0823232323', 1000000, 'Hiện'),
('KH04', 'Nguyễn Thị', 'A', '0902030401', 690000, 'Hiện'),
('KH05', 'Noor A Kim', 'Lam', '0677010101', 60000, 'Hiện'),
('KH06', 'Nguyễn Minh Phương', 'Nam', '0867010101', 0, 'Hiện'),
('KH07', 'Nguyễn Hữu', 'Nhân', '0967010102', 0, 'Hiện'),
('KH08', 'Đặng Thị Kiều', 'Oanh', '0967017105', 0, 'Hiện'),
('KH09', 'Nguyễn Thị Ngọc', 'Giàu', '0867010324', 0, 'Hiện'),
('KH10', 'Đặng Cao Phúc', 'Hòa', '0767010132', 80000, 'Hiện'),
('KH11', 'Trần Ngọc Huy', 'Hoàng', '0677018963', 0, 'Hiện'),
('KH12', 'Nguyễn Mai Kim ', 'Ngân', '0977075320', 0, 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `IDKhuyenMai` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `TenChuongTrinh` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `TienGiam` float NOT NULL,
  `NgayBatDau` date DEFAULT NULL,
  `NgayKetThuc` date DEFAULT NULL,
  `NoiDungGiamGia` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khuyenmai`
--

INSERT INTO `khuyenmai` (`IDKhuyenMai`, `TenChuongTrinh`, `TienGiam`, `NgayBatDau`, `NgayKetThuc`, `NoiDungGiamGia`, `TrangThai`) VALUES
('KM01', 'Không khuyến mãi', 0, '2022-03-24', '9799-05-11', 'Áp dụng cho những ngày thường', 'Hiện'),
('KM02', 'Lễ 30/4', 30000, '2022-04-27', '2022-05-03', 'Mừng ngày lễ 30 tháng 4', 'Hiện'),
('KM03', 'Tết Dương lịch', 50000, '2021-12-27', '2022-01-04', 'Nhân ngày Tết Dương lịch ', 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `monan`
--

CREATE TABLE `monan` (
  `IDMonAn` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `TenMonAn` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DonViTinh` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `DonGia` float UNSIGNED NOT NULL,
  `HinhAnh` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Loai` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `monan`
--

INSERT INTO monan (IDMonAn, TenMonAn, DonViTinh, DonGia, HinhAnh, Loai, TrangThai) VALUES
('MA01', 'Cơm Bò Trứng', 'Phần', 25000, 'Cơm Bò Trứng.jpg', 'Món chính',  'Hiện'),
('MA02', 'Cơm Bò xào Đậu que', 'Dĩa', 20000, 'Cơm Bò xào Đậu que.jpg', 'Món chính',  'Hiện'),
('MA03', 'Cơm chiên Trứng', 'Dĩa', 15000, 'Cơm chiên Trứng.jpg', 'Món chính', 'Hiện'),
('MA04', 'Cơm gà Sốt Cà chua', 'Dĩa', 21000, 'Cơm gà Sốt Cà chua.jpg', 'Món chính', 'Hiện'),
('MA05', 'Cơm Thịt heo xào Kim Chi', 'Dĩa', 18000, 'Cơm Thịt heo xào Kim Chi.jpg', 'Món chính', 'Hiện'),
('MA06', 'Gà Nướng', 'Phần', 40000, 'Gà Nướng.jpg', 'Món chính', 'Hiện'),
('MA07', 'Gà sốt Cay', 'Phần', 40000, 'Gà sốt Cay.jpg', 'Món chính', 'Hiện'),
('MA08', 'Gà sốt Đậu', 'Phần', 15000, 'Gà sốt Đậu.jpg', 'Món chính', 'Hiện'),
('MA09', 'Mì gà quay', 'Dĩa', 20000, 'Mì gà quay.jpg', 'Món chính', 'Hiện'),
('MA10', 'Mì trộn thịt heo xào', 'Dĩa', 15000, 'Mì trộn thịt heo xào.jpg', 'Món chính', 'Hiện'),
('MA11', 'Mì trộn Trứng Xúc xích', 'Dĩa', 15000, 'Mì trộn Trứng Xúc xích.jpg', 'Món chính', 'Hiện'),
('MA12', 'Phi lê Cá rán', 'Phần', 9000, 'Phi lê Cá rán.jpg', 'Món chính', 'Hiện'),
('MA13', 'Pizza Hải sản', 'Phần', 21000, 'Pizza Hải sản.jpg', 'Món chính', 'Hiện'),
('MA14', 'Pizza Rau củ', 'Phần', 10000, 'Pizza Rau củ.jpg', 'Món chính', 'Hiện'),
('MA15', 'Pizza Thịt nguội và Nấm', 'Phần', 11000, 'Pizza Thịt nguội và Nấm.jpg', 'Món chính', 'Hiện'),
('MA16', 'Spagghetti', 'Dĩa', 10000, 'Spagghetti.jpg', 'Món chính', 'Hiện'),
('MA17', 'Bánh Pie vị Đào', 'Phần', 3000, 'Bánh Pie vị Đào.jpg', 'Món phụ', 'Hiện'),
('MA18', 'Bánh xếp Gà quay', 'Phần', 14000, 'Bánh xếp Gà quay.jpg', 'Món phụ', 'Hiện'),
('MA19', 'Gà nướng 3 vị nhân nhồi', 'Phần', 14000, 'Gà nướng 3 vị nhân nhồi.jpg', 'Món phụ', 'Hiện'),
('MA20', 'Kem Dâu', 'Phần', 3000, 'Kem Dâu.jpg', 'Món phụ', 'Hiện'),
('MA21', 'Kem Socola', 'Phần', 3000, 'Kem Socola.jpg', 'Món phụ', 'Hiện'),
('MA22', 'Kem Việt quất', 'Phần', 3000, 'Kem Việt quất.jpg', 'Món phụ', 'Hiện'),
('MA23', 'Khoai tây chiên', 'Phần', 2000, 'Khoai tây chiên.jpg', 'Món phụ', 'Hiện'),
('MA24', 'Khoai viên Phô mai', 'Phần', 4000, 'Khoai viên Phô mai.jpg', 'Món phụ', 'Hiện'),
('MA25', 'Mực rán', 'Phần', 21000, 'Mực rán.jpg', 'Món phụ', 'Hiện'),
('MA26', 'Phô mai que', 'Phần', 9000, 'Phô mai que.jpg', 'Món phụ', 'Hiện'),
('MA27', 'Cà phê Đen', 'Ly', 9000, 'Cà phê Đen.jpg', 'Nước uống', 'Hiện'),
('MA28', 'Cà phê Sữa', 'Ly', 13000, 'Cà phê Sữa.jpg', 'Nước uống', 'Hiện'),
('MA29', 'CocaCola', 'Ly', 13000, 'CocaCola.jpg', 'Nước uống', 'Hiện'),
('MA30', 'Nestea', 'Ly', 5000, 'Nestea.jpg', 'Nước uống', 'Hiện'),
('MA31', 'Soda Blue Sky', 'Ly', 23000, 'Soda Blue Sky.jpg', 'Nước uống', 'Hiện'),
('MA32', 'Trà Chanh', 'Ly', 5000, 'Trà Chanh.jpg', 'Nước uống', 'Hiện'),
('MA33', 'Trà đá', 'Ly', 3000, 'Trà đá.jpg', 'Nước uống', 'Hiện'),
('MA34', 'Trà Đào', 'Ly', 8000, 'Trà Đào.jpg', 'Nước uống', 'Hiện'),
('MA35', 'Trà Vải', 'Ly', 7000, 'Trà Vải.jpg', 'Nước uống', 'Hiện');



-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguyenlieu`
--

CREATE TABLE `nguyenlieu` (
  `IDNguyenLieu` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `TenNguyenLieu` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DonGia` float UNSIGNED NOT NULL,
  `HinhAnh` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `DonViTinh` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `SoLuong` float NOT NULL,
  `TrangThai` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nguyenlieu`
--

INSERT INTO nguyenlieu (IDNguyenLieu, TenNguyenLieu, DonGia, HinhAnh,DonViTinh, SoLuong, TrangThai) VALUES
('NL01', 'Gạo', 22000, 'Gạo.jpg', 'Kg', 8, 'Hiện'),
('NL02', 'Thịt bò', 77000, 'Thịt bò.jpg', 'Kg', 8.8, 'Hiện'),
('NL03', 'Thịt heo', 55000, 'Thịt heo.jpg', 'Kg', 39.7, 'Hiện'),
('NL04', 'Trứng', 4400, 'Trứng.jpg', 'Quả', 99, 'Hiện'),
('NL05', 'Rau củ', 44000, 'Rau củ.jpg', 'Kg', 20.1, 'Hiện'),
('NL06', 'Thịt gà', 77000, 'Thịt gà.jpg', 'Kg', 38.5, 'Hiện'),
('NL07', 'Kim chi', 33000, 'Kim chi.jpg', 'Kg', 6.79, 'Hiện'),
('NL08', 'Gia vị ướp', 16500, 'Gia vị ướp.jpg', 'Kg', 29.766, 'Hiện'),
('NL09', 'Mì', 44000, 'Mì.jpg', 'Kg', 100, 'Hiện'),
('NL10', 'Xúc xích', 44000, 'Xúc xích.jpg', 'Kg', 123, 'Hiện'),
('NL11', 'Bột mì', 13200, 'Bột mì.jpg', 'Kg', 52, 'Hiện'),
('NL12', 'Bột chiên giòn', 15400, 'Bột chiên giòn.jpg', 'Bịch', 20, 'Hiện'),
('NL13', 'Cá', 55000, 'Cá.jpg', 'Kg', 30, 'Hiện'),
('NL14', 'Men nở', 23100, 'Men nở.jpg', 'Kg', 3, 'Hiện'),
('NL15', 'Tôm', 99000, 'Tôm.jpg', 'Kg', 13, 'Hiện'),
('NL16', 'Mực', 132000, 'Mực.jpg', 'Kg', 41, 'Hiện'),
('NL17', 'Phô mai', 71500, 'Phô mai.jpg', 'Kg', 21, 'Hiện'),
('NL18', 'Thịt nguội', 66000, 'Thịt nguội.jpg', 'Kg', 14, 'Hiện'),
('NL19', 'Nấm', 22000, 'Nấm.jpg', 'Kg', 100, 'Hiện'),
('NL20', 'Bột gạo', 27500, 'Bột gạo.jpg', 'Kg', 100, 'Hiện'),
('NL21', 'Hương vị đào', 16500, 'Hương vị đào.jpg', 'Kg', 10, 'Hiện'),
('NL22', 'Hương vị dâu', 16500, 'Hương vị dâu.jpg', 'Kg', 10, 'Hiện'),
('NL23', 'Hương vị socola', 16500, 'Hương vị socola.jpg', 'Kg', 10, 'Hiện'),
('NL24', 'Hương vị việt quất', 16500, 'Hương vị việt quất.jpg', 'Kg', 10, 'Hiện'),
('NL26', 'Whipping cream', 44000, 'Whipping cream.jpg', 'Kg', 20, 'Hiện'),
('NL27', 'Khoai môn', 7700, 'Khoai môn.jpg', 'Kg', 40, 'Hiện'),
('NL28', 'Khoai tây', 11000, 'Khoai tây.jpg', 'Kg', 38, 'Hiện'),
('NL29', 'Cà chua', 5500, 'Cà chua.jpg', 'Quả', 28, 'Hiện'),
('NL30', 'Bột cà phê', 275000, 'Bột cà phê.jpg', 'Kg', 10, 'Hiện'),
('NL31', 'Sữa đặc', 93500, 'Sữa đặc.png', 'Lít', 20, 'Hiện'),
('NL32', 'Sữa tươi', 35200, 'Sữa tươi.jpg', 'Lít', 30, 'Hiện'),
('NL33', 'Nước đá viên', 22000, 'Nước đá viên.jpg', 'Kg', 50, 'Hiện'),
('NL34', 'CocaCola', 11000, 'LonCocaCola.jpg', 'Lon', 100, 'Hiện'),
('NL35', 'Nestea', 2420, 'GoiNestea.jpg', 'Bịch', 50, 'Hiện'),
('NL36', 'Siro Blue Curacao', 286000, 'Siro Blue Curacao.jpg', 'Lít', 3, 'Hiện'),
('NL37', 'Sprite', 11000, 'Sprite.jpg', 'Lon', 50, 'Hiện'),
('NL38', 'Nước cốt chanh', 132000, 'Nước cốt chanh.jpg', 'Lít', 10, 'Hiện'),
('NL39', 'Trà túi lọc', 1100, 'Trà túi lọc.jpg', 'Bịch', 100, 'Hiện'),
('NL40', 'Trà đào', 2200, 'Gói Trà đào.jpg', 'Bịch', 50, 'Hiện'),
('NL41', 'Quả đào', 7700, 'Quả đào.jpg', 'Quả', 20, 'Hiện'),
('NL42', 'Trà vải', 1650, 'Gói Trà vải.jpg', 'Bịch', 50, 'Hiện'),
('NL43', 'Quả vải', 1650, 'Quả vải.jpg', 'Quả', 20, 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `IDNhaCungCap` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TenNhaCungCap` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SoDienThoai` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Gmail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `DiaChi` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`IDNhaCungCap`, `TenNhaCungCap`, `SoDienThoai`, `Gmail`, `DiaChi`, `TrangThai`) VALUES
('NCC01', 'Circle K', '0923515615', 'CircleK@gmail.com', 'a', 'Hiện'),
('NCC02', 'GS25', '0851565488', 'GS25@gmail.com', 'a', 'Hiện'),
('NCC03', 'FamilyMart', '0765166515', 'FamilyMart@gmail.com', 'a', 'Hiện'),
('NCC04', '7- Eleven', '0916155649', '7Eleven@gmail.com', 'a', 'Hiện'),
('NCC05', 'Ministop', '0821564168', 'Ministop@gmail.com', 'a', 'Hiện'),
('NCC06', 'Cheers', '0935165165', 'Cheers@gmail.com', 'a', 'Hiện'),
('NCC07', 'Co.op Smile', '0916544845', 'CoopSmile@gmail.com', 'a', 'Hiện'),
('NCC08', 'Speed L', '0851515684', 'SpeedL@gmail.com', 'a', 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `IDNhanVien` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `HoNhanVien` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TenNhanVien` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Gmail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `GioiTinh` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SoDienThoai` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ChucVu` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`IDNhanVien`, `HoNhanVien`, `TenNhanVien`, `Gmail`, `GioiTinh`, `SoDienThoai`, `ChucVu`, `TrangThai`) VALUES
('NV01', 'Nguyễn Văn', 'Đốc', 'docnguyen@gmail.com', 'Nam', '0987654321', 'Quản lý', 'Hiện'),
('NV02', 'Trần Văn', 'Bảo', 'baotran@gmail.com', 'Nam', '0945897412', 'Bán hàng', 'Hiện'),
('NV03', 'Lý Thị', 'Thơ', 'tholy@gmail.com', 'Nam', '0888454321', 'Nhập hàng', 'Hiện'),
('NV04', 'Lê Thị Kim', 'Dung', 'dungle@gmail.com', 'Nam', '0987654322', 'Admin', 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phanquyen`
--

CREATE TABLE `phanquyen` (
  `IDPhanQuyen` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `TenQuyen` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `MoTaQuyen` text COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `phanquyen`
--

INSERT INTO `phanquyen` (`IDPhanQuyen`, `TenQuyen`, `MoTaQuyen`, `TrangThai`) VALUES
('PQ00', 'Quản lý', 'QLBanHangQLNhapHangQLMonAnQLNguyenLieuQLCongThucQLHoaDonQLHDNhapQLKhuyenMaiQLKhachHangQLNhanVienQLNhaCungCapQLTaiKhoanQLPhanQuyenQLThongKe', 'Hiện'),
('PQ01', 'Bán hàng', 'QLBanHangQLHoaDon', 'Hiện'),
('PQ02', 'Nhập hàng', 'QLNhapHangQLNguyenLieuQLHDNhap', 'Hiện'),
('PQ03', 'Admin', 'QLTaiKhoanQLPhanQuyen', 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoannhanvien`
--

CREATE TABLE `taikhoannhanvien` (
  `TaiKhoan` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `IDNhanVien` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `IDPhanQuyen` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MatKhau` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TrangThai` varchar(10) NOT NULL DEFAULT 'Hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `taikhoannhanvien`
--

INSERT INTO `taikhoannhanvien` (`TaiKhoan`, `IDNhanVien`, `IDPhanQuyen`, `MatKhau`, `TrangThai`) VALUES
('admin', 'NV01', 'PQ00', 'admin', 'Hiện'),
('TranVanBao', 'NV02', 'PQ01', '123456', 'Hiện'),
('LyThiTho', 'NV03', 'PQ02', '123456', 'Hiện'),
('LeThiKimDung', 'NV04', 'PQ03', '123456', 'Hiện');


--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD UNIQUE KEY `IDHoaDon_3` (`IDHoaDon`,`IDMonAn`,`SoLuong`,`DonGia`,`ThanhTien`),
  ADD KEY `IDHoaDon` (`IDHoaDon`),
  ADD KEY `IDMonAn` (`IDMonAn`),
  ADD KEY `IDHoaDon_2` (`IDHoaDon`);



--
-- Chỉ mục cho bảng `chitiethoadonnhap`
--
ALTER TABLE `chitiethoadonnhap`
  ADD UNIQUE KEY `IDHoaDonNhap_2` (`IDHoaDonNhap`,`IDNguyenLieu`,`SoLuong`,`GiaNhap`,`ThanhTien`),
  ADD KEY `IDHoaDonNhap` (`IDHoaDonNhap`),
  ADD KEY `IDNguyenLieu` (`IDNguyenLieu`);

--
-- Chỉ mục cho bảng `chitietnguyenlieu`
--
ALTER TABLE `chitietnguyenlieu`
  ADD UNIQUE KEY `IDCongThuc_2` (`IDCongThuc`,`IDNguyenLieu`,`SoLuong`),
  ADD KEY `IDCongThuc` (`IDCongThuc`),
  ADD KEY `IDNguyenLieu` (`IDNguyenLieu`);

--
-- Chỉ mục cho bảng `congthuc`
--
ALTER TABLE `congthuc`
  ADD PRIMARY KEY (`IDCongThuc`),
  ADD KEY `IDMonAn` (`IDMonAn`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`IDHoaDon`),
  ADD KEY `IDNhanVien` (`IDNhanVien`),
  ADD KEY `IDKhachHang` (`IDKhachHang`),
  ADD KEY `IDGiamGia` (`IDKhuyenMai`);

--
-- Chỉ mục cho bảng `hoadonnhap`
--
ALTER TABLE `hoadonnhap`
  ADD PRIMARY KEY (`IDHoaDonNhap`),
  ADD KEY `IDNhanVien` (`IDNhanVien`),
  ADD KEY `IDNhaCungCap` (`IDNhaCungCap`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`IDKhachHang`);

--
-- Chỉ mục cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`IDKhuyenMai`);

--
-- Chỉ mục cho bảng `monan`
--
ALTER TABLE `monan`
  ADD PRIMARY KEY (`IDMonAn`);

--
-- Chỉ mục cho bảng `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  ADD PRIMARY KEY (`IDNguyenLieu`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`IDNhaCungCap`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`IDNhanVien`);

--
-- Chỉ mục cho bảng `phanquyen`
--
ALTER TABLE `phanquyen`
  ADD PRIMARY KEY (`IDPhanQuyen`);

--
-- Chỉ mục cho bảng `taikhoannhanvien`
--
ALTER TABLE `taikhoannhanvien`
  ADD PRIMARY KEY (`TaiKhoan`),
  ADD KEY `IDPhanQuyen` (`IDPhanQuyen`),
  ADD KEY `IDNhanVien` (`IDNhanVien`) USING BTREE;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`IDMonAn`) REFERENCES `monan` (`IDMonAn`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`IDHoaDon`) REFERENCES `hoadon` (`IDHoaDon`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `chitiethoadonnhap`
--
ALTER TABLE `chitiethoadonnhap`
  ADD CONSTRAINT `chitiethoadonnhap_ibfk_1` FOREIGN KEY (`IDHoaDonNhap`) REFERENCES `hoadonnhap` (`IDHoaDonNhap`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `chitiethoadonnhap_ibfk_2` FOREIGN KEY (`IDNguyenLieu`) REFERENCES `nguyenlieu` (`IDNguyenLieu`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `chitietnguyenlieu`
--
ALTER TABLE `chitietnguyenlieu`
  ADD CONSTRAINT `chitietnguyenlieu_ibfk_1` FOREIGN KEY (`IDNguyenLieu`) REFERENCES `nguyenlieu` (`IDNguyenLieu`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `chitietnguyenlieu_ibfk_2` FOREIGN KEY (`IDCongThuc`) REFERENCES `congthuc` (`IDCongThuc`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `congthuc`
--
ALTER TABLE `congthuc`
  ADD CONSTRAINT `congthuc_ibfk_1` FOREIGN KEY (`IDMonAn`) REFERENCES `monan` (`IDMonAn`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`IDKhuyenMai`) REFERENCES `khuyenmai` (`IDKhuyenMai`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`IDKhachHang`) REFERENCES `khachhang` (`IDKhachHang`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `hoadon_ibfk_3` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`IDNhanVien`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `hoadonnhap`
--
ALTER TABLE `hoadonnhap`
  ADD CONSTRAINT `hoadonnhap_ibfk_1` FOREIGN KEY (`IDNhaCungCap`) REFERENCES `nhacungcap` (`IDNhaCungCap`),
  ADD CONSTRAINT `hoadonnhap_ibfk_2` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`IDNhanVien`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `taikhoannhanvien`
--
ALTER TABLE `taikhoannhanvien`
  ADD CONSTRAINT `taikhoannhanvien_ibfk_1` FOREIGN KEY (`IDPhanQuyen`) REFERENCES `phanquyen` (`IDPhanQuyen`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `taikhoannhanvien_ibfk_2` FOREIGN KEY (`IDNhanVien`) REFERENCES `nhanvien` (`IDNhanVien`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
