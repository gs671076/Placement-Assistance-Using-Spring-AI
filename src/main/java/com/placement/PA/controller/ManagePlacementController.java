package com.placement.PA.controller;

import com.placement.PA.entities.Application;
import com.placement.PA.entities.Recruit;
import com.placement.PA.repository.ApplicationRepository;
import com.placement.PA.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Placement")
public class ManagePlacementController {

    @Autowired
    public RecruitRepository recruitRepository;

    @Autowired
    public ApplicationRepository applicationRepository;

    @GetMapping("/manage")
    public String manageJob(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "5") int size) {
        model.addAttribute("title1", "Manage Jobs");
        Pageable pageable = PageRequest.of(page, size);
        Page<Recruit> applications = recruitRepository.findAll(pageable);
        model.addAttribute("applications", applications);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", applications.getTotalPages());
        return "placement/manageJob";
    }

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

    @GetMapping("/edit/{id}")
    public String showEditPlacementForm(@PathVariable Long id, Model model) {
        Recruit placement = recruitRepository.findById(id);
        if (placement == null) {
            return "redirect:/Placement/manage";
        }
        model.addAttribute("placement", placement);
        model.addAttribute("p", placement.getPlacement());
        return "placement/edit-placement";
    }

    @PostMapping("/update")
    public String updatePlacement(@ModelAttribute Recruit placement) {
        Recruit r1 = recruitRepository.getReferenceById(placement.getId());
        placement.setPlacement(r1.getPlacement());
        recruitRepository.save(placement);
        return "redirect:/Placement/manage";
    }

    @GetMapping("/view-applications/{id}")
    public String viewApplication(@PathVariable("id") int id, Model model) {
        Recruit recruit = recruitRepository.getReferenceById(id);
        List<Application> application = applicationRepository.findAllByRecruit(recruit);
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