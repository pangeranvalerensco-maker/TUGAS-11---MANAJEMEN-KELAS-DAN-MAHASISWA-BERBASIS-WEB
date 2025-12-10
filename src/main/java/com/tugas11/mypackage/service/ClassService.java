package com.tugas11.mypackage.service;

import com.tugas11.mypackage.model.Class;
import java.util.List;
import java.util.Optional;

public interface ClassService {
    List<Class> findAllClasses();
    Optional<Class> findClassById(Long id);
    Class saveClass(Class classObj);
    void deleteClass(Long id);
}