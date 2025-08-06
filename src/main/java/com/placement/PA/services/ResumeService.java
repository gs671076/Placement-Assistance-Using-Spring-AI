/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.services;

import com.placement.PA.entities.Resume;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gs671
 */
@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    public Resume getResumeByStudentId(Long studentId) {
        return resumeRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Resume not found for Student ID: " + studentId));
    }

    public Resume findByStudent(Student student) {
        return resumeRepository.findByStudent_Id(student.getId());
    }
}
