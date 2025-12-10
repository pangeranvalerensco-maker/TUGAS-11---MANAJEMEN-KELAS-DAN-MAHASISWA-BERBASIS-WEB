package com.tugas11.mypackage.controller;

import com.tugas11.mypackage.model.Class;
import com.tugas11.mypackage.model.Student;
import com.tugas11.mypackage.service.ClassService;
import com.tugas11.mypackage.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ClassService classService; 

    public StudentController(StudentService studentService, ClassService classService) {
        this.studentService = studentService;
        this.classService = classService;
    }
    
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.findAllStudents();
        model.addAttribute("students", students);
        return "students";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Class> classes = classService.findAllClasses(); 
        
        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new Student());
        }
        model.addAttribute("classes", classes);
        
        return "add-student"; 
    }
    
    @PostMapping("/add")
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        
        if (student.getAClass() != null && student.getAClass().getId() != null) {
            Optional<Class> selectedClass = classService.findClassById(student.getAClass().getId());
            selectedClass.ifPresent(student::setAClass);
        }
        
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Mahasiswa berhasil ditambahkan!");
            return "redirect:/students";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menyimpan mahasiswa: " + e.getMessage());
            
            redirectAttributes.addFlashAttribute("student", student);
            return "redirect:/students/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Student> studentOpt = studentService.findStudentById(id);
        List<Class> classes = classService.findAllClasses();
        
        if (studentOpt.isPresent()) {
            if (!model.containsAttribute("student")) {
                 model.addAttribute("student", studentOpt.get());
            }
            model.addAttribute("classes", classes);
            return "edit-student"; 
        }
        return "redirect:/students";
    }
    
    @PostMapping("/edit")
    public String updateStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        
        studentService.findStudentById(student.getId()).ifPresent(existingStudent -> {
             student.setCreatedAt(existingStudent.getCreatedAt()); 
        });
        
        if (student.getAClass() != null && student.getAClass().getId() != null) {
            Optional<Class> selectedClass = classService.findClassById(student.getAClass().getId());
            selectedClass.ifPresent(student::setAClass);
        }
        
        try {
            studentService.saveStudent(student); 
            redirectAttributes.addFlashAttribute("successMessage", "Data mahasiswa berhasil diperbarui!");
            return "redirect:/students";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal memperbarui data: " + e.getMessage());
            
            redirectAttributes.addFlashAttribute("student", student);
            return "redirect:/students/edit/" + student.getId();
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Mahasiswa berhasil dihapus.");
        return "redirect:/students";
    }
}