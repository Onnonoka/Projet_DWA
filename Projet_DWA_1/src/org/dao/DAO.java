/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * @param <D>
 */
public abstract class DAO<D> {
    
    @PersistenceContext
    protected final EntityManager entityManager;
    protected final EntityTransaction entityTransaction;
    private Class<D> clazz;
    
   /**
     * Returns from the persistence support an object according to its identifier.
     * @param id identifier of the object.
     * @return  the instance of the object.
    */
    public D find(Object id) {
        return entityManager.find(clazz, id);
    }

    /**
     * Make persistent an object that had no representation on the persistence medium yet.
     * @param data the object to make persistent.
     */
    public void create(D data) {
        this.entityTransaction.begin();
        this.entityManager.persist( data );
        this.entityTransaction.commit();
    }

    /**
     * Update the content corresponding to the object on the persistent medium (the object.
     * object already had a representation on the persistent medium).
     * @param data the modified object whose content is to be updated.
     */
    public void update(D data) {
        this.entityTransaction.begin();
        this.entityManager.merge( data );
        this.entityTransaction.commit();
    }

    /**
     * Delete from the persistent medium the content equivalent to the object.
     * @param data the object to delete.
     */
    public void delete(D data) {
        this.entityTransaction.begin();
        this.entityManager.remove( data );
        this.entityTransaction.commit();
    }
    
    /**
     * Return all element from the table RESULTAT.
     * @return  all element from the table RESULTAT.
     */
    public abstract List<D> findAll();
    
    protected void setClass(Class<D> classType) {
        clazz = classType;
    }
    
    public DAO() {
        this.entityManager = Persistence.createEntityManagerFactory("Projet_DWA").createEntityManager();
        this.entityTransaction = this.entityManager.getTransaction();
    }
    
}
