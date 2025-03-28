# TP5DPBO2025C2
Saya Zaki Adam dengan NIM 2304934 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk Keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain Program
Aplikasi ini merupakan interface dengan Java Swing GUI untuk mengelola data mahasiswa yang meliputi NIM, nama, kelamin, dan status keaktifan. Aplikasi ini berkemampuan untuk menambah, memperbarui, dan menghapus data-data mahasiswa. Selain itu fitur kali ini diintegrasikan dengan implementasi penggunaan database, semua atribut harus terisi, dan menangani NIM duplikat.

### Class Mahasiswa
Merupakan class untuk data mahasiswa yang terdiri dari atribut `nim`, `nama`, `jenis kelamin`, `status mahasiswa`. Class ini memiliki getter dan setter untuk masing-masing atribut dalam class tersebut.

### Class Menu
Merupakan class graphics user interface untuk program ini. GUI meliputi dari JPanel, JLabel, JTextField, JButton, JTabel, JComboBox, dan JRadioButton. Class ini juga dapat `Add`, `Update`, dan `Delete` data yang ada.

### Class Database
Merupakan class untuk menangani koneksi dan perintah-perintah dengan database. Atribut terdiri dari Connection dan Statement.
* Connection, atribut yang menyimpan koneksi antar program dengan database yang tersimpan atau ingin diakses.
* Statement, atribut yang menangani query SQL yang dieksekusi.
Class ini juga terdapat *dua* method:
* selectQuery, method untuk menjalankan query yang berfungsi untuk SELECT dan menampung hasil SELECT tersebut.
* insertUpdateDeleteQuery, method untuk menjalankan query selain SELECT.

## Penjelasan Alur
### 1. Inisialisasi
Membuat window dari aplikasi, lalu juga memasukkan data secara statis ke dalam program. Tampilan program akan menyajikan Formulir data, Tombol Add/Update, Tombol Delete, Tombol Cancel, dan List data mahasiswa.

### 2. Add
Memasukkan NIM dan Nama, lalu memilih jenis kelamin yang tersedia, selanjutnya memilih status mahasiswa. Setelah itu menekan tombol Add untuk menambahkan. Ketika terdapat kolom yang kosong maka akan muncul warning bahwa data kurang lengkap. Ketika menggunakan NIM yang sudah ada, maka akan menampilkan error bahwa NIM sudah terdaftar. Melewati kedua hal tersebut maka akan langsung menambahkan data baru ke dalam database.

### 3. Update
Memilih data terlebih dahulu. Opsi Add sebelumnya berubah menjadi Update. Formulir Data terisi dengan atribut data yang diakses. Memodifikasi setiap atribut yaitu NIM, Nama, Jenis Kelamin, dan Status Mahasiswa. Setelah menekan tombol Update untuk memperbarui. Ketika terdapat kolom yang kosong maka akan muncul warning bahwa data kurang lengkap. Ketika menggunakan NIM yang sudah ada, maka akan mengembalikan error bahwa NIM sudah terdaftar. Melewati kedua hal tersebut maka akan langsung memperbarui data baru ke dalam database.

### 4. Delete
Memilih data terlebih dahulu. Opsi Delete akan muncul. Formulir Data terisi dengan atribut data yang diakses. Menekan tombol Delete, akan memunculkan pop up untuk konfirmasi penghapusan data. Tekan Yes untuk menghapus data.

### 5. Clear
Setelah melakukan salah satu perintah di atas (`Add`, `Update`, ataupun `Delete`). Formulir akan dibersihkan datanya sehingga tidak ada satu pun nilai yang mengisi kolom formulir tersebut.

## Dokumentasi
![Video Demo](https://github.com/user-attachments/assets/df0361fa-37d9-470c-b35a-d483ecbecd3f)
<video>
  <source src="https://github.com/user-attachments/assets/df0361fa-37d9-470c-b35a-d483ecbecd3f" type="video/mp4">
  Your browser does not support the video tag.
</video>
