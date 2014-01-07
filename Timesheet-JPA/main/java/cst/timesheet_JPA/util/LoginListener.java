package cst.timesheet_JPA.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class LoginListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1);
        HttpSession session = (HttpSession) facesContext.getExternalContext()
                .getSession(false);

        if (session == null) {
            NavigationHandler nh = facesContext.getApplication()
                    .getNavigationHandler();
            nh.handleNavigation(facesContext, null, "login");
        } else {
            Object currentUser = session.getAttribute("user");

            if (!isLoginPage && (currentUser == null || currentUser == "")) {
                NavigationHandler nh = facesContext.getApplication()
                        .getNavigationHandler();
                nh.handleNavigation(facesContext, null, "login");
            }
        }

    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public PhaseId getPhaseId() {
        // TODO Auto-generated method stub
        return PhaseId.RESTORE_VIEW;
    }

}
