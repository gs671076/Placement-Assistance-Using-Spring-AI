/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.placement.PA.repository;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Recruit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author gs671
 */
public interface ApplicationRepository extends JpaRepository<Application,Integer>{
    public List<Application> findAllByEmail(String email);
    public List<Application> findAllByRecruit(Recruit recruit);
}
    
