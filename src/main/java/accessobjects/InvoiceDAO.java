package accessobjects;

import entities.Invoice;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class InvoiceDAO {

    public ArrayList<Invoice> getAllInvoices(){
        ArrayList<Invoice> invoices = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM invoice");

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setRefNumber(rs.getInt(1));
                invoice.setDate(rs.getDate(2));
                invoice.setTotalValue(rs.getFloat(3));
                invoice.setSupplierID(rs.getInt(4));

                invoices.add(invoice);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return invoices;
    }

    public void deleteInvoice(Invoice toBeDeleted) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " DELETE from Invoice WHERE RefNumber = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getRefNumber());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addInvoice(Invoice invoice) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " insert into invoice (SupplierID, TotalValue, RefNumber)"
                    + " values (?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(RefNumber) FROM invoice");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, invoice.getSupplierID());
            preparedStmt.setFloat (2, invoice.getTotalValue());
            preparedStmt.setInt(3, highestValue+1);

            // execute the preparedstatement
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Invoice> getInvoicesByName(String filter){
        ArrayList<Invoice> invoices = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM invoice WHERE name LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setRefNumber(rs.getInt(1));
                invoice.setDate(rs.getDate(2));
                invoice.setTotalValue(rs.getFloat(3));
                invoice.setSupplierID(rs.getInt(4));

                invoices.add(invoice);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return invoices;
    }
}
