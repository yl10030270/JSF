package lab02_spr;

import java.io.Serializable;

public class Project implements Serializable {
    private String name;
    private String title;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
}
