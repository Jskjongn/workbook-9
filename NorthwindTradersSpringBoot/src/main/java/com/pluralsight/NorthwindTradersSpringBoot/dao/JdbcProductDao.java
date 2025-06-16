package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                        INSERT INTO products (ProductName, CategoryID, UnitPrice)
                        Values (?, ?, ?)
                        """);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getCategoryId());
            preparedStatement.setDouble(3, product.getPrice());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding product - " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {

        List<Product> products = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT
                            P.ProductID
                            , P.ProductName
                            , P.CategoryID
                            , P.UnitPrice
                        FROM
                            products P
                        """);

                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int productId = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                int categoryId = resultSet.getInt("CategoryID");
                double price = resultSet.getDouble("UnitPrice");

                products.add(new Product(productId, name, categoryId, price));
            }

        } catch (SQLException e) {
            System.out.println("Error listing all products - " + e.getMessage());
        }
        return products;
    }
}
