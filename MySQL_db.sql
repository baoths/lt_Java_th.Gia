-- Xóa database nếu đã tồn tại, sau đó tạo mới
DROP DATABASE IF EXISTS QLCuaHangDienThoaiDb;
CREATE DATABASE QLCuaHangDienThoaiDb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE QLCuaHangDienThoaiDb;

-- Bảng TaiKhoan
CREATE TABLE TaiKhoan (
  TenTaiKhoan VARCHAR(50) PRIMARY KEY,
  MatKhau VARCHAR(50) NOT NULL,
  HoTen VARCHAR(50) NOT NULL,
  GioiTinh TINYINT(1) NOT NULL,          -- 0 hoặc 1
  SoDienThoai VARCHAR(15),
  Email VARCHAR(50),
  DiaChi VARCHAR(255),
  IsAdmin TINYINT(1) NOT NULL DEFAULT 0  -- 0 = user thường, 1 = admin
) ENGINE=InnoDB;

-- Bảng LoaiDienThoai
CREATE TABLE LoaiDienThoai (
  Id INT PRIMARY KEY AUTO_INCREMENT,
  Ten VARCHAR(100) NOT NULL,
  MoTa VARCHAR(200)
) ENGINE=InnoDB;

-- Bảng DienThoai
CREATE TABLE DienThoai (
  Id INT PRIMARY KEY AUTO_INCREMENT,
  Ten VARCHAR(100) NOT NULL,
  MoTa VARCHAR(1000),
  SoLuong INT NOT NULL CHECK (SoLuong > 0),
  UrlHinhAnh VARCHAR(200),
  Gia INT NOT NULL,
  IdLoaiDienThoai INT NOT NULL,
  FOREIGN KEY (IdLoaiDienThoai)
    REFERENCES LoaiDienThoai(Id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Bảng GioHang
CREATE TABLE GioHang (
  Id INT PRIMARY KEY AUTO_INCREMENT,
  TaiKhoan VARCHAR(50) NOT NULL,
  IdDienThoai INT NOT NULL,
  DaDatHang TINYINT(1) NOT NULL DEFAULT 0,
  NgayThem DATE,
  FOREIGN KEY (TaiKhoan)
    REFERENCES TaiKhoan(TenTaiKhoan)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (IdDienThoai)
    REFERENCES DienThoai(Id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Bảng DatHang
CREATE TABLE DatHang (
  Id INT PRIMARY KEY AUTO_INCREMENT,
  TaiKhoan VARCHAR(50) NOT NULL,
  IdDienThoai INT NOT NULL,
  TrangThai INT NOT NULL CHECK (TrangThai BETWEEN 1 AND 3),
  NgayTao DATE,
  TongTien INT,
  FOREIGN KEY (TaiKhoan)
    REFERENCES TaiKhoan(TenTaiKhoan)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (IdDienThoai)
    REFERENCES DienThoai(Id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Chèn dữ liệu mẫu
INSERT INTO TaiKhoan (TenTaiKhoan, MatKhau, HoTen, GioiTinh, SoDienThoai, Email, DiaChi, IsAdmin) VALUES
  ('Bao',   '123456', 'Hoàng Kim Bảo', 0, '012345678',   'b@gmail.com',    'Đường Z 115, Quyết Thắng, Thành phố Thái Nguyên, Thái Nguyên', 1),
  ('admin', '123456', 'Admin',          1, '0123455678', 'admin@gmail.com','Ngõ 1 Phạm Văn Đồng',                                             1),
  ('User',  '123456', 'Khách Hàng 1',   0, '012345678',   'user@gmail.com', 'Ngõ 1 Phạm Văn Đồng',                                             0);

INSERT INTO LoaiDienThoai (Ten, MoTa) VALUES
  ('IPhone',  'IPhone'),
  ('SamSung', 'Samsung'),
  ('OPPO',    'Oppo');

INSERT INTO DienThoai (Ten, MoTa, SoLuong, UrlHinhAnh, Gia, IdLoaiDienThoai) VALUES
	('IPhone 12',    'iPhone là mẫu smartphone của hãng điện tử Mỹ Apple Inc', 100, 'images/iphone_12.jpg', 25000000, 1),
	('SamSung S10',  'Samsung đang kinh doanh các dòng điện thoại như: Galaxy A.', 70,  'images/samsung_s10.jpg', 11000000, 2),
	('SamSung B5722',  'Samsung đang kinh doanh các dòng điện thoại như: B5722.', 70,  'images/ss_B5722.png', 11000000, 2),
	('SamSung B2700',  'Samsung đang kinh doanh các dòng điện thoại như: B2700.', 70,  'images/ss_B2700.png', 11000000, 2),
	('SamSung B7722',  'Samsung đang kinh doanh các dòng điện thoại như: B7722.', 70,  'images/ss_B7722.png', 11000000, 2),
	('SamSung Blade',  'Samsung đang kinh doanh các dòng điện thoại như: Blade.', 70,  'images/ss_Blade.png', 11000000, 2),
    ('SamSung C3312',  'Samsung đang kinh doanh các dòng điện thoại như: C3312.', 70,  'images/ss_C3312.png', 11000000, 2),
	('SamSung Acton',  'Samsung đang kinh doanh các dòng điện thoại như: Acton.', 70,  'images/samsung_acton.png', 11000000, 2),
	('SamSung Ativ S',  'Samsung đang kinh doanh các dòng điện thoại như: Ativ S.', 70,  'images/samsung_ativ_s.png', 11000000, 2),
	('OPPO A5s',     'Sản phẩm đầu tiên đánh dấu sự xuất.',                     50,  'images/oppo_a5s.jpg',  5000000, 3);

-- (Tùy chọn) nếu muốn chèn luôn dữ liệu vào GioHang và DatHang, bạn có thể INSERT tương tự
