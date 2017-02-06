package accessobjects;

import entities.Supplier;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class SupplierDAO {
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
