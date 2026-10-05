CREATE DATABASE IF NOT EXISTS db_bigottalent;
USE db_bigottalent;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('admin', 'siswa') NOT NULL
);

CREATE TABLE IF NOT EXISTS lomba (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_lomba VARCHAR(100) NOT NULL,
    deskripsi TEXT
);

CREATE TABLE IF NOT EXISTS pendaftaran (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    lomba_id INT NOT NULL,
    status VARCHAR(100) DEFAULT 'Dokumen dalam Tinjauan'
);

INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');
INSERT INTO users (username, password, role) VALUES ('budi', '123', 'siswa');

INSERT INTO lomba (nama_lomba, deskripsi) VALUES ('Solo Vocal', 'Lomba menyanyi solo lagu pop/daerah');
INSERT INTO lomba (nama_lomba, deskripsi) VALUES ('Dance Modern', 'Lomba tari kreasi modern berkelompok');
