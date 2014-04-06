/**
 * 
 */
package com.lab07_JPA.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lab07_JPA.model.Suppliers;
import com.lab07_JPA.service.SupplierManager;

/**
 * @author leon
 * 
 */
@Named
@SessionScoped
public class SupplierListForm implements Serializable {

    @Inject
    private SupplierManager supplierManager;

    List<EditableSupplier> list;

    private String searchString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void setList(List<EditableSupplier> list) {
        this.list = list;
    }

    public List<EditableSupplier> getList() {
        if (list == null) {
            refreshList();
        }
        return list;
    }

    private void refreshList() {
        Suppliers[] suppliers = supplierManager.getAll();
        list = new ArrayList<EditableSupplier>();
        for (int i = 0; i < suppliers.length; i++) {
            list.add(new EditableSupplier(suppliers[i]));
        }
    }

    public String save() {
        for (EditableSupplier e : list) {
            if (e.isAdded()) {
                supplierManager.persist(e.getSupplier());
                e.setEditable(false);
                e.setAdded(false);
            } else if (e.isEditable()) {
                supplierManager.merge(e.getSupplier());
                e.setEditable(false);
            }
        }
        return null;
    }

    public String deleteRow(EditableSupplier e) {
        supplierManager.remove(e.getSupplier());
        list.remove(e);
        return null;
    }

    public String add() {
        EditableSupplier newSupplier = new EditableSupplier(new Suppliers());
        newSupplier.setEditable(true);
        newSupplier.setAdded(true);
        list.add(newSupplier);
        return null;
    }

    public String search() {
        list.clear();
        Suppliers[] suppliers = supplierManager.getByName(searchString);
        for (int i = 0; i < suppliers.length; i++) {
            list.add(new EditableSupplier(suppliers[i]));
        }
        return null;
    }
}
