package cst.timesheet_JPA.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cst.timesheet_JPA.data.dbContent;
import cst.timesheet_JPA.model.Employ;

/**
 * @author leon
 * 
 */
@Stateless
public class EmployEntityManager implements Serializable {

    @Inject
    EntityManager em;

    public EmployEntityManager() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Authenticate user login
     * 
     * @param name
     *            - user name
     * @param password
     * @return
     * @throws Exception
     *             - throw when user not exist or wrong password.
     */
    public Employ Authenticate(String name, String password) throws Exception {
        String sqlString = "select s from Employ s WHERE s.employName = ?1";
        TypedQuery<Employ> query = em.createQuery(sqlString, Employ.class);
        query.setParameter(1, name);
        List<Employ> employs = query.getResultList();
        if (employs.size() == 0) {
            throw new Exception("User doesn't exist.");
        } else if (!employs.get(0).getPassword().equals(password)) {
            throw new Exception("Wrong passowrd.");
        } else {
            return employs.get(0);
        }
    }

    /**
     * Load User table from database
     * 
     * @param db
     *            - local data store;
     */
    public void load(dbContent db) {
        db.getEmploys().clear();
        TypedQuery<Employ> query = em.createQuery("select s from Employ s",
                Employ.class);
        List<Employ> employs = query.getResultList();
        Employ[] suparray = new Employ[employs.size()];
        for (int i = 0; i < suparray.length; i++) {
            db.getEmploys().add(employs.get(i));
        }
    }

    /**
     * Save user into database
     * 
     * @param employ
     */
    public void saveChanges(Employ employ) {
        em.persist(employ);
    }

    public Employ find(int id) {
        return em.find(Employ.class, id);
    }

    public void remove(Employ employ) throws Exception {
        Employ tmpEmploy = Authenticate(employ.getEmployName(),
                employ.getPassword());
        em.remove(tmpEmploy);
    }
}
