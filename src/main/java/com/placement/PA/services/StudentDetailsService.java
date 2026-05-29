package com.placement.PA.services;

import com.placement.PA.entities.Student;
import com.placement.PA.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("Student not found: " + email);
        }
        return new User(
            student.getEmail(),
            student.getPassword(),
            student.isEnabled(),
            true, true, true,
            List.of(new SimpleGrantedAuthority(student.getRole()))
        );
    }
}
