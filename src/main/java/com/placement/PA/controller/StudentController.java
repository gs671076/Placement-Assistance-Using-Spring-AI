/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.controller;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Message;
import com.placement.PA.entities.Recruit;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.ApplicationRepository;
import com.placement.PA.repository.RecruitRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author gs671
 */
/**
 *
 * @author gs671
 */
@Controller
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        Student student = (Student) session.getAttribute("student");

        // If no student is found, redirect to the login page
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login"; // Redirect to login if not logged in
        }

        // Add the student to the model for Thymeleaf to use
        model.addAttribute("title1", "Dashboard");
        model.addAttribute("student", student);

        return "student/dashboard"; //
    }

    @GetMapping("/ai")
    public String aI(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        // If no student is found, redirect to the login page
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login"; // Redirect to login if not logged in
        }

        // Add the student to the model for Thymeleaf to use
        model.addAttribute("title1", "Carrier Guidance");
        model.addAttribute("student", student);

        return "student/CarrierGuidance";
    }

   
    @GetMapping("/job")
    public String job(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login";
        }
        List<Recruit> recruit = this.recruitRepository.findAll();
        model.addAttribute("recruit", recruit);
        model.addAttribute("title1", "Carriers");
        List<Application> applications = this.applicationRepository.findAllByEmail(student.getEmail());

        List<Integer> appliedJobIds = applications.stream()
                .map(application -> application.getRecruit().getId()) // Get Recruit ID from Application
                .toList();
        model.addAttribute("applied", appliedJobIds);
        return "student/job";
    }
}
