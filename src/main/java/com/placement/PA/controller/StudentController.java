package com.placement.PA.controller;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Recruit;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.ApplicationRepository;
import com.placement.PA.repository.RecruitRepository;
import com.placement.PA.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepo studentRepo;

    // Helper: get Student entity from the authenticated principal's email
    private Student getStudent(UserDetails userDetails) {
        return studentRepo.findByEmail(userDetails.getUsername());
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Student student = getStudent(userDetails);
        model.addAttribute("title1", "Dashboard");
        model.addAttribute("student", student);
        return "student/dashboard";
    }

    @GetMapping("/ai")
    public String aI(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Student student = getStudent(userDetails);
        model.addAttribute("title1", "Carrier Guidance");
        model.addAttribute("student", student);
        return "student/CarrierGuidance";
    }

    @GetMapping("/job")
    public String job(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Student student = getStudent(userDetails);
        List<Recruit> recruit = recruitRepository.findAll();
        model.addAttribute("recruit", recruit);
        model.addAttribute("title1", "Carriers");

        List<Application> applications = applicationRepository.findAllByEmail(student.getEmail());
        List<Integer> appliedJobIds = applications.stream()
                .map(application -> application.getRecruit().getId())
                .toList();
        model.addAttribute("applied", appliedJobIds);
        return "student/job";
    }
}