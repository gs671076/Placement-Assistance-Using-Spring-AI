/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.controller;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Message;
import com.placement.PA.entities.PlacementAuthority;
import com.placement.PA.entities.Recruit;
import com.placement.PA.repository.ApplicationRepository;
import com.placement.PA.repository.RecruitRepository;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author gs671
 */
@Controller
@RequestMapping("/Placement")
public class ManagePlacementController {

    @Autowired
    public RecruitRepository recruitRepository;

    @Autowired
    public ApplicationRepository applicationRepository;

    @GetMapping("/manage")
    public String manageJob(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
        if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        model.addAttribute("title1", "Add Placement");
        Pageable pageable = PageRequest.of(page, size);
        Page<Recruit> applications = this.recruitRepository.findAll(pageable);
        System.out.println(applications.getContent());
        System.out.println("Total Pages: " + applications.getTotalPages());

        model.addAttribute("applications", applications);  // Add data to model
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", applications.getTotalPages());

        return "placement/manageJob";  // Return Thymeleaf template
    }

    //delete the job
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, String> deleteJob(@PathVariable("id") int id) {
        
        Map<String, String> response = new HashMap<>();

        if (recruitRepository.existsById(id)) {
            recruitRepository.deleteById(id);
            response.put("status", "success");
            response.put("message", "Job deleted successfully.");
        } else {
            response.put("status", "error");
            response.put("message", "Job not found.");
        }

        return response;
    }

    // Show Edit Form
    @GetMapping("/edit/{id}")
    public String showEditPlacementForm(@PathVariable Long id, Model model,HttpSession session) {
        PlacementAuthority placement1 = (PlacementAuthority) session.getAttribute("placement");
          if (placement1 == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        Recruit placement = this.recruitRepository.findById(id);
        if (placement == null) {
            return "redirect:/Placement/manage"; // Redirect if ID is invalid
        }
        model.addAttribute("placement", placement);
        model.addAttribute("p", placement.getPlacement());
        return "placement/edit-placement";
    }

    // Update Placement
    @PostMapping("/update")
    public String updatePlacement(@ModelAttribute Recruit placement,HttpSession session) {
        PlacementAuthority placement1 = (PlacementAuthority) session.getAttribute("placement");
          if (placement1 == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        Recruit r1 = this.recruitRepository.getReferenceById(placement.getId());

        placement.setPlacement(r1.getPlacement());
        this.recruitRepository.save(placement);
        return "redirect:/Placement/manage"; // Redirect after successful update
    }

    @GetMapping("/view-applications/{id}")
    public String viewApplication(@PathVariable("id") int id,Model model, HttpSession session) {
        PlacementAuthority placement = (PlacementAuthority) session.getAttribute("placement");
          if (placement == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login-placement"; // Redirect to login if not logged in
        }
        Recruit recruit = this.recruitRepository.getReferenceById(id);
        List<Application> application = this.applicationRepository.findAllByRecruit(recruit);
        for (Application app : application) {
            System.out.println(app);
        }
        model.addAttribute("title1", "Applications");
        model.addAttribute("applications", application);
        return "placement/viewApplication";
    }
    
    @GetMapping("/download-resume/{id}")
public ResponseEntity<byte[]> downloadResume(@PathVariable("id") int id) {
    Application application = applicationRepository.findById(id).orElse(null);
    
    if (application == null || application.getResume() == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(application.getResume());
}

}
