package accessobjects;

import entities.Reservations;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class ReservationsDAO {
    public ArrayList<Reservations> getAllReservations(){
        ArrayList<Reservations> reservations = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservations");

            while (rs.next()) {
                Reservations reservation = new Reservations();
                reservation.setReservationID(rs.getInt(1));
                reservation.setStartingTime(rs.getFloat(2));
                reservation.setEndingTime(rs.getFloat(3));
                reservation.setDate(rs.getDate(4));
                reservation.setName(rs.getString(5));
                reservation.setNumber(rs.getInt(6));
                reservation.setEmail(rs.getString(7));
                reservation.setNumberOfPeople(rs.getInt(8));
                reservation.setDescription(rs.getString(9));

                reservations.add(reservation);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return reservations;
    }

    public void deleteReservation(Reservations toBeDeleted) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " DELETE from reservations WHERE ReservationID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getReservationID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<Reservations> getReservationsByName(String filter){
        ArrayList<Reservations> reservations = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE name LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Reservations reservation = new Reservations();
                reservation.setReservationID(rs.getInt(1));
                reservation.setStartingTime(rs.getFloat(2));
                reservation.setEndingTime(rs.getFloat(3));
                reservation.setDate(rs.getDate(4));
                reservation.setName(rs.getString(5));
                reservation.setNumber(rs.getInt(6));
                reservation.setEmail(rs.getString(7));
                reservation.setNumberOfPeople(rs.getInt(8));
                reservation.setDescription(rs.getString(9));

                reservations.add(reservation);


            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return reservations;
    }
}
