package lab05_tshirtsBusiness.mockDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Data implements Serializable {

    private LinkedHashMap<Integer, Customer> customers;

    public Data() {
        customers = new LinkedHashMap<Integer, Customer>();
        customers.put(101, new Customer(101, "Rajiv", "pauli", "",
                "213 Erstwild Court", "Apt 30", "Sunnyvale", "CA", "94086",
                "408-789-8075", "rp@rpuli.com "));
        customers.put(102, new Customer(102, "Carole", "Sandler", "S",
                "785 Geary St", "", "San Francisco", "CA", "94117",
                "415-822-1289", "cs@sandler.com"));
        customers.put(103, new Customer(103, "Philip", "Currie", "",
                "654 Poplar", "Apt 50", "Wahsington", "CA", "94303",
                "415-328-4543", "pc@pCurrie.com"));
        customers.put(104, new Customer(104, "Tony", "Higgins", "H",
                "East Shopping Cntr.", "422 Bay Road", "Altanta", "CA",
                "94026", "415-328-4542", "th@tHiggins.com"));

    }

    public LinkedHashMap<Integer, Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Customer> getCustomerTable() {
        return new ArrayList<Customer>(customers.values());
    }
}
