/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.placement.PA.repository;

import com.placement.PA.entities.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gs671
 */
@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Integer>{

    public Recruit findById(Long id);
    
}
