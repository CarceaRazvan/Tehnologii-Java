package com.demo.rest.laboratorul11.repository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class DataRepository<T, ID extends Serializable> implements Serializable {
    protected Class<T> entityClass;

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;

    protected DataRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PostConstruct
    protected void init() {
        if (em == null) {
            throw new IllegalStateException("EntityManager has not been injected!");
        }
    }

    public T newInstance() {
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }

    public T refresh(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.refresh(entity);
        return entity;
    }

    public T findById(ID id) {
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id);
    }
    public List<T> findAll() {
        String qlString =
                "select e from " + entityClass.getSimpleName() + " e";
        return em.createQuery(qlString).getResultList();
    }
    public void clearCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }
}