package com.placement.PA.controller;

import com.placement.PA.entities.Answer;
import com.placement.PA.entities.MockTest;
import com.placement.PA.entities.PlacementAuthority;
import com.placement.PA.entities.Question;
import com.placement.PA.entities.Recruit;
import com.placement.PA.repository.MockTestRepository;
import com.placement.PA.repository.PlacementAuthorityRepository;
import com.placement.PA.services.RecruitService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Placement")
public class PlacementController {

    @Autowired
    private MockTestRepository mockTestRepository;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private PlacementAuthorityRepository placementRepo;

    private PlacementAuthority getPlacement(UserDetails userDetails) {
        return placementRepo.findByEmail(userDetails.getUsername());
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("title1", "Dashboard");
        model.addAttribute("placement", getPlacement(userDetails));
        return "placement/dashboard";
    }

    @GetMapping("/mocktest")
    public String mockTest(Model model) {
        model.addAttribute("title1", "Mock Test");
        model.addAttribute("mockTest", new MockTest());
        return "placement/mocktest";
    }

    @GetMapping("/pd")
    public String drive(Model model) {
        model.addAttribute("title1", "Placement Drive");
        return "placement/placementDrive";
    }

    @PostMapping("/mocktests-create")
    public String saveMockTest(@ModelAttribute MockTest mockTest) {
        for (Question question : mockTest.getQuestions()) {
            question.setMockTest(mockTest);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
            }
        }
        mockTestRepository.save(mockTest);
        return "redirect:/Placement/mocktest";
    }

    @GetMapping("/placement-drive")
    public String placementDrive(Model model) {
        model.addAttribute("title1", "Placement Drive");
        return "placement/placementDrive";
    }

    @GetMapping("/placementDrive/add")
    public String addPlacement(Model model) {
        model.addAttribute("title1", "Add Placement");
        model.addAttribute("recruit", new Recruit());
        return "/placement/addPlacement";
    }

    @PostMapping("/placementDrive/process")
    public String processPlacement(@ModelAttribute("recruit") Recruit recruit,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   HttpSession session) {
        recruit.setPlacement(getPlacement(userDetails));
        try {
            recruitService.saveRecruit(recruit);
            session.setAttribute("message",
                    new com.placement.PA.entities.Message("alert-success", "Drive Uploaded Successfully"));
        } catch (Exception e) {
            session.setAttribute("message",
                    new com.placement.PA.entities.Message("alert-danger", "Some error occurred"));
        }
        return "redirect:/Placement/placement-drive";
    }
}