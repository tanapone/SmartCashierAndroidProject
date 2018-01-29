package com.example.tanapone.smartcashier;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Tanapone on 28/1/2561.
 */

public class Order {
    private String orderID;
    private Date orderDate;
    private Store store;
    private Vector<Product> orderProducts = new Vector<Product>();

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Vector<Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Vector<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
