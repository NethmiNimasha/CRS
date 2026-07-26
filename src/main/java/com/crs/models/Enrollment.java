package com.crs.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(length = 20)
    private String semester;

    @Column(length = 20)
    private String status; // REGISTERED, DROPPED, COMPLETED

    @Column(length = 2)
    private String grade;

    // Constructors, Getters, Setters
    public Enrollment() {}

    public Enrollment(Student student, Course course, String semester, String status) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
