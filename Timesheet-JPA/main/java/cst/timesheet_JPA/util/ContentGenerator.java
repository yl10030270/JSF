package cst.timesheet_JPA.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import cst.timesheet_JPA.data.dbContent;

@ApplicationScoped
public class ContentGenerator implements Serializable {

    @Produces
    @Named
    @Content
    public dbContent getContent() {
        return new dbContent();
    }
}
