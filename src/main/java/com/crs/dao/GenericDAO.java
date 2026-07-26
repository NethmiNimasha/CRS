package com.crs.dao;

import java.util.List;

public interface GenericDAO<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    T findById(Class<T> type, Long id);
    List<T> findAll(Class<T> type);
}
