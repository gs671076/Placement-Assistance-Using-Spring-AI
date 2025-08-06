/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.repository;

import com.placement.PA.entities.Student;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 *
 * @author gs671
 */
public interface StudentRepo extends JpaRepositoryImplementation<Student, Integer>{
   public Student findByEmailAndPassword(String email,String password); 

    public Student findById(Long studentId);

    public void deleteById(Long studentId);

    public Student findByEmail(String email);
}
