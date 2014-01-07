package cst.timesheet_JPA.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cst.timesheet_JPA.data.EntityState;
import cst.timesheet_JPA.data.StatefulTimesheet;
import cst.timesheet_JPA.data.dbContent;
import cst.timesheet_JPA.model.Employ;
import cst.timesheet_JPA.model.Timesheet;
import cst.timesheet_JPA.service.TimesheetEntityManager;
import cst.timesheet_JPA.util.EditingTimesheet;
import cst.timesheet_JPA.util.EditingUser;

@Named
@SessionScoped
public class IndexController implements Serializable {

    private Employ loginUser;

    private dbContent dbContent;

    private StatefulTimesheet editingTimesheet;

    @Inject
    private TimesheetEntityManager tem;

    public IndexController() {

    }

    @Inject
    public IndexController(LoginController lc) {
        this.loginUser = lc.getUser();
        this.dbContent = lc.getDb();
    }

    public void setLoginUser(Employ loginUser) {
        this.loginUser = loginUser;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public StatefulTimesheet[] getTimesheets() {
        if (loginUser.isSuperManager()) {
            tem.load(this.dbContent);
        } else {
            tem.load(this.dbContent, loginUser);
        }
        StatefulTimesheet[] result = dbContent.getTimesheets().toArray(
                new StatefulTimesheet[0]);
        Arrays.sort(result);
        return result;
    }

    public String add() {
        Calendar newWeekEnd = new GregorianCalendar(Locale.CANADA);
        Date newDate;
        dbContent tmpDbContent = new dbContent();
        tem.load(tmpDbContent, loginUser);
        if (tmpDbContent.getTimesheets().size() == 0) {
            if (newWeekEnd.get(Calendar.DAY_OF_WEEK) == 7) {
                newWeekEnd.add(Calendar.DATE, 5);
            } else {
                int offset = 6 - newWeekEnd.get(Calendar.DAY_OF_WEEK);
                newWeekEnd.add(Calendar.DATE, offset);
            }
            newWeekEnd.set(Calendar.HOUR_OF_DAY, 0);
            newWeekEnd.set(Calendar.MINUTE, 0);
            newWeekEnd.set(Calendar.SECOND, 0);
            newWeekEnd.set(Calendar.MILLISECOND, 0);
            newDate = newWeekEnd.getTime();
        } else {
            List<Date> weekEnds = new ArrayList<Date>();
            for (StatefulTimesheet x : tmpDbContent.getTimesheets()) {
                weekEnds.add(x.getTimesheet().getWeekEnd());
            }
            newDate = (Date) Collections.max(weekEnds).clone();
            Calendar cal = Calendar.getInstance();
            cal.setTime(newDate);
            cal.add(Calendar.DATE, 7);
            newDate = cal.getTime();
        }
        dbContent.getTimesheets().add(
                new StatefulTimesheet(new Timesheet(loginUser, newDate),
                        EntityState.added));
        tem.saveChanges(dbContent);
        return null;
    }

    public String delete(StatefulTimesheet t) {
        t.setState(EntityState.deleted);
        tem.saveChanges(dbContent);
        return null;
    }

    @Produces
    @Named
    @EditingTimesheet
    public StatefulTimesheet getEditingTimesheet() {
        return editingTimesheet;
    }

    @Produces
    @Named
    @EditingUser
    public Employ getLoginUser() {
        return loginUser;
    }

    public String edit(StatefulTimesheet ts) {
        this.editingTimesheet = ts;
        return "timetable";
    }

}
