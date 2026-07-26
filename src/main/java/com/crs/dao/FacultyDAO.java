package com.crs.dao;

import com.crs.models.Faculty;
import com.crs.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class FacultyDAO extends GenericDAOImpl<Faculty> {

    public Faculty findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Faculty> query = session.createQuery("from Faculty where user.id = :userId", Faculty.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        }
    }
}
