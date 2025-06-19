package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
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
public class JdbcCategoryDao implements CategoryDao{

    private DataSource dataSource;

    @Autowired
    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {

        List<Category> categories = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT
                            CategoryID
                            , CategoryName
                        FROM
                            categories;
                        """);

                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {

                Category category = new Category();

                category.setCategoryId(resultSet.getInt("CategoryID"));
                category.setCategoryName(resultSet.getString("CategoryName"));

               categories.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public Category getById(int id) {

        Category category = new Category();

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                         SELECT
                            CategoryID
                            , CategoryName
                        FROM
                            categories
                        WHERE
                            CategoryID = ?;
                        """);
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {


                    category.setCategoryId(resultSet.getInt("CategoryID"));
                    category.setCategoryName(resultSet.getString("CategoryName"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }
}
