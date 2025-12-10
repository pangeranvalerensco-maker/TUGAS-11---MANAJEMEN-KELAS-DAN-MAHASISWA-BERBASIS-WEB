# TUGAS 11 – MANAJEMEN KELAS DAN MAHASISWA BERBASIS WEB

## 1. 🎯 Ringkasan Proyek

Aplikasi ini adalah implementasi sistem manajemen data Kelas dan Mahasiswa berbasis web menggunakan Java Spring Boot . Proyek ini secara khusus berfokus pada penerapan dan visualisasi relasi **One-to-Many** dan **Many-to-One** menggunakan Spring Data JPA .

## 2. 🛠️ Teknologi yang Digunakan

| Komponen | Teknologi | Keterangan |
| :--- | :--- | :--- |
| **Framework** | Java Spring Boot | Untuk membangun aplikasi *backend*.  |
| **MVC** | Spring MVC | Pola arsitektur Controller-Service-Repository.  |
| **View** | Thymeleaf | Templating Engine untuk antarmuka pengguna (UI).  |
| **Database Access** | Spring Data JPA | Digunakan untuk operasi CRUD dan relasi Entity.  |
| **Database** | MySQL | Database relasional untuk penyimpanan data.  |
| **Relasi** | One-to-Many & Many-to-One | Konsep Association Mapping.  |

## 3. ✨ Fitur Aplikasi (CRUD)

### A. Fitur Kelas 
* **Tambah Kelas** 
* **Lihat Semua Kelas**: Menampilkan tabel kelas dengan jumlah mahasiswa per kelas .
* **Edit Kelas** 
* **Hapus Kelas** 
* **Detail Kelas**: Menampilkan detail kelas + daftar mahasiswa di kelas tersebut.

### B. Fitur Mahasiswa 
* **Tambah Mahasiswa**: Form harus memiliki *dropdown list* berisi semua kelas .
* **Lihat Semua Mahasiswa**: Menampilkan daftar seluruh mahasiswa + informasi nama kelas .
* **Edit Mahasiswa**: Termasuk fitur untuk pindah kelas
* **Hapus Mahasiswa** 
* **Tampilkan Mahasiswa per Kelas**: Disediakan di halaman detail kelas

## 4. 🤝 Penjelasan Relasi Entitas

Hubungan antara `Class` (Kelas) dan `Student` (Mahasiswa) adalah hubungan **One-to-Many**.

### A. Entitas `Class` (One)
* Satu Kelas boleh memiliki banyak Mahasiswa
* Di Java (JPA), direpresentasikan dengan *field* `List<Student>` yang dianotasi `@OneToMany`.

### B. Entitas `Student` (Many)
* Setiap Mahasiswa harus terkait dengan sebuah Kelas 55].
* Di Java (JPA), direpresentasikan dengan *field* `Class aClass` yang dianotasi `@ManyToOne`. Kolom *Foreign Key* di database adalah `class_id`

### 5. 📂 Struktur Folder Proyek

Berikut adalah struktur folder inti aplikasi, mengikuti pola MVC (Model-View-Controller):

```
src/main/java/com/tugas11/mypackage
├── controller/         
│   ├── ClassController.java
│   └── StudentController.java
│
├── model/              
│   ├── Class.java
│   └── Student.java
│
├── repository/         
│   ├── ClassRepository.java
│   └── StudentRepository.java
│
├── service/            
│   ├── ClassService.java
│   ├── StudentService.java
│   └── impl/
│       ├── ClassServiceImpl.java
│       └── StudentServiceImpl.java
│
└── MypackageApplication.java

src/main/resources/
├── application.properties 
└── templates/             
    ├── classes.html
    ├── class-detail.html
    ├── students.html
    ├── add-class.html
    ├── edit-class.html
    ├── add-student.html
    └── edit-student.html
```

### 6. 🛡️ Validasi Kunci (Manual)

Validasi diterapkan secara manual di lapisan Service, sesuai persyaratan:
* Nama tidak boleh kosong
* Email harus valid dan unique
* Mahasiswa wajib memilih kelas

### Link Video Demo
* **Link YouTube**: `https://www.youtube.com/@pangeranvalerensco9928` 
* **Isi Video**: Demo program, penjelasan alur Controller → Service → Repository, dan penjelasan One-to-Many serta Many-to-One .
