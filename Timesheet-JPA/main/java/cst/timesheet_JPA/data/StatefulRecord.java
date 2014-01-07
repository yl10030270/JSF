/**
 * 
 */
package cst.timesheet_JPA.data;

import java.io.Serializable;

import cst.timesheet_JPA.model.Record;

/**
 * @author leon
 * 
 */
public class StatefulRecord implements Serializable {

    /**
     * Mapped Entity
     */
    private Record record;

    /**
     * Entity state in local database
     */
    private EntityState state;

    public StatefulRecord(Record record, EntityState state) {
        this.record = record;
        this.state = state;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public Float getTotal() {
        float mon = record.getMon() == null ? 0 : record.getMon().floatValue();
        float tue = (record.getTue() == null ? 0 : record.getTue().floatValue());
        float wed = (record.getWed() == null ? 0 : record.getWed().floatValue());
        float thu = (record.getThu() == null ? 0 : record.getThu().floatValue());
        float fri = (record.getFri() == null ? 0 : record.getFri().floatValue());
        float sat = (record.getSat() == null ? 0 : record.getSat().floatValue());
        float sun = (record.getSun() == null ? 0 : record.getSun().floatValue());
        float result = mon + tue + wed + thu + fri + sat + sun;
        if (result == 0) {
            return null;
        } else {
            return result;
        }
    }

}
