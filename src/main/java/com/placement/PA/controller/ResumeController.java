/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.controller;

import com.placement.PA.entities.Message;
import com.placement.PA.entities.Resume;
import com.placement.PA.entities.Student;
import com.placement.PA.services.ResumeService;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author gs671
 */
@Controller
@RequestMapping("/student")  // Keeping "/student" as the base path
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/resume")
    public String goResume(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login";
        }
        model.addAttribute("title1", "Upload Resume");
        model.addAttribute("student", student);
        model.addAttribute("resume", new Resume());
        model.addAttribute("message", session.getAttribute("message"));

        session.removeAttribute("message");
        Resume resume = resumeService.findByStudent(student);

        // Convert file to Base64 string
        if (resume != null) {
            String base64Resume = Base64.getEncoder().encodeToString(resume.getResume());

            model.addAttribute("resume1", base64Resume);
        }
        return "student/resume";
    }

    @PostMapping("/upload-resume")
    public String saveResume(@RequestParam("resumeFile") MultipartFile file, HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session expired. Please log in again."));
            return "redirect:/login";
        }

        try {
            if (file.isEmpty()) {
                session.setAttribute("message", new Message("alert-warning", "Please select a file to upload."));
                return "redirect:/student/resume";
            }

            // Find existing resume for this student
            Resume resume = resumeService.findByStudent(student);

            if (resume == null) {
                // If no resume exists, create a new one
                resume = new Resume();
                resume.setStudent(student);
                session.setAttribute("message", new Message("alert-success", "Resume uploaded successfully!"));
            } else {
                // If a resume already exists, update it
                session.setAttribute("message", new Message("alert-success", "Resume updated successfully!"));
            }

            // Set file details
            resume.setFileName(file.getOriginalFilename());
            resume.setResume(file.getBytes());

            // Save or update resume
            resumeService.saveResume(resume);

        } catch (Exception e) {
            session.setAttribute("message", new Message("alert-danger", "Failed to upload resume. Please try again." + e.getMessage()));
            System.out.print(e.getStackTrace());
        }

        return "redirect:/student/resume";
    }

}
