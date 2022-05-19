-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 19, 2022 lúc 04:39 AM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 7.4.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `dientu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `id` int(11) NOT NULL,
  `iddonhang` int(11) NOT NULL,
  `idsanpham` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `gia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`id`, `iddonhang`, `idsanpham`, `soluong`, `gia`) VALUES
(1, 3, 2, 1, 19990000),
(2, 1, 2, 1, 19990000),
(3, 4, 2, 1, 19990000),
(4, 5, 2, 1, 19990000),
(5, 2, 2, 1, 19990000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `tenkhachhang` varchar(200) NOT NULL,
  `sodienthoai` int(10) NOT NULL,
  `diachi` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`id`, `tenkhachhang`, `sodienthoai`, `diachi`, `email`) VALUES
(1, 'Hoàng Nhật Tân', 926738233, '52 Nguyễn Lương Bằng,phường Hòa Khánh ,quận Liên Chiểu ,TP Đà Nẵng', 'tang@gmail.com'),
(2, 'Tạ Phươc Nguyên', 936284626, '188 Nguyễn Lương Bằng,phường Hòa Khánh ,quận Liên Chiểu ,TP Đà Nẵng', 'nguyen@gmail.com'),
(3, 'Cao Minh Trí', 823742432, '60 Ngô Sỹ Liên,phường Hòa Khánh ,quận Liên Chiểu ,TP Đà Nẵng', 'tri@gmail.com'),
(4, 'Lâm Ngọc Mỹ', 434353454, '20 Âu Cơ,phường Hòa Khánh ,quận Liên Chiểu ,TP Đà Nẵng', 'my@gmail.com'),
(5, 'Trần Lê Thị Trà My', 969743828, '58 Ngô Thì Nhậm, phường Hòa Minh ,quận Liên Chiểu ,TP Đà Nẵng', 'tramy@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `id` int(11) NOT NULL,
  `tenloaisanpham` varchar(200) NOT NULL,
  `hinhanhsanpham` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`id`, `tenloaisanpham`, `hinhanhsanpham`) VALUES
(1, 'Điện thoại', 'https://banner2.cleanpng.com/20180316/wxe/kisspng-cartoon-drawing-illustrator-telephone-cartoon-apple-phone-5aaba7c741d611.6752565215211990472697.jpg'),
(2, 'Laptop', 'https://img.lovepik.com/free_png/28/88/52/20X58PICFPbsSqd7gB4Vw_PIC2018.png_860.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `id` int(11) NOT NULL,
  `tensanpham` varchar(200) NOT NULL,
  `giasanpham` int(15) NOT NULL,
  `hinhanhsanpham` varchar(200) NOT NULL,
  `motasanpham` varchar(10000) NOT NULL,
  `idloaisanpham` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`id`, `tensanpham`, `giasanpham`, `hinhanhsanpham`, `motasanpham`, `idloaisanpham`) VALUES
(1, 'Điện thoại iPhone 13 128GB', 22290000, 'https://cdn.tgdd.vn/Products/Images/42/223602/iphone-13-xanh-1.jpg', 'Nhìn chung về ngoại hình, iPhone 13 của năm nay vẫn được thiết kế theo kiểu khung viền vuông vức tương tự. Sau khi được cầm nắm và trải nghiệm thiết bị thì nó mang lại cho mình một cảm rất êm tay và vô cùng thoải mái.\r\niPhone 13 có thời lượng pin tăng thêm 2.5 tiếng so với thế hệ trước, cho thời gian sử dụng lâu hơn, phục vụ hiệu quả với người dùng có nhu cầu sử dụng tần suất cao.', 1),
(2, 'Điện thoại iPhone 12 64GB', 19990000, 'https://cdn.tgdd.vn/Products/Images/42/213031/iphone-12-xanh-la-new-2-600x600.jpg', 'Hiệu năng vượt xa mọi giới hạn\r\nApple đã trang bị con chip mới nhất của hãng (tính đến 11/2020) cho iPhone 12 đó là A14 Bionic, được sản xuất trên tiến trình 5 nm với hiệu suất ổn định hơn so với chip A13 được trang bị trên phiên bản tiền nhiệm iPhone 11.', 1),
(3, 'Laptop Apple MacBook Pro 13 TouchBar MYD82SA/A Space Grey', 30900000, 'https://mega.com.vn/media/product/19584_laptop_apple_macbook_pro_myd82saa_space_grey_1.png', 'Macbook Pro M1 MYD82SA/A  có thiết kế đẹp với màn hình Retina truetone đẳng cấp và tuyệt với từ trước đến nay. Apple luôn đầu tư cho những chiếc macbook air kiểu dáng đẹp mắt, sang trọng.\r\n\r\nĐặc điểm sản phẩm:  Laptop Apple Macbook Pro M1 MYD82SA/A \r\n\r\nThiết kế tinh tế với những đường cắt thủ công hiện đại đem lại cho người dùng cảm giác thời thượng, đi đầu xu hướng. Macbook không chỉ dừng ở vai trò như một món đồ công nghệ mà nó còn như một món đồ trang sức đắt tiền. Nếu lo bạn cảm thấy nhàm chán về các tông màu cơ bản như trắng, đen thì Macbook pro với tông grey  là sự lựa chọn sáng giá bạn.', 2),
(4, 'Dell XPS 9310', 29490000, 'https://bizweb.dktcdn.net/thumb/large/100/378/807/products/9310-2.jpg?v=1603428531887', 'XPS 13 9310 dành cho ai?\r\nLà laptop đa phương tiện, với thiết kế thời thượng và trọng lượng nhẹ, XPS 9310 hướng tới những đối tượng làm những công việc đa phương tiện, chỉnh sửa ảnh hay video nhẹ.\r\n\r\nHiệu năng thét ra lửa\r\nMang trong mình chú hổ Tiger Lake thế hệ 11 mới nhất, cải thiện tối đa về mức tiêu thụ điện năng, cũng như xung nhịp, kèm theo đó là công nghệ Intel Evo, khiến chiếc máy này luôn hoạt động với công suất tối đa dù không cần cắm sạc, thoải mái cho người dùng sử dụng liên tục các tác vụ nặng. Máy có 3 tuỳ chọn CPU lần lượt là i3 1115G4, i5 1135G7 và i7 1165G7, tuy nhiên chỉ có phiên bản i5 và i7 là được hỗ trợ Intel Evo\r\n\r\nMáy được trang bị card đồ hoạ Intel Iris Xe mạnh mẽ, ngang hàng với những card đồ hoạ rời tới từ đối thủ Nvidia và AMD. Với card đồ hoạ này, người dùng có thể xử lý các tác vụ đồ hoạ, cũng như chơi một vài tựa game ở mức thiết đặt phù hợp. Tất nhiên, chỉ có phiên bản i5 và i7 mới được trang bị card đồ hoạ này, còn i3 vẫn là Intel UHD.', 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `iddonhang` (`iddonhang`),
  ADD KEY `idsanpham` (`idsanpham`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sanpham_ibfk_1` (`idloaisanpham`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `chitietdonhang_ibfk_1` FOREIGN KEY (`iddonhang`) REFERENCES `donhang` (`id`),
  ADD CONSTRAINT `chitietdonhang_ibfk_2` FOREIGN KEY (`idsanpham`) REFERENCES `sanpham` (`id`);

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`idloaisanpham`) REFERENCES `loaisanpham` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
