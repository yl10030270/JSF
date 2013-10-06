package com.corejsf;

import java.io.Serializable;
import javax.inject.Named;
// or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;

// or import javax.faces.bean.SessionScoped;

@Named("user")
// or @ManagedBean(name="user")
@SessionScoped
public class UserBean implements Serializable {

    private String fname;
    private String password;
    private String lname;

    public String getFname() {
        return fname;
    }

    public void setFname(String newValue) {
        fname = newValue;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newValue) {
        password = newValue;
    }
}