package com.pluralsight.NorthwindTradersSpringBoot;

import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindApp implements CommandLineRunner {

    @Autowired
    private ProductDao productDao;

    @Override
    public void run(String... args) throws Exception {

        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.print("""
                    
                    --- Product Admin Menu ---
                    1) List Products
                    2) Add Product
                    0) Exit
                    """);
            System.out.print("Enter choice: ");

            int choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1:
                    List<Product> products = productDao.getAll();

                    for (Product product : products) {
                        System.out.printf("""
                                
                                Product ID:     %d
                                Product Name:   %s
                                Category:       %s
                                Price:          %.2f
                                """, product.getProductId(), product.getName(), product.getCategoryId(), product.getPrice());
                    }

                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    String name = userInput.nextLine();

                    System.out.print("Enter product category: ");
                    int categoryId = userInput.nextInt();
                    userInput.nextLine();

                    System.out.print("Enter product price: ");
                    double price = userInput.nextDouble();
                    userInput.nextLine();

                    Product newProduct = new Product();

                    newProduct.setName(name);
                    newProduct.setCategoryId(categoryId);
                    newProduct.setPrice(price);

                    productDao.add(newProduct);

                    System.out.println("Product added successfully!");

                    break;
                case 0:
                    System.out.println("Exiting app... Goodbye!");

                    System.exit(0);
                default:
                    System.out.println("Please choose 1-2 or 0 to exit!");
                    break;
            }
        }
    }
}
