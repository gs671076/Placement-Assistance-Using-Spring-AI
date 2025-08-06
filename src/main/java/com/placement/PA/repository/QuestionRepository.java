/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.placement.PA.repository;

import com.placement.PA.entities.Question;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 *
 * @author gs671
 */
public interface QuestionRepository extends JpaRepositoryImplementation<Question,Long> {
    
}
