package com.sudheer.placement_tracker.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Transient;

@Data
@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;  // EASY, MEDIUM, HARD

    @Column
    private String link;            // Leetcode link

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonIgnore
    private Section section;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
    @Transient
    private String userStatus;

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}