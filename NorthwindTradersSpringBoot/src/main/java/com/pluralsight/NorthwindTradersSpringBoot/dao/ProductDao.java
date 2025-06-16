package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.models.Product;

import java.util.List;

public interface ProductDao {

    // adds a new product
    void add(Product product);

    // list all products stored
    List<Product> getAll();
}
