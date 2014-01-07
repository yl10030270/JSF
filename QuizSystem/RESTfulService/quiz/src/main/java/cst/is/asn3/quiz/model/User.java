package cst.is.asn3.quiz.model;

// Generated 3-Dec-2013 8:32:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "User")
public class User implements java.io.Serializable {

    private Integer idUser;
    private String loginName;
    private String password;
    private String fname;
    private String lname;
    private String studentNumber;

    private String token;
    private Date timestamp;
    private Set<QuizTaken> quizTakens = new HashSet<QuizTaken>(0);

    public User() {
    }

    public User(String loginName, String password, String studentNumber) {
        this.loginName = loginName;
        this.password = password;
        this.studentNumber = studentNumber;
    }

    public User(String loginName, String password, String fname, String lname,
            String studentNumber, Set<QuizTaken> quizTakens) {
        this.loginName = loginName;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.studentNumber = studentNumber;
        this.quizTakens = quizTakens;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idUser", unique = true, nullable = false)
    public Integer getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Column(name = "loginName", nullable = false, length = 45)
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "fname", length = 45)
    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Column(name = "lname", length = 45)
    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Column(name = "studentNumber", nullable = false, length = 45)
    public String getStudentNumber() {
        return this.studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    @Column(name = "token", length = 255)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", length = 19)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    public Set<QuizTaken> getQuizTakens() {
        return this.quizTakens;
    }

    public void setQuizTakens(Set<QuizTaken> quizTakens) {
        this.quizTakens = quizTakens;
    }

}