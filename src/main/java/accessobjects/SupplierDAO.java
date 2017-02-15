package accessobjects;

import entities.Supplier;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class SupplierDAO {
    public void deleteSupplier(Supplier toBeDeleted) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " DELETE from supplier WHERE SupplierID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getSupplierID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addSupplier(Supplier supplier) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " insert into supplier (Address, Name, Email, Website,ContactNumber,  SupplierID)"
                    + " values (?, ?, ?, ?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(SupplierID) FROM supplier");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, supplier.getAddress());
            preparedStmt.setString (2, supplier.getName());
            preparedStmt.setString(3, supplier.getEmail());
            //  preparedStmt.setDate   (4, event.getDate());
            preparedStmt.setString(4, supplier.getWebsite());
            preparedStmt.setInt(5, supplier.getContactNumber());
            preparedStmt.setInt(6, highestValue+1);

            // execute the preparedstatement
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Supplier> getAllSuppliers(){
        ArrayList<Supplier> suppliers = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM supplier");

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierID(rs.getInt(1));
                supplier.setName(rs.getString(2));
                supplier.setAddress(rs.getString(3));
                supplier.setContactNumber(rs.getInt(4));
                supplier.setEmail(rs.getString(5));
                supplier.setWebsite(rs.getString(6));
                suppliers.add(supplier);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return suppliers;
    }

    public ArrayList<Supplier> getSupplierByName(String filter){
        ArrayList<Supplier> suppliers = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM supplier WHERE name LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Supplier supplier = new Supplier();
                supplier.setSupplierID(rs.getInt(1));
                supplier.setName(rs.getString(2));
                supplier.setAddress(rs.getString(3));
                supplier.setContactNumber(rs.getInt(4));
                supplier.setEmail(rs.getString(5));
                supplier.setWebsite(rs.getString(6));
                suppliers.add(supplier);



            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return suppliers;
    }
}
