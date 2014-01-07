/**
 * 
 */
package cst.timesheet_JPA.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import cst.timesheet_JPA.model.Timesheet;

/**
 * @author leon
 * 
 */
public class StatefulTimesheet implements Serializable,
        Comparable<StatefulTimesheet> {

    /**
     * timesheet mapped to datarow in database
     */
    private Timesheet timesheet;
    /**
     * Entity state
     */
    private EntityState state;
    /**
     * Calculated Week Number.
     */
    private Integer weekNum;

    public StatefulTimesheet() {
    }

    public StatefulTimesheet(Timesheet timesheet, EntityState state) {
        this.timesheet = timesheet;
        this.state = state;
        this.weekNum = WeekEnd2WeekNumber(timesheet.getWeekEnd());
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    /**
     * Helper method , covert Week end to Week number
     * 
     * @param weekEnd
     * @return week number
     */
    private Integer WeekEnd2WeekNumber(Date weekEnd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(weekEnd);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(StatefulTimesheet o) {
        int result = this.timesheet.getEmploy().getEmployName()
                .compareTo(o.getTimesheet().getEmploy().getEmployName());
        if (result == 0) {
            return this.timesheet.getWeekEnd().compareTo(
                    o.getTimesheet().getWeekEnd());
        } else {
            return result;
        }
    }
}
