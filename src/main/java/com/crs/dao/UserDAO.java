package com.crs.dao;

import com.crs.models.User;
import com.crs.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO extends GenericDAOImpl<User> {

    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }
}
