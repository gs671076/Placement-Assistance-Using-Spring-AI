package com.placement.PA.services;

import com.placement.PA.entities.PlacementAuthority;
import com.placement.PA.repository.PlacementAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementDetailsService implements UserDetailsService {

    @Autowired
    private PlacementAuthorityRepository placementRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PlacementAuthority pa = placementRepo.findByEmail(email);
        if (pa == null) {
            throw new UsernameNotFoundException("Placement authority not found: " + email);
        }
        return new User(
            pa.getEmail(),
            pa.getPassword(),
            pa.isEnabled(),
            true, true, true,
            List.of(new SimpleGrantedAuthority(pa.getRole()))
        );
    }
}
