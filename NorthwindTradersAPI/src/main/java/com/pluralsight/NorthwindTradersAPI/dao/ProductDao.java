package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    Product getById(int id);

    Product add(Product Product);

    void update(int id, Product product);

    void delete(int id);
}
