package com.tugas11.mypackage.service.impl;

import com.tugas11.mypackage.model.Student;
import com.tugas11.mypackage.repository.StudentRepository;
import com.tugas11.mypackage.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email); 
    }

    @Override
    public Student saveStudent(Student student) {
        validateStudent(student);
        
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    @Override
    public List<Student> findStudentsByClassId(Long classId) {
        return studentRepository.findByAClassId(classId); 
    }

    @Override
    public void validateStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama mahasiswa tidak boleh kosong.");
        }
        
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
             throw new IllegalArgumentException("Email tidak boleh kosong.");
        }

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(student.getEmail());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Format email tidak valid.");
        }

        Optional<Student> existingStudent = findByEmail(student.getEmail());
        if (existingStudent.isPresent() && !existingStudent.get().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Email ini sudah terdaftar. Email harus unik.");
        }

        if (student.getAClass() == null || student.getAClass().getId() == null) {
            throw new IllegalArgumentException("Mahasiswa wajib memilih kelas.");
        }
    }
}