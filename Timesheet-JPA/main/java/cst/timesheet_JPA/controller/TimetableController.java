/**
 * 
 */
package cst.timesheet_JPA.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cst.timesheet_JPA.data.EntityState;
import cst.timesheet_JPA.data.StatefulRecord;
import cst.timesheet_JPA.data.StatefulTimesheet;
import cst.timesheet_JPA.data.dbContent;
import cst.timesheet_JPA.model.Employ;
import cst.timesheet_JPA.model.Record;
import cst.timesheet_JPA.service.RecordEntityManager;
import cst.timesheet_JPA.util.Content;
import cst.timesheet_JPA.util.EditingTimesheet;
import cst.timesheet_JPA.util.EditingUser;

/**
 * @author leon
 * 
 */
@Named
@ConversationScoped
public class TimetableController implements Serializable {

    /**
     * the login user
     */
    private Employ editingEmploy;

    /**
     * The timesheet being editing
     */
    private StatefulTimesheet ediTimesheet;

    /**
     * Local database
     */
    private dbContent dbContent;

    private Conversation conversation;

    private RecordEntityManager rem;

    public TimetableController() {
        // TODO Auto-generated constructor stub
    }

    @Inject
    public TimetableController(Conversation con, RecordEntityManager rem,
            @Content dbContent db, @EditingTimesheet StatefulTimesheet ts,
            @EditingUser Employ user) {
        this.conversation = con;
        this.rem = rem;
        this.dbContent = db;
        this.ediTimesheet = ts;
        this.editingEmploy = user;
        this.conversation.begin();
        this.rem.load(dbContent, ediTimesheet.getTimesheet());

    }

    public Employ getEditingEmploy() {
        return editingEmploy;
    }

    public void setEditingEmploy(Employ editingEmploy) {
        this.editingEmploy = editingEmploy;
    }

    public StatefulTimesheet getEdiTimesheet() {
        return ediTimesheet;
    }

    public void setEdiTimesheet(StatefulTimesheet ediTimesheet) {
        this.ediTimesheet = ediTimesheet;
    }

    /**
     * get all records from local database
     * 
     * @return - array of all records
     */
    public StatefulRecord[] getRecords() {
        int add = 5 - dbContent.getRecords().size();
        for (int i = 0; i < add; i++) {
            addNew();
        }
        StatefulRecord[] result = dbContent.getRecords().toArray(
                new StatefulRecord[0]);
        return result;
    }

    public String editRow(StatefulRecord row) {
        row.setState(EntityState.modified);
        return null;
    }

    /**
     * delete record from local database(set row state to deleted)
     * 
     * @param row
     *            - the record to be deleted
     * @return null
     */
    public String deleteRow(StatefulRecord row) {
        row.setState(EntityState.deleted);
        return null;
    }

    /**
     * Add new row to local database
     * 
     * @return null
     */
    public String addNew() {
        Record newRecord = new Record();
        newRecord.setTimesheet(ediTimesheet.getTimesheet());
        dbContent.getRecords().add(
                new StatefulRecord(newRecord, EntityState.added));
        return null;
    }

    /**
     * Save all changes made in local database to databasel
     * 
     * @return null
     */
    public String save() {
        try {
            rem.saveChanges(dbContent);
            return reset();
        } catch (Exception e) {
            String message = e.getMessage();
            FacesContext.getCurrentInstance().addMessage(
                    "timeentry:allerror",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, message,
                            message));
            return null;
        }

    }

    /**
     * Reset local database, abandon all changes
     * 
     * @return null
     */
    public String reset() {
        this.rem.load(dbContent, ediTimesheet.getTimesheet());
        return null;
    }

    /**
     * Return to timesheet management page
     * 
     * @return navigation string "index".
     */
    public String back() {
        conversation.end();
        return "index";
    }

    /**
     * @author leon Represent total row
     */
    public class TotalRow {
        float mon = 0;
        float tue = 0;
        float wed = 0;
        float thu = 0;
        float fri = 0;
        float sat = 0;
        float sun = 0;
        float total = 0;

        public float getMon() {
            return mon;
        }

        public void setMon(float mon) {
            this.mon = mon;
        }

        public float getTue() {
            return tue;
        }

        public void setTue(float tue) {
            this.tue = tue;
        }

        public float getWed() {
            return wed;
        }

        public void setWed(float wed) {
            this.wed = wed;
        }

        public float getThu() {
            return thu;
        }

        public void setThu(float thu) {
            this.thu = thu;
        }

        public float getFri() {
            return fri;
        }

        public void setFri(float fri) {
            this.fri = fri;
        }

        public float getSat() {
            return sat;
        }

        public void setSat(float sat) {
            this.sat = sat;
        }

        public float getSun() {
            return sun;
        }

        public void setSun(float sun) {
            this.sun = sun;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }

    /**
     * Get data for total Row.
     * 
     * @return total Row
     */
    public TotalRow[] getTotalRow() {
        TotalRow[] totalRow = new TotalRow[1];
        totalRow[0] = new TotalRow();
        for (StatefulRecord record : dbContent.getRecords()) {
            totalRow[0].mon += record.getRecord().getMon() == null ? 0 : record
                    .getRecord().getMon().floatValue();
            totalRow[0].tue += (record.getRecord().getTue() == null ? 0
                    : record.getRecord().getTue().floatValue());
            totalRow[0].wed += (record.getRecord().getWed() == null ? 0
                    : record.getRecord().getWed().floatValue());
            totalRow[0].thu += (record.getRecord().getThu() == null ? 0
                    : record.getRecord().getThu().floatValue());
            totalRow[0].fri += (record.getRecord().getFri() == null ? 0
                    : record.getRecord().getFri().floatValue());
            totalRow[0].sat += (record.getRecord().getSat() == null ? 0
                    : record.getRecord().getSat().floatValue());
            totalRow[0].sun += (record.getRecord().getSun() == null ? 0
                    : record.getRecord().getSun().floatValue());
            totalRow[0].total += (record.getTotal() == null ? 0 : record
                    .getTotal().floatValue());
        }
        return totalRow;
    }

}
