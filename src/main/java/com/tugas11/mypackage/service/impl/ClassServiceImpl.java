package com.tugas11.mypackage.service.impl;

import com.tugas11.mypackage.model.Class;
import com.tugas11.mypackage.repository.ClassRepository;
import com.tugas11.mypackage.service.ClassService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    private void validateClass(Class classObj) {
        if (classObj.getName() == null || classObj.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kelas tidak boleh kosong.");
        }
    }

    @Override
    public List<Class> findAllClasses() {
        return classRepository.findAll();
    }

    @Override
    public Optional<Class> findClassById(Long id) {
        return classRepository.findById(id);
    }

    @Override
    public Class saveClass(Class classObj) {
        validateClass(classObj);
        
        return classRepository.save(classObj);
    }

    @Override
    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}