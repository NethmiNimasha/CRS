package com.crs.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private Integer credits;

    @Column(length = 100)
    private String department;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Prerequisites",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisite_course_id")
    )
    private Set<Course> prerequisites = new HashSet<>();

    // Constructors, Getters, Setters
    public Course() {}

    public Course(String title, Integer credits, String department, Integer maxCapacity) {
        this.title = title;
        this.credits = credits;
        this.department = department;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public Set<Course> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(Set<Course> prerequisites) { this.prerequisites = prerequisites; }

    @Override
    public String toString() {
        return title + " (" + department + ")";
    }
}
