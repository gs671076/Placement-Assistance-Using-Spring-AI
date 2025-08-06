package com.placement.PA.controller;

import com.placement.PA.entities.Answer;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.placement.PA.entities.Message;
import com.placement.PA.entities.MockTest;
import com.placement.PA.entities.PlacementAuthority;
import com.placement.PA.entities.Question;
import com.placement.PA.entities.Recruit;
import com.placement.PA.repository.MockTestRepository;
import com.placement.PA.services.RecruitService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/Placement")
public class PlacementController {

    @Autowired
    private MockTestRepository mockTestRepository;
    @Autowired
    private RecruitService recruitService;

    @GetMapping("/dashboard")
    public String processLOgin(Model model, HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
        if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }

        // Add the student to the model for Thymeleaf to use
        model.addAttribute("title1", "Dashboard");
        model.addAttribute("placement", placement);

        return "placement/dashboard";
    }

    @GetMapping("/mocktest")
    public String mockTest(Model model, HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
          if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        model.addAttribute("title1", "Mock Test");
        model.addAttribute("mockTest", new MockTest());

        return "placement/mocktest";
    }

    @GetMapping("/pd")
    public String drive(Model model,HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
          if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        model.addAttribute("title1", "Placerment Drive");
        return "placement/placementDrive";
    }

    @PostMapping("/mocktests-create")
    public String saveMockTest(@ModelAttribute MockTest mockTest,HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
          if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        // Ensure the answers list for each question is initialized
        
        for (Question question : mockTest.getQuestions())
        {       question.setMockTest(mockTest);
//            if (question.getAnswers() == null) {
//                question.setAnswers(new ArrayList<>()); // Initialize the answers list if it's null
//            }
            // Now populate each answer and set the correct relationship
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question); // Set the relationship with the question
            }
        }
        this.mockTestRepository.save(mockTest);
        return "redirect:/Placement/mocktest";
    }

    @GetMapping("/placement-drive")
    public String placementDrive(Model model,HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
          if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        model.addAttribute("title1", "Placement Drive");
        return "placement/placementDrive";
    }

    @GetMapping("/placementDrive/add")
    public String addPlacement(Model model,HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
          if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        model.addAttribute("title1", "Add Placement");
        model.addAttribute("recruit", new Recruit());
        return "/placement/addPlacement";
    }

    @PostMapping("/placementDrive/process")
    public String processPlacement(@ModelAttribute("recruit") Recruit recruit, HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
        if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        recruit.setPlacement(placement);
        System.out.println(recruit);
        try {
            this.recruitService.saveRecruit(recruit);
            session.setAttribute("message", new Message("alert-success", "Drive is Uploded Successfully"));

        } catch (Exception e) {
            session.setAttribute("message", new Message("alert-danger", "Invalid credentials"));

        }

        return "redirect:/Placement/placement-drive";
    }

}
