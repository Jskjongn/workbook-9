package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
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

    @Override
    public Category add(Category category) {

        try (
                Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("""
                        INSERT INTO categories (CategoryName)
                        VALUES
                        (?);
                        """, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, category.getCategoryName());

            preparedStatement.executeUpdate();

            // gets new primary key of new film and sets it
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    int newId = keys.getInt(1);
                    category.setCategoryId(newId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }
}
