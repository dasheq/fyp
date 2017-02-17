package accessobjects;

import entities.Event;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class EventDAO {
    public void addEvent(Event event) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " insert into event (ActName, ActType, Area, EndTime, Price, StartingTime, EventID)"
                    + " values (?, ?, ?, ?, ?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(eventID) FROM event");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, event.getActName());
            preparedStmt.setString (2, event.getActType());
            preparedStmt.setString(3, event.getArea());
            preparedStmt.setDate   (4, event.getDate());
            preparedStmt.setFloat(4, event.getEndTime());
            preparedStmt.setFloat(5, event.getPrice());
            preparedStmt.setFloat    (6, event.getStartingTime());
            preparedStmt.setInt(7, highestValue+1);

            // execute the preparedstatement
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteEvent(Event toBeDeleted) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " DELETE from event WHERE EventID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, toBeDeleted.getEventID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> events = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM event");

            while (rs.next()) {
                Event event = new Event();
                event.setEventID(rs.getInt(1));
                event.setActName(rs.getString(2));
                event.setActType(rs.getString(3));
                event.setPrice(rs.getFloat(4));
                event.setStartingTime(rs.getFloat(5));
                event.setEndTime(rs.getFloat(6));
                event.setDate(rs.getDate(7));
                event.setArea(rs.getString(8));

                events.add(event);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return events;
    }

    public ArrayList<Event> getEventsByName(String filter){
        ArrayList<Event> events = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM event WHERE actname LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventID(rs.getInt(1));
                event.setActName(rs.getString(2));
                event.setActType(rs.getString(3));
                event.setPrice(rs.getFloat(4));
                event.setStartingTime(rs.getFloat(5));
                event.setEndTime(rs.getFloat(6));
                event.setDate(rs.getDate(7));
                event.setArea(rs.getString(8));

                events.add(event);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return events;
    }

}


