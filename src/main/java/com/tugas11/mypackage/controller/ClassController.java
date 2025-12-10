package com.tugas11.mypackage.controller;

import com.tugas11.mypackage.model.Class;
import com.tugas11.mypackage.service.ClassService;
import com.tugas11.mypackage.service.StudentService; // Diperlukan untuk Total Mahasiswa
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/classes")
public class ClassController {

    private final ClassService classService;
    private final StudentService studentService;

    public ClassController(ClassService classService, StudentService studentService) {
        this.classService = classService;
        this.studentService = studentService;
    }
    
    @GetMapping
    public String listClasses(Model model) {
        List<Class> classes = classService.findAllClasses();
        model.addAttribute("classes", classes);
        
        return "classes";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("classObj", new Class());
        return "add-class"; 
    }
    
    @PostMapping("/add")
    public String saveClass(@ModelAttribute("classObj") Class classObj, RedirectAttributes redirectAttributes) {
        try {
            classService.saveClass(classObj);
            redirectAttributes.addFlashAttribute("successMessage", "Kelas berhasil ditambahkan!");
            return "redirect:/classes";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menyimpan kelas: " + e.getMessage());
            redirectAttributes.addFlashAttribute("classObj", classObj);
            return "redirect:/classes/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return classService.findClassById(id).map(classObj -> {
            model.addAttribute("classObj", classObj);
            return "edit-class";
        }).orElse("redirect:/classes");
    }
    
    @PostMapping("/edit")
    public String updateClass(@ModelAttribute("classObj") Class classObj, RedirectAttributes redirectAttributes) {
        try {
            classService.findClassById(classObj.getId()).ifPresent(existingClass -> {
                classObj.setCreatedAt(existingClass.getCreatedAt()); 
            });
            
            classService.saveClass(classObj);
            redirectAttributes.addFlashAttribute("successMessage", "Kelas berhasil diperbarui!");
            return "redirect:/classes";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal memperbarui kelas: " + e.getMessage());
            redirectAttributes.addFlashAttribute("classObj", classObj); 
            return "redirect:/classes/edit/" + classObj.getId();
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            classService.deleteClass(id);
            redirectAttributes.addFlashAttribute("successMessage", "Kelas berhasil dihapus!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus kelas. Pastikan tidak ada mahasiswa di kelas ini.");
        }
        return "redirect:/classes";
    }

    @GetMapping("/{id}")
    public String classDetail(@PathVariable Long id, Model model) {
        return classService.findClassById(id).map(classObj -> {
            model.addAttribute("classObj", classObj);
            
            return "class-detail";
        }).orElse("redirect:/classes");
    }
}