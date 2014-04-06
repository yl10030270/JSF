/**
 * 
 */
package com.lab07_JPA.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lab07_JPA.model.Suppliers;
import com.lab07_JPA.service.SupplierManager;

/**
 * @author leon
 * 
 */
@Path("/suppliers")
@RequestScoped
public class SuppliersResourceRESTService {

    @Inject
    private SupplierManager supplierManager;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Suppliers[] getAll() {
        return supplierManager.getAll();
    }
}
