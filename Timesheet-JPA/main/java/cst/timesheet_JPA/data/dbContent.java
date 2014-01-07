package cst.timesheet_JPA.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cst.timesheet_JPA.model.Employ;

/**
 * @author leon Represent local database
 */
public class dbContent implements Serializable {

    /**
     * Local Employ table.
     */
    private HashSet<Employ> employs;
    /**
     * Local Timesheet table.
     */
    private List<StatefulTimesheet> timesheets;
    /**
     * Local Record table.
     */
    private List<StatefulRecord> records;

    public dbContent() {
        employs = new HashSet<Employ>();
        timesheets = new ArrayList<StatefulTimesheet>();
        records = new ArrayList<StatefulRecord>();
    }

    public HashSet<Employ> getEmploys() {
        return employs;
    }

    public void setEmploys(HashSet<Employ> employs) {
        this.employs = employs;
    }

    public List<StatefulTimesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<StatefulTimesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public List<StatefulRecord> getRecords() {
        return records;
    }

    public void setRecords(List<StatefulRecord> records) {
        this.records = records;
    }

}
