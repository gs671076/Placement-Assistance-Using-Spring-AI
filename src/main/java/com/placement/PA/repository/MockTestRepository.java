/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.placement.PA.repository;
import com.placement.PA.entities.MockTest;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gs671
 */
@Repository
public interface MockTestRepository extends JpaRepositoryImplementation<MockTest, Long> {
    
}
