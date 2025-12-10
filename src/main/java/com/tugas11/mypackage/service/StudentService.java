package com.tugas11.mypackage.service;

import com.tugas11.mypackage.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAllStudents();
    Optional<Student> findStudentById(Long id);
    Student saveStudent(Student student); 
    void deleteStudent(Long id);
    List<Student> findStudentsByClassId(Long classId);
    
    void validateStudent(Student student);
    Optional<Student> findByEmail(String email);
}