package com.placement.PA.controller;

import com.placement.PA.entities.Answer;
import com.placement.PA.entities.Message;
import com.placement.PA.entities.MockTest;
import com.placement.PA.entities.Question;
import com.placement.PA.entities.Student;
import com.placement.PA.repository.AnswerRepository;
import com.placement.PA.services.MockTestService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
public class MockTestController {

    @Autowired
    private MockTestService mockTestService;

    @Autowired
    private AnswerRepository answerRepository;
    // Show the list of available mock tests
    @GetMapping("/mocktests")
    public String listMockTests(Model model,HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        // If no student is found, redirect to the login page
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login"; // Redirect to login if not logged in
        }

        List<MockTest> mockTests = mockTestService.getAllMockTests();
        model.addAttribute("mockTests", mockTests);
        model.addAttribute("title1", "LIST Of Mock Test");
        return "student/mocktests"; // Thymeleaf template to show the list of mock tests
    }

    // Show the details of a selected mock test
    @GetMapping("/mocktest/{id}")
    public String getMockTest(@PathVariable Long id, Model model,HttpSession session) {
        Student student = (Student) session.getAttribute("student");

        // If no student is found, redirect to the login page
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login"; // Redirect to login if not logged in
        }

        MockTest mockTest = mockTestService.getMockTestById(id);
        model.addAttribute("mockTest", mockTest);
        model.addAttribute("title1", mockTest.getTitle());
        return "student/mocktest"; // Thymeleaf template to show the selected mock test
    }

    // Handle the submission of the mock test answers
   @PostMapping("/submitMockTest")
public String submitMockTest(@RequestParam Long mockTestId, 
                             @RequestParam Map<String, String> selectedAnswers, 
                             Model model,HttpSession session) {
    
    Student student = (Student) session.getAttribute("student");

        // If no student is found, redirect to the login page
        if (student == null) {
            session.setAttribute("message", new Message("alert-danger", "Session Got Expired"));
            return "redirect:/login"; // Redirect to login if not logged in
        }

    MockTest mockTest = mockTestService.getMockTestById(mockTestId);
    int score = 0;

    // Store selected answers
    Map<Long, Answer> selectedAnswerMap = new HashMap<>();

    for (Question question : mockTest.getQuestions()) {
        String selectedAnswerIdStr = selectedAnswers.get("question_" + question.getId());

        if (selectedAnswerIdStr != null) {
            Long selectedAnswerId = Long.parseLong(selectedAnswerIdStr);

            // Fetch the actual answer from DB
            Answer selectedAnswer = answerRepository.findById(selectedAnswerId).orElse(null);
            selectedAnswerMap.put(question.getId(), selectedAnswer);

            // Check if the selected answer is correct
            if (selectedAnswer != null && selectedAnswer.getAnswer().equals(question.getCorrectAnswer())) {
                score++;
            }
        }
    }

    
    model.addAttribute("selectedAnswers", selectedAnswerMap);
    
    model.addAttribute("title1","Result");
    model.addAttribute("mockTest", mockTest);
    model.addAttribute("score", score);
    return "student/result"; // Redirect to result page
}


}
