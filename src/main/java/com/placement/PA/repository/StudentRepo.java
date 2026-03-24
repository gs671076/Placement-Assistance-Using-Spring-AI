/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.repository;

import com.placement.PA.entities.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gs671
 */
@Repository
public interface StudentRepo extends JpaRepositoryImplementation<Student, Integer>{
   public Student findByEmailAndPassword(String email,String password); 

    public Student findById(Long studentId);

    public void deleteById(Long studentId);

    public Student findByEmail(String email);
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findByUsername(String username);
}
