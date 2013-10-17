package lab05_tshirtsBusiness.access;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lab05_tshirtsBusiness.entityModle.CustomerRow;
import lab05_tshirtsBusiness.entityModle.CustomerTable;
import lab05_tshirtsBusiness.entityModle.EntityState;
import lab05_tshirtsBusiness.mockDatabase.Customer;
import lab05_tshirtsBusiness.mockDatabase.Data;

@Named
@ConversationScoped
public class CustomerTableAdaptor implements Serializable {
    @Inject
    private Data dataBase;

    public String fill(CustomerTable customerTable) throws CloneNotSupportedException {
        for (Customer customer : dataBase.getCustomerTable()) {
            customerTable.getRows().add(
                    new CustomerRow(customer.clone(), EntityState.unchanged));
        }
        return "editCustomers";
    }

    public String update(CustomerTable customerTable) {
        List<CustomerRow> rows = customerTable.getRows();
        for (CustomerRow row : rows) {
            switch (row.state) {
            case added:
                int newId = dataBase.getCustomers().size() + 101;
                row.rowView.setCustomerId(newId);
                dataBase.getCustomers().put(
                        newId,
                        new Customer(newId, row.rowView.getFirstName(),
                                row.rowView.getLastName(), row.rowView
                                        .getMiddleName(), row.rowView
                                        .getAddress1(), row.rowView
                                        .getAddress2(), row.rowView.getCity(),
                                row.rowView.getState(), row.rowView
                                        .getZipCode(), row.rowView.getPhone(),
                                row.rowView.getEmail()));
                row.state = EntityState.unchanged;
                break;
            case modified:
                Customer customer = dataBase.getCustomers().get(
                        row.rowView.getCustomerId());
                customer.setFirstName(row.rowView.getFirstName());
                customer.setLastName(row.rowView.getLastName());
                customer.setMiddleName(row.rowView.getMiddleName());
                customer.setAddress1(row.rowView.getAddress1());
                customer.setAddress2(row.rowView.getAddress2());
                customer.setState(row.rowView.getState());
                customer.setCity(row.rowView.getCity());
                customer.setZipCode(row.rowView.getZipCode());
                customer.setPhone(row.rowView.getPhone());
                customer.setEmail(row.rowView.getEmail());
                row.state = EntityState.unchanged;
            default:
                break;
            }
        }
        return null;
    }
}
