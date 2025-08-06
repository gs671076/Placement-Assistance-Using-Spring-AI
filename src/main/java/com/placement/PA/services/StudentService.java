/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.services;

import com.placement.PA.entities.Student;
import com.placement.PA.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author gs671
 */
    @Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

   public Student getStudentById(Long studentId) {
    return studentRepo.findById(studentId);
            
}


    public Student updateStudent(Long studentId, Student updatedStudent) {
        Student existingStudent = getStudentById(studentId);
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setContact(updatedStudent.getContact());
        existingStudent.setAddress(updatedStudent.getAddress());
        return studentRepo.save(existingStudent);
    }

    public void deleteStudent(Long studentId) {
        studentRepo.deleteById(studentId);
    }
}


