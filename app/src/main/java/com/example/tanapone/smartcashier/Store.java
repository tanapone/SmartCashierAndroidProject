package com.example.tanapone.smartcashier;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Tanapone on 28/1/2561.
 */

public class Store {
    private String username;
    private String password;
    private String email;
    private String storename;
    private Vector<Product> products = new Vector<Product>();

    public Vector<Product> getProducts() {
        return products;
    }

    public void setProducts(Vector<Product> products) {
        this.products = products;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }
}
