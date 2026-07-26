package com.crs.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    private LocalDate dob;

    @Column(length = 100)
    private String program;

    private Integer year;

    @Column(length = 100)
    private String contact;

    // Constructors, Getters, Setters
    public Student() {}

    public Student(User user, String name, LocalDate dob, String program, Integer year, String contact) {
        this.user = user;
        this.name = name;
        this.dob = dob;
        this.program = program;
        this.year = year;
        this.contact = contact;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}
