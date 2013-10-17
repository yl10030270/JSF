package lab05_tshirtsBusiness.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lab05_tshirtsBusiness.access.CustomerTableAdaptor;
import lab05_tshirtsBusiness.entityModle.CustomerTable;

@Named
@ConversationScoped
public class ListCustomersController implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private CustomerTableAdaptor cTableAdaptor;
    @Inject
    private CustomerTable cTable;

    public String startEditing() throws CloneNotSupportedException {
        conversation.begin();
        cTableAdaptor.fill(cTable);
        return "editCustomers";
    }

}
