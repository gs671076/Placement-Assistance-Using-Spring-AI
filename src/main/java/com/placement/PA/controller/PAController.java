package com.placement.PA.controller;

import com.placement.PA.entities.Message;
import com.placement.PA.entities.Student;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.placement.PA.repository.StudentRepo;

@Controller
public class PAController {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title1", "Home");
        return "index";
    }

    @RequestMapping("/Register")
    public String doRegistration(Model model) {
        model.addAttribute("title1", "Register");
        model.addAttribute("student", new Student());
        return "Register";
    }

    @PostMapping("/add-student")
    public String addStudent(@ModelAttribute("student") @Valid Student student,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("message", new Message("alert-danger", "Something Error Occurred"));
            return "Register";
        }
        // Encode password and set defaults before saving
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("ROLE_STUDENT");
        student.setEnabled(true);
        studentRepo.save(student);
        model.addAttribute("message", new Message("alert-success", "Successfully Registered"));
        return "redirect:/Register";
    }

    // Spring Security handles POST /processLogin — this just renders the page
    @GetMapping("/login")
    public String doLogin(@RequestParam(value = "error", required = false) String error,
                          @RequestParam(value = "logout", required = false) String logout,
                          Model model) {

        if (error != null) {
            model.addAttribute("message", new Message("alert-danger", "Invalid email or password"));
        }
        if (logout != null) {
            model.addAttribute("message", new Message("alert-success", "Logged out successfully"));
        }
        return "login";
    }

    // Spring Security handles POST /process-placementLogin — this just renders the page
    @GetMapping("/login-placement")
    public String doLoginPA(Model model) {
        model.addAttribute("title1", "Admin login");
        return "loginPA";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title1", "About");
        return "about";
    }
}