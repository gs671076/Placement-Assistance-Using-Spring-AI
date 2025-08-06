/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.services;

import com.placement.PA.entities.Recruit;
import com.placement.PA.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gs671
 */
@Service
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;

    public Recruit saveRecruit(Recruit recruit) {

        return this.recruitRepository.save(recruit);
    }
      
    public Recruit getDataById(int id)
    {
    return this.recruitRepository.getReferenceById(id);
    }
}
