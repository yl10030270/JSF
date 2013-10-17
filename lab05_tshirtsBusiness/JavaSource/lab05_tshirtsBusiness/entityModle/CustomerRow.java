package lab05_tshirtsBusiness.entityModle;

import lab05_tshirtsBusiness.mockDatabase.Customer;

public class CustomerRow {

    public Customer rowView;

    public EntityState state;

    public CustomerRow(Customer rowView, EntityState state) {
        super();
        this.rowView = rowView;
        this.state = state;
    }

    public Customer getRowView() {
        return rowView;
    }

    public void setRowView(Customer rowView) {
        this.rowView = rowView;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }
}
