/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.controller;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Message;
import com.placement.PA.entities.Recruit;
import com.placement.PA.entities.Resume;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.ApplicationRepository;
import com.placement.PA.services.RecruitService;
import com.placement.PA.services.ResumeService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author gs671
 */
@Controller
@RequestMapping("/student/job")
public class JobApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private RecruitService recruitService;

    @GetMapping("apply/{recruitId}")
    public String applyHere(@PathVariable("recruitId") int id, Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login";
        }
        Application application = new Application();
        Resume resume = this.resumeService.findByStudent(student);
        if(resume==null)
        {
        return "redirect:/student/resume";
        }
        application.setResume(resume.getResume());
        application.setName(student.getName());
        application.setContact(student.getContact());
        application.setEnrollmentNumber(student.getEnrollmentNumber());
        application.setEmail(student.getEmail());
        Recruit recruit = this.recruitService.getDataById(id);
        if (recruit != null) {
            application.setRecruit(recruit);
        }
        try {
            Application result = applicationRepository.save(application);
            System.out.print(result.getEmail());
            session.setAttribute("message", new Message("alert-success", "Application submitted successfully!"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.setAttribute("message", new Message("alert-danger", "Some error Occurred"));

        }
        model.addAttribute("message", session.getAttribute("message"));

        return "redirect:/student/job";
    }
    @GetMapping("/Applied")
    public String fetchAllJob(HttpSession session,Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login";
        }
          List<Application> applications = this.applicationRepository.findAllByEmail(student.getEmail());

    // Extract applied jobs (Recruit entities)
    List<Recruit> appliedJobs = applications.stream()
            .map(Application::getRecruit) // Get Recruit object from Application
            .toList();

    model.addAttribute("appliedJobs", appliedJobs);
    model.addAttribute("title1", "Applied Jobs");

        return "student/jobApplied";
    }

}
