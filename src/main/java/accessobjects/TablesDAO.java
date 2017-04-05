package accessobjects;

import entities.Tables;
import entities.TablesCheckBox;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class TablesDAO {
    public void deleteTable(Tables toBeDeleted) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = " DELETE from tables WHERE TableID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getTableID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getAreas() {
        ArrayList<String> areas = new ArrayList<>();
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT distinct Area FROM bar_mgmt.tables ORDER BY Area Asc;");

            while(rs.next()) {
                areas.add(rs.getString(1));
            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return areas;

    }

    public int getNoOfAreas() {
        int noOfAreas = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(DISTINCT Area) FROM tables");

            if (rs.first()) {
                noOfAreas = rs.getInt(1);
            }

            con.close();

        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return noOfAreas;

    }


    public void addTable(Tables table) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = " insert into tables (NoOfSeats, TableID, Area)"
                    + " values (?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(TableID) FROM tables");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, table.getNoOfSeats());
            preparedStmt.setInt(2, highestValue+1);
            preparedStmt.setString(3, table.getArea());

            // execute the preparedstatement
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Tables> getAllTables(){
        ArrayList<Tables> tables = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tables ORDER BY area Asc");

            while (rs.next()) {
                Tables table = new Tables("ID: "+ String.valueOf(rs.getInt(1)) + " Seats: " + String.valueOf(rs.getInt(2)));
                table.setTableID(rs.getInt(1));
                table.setNoOfSeats(rs.getInt(2));
                table.setArea(rs.getString(3));
                tables.add(table);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return tables;
    }

    public ArrayList<TablesCheckBox> getAllTablesCb(){
        ArrayList<TablesCheckBox> tables = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tables ORDER BY area Asc");

            while (rs.next()) {
                TablesCheckBox table = new TablesCheckBox("ID: "+ String.valueOf(rs.getInt(1)) + " Seats: " + String.valueOf(rs.getInt(2)));
                table.setTableID(rs.getInt(1));
                table.setNoOfSeats(rs.getInt(2));
                table.setArea(rs.getString(3));
                tables.add(table);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return tables;
    }

    public ArrayList<Tables> getTableByName(String filter){
        ArrayList<Tables> tables = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tables WHERE tables.NoOfSeats LIKE ? or Area LIKE ? or TableID LIKE ?");
            stmt.setString(1, filter);
            stmt.setString(2, filter);
            stmt.setString(3, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Tables table = new Tables("ID: "+rs.getInt(1) + " Seats: " + rs.getInt(3));
                table.setTableID(rs.getInt(1));
                table.setNoOfSeats(rs.getInt(2));
                table.setArea(rs.getString(3));

                tables.add(table);


            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return tables;
    }
}
