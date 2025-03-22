package com.example.volunteerMatching.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserInfo")
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    @Id
    @Column(name = "UserID")
    private Long userId;
    
    @Column(name = "FullName")
    private String name;
    
    @Column(name = "Email")
    private String email;
    
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    
    @Column (name = "Address1")
    private String address1;

    @Column (name = "Address2")
    private String address2;

    @Column (name = "City")
    private String city;

    @Column (name = "State")
    private String state;

    @Column (name = "ZipCode")
    private String zipCode;

    @Column(name = "Skills")
    private String skills;

    @Column(name = "Preferences")   
    private String preferences;

    @Column(name = "Availability")
    private String availability;

    // Define relationship with UserCredentials
    @OneToOne
    @MapsId
    @JoinColumn(name = "UserID")
    private UserCredentials userCredentials;
}