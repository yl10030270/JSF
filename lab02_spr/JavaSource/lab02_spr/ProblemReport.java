package lab02_spr;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
// or import javax.faces.bean.ManagedBean;
// or import javax.faces.bean.SessionScoped;

@Named
// or @ManagedBean
@RequestScoped
public class ProblemReport implements Serializable {
    @Inject
    private Project project;
    private String number;
    private String doi;
    private String originator;
    private String modAffected;
    private String description;
    private String recSolution;
    private Priority priority = Priority.LOW;
    private String severity;
    private String dateAssigned;
    private String dateFixed;
    private boolean inscope = true;


    public boolean isInscope() {
        return inscope;
    }

    public void setInscope(boolean inscope) {
        this.inscope = inscope;
    }

    public ProblemReport() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getModAffected() {
        return modAffected;
    }

    public void setModAffected(String modAffected) {
        this.modAffected = modAffected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecSolution() {
        return recSolution;
    }

    public void setRecSolution(String recSolution) {
        this.recSolution = recSolution;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public String getDateFixed() {
        return dateFixed;
    }

    public void setDateFixed(String dateFixed) {
        this.dateFixed = dateFixed;
    }
    
    public String submit() {
        return "success";
    }
    public String back(){
        return "enterProblem";     
    }

}
