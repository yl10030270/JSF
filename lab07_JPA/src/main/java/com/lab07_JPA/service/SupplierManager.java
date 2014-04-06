package com.lab07_JPA.service;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.lab07_JPA.model.Suppliers;

/**
 * Handle CRUD actions for Supplier class.
 * 
 * @author blink
 * @version 1.0
 * 
 */
@SessionScoped
@Stateful
public class SupplierManager implements Serializable {
    @PersistenceContext(unitName = "lab07_JPA")
    EntityManager em;

    /**
     * Find Supplier record from database.
     * 
     * @param id
     *            primary key of supplier record.
     * @return the Supplier record with key = id, null if not found.
     */
    public Suppliers find(int id) {
        return em.find(Suppliers.class, id);
    }

    /**
     * Persist suppliers record into database. id must be unique.
     * 
     * @param suppliers
     *            the record to be persisted.
     */
    public void persist(Suppliers supplier) {
        em.persist(supplier);
    }

    /**
     * merge Supplier record fields into existing database record.
     * 
     * @param supplier
     *            the record to be merged.
     */
    public void merge(Suppliers supplier) {
        em.merge(supplier);
    }

    /**
     * Remove supplier from database.
     * 
     * @param supplier
     *            record to be removed from database
     */
    public void remove(Suppliers supplier) {
        // attach supplier
        supplier = find(supplier.getSupplierId());
        em.remove(supplier);
    }

    /**
     * Return Suppliers table as array of Supplier.
     * 
     * @return Supplier[] of all records in Suppliers table
     */
    public Suppliers[] getAll() {
        TypedQuery<Suppliers> query = em.createQuery(
                "select s from Suppliers s", Suppliers.class);
        java.util.List<Suppliers> suppliers = query.getResultList();
        Suppliers[] suparray = new Suppliers[suppliers.size()];
        for (int i = 0; i < suparray.length; i++) {
            suparray[i] = suppliers.get(i);
        }
        return suparray;
    }

    public Suppliers[] getByName(String name) {
        TypedQuery<Suppliers> query = em.createQuery(
                "select s from Suppliers s where s.supplierName like :keyword",
                Suppliers.class);
        query.setParameter("keyword","%" + name + "%");
        java.util.List<Suppliers> suppliers = query.getResultList();
        Suppliers[] suparray = new Suppliers[suppliers.size()];
        for (int i = 0; i < suparray.length; i++) {
            suparray[i] = suppliers.get(i);
        }
        return suparray;
    }
}
