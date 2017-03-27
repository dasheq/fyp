package accessobjects;

import entities.Tables;
import entities.Transaction;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class TransactionDAO {

    public void addSale(Transaction transaction) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "");
            String query = " insert into transaction (SaleID, TimeOfSale, Username, TotalPrice, PaymentMethod)"
                    + " values (?, ?, ?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(SaleID) FROM transaction");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, highestValue+1);
            preparedStmt.setTimestamp (2, transaction.getTimeOfSale());
            preparedStmt.setString(3, transaction.getUsername());
            preparedStmt.setFloat(4, transaction.getTotalPrice());
            preparedStmt.setString(5, transaction.getPaymentMethod());
            // execute the preparedstatement
            preparedStmt.execute();


            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteSale(Transaction toBeDeleted) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "");
            String query = " DELETE from transaction WHERE SaleID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getSaleID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT username FROM transaction");

            while(rs.next()) {
                usernames.add(rs.getString(1));
            }

            con.close();

        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return usernames;
    }

    public ArrayList<Transaction> getAllSales(){
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM transaction");

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setSaleID(rs.getInt(1));
                transaction.setTimeOfSale(rs.getTimestamp(2));
                transaction.setUsername(rs.getString(3));
                transaction.setTotalPrice(rs.getFloat(4));
                transaction.setPaymentMethod(rs.getString(5));

                transactions.add(transaction);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return transactions;
    }

    public ArrayList<Transaction> getSalesByFilter(int filterType, int  filter){
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "");
            PreparedStatement stmt = null;

            if(filterType == 1)
                stmt = con.prepareStatement("SELECT * FROM transaction WHERE MONTH(TimeOfSale) = ?");

            if(filterType == 2)
                stmt = con.prepareStatement("SELECT * FROM transaction WHERE YEAR(TimeOfSale) = ?");

            stmt.setInt(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Transaction transaction = new Transaction();
                transaction.setSaleID(rs.getInt(1));
                transaction.setTimeOfSale(rs.getTimestamp(2));
                transaction.setUsername(rs.getString(3));
                transaction.setTotalPrice(rs.getFloat(4));
                transaction.setPaymentMethod(rs.getString(5));

                transactions.add(transaction);


            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionByName(String filter){
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM transaction WHERE Username LIKE ? OR TotalPrice LIKE ? OR PaymentMethod LIKE ?");
            stmt.setString(1, filter);
            stmt.setString(2, filter);
            stmt.setString(3, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setSaleID(rs.getInt(1));
                transaction.setTimeOfSale(rs.getTimestamp(2));
                transaction.setUsername(rs.getString(3));
                transaction.setTotalPrice(rs.getFloat(4));
                transaction.setPaymentMethod(rs.getString(5));

                transactions.add(transaction);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return transactions;
    }
}
