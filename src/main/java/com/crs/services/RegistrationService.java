package com.crs.services;

import com.crs.dao.CourseDAO;
import com.crs.dao.EnrollmentDAO;
import com.crs.dao.StudentDAO;
import com.crs.models.Course;
import com.crs.models.Enrollment;
import com.crs.models.Student;

import java.util.List;
import java.util.Set;

public class RegistrationService {
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public String registerStudentForCourse(Long studentId, Long courseId, String semester) {
        Student student = studentDAO.findById(Student.class, studentId);
        Course course = courseDAO.findById(Course.class, courseId);

        if (student == null || course == null) {
            return "Student or Course not found.";
        }

        // 1. Check Capacity
        long currentEnrollmentCount = enrollmentDAO.getEnrolledCountForCourse(courseId);
        if (currentEnrollmentCount >= course.getMaxCapacity()) {
            return "Registration failed: Course is at maximum capacity.";
        }

        // 2. Check Prerequisites
        List<Enrollment> studentEnrollments = enrollmentDAO.findByStudentId(studentId);
        Set<Course> prerequisites = course.getPrerequisites();
        
        for (Course prereq : prerequisites) {
            boolean hasCompletedPrereq = false;
            for (Enrollment e : studentEnrollments) {
                if (e.getCourse().getId().equals(prereq.getId()) && "COMPLETED".equals(e.getStatus())) {
                    hasCompletedPrereq = true;
                    break;
                }
            }
            if (!hasCompletedPrereq) {
                return "Registration failed: Missing prerequisite - " + prereq.getTitle();
            }
        }

        // 3. Check if already enrolled
        for (Enrollment e : studentEnrollments) {
            if (e.getCourse().getId().equals(courseId) && ("REGISTERED".equals(e.getStatus()) || "COMPLETED".equals(e.getStatus()))) {
                return "Registration failed: Already enrolled or completed this course.";
            }
        }

        // 4. Register
        Enrollment newEnrollment = new Enrollment(student, course, semester, "REGISTERED");
        enrollmentDAO.save(newEnrollment);

        return "Successfully registered for " + course.getTitle();
    }

    public String dropCourse(Long studentId, Long courseId) {
        List<Enrollment> studentEnrollments = enrollmentDAO.findByStudentId(studentId);
        for (Enrollment e : studentEnrollments) {
            if (e.getCourse().getId().equals(courseId) && "REGISTERED".equals(e.getStatus())) {
                e.setStatus("DROPPED");
                enrollmentDAO.update(e);
                return "Successfully dropped course.";
            }
        }
        return "Failed to drop: Not currently registered for this course.";
    }
}
