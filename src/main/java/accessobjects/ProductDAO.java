package accessobjects;

import entities.Product;
import entities.ProductButton;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class ProductDAO {
    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> products = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getFloat(3));
                product.setQuantity(rs.getInt(4));
                product.setRefNumber(rs.getInt(5));
                product.setType(rs.getString(6));

                products.add(product);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return products;
    }

    public ArrayList<ProductButton> getAllProductsBtn() {
        ArrayList<ProductButton> products = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                ProductButton product = new ProductButton();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getFloat(3));
                product.setQuantity(rs.getInt(4));
                product.setRefNumber(rs.getInt(5));
                product.setType(rs.getString(6));

                products.add(product);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return products;
    }

    public void addProduct(Product product) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = " insert into product (ProductID, Name, Price, Quantity, RefNumber, Type)"
                    + " values (?, ?, ?, ?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(ProductID) FROM product");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, highestValue+1);
            preparedStmt.setString(2, product.getName());
            preparedStmt.setFloat(3, product.getPrice());
            preparedStmt.setInt(4, product.getQuantity());
            preparedStmt.setInt(5, 0);
            preparedStmt.setString(6, product.getType());
            // execute the preparedstatement
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateProductQuantityButton(ProductButton toBeUpdated) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = "UPDATE product \n" +
                    "SET Quantity = Quantity-1\n" +
                    "WHERE productid = ?;";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeUpdated.getProductID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteProduct(Product toBeDeleted) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = " DELETE from product WHERE ProductID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getProductID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getTypes() {
        ArrayList<String> types = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT TYPE FROM product");

            while (rs.next()) {
                types.add(rs.getString(1));
            }

            con.close();

        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return types;

    }

    public ArrayList<Product> getProductByName(String filter){
        ArrayList<Product> products = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE name LIKE ? OR Type LIKE ? OR Price LIKE ?");
            stmt.setString(1, filter);
            stmt.setString(2, filter);
            stmt.setString(3, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getFloat(3));
                product.setQuantity(rs.getInt(4));
                product.setRefNumber(rs.getInt(5));
                product.setType(rs.getString(6));

                products.add(product);


            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return products;
    }
}
