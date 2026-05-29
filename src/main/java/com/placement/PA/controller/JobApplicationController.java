package com.placement.PA.controller;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Message;
import com.placement.PA.entities.Recruit;
import com.placement.PA.entities.Resume;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.ApplicationRepository;
import com.placement.PA.repository.StudentRepo;
import com.placement.PA.services.RecruitService;
import com.placement.PA.services.ResumeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student/job")
public class JobApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private StudentRepo studentRepo;

    private Student getStudent(UserDetails userDetails) {
        return studentRepo.findByEmail(userDetails.getUsername());
    }

    @GetMapping("apply/{recruitId}")
    public String applyHere(@PathVariable("recruitId") int id,
                            Model model,
                            HttpSession session,
                            @AuthenticationPrincipal UserDetails userDetails) {
        Student student = getStudent(userDetails);

        Resume resume = resumeService.findByStudent(student);
        if (resume == null) {
            return "redirect:/student/resume";
        }

        Application application = new Application();
        application.setResume(resume.getResume());
        application.setName(student.getName());
        application.setContact(student.getContact());
        application.setEnrollmentNumber(student.getEnrollmentNumber());
        application.setEmail(student.getEmail());

        Recruit recruit = recruitService.getDataById(id);
        if (recruit != null) {
            application.setRecruit(recruit);
        }

        try {
            applicationRepository.save(application);
            session.setAttribute("message", new Message("alert-success", "Application submitted successfully!"));
        } catch (Exception e) {
            session.setAttribute("message", new Message("alert-danger", "Some error occurred"));
        }

        return "redirect:/student/job";
    }

    @GetMapping("/Applied")
    public String fetchAllJob(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Student student = getStudent(userDetails);
        List<Application> applications = applicationRepository.findAllByEmail(student.getEmail());
        List<Recruit> appliedJobs = applications.stream()
                .map(Application::getRecruit)
                .toList();
        model.addAttribute("appliedJobs", appliedJobs);
        model.addAttribute("title1", "Applied Jobs");
        return "student/jobApplied";
    }
}