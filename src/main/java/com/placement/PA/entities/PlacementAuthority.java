/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

/**
 *
 * @author gs671
 */
@Entity
public class PlacementAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pid")
    private int pid;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "contact", nullable = false, length = 10, unique = true)
    private String contact;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "dob", nullable = false, length = 45)
    private String dob;
    private boolean enabled;
    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'ROLE_ADMIN'")
    private String role;

    @OneToMany(mappedBy = "placement", cascade = CascadeType.ALL)
    private List<Recruit> recruit; // Change from single object to a List
    // Default constructor

    public PlacementAuthority() {
    }

    // Getters and setters
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Recruit> getRecruit() {
        return recruit;
    }

    public void setRecruit(List<Recruit> recruit) {
        this.recruit = recruit;
    }

   

    @Override
    public String toString() {
        return "PlacementAuthority{"
                + "pid=" + pid
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", contact='" + contact + '\''
                + ", password='" + password + '\''
                + ", dob='" + dob + '\''
                + '}';
    }
}
