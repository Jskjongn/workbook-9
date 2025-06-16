package com.pluralsight.NorthwindTradersSpringBoot.models;

public class Product {

    // properties of a product
    private int productId;
    private String name;
    private int categoryId;
    private double price;

    // constructors
    public Product() {}

    public Product(int productId, String name, int categoryId, double price) {
        this.productId = productId;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
    }

    // getter and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
