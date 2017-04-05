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

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservations");

            while (rs.next()) {
                Reservations reservation = new Reservations();
                reservation.setReservationID(rs.getInt(1));
                reservation.setStartingTime(rs.getFloat(2));
                reservation.setEndingTime(rs.getFloat(3));
                reservation.setName(rs.getString(4));
                reservation.setNumber(rs.getInt(5));
                reservation.setEmail(rs.getString(6));
                reservation.setNumberOfPeople(rs.getInt(7));
                reservation.setDescription(rs.getString(8));
                reservation.setTableID(rs.getInt(9));
                reservation.setDate(rs.getDate(10));

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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
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

    public void addReservation(Reservations toBeAdded) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = " insert into reservations (ReservationID, StartingTime, EndingTime, Name, Number, Email, NumberOfPeople, Description, TableID, ReservationDate)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(reservationID) FROM reservations");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, highestValue+1);
            preparedStmt.setFloat(2, toBeAdded.getStartingTime());
            preparedStmt.setFloat(3, toBeAdded.getEndingTime());
            preparedStmt.setString(4, toBeAdded.getName());
            preparedStmt.setInt(5, toBeAdded.getNumber());
            preparedStmt.setString(6, toBeAdded.getEmail());
            preparedStmt.setInt(7, toBeAdded.getNumberOfPeople());
            preparedStmt.setString(8, toBeAdded.getDescription());
            preparedStmt.setInt(9, toBeAdded.getTableID());
            preparedStmt.setDate(10, toBeAdded.getDate());

            // execute the preparedstatement
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
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM reservations WHERE name LIKE ? OR Number LIKE ? or Email LIKE ? OR Description LIKE ?");
            stmt.setString(1, filter);
            stmt.setString(2, filter);
            stmt.setString(3, filter);
            stmt.setString(4, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservations reservation = new Reservations();
                reservation.setReservationID(rs.getInt(1));
                reservation.setStartingTime(rs.getFloat(2));
                reservation.setEndingTime(rs.getFloat(3));
                reservation.setName(rs.getString(4));
                reservation.setNumber(rs.getInt(5));
                reservation.setEmail(rs.getString(6));
                reservation.setNumberOfPeople(rs.getInt(7));
                reservation.setDescription(rs.getString(8));
                reservation.setTableID(rs.getInt(9));
                reservation.setDate(rs.getDate(10));

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
