package cst.timesheet_JPA.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cst.timesheet_JPA.data.dbContent;
import cst.timesheet_JPA.model.Employ;
import cst.timesheet_JPA.service.EmployEntityManager;
import cst.timesheet_JPA.util.Content;

@Named
@SessionScoped
public class LoginController implements Serializable {

    /**
     * Login User
     */
    private Employ user;

    private EmployEntityManager eem;

    /**
     * In memory database
     */
    private dbContent db;

    public LoginController() {

    }

    @Inject
    public LoginController(@Content dbContent db, EmployEntityManager em) {
        this.eem = em;
        user = new Employ();
        this.db = db;
    }

    public Employ getUser() {
        return user;
    }

    public void setUser(Employ user) {
        this.user = user;
    }

    public EmployEntityManager getEm() {
        return eem;
    }

    public void setEm(EmployEntityManager em) {
        this.eem = em;
    }

    public dbContent getDb() {
        return db;
    }

    public void setDb(dbContent db) {
        this.db = db;
    }

    /**
     * For Login page to get all employs.
     * 
     * @return all employs List in local database
     */
    public ArrayList<Employ> getEmploys() {
        db.getEmploys().clear();
        eem.load(db);
        TreeMap<Integer, Employ> tMap = new TreeMap<Integer, Employ>();
        for (Employ x : db.getEmploys()) {
            tMap.put(x.getEmployNumber(), x);
        }
        return new ArrayList<Employ>(tMap.values());
    }

    /**
     * When register button click, try to insert user into database.
     * 
     * @param admin
     *            - if register a super admin user.
     * @return null;
     */
    public String regist(boolean admin) {
        try {
            Employ newUser = new Employ(user.getEmployName(),
                    user.getPassword(), admin);
            eem.saveChanges(newUser);
            return null;
        } catch (Exception e) {
            String message = "User already exist.";
            FacesContext.getCurrentInstance().addMessage(
                    "login:username",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, message,
                            message));
            return null;
        }
    }

    /**
     * When login button click, authenticate user.
     * 
     * @return null or index for page navigation.
     */
    public String login() {
        try {
            Employ loginUser = eem.Authenticate(user.getEmployName(),
                    user.getPassword());
            this.user = loginUser;
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("user", this.user);
            return "index";
        } catch (Exception e) {
            String message = e.getMessage();
            FacesContext.getCurrentInstance().addMessage(
                    message.contains("User") ? "login:username"
                            : "login:password",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, message,
                            message));
            return null;
        }
    }

}
