/**
 * 
 */
package com.lab07_JPA.data;

import com.lab07_JPA.model.Suppliers;

/**
 * @author leon
 * 
 */
public class EditableSupplier {
    /** Indicates associated product can be edited on a form. */
    private boolean editable;

    /** Holds product to be displayed, edited or deleted. */
    private Suppliers supplier;

    private boolean added;

    public EditableSupplier(Suppliers supplier) {
        super();
        this.supplier = supplier;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

}
