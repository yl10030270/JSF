/**
 * 
 */
package cst.timesheet_JPA.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cst.timesheet_JPA.data.EntityState;
import cst.timesheet_JPA.data.StatefulRecord;
import cst.timesheet_JPA.data.dbContent;
import cst.timesheet_JPA.model.Record;
import cst.timesheet_JPA.model.Timesheet;

/**
 * @author leon
 * 
 */
@Stateless
public class RecordEntityManager {

    @Inject
    EntityManager em;

    public RecordEntityManager() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Load records into local database by timesheet
     * 
     * @param db - local database
     * @param ts - timesheet
     */
    public void load(dbContent db, Timesheet ts) {
        db.getRecords().clear();
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder
                .append("select r from Record r WHERE r.timesheet.timesheetId = ");
        sqlStringBuilder.append(ts.getTimesheetId());
        sqlStringBuilder.append(" ORDER BY r.project, r.wp");
        TypedQuery<Record> query = em.createQuery(sqlStringBuilder.toString(),
                Record.class);
        List<Record> records = query.getResultList();
        for (Record record : records) {
            db.getRecords().add(
                    new StatefulRecord(record, EntityState.unchanged));
        }
    }

    /**
     * Check project and wp existance
     * 
     * @param r
     *            - record
     * @return true if exist.
     */
    public boolean exist(Record r) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder
                .append("select r from Record r WHERE r.timesheet.timesheetId = ");
        sqlStringBuilder.append(r.getTimesheet().getTimesheetId() + " and ");
        sqlStringBuilder.append("r.project = " + r.getProject() + " and ");
        sqlStringBuilder.append("r.wp = " + "'" + r.getWp() + "'");
        TypedQuery<Record> query = em.createQuery(sqlStringBuilder.toString(),
                Record.class);
        List<Record> records = query.getResultList();
        if (records.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * save changes made in local database to database
     * 
     * @param dbContent
     *            - local database
     * @throws Exception
     */
    public void saveChanges(dbContent dbContent) throws Exception {
        for (StatefulRecord x : dbContent.getRecords()) {
            if ((x.getRecord().getProject() == 0)
                    && (x.getRecord().getWp() == null || x.getRecord().getWp()
                            .isEmpty())) {
                continue;
            }
            switch (x.getState()) {
            case added:
                if (exist(x.getRecord())) {
                    throw new Exception("Project ("
                            + x.getRecord().getProject() + ") and WP ("
                            + x.getRecord().getWp() + ") already exist.");
                }
                if (x.getRecord().getProject() == 0
                        && !(x.getRecord().getWp().isEmpty())) {
                    throw new Exception("Project can not be 0 or empty.");
                }
                if (!(x.getRecord().getProject() == 0)
                        && (x.getRecord().getWp() == null || x.getRecord()
                                .getWp().isEmpty())) {
                    throw new Exception("Wp can not be empty.");
                }
                em.persist(x.getRecord());
                break;
            case modified:
                em.merge(x.getRecord());
                break;
            case deleted:
                if (x.getRecord().getRecordId() != null) {
                    Record tmpRecord = em.find(Record.class, x.getRecord()
                            .getRecordId());
                    em.remove(tmpRecord);
                }
                break;
            default:
                break;
            }
        }
    }
}
