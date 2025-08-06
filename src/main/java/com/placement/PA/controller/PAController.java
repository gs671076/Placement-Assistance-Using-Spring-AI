package com.placement.PA.controller;

import com.placement.PA.repository.StudentRepo;
import com.placement.PA.entities.Message;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.PlacementAuthorityRepository;
import com.placement.PA.entities.PlacementAuthority;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling page navigation.
 */
@Controller
public class PAController {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private PlacementAuthorityRepository placementAuthorityRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title1", "Home");
        return "index"; // Maps to src/main/resources/templates/index.html
    }

    @RequestMapping("/Register")
    public String doRegistration(Model model) {
        model.addAttribute("title1", "Register");
        model.addAttribute("student", new Student());
        System.out.println("Navigating to the registration page");

        return "Register"; // Maps to src/main/resources/templates/register.html
    }

    //save student to database
    @PostMapping("/add-student")
    public String addStudent(@ModelAttribute("student") @Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors occurred");
            model.addAttribute("student", student); // Retain form data with validation errors
            model.addAttribute("message", new Message("alert-danger", "Something Error Occurred"));
            return "Register"; // Return to the registration page
        } else {
            System.out.println("Processing student: " + student);
            studentRepo.save(student); // Save student to the database
            model.addAttribute("message", new Message("alert-success", "Successfully Registererd"));
            return "redirect:/Register"; // Redirect after saving, or redirect to another success page
        }
    }

    @GetMapping("/login")
    public String doLogin(HttpSession session, Model model) {
        Message message = (Message) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message"); // Remove message after displaying
        }
        return "login";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title1", "About");
        return "about";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/processLogin")
    public String processLogin(HttpSession session, @RequestParam("email") String username, @RequestParam("password") String password, Model model) {
        Student student = studentRepo.findByEmailAndPassword(username, password);
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Invalid credentials"));
            return "redirect:/login";
        } else {

            session.setAttribute("student", student);
            return "redirect:/student/dashboard";
        }

    }

    @GetMapping("/login-placement")
    public String doLogin(Model model, HttpSession session) {
        Message message = (Message) session.getAttribute("message");
        model.addAttribute("title1", "Admin login");
        model.addAttribute("message", message);
        return "loginPA";
    }

    @PostMapping("/process-placementLogin")
    public String processLoginPA(HttpSession session, @RequestParam("email") String username, @RequestParam("password") String password, Model model) {
        PlacementAuthority placement = placementAuthorityRepository.findByEmailAndPassword(username, password);
        System.out.println(placement);
        if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Invalid credentials"));
            return "redirect:/login-placement";
        } else {

            session.setAttribute("placement", placement);
            return "redirect:/Placement/dashboard";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes redirectAttributes,HttpServletResponse response) {
       try {
        // Remove attributes before invalidating session
        session.removeAttribute("student");
        
    } catch (IllegalStateException e) {
        System.out.println("Session was already invalidated.");
    }
        session.setAttribute("message", new Message("alert-success", "Logout Successfully"));
        return "redirect:/login?logout=true";
    }
     @GetMapping("/logoutPA")
    public String logoutPA(HttpSession session,RedirectAttributes redirectAttributes,HttpServletResponse response) {
       try {
        // Remove attributes before invalidating session
        session.removeAttribute("placement");


       
        
    } catch (IllegalStateException e) {
        System.out.println("Session was already invalidated.");
    }
        session.setAttribute("message", new Message("alert-success", "Logout Successfully"));
        return "redirect:/login-placement";
    }

}
