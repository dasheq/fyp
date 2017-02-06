package accessobjects;

import entities.Tables;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class TablesDAO {
    public ArrayList<Tables> getAllTables(){
        ArrayList<Tables> tables = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tables");

            while (rs.next()) {
                Tables table = new Tables();
                table.setTableID(rs.getInt(1));
                table.setNoOfSeats(rs.getInt(2));
                table.setReservationID(rs.getInt(3));

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
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tables WHERE ReservationID LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Tables table = new Tables();
                table.setTableID(rs.getInt(1));
                table.setNoOfSeats(rs.getInt(2));
                table.setReservationID(rs.getInt(3));

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
