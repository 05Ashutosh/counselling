package com.ashu.counsellingbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false) // foreign key column in 'students' table
    private User user;

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "mob_no")
    private String contactNumber;

    // High School Marks (storing all marks in a list)
    @ElementCollection
    @CollectionTable(name = "high_school_marks", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "ten_mark")
    private List<Double> highSchoolMarks = new ArrayList<>();

    // 10+2 Marks (storing all marks in a list)
    @ElementCollection
    @CollectionTable(name = "ten_plus_two_marks", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "th_mark")
    private List<Double> tenPlusTwoMarks = new ArrayList<>();

    // Engineering Branch Preferences
    @ElementCollection
    @CollectionTable(name = "engineering_preferences", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "preference")
    private List<String> preferences = new ArrayList<>();

    private String allocatedBranch;

    private Boolean interested=false;
}
