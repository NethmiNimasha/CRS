package com.crs.dao;

import com.crs.models.Enrollment;
import com.crs.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class EnrollmentDAO extends GenericDAOImpl<Enrollment> {

    public List<Enrollment> findByStudentId(Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Enrollment> query = session.createQuery("from Enrollment where student.id = :studentId", Enrollment.class);
            query.setParameter("studentId", studentId);
            return query.list();
        }
    }

    public List<Enrollment> findByCourseId(Long courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Enrollment> query = session.createQuery("from Enrollment where course.id = :courseId", Enrollment.class);
            query.setParameter("courseId", courseId);
            return query.list();
        }
    }
    
    public long getEnrolledCountForCourse(Long courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("select count(e) from Enrollment e where e.course.id = :courseId and e.status = 'REGISTERED'", Long.class);
            query.setParameter("courseId", courseId);
            Long count = query.uniqueResult();
            return count != null ? count : 0;
        }
    }
}
