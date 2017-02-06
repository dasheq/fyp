package accessobjects;

import entities.Product;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class ProductDAO {
    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> products = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getFloat(3));
                product.setQuantity(rs.getInt(4));
                product.setRefNumber(rs.getInt(5));

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

    public ArrayList<Product> getProductByName(String filter){
        ArrayList<Product> products = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE name LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getFloat(3));
                product.setQuantity(rs.getInt(4));
                product.setRefNumber(rs.getInt(5));

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