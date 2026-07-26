package com.crs.dao;

import com.crs.models.Student;
import com.crs.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class StudentDAO extends GenericDAOImpl<Student> {

    public Student findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("from Student where user.id = :userId", Student.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        }
    }
}
