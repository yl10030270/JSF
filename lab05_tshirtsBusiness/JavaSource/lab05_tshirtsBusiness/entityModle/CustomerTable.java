package lab05_tshirtsBusiness.entityModle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@Named
@ConversationScoped
public class CustomerTable implements Serializable {

    private List<CustomerRow> rows;

    public List<CustomerRow> getRows() {
        return rows;
    }

    public void setRows(List<CustomerRow> rows) {
        this.rows = rows;
    }

    public CustomerTable() {
        super();
        rows = new ArrayList<CustomerRow>();
    }

    public String editRow(CustomerRow row) {
        row.setState(EntityState.modified);
        return null;
    }

    public String toString() {
        String result = null;
        for (CustomerRow row : rows) {
            result += row.rowView.getCustomerId() + row.state.toString() + "  ";
        }
        return result;
    }

}
