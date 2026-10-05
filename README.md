# BiGotTalent - Aplikasi Pendaftaran Lomba Berbasis Java Swing & MySQL

Aplikasi desktop sederhana untuk manajemen pendaftaran ajang pencarian bakat (BiGotTalent). Aplikasi ini dibangun menggunakan bahasa pemrograman Java (Swing) dan database MySQL (XAMPP).

---

## Fitur Utama

### 1. Sisi Admin
- Menambahkan mata lomba baru ke database.
- Melihat daftar siswa yang sudah mendaftar.
- Memperbarui status tahapan seleksi peserta.

### 2. Sisi Siswa
- Login ke sistem menggunakan akun peserta.
- Memilih dan mendaftar pada mata lomba yang tersedia.
- Memantau status tahapan pendaftaran secara real-time.

---

## Prasyarat Sistem

- Java Development Kit (JDK 8 / 11 / 17 / 21)
- XAMPP (Apache & MySQL)
- MySQL Connector/J (`.jar`)

---

## Struktur Folder

```text
tugasPbo_Bigotel/
├── database.sql
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── koneksi/
│   ├── Koneksi.java
│   └── CekKoneksi.java
├── form/
│   ├── FormLogin.java
│   ├── FormAdmin.java
│   ├── FormSiswa.java
│   └── Main.java
└── README.md
