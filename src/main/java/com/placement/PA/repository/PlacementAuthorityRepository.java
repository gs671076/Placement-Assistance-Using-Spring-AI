/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.placement.PA.repository;

import com.placement.PA.entities.PlacementAuthority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gs671
 */
@Repository
public interface PlacementAuthorityRepository extends JpaRepository<PlacementAuthority, Integer>{
    public PlacementAuthority findByEmailAndPassword(String email,String password);
    @Query("SELECT s FROM PlacementAuthority s WHERE s.email = ?1")
    Optional<PlacementAuthority> findByUsername(String username);

    public PlacementAuthority findByEmail(String email);
    
}
