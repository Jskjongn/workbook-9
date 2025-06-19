package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
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
    public List<Product> getAll() {

        List<Product> products = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT
                            ProductID
                            , ProductName
                            , CategoryID
                            , UnitPrice
                        FROM
                            products;
                        """);

                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {

                Product product = new Product();

                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setCategoryId(resultSet.getInt("CategoryID"));
                product.setUnitPrice(resultSet.getDouble("UnitPrice"));

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public Product getById(int id) {

        Product product = new Product();

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT
                            ProductID
                            , ProductName
                            , CategoryID
                            , UnitPrice
                        FROM
                            products
                        WHERE
                            ProductID = ?;
                        """);
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {


                    product.setProductId(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryId(resultSet.getInt("CategoryID"));
                    product.setUnitPrice(resultSet.getDouble("UnitPrice"));

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
}
