package com.friend_map.persistence_layer.dao;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class BaseDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /** ПОИСК ПО АЙДИ */
    public <T> T findById(Class<T> type, final UUID id) {
        getSession().setCacheMode(CacheMode.NORMAL);
        return sessionFactory.getCurrentSession().get(type, id);
    }

    /** ПОЛУЧИТЬ ВСЕ */
    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<T> type) {
        getSession().setCacheMode(CacheMode.NORMAL);
        return (List<T>) sessionFactory.getCurrentSession().createCriteria(type).setCacheable(true).list();
    }

    public void persist(Object object) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        getSession().persist(object);
    }

    public void merge(Object object) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        getSession().merge(object);
    }

    public void butchPersist(List<Object> objects) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        getSession().persist(objects);
    }

    public UUID save(Object object) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        return (UUID) getSession().save(object);
    }

    public void update(Object object) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        getSession().update(object);
    }

    public void delete(Object object) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        getSession().delete(object);
    }

    public void butchDelete(List<Object> objects) throws Exception {
        getSession().setCacheMode(CacheMode.NORMAL);
        getSession().delete(objects);
    }
}