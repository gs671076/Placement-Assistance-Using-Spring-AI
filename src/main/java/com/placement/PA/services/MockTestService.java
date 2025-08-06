package com.placement.PA.services;

import com.placement.PA.entities.MockTest;
import com.placement.PA.repository.MockTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockTestService {

    @Autowired
    private MockTestRepository mockTestRepository;

    // Retrieve all available mock tests
    public List<MockTest> getAllMockTests() {
        return mockTestRepository.findAll();
    }

    // Retrieve MockTest by its ID
    public MockTest getMockTestById(Long id) {
        return mockTestRepository.findById(id).orElse(null); // If not found, return null
    }
}
