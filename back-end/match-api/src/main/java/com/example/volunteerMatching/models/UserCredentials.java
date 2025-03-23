package com.example.volunteerMatching.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "UserCredentials")
@NoArgsConstructor
@Getter
@Setter
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;
    
    @Column(name = "Password")
    private String password;
    
    // Relationship with UserProfile
    @OneToOne(mappedBy = "userCredentials", cascade = CascadeType.ALL)
    private UserInfo userInfo;
    
    // // Relationship with VolunteerHistory
    // @OneToMany(mappedBy = "userCredentials")
    // private List<VolunteerHistory> volunteerHistories;
}
