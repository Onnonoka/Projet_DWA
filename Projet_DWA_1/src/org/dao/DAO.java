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
 *
 * @author fred2
 * @param <D>
 */
public abstract class DAO<D> {
    
    @PersistenceContext
    protected final EntityManager entityManager;
    protected final EntityTransaction entityTransaction;
    private Class<D> clazz;
    
   /**
     * Retourne à partir du support de persistance un objet en fonction de son identifiant
     * @param id identifiant de l'objet
     * @return l'instance de l'objet
     * @throws DAOException en cas de problème
    */
    public D find(Object id) throws DAOException {
        return entityManager.find(clazz, id);
    }

    /**
     * Rend persistant un objet qui n'avait pas encore de réprésentation sur le support de persistance
     * @param data l'objet à rendre persistant
     * @throws DAOException en cas de problème
     */
    public void create(D data) throws DAOException {
        this.entityTransaction.begin();
        this.entityManager.persist( data );
        this.entityTransaction.commit();
    }

    /**
     * Met à jour le contenu correspondant à l'objet sur le support persistant (l'objet
     * avait déjà une représentation sur le support persistant)
     * @param data l'objet modifié dont le contenu est à mettre à jour
     * @throws DAOException en cas de problème
     */
    public void update(D data) throws DAOException {
        this.entityTransaction.begin();
        this.entityManager.merge( data );
        this.entityTransaction.commit();
    }

    /**
     * Efface du support persistant le contenu équivalent à l'objet
     * @param data l'objet à supprimer 
     * @throws DAOException en cas de problème
     */
    public void delete(D data) throws DAOException {
        this.entityTransaction.begin();
        this.entityManager.remove( data );
        this.entityTransaction.commit();
    }
    
    /**
     * Return all element from the table RESULTAT
     * @return  all element from the table RESULTAT
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
