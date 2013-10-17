package lab05_tshirtsBusiness.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lab05_tshirtsBusiness.access.CustomerTableAdaptor;
import lab05_tshirtsBusiness.entityModle.CustomerRow;
import lab05_tshirtsBusiness.entityModle.CustomerTable;
import lab05_tshirtsBusiness.entityModle.EntityState;
import lab05_tshirtsBusiness.mockDatabase.Customer;

@Named
@ConversationScoped
public class editCustomersController implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private CustomerTableAdaptor cTableAdaptor;
    @Inject
    private CustomerTable cTable;

    public String editRow(CustomerRow row) {
        row.setState(EntityState.modified);
        return "editCustomersController";
    }

    public String addNew() {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerId(-1);
        cTable.getRows().add(new CustomerRow(newCustomer, EntityState.added));
        return null;
    }

    public String save() {
        cTableAdaptor.update(cTable);
        return null;
    }

    public String reset() throws CloneNotSupportedException {
        cTable.getRows().clear();
        cTableAdaptor.fill(cTable);
        return null;
    }
    
    public String endEditing(){
        conversation.end();
        return "listCustomers";
    }
}
