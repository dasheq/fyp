package accessobjects;

import entities.Sale;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class SaleDAO {
    public ArrayList<Sale> getAllSales(){
        ArrayList<Sale> sales = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sale");

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleID(rs.getInt(1));
                sale.setTimeOfSale(rs.getTimestamp(2));
                sale.setQuantity(rs.getInt(3));
                sale.setProductID(rs.getInt(4));
                sale.setUsername(rs.getString(5));

                sales.add(sale);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return sales;
    }

    public ArrayList<Sale> getSaleByName(String filter){
        ArrayList<Sale> sales = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM sale WHERE ProductID LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Sale sale = new Sale();
                sale.setSaleID(rs.getInt(1));
                sale.setTimeOfSale(rs.getTimestamp(2));
                sale.setQuantity(rs.getInt(3));
                sale.setProductID(rs.getInt(4));
                sale.setUsername(rs.getString(5));

                sales.add(sale);



            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return sales;
    }
}
