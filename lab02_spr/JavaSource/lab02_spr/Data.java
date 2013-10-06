package lab02_spr;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
// or import javax.faces.bean.ManagedBean;
   // or import javax.faces.bean.SessionScoped;

@Named // or @ManagedBean
@ApplicationScoped
public class Data implements Serializable{
    public Priority[] getPriorities(){
        return Priority.values();
    }
}
