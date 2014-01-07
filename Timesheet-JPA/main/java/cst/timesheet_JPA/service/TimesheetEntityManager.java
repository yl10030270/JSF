/**
 * 
 */
package cst.timesheet_JPA.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cst.timesheet_JPA.data.EntityState;
import cst.timesheet_JPA.data.StatefulTimesheet;
import cst.timesheet_JPA.data.dbContent;
import cst.timesheet_JPA.model.Employ;
import cst.timesheet_JPA.model.Timesheet;

/**
 * @author leon
 * 
 */
@Stateless
public class TimesheetEntityManager implements Serializable {

    @Inject
    EntityManager em;

    public TimesheetEntityManager() {

    }

    /**
     * Load all timesheets into local database.
     * 
     * @param db - local database
     */
    public void load(dbContent db) {
        db.getTimesheets().clear();
        TypedQuery<Timesheet> query = em.createQuery(
                "select t from Timesheet t", Timesheet.class);
        List<Timesheet> timesheets = query.getResultList();
        for (Timesheet timesheet : timesheets) {
            db.getTimesheets().add(
                    new StatefulTimesheet(timesheet, EntityState.unchanged));
        }
    }

    /**
     * Load all timesheets into local database for employ
     * 
     * @param db - local database
     * @param employ - 
     */
    public void load(dbContent db, Employ employ) {
        db.getTimesheets().clear();
        TypedQuery<Timesheet> query = em.createQuery(
                "select t from Timesheet t WHERE t.employ.employNumber = "
                        + employ.getEmployNumber(), Timesheet.class);
        List<Timesheet> timesheets = query.getResultList();
        for (Timesheet timesheet : timesheets) {
            db.getTimesheets().add(
                    new StatefulTimesheet(timesheet, EntityState.unchanged));
        }
    }

    /**
     * Save changes made in local database to database
     * 
     * @param dbContent
     */
    public void saveChanges(dbContent dbContent) {
        for (StatefulTimesheet x : dbContent.getTimesheets()) {
            switch (x.getState()) {
            case added:
                em.persist(x.getTimesheet());
                break;
            case deleted:
                Timesheet tmpTimesheet = em.find(Timesheet.class, x
                        .getTimesheet().getTimesheetId());
                em.remove(tmpTimesheet);
                break;
            default:
                break;
            }
        }
    }
}
