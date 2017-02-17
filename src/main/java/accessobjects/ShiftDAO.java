package accessobjects;

import entities.Shift;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by damo k on 02/02/2017.
 */
public class ShiftDAO {
    public void deleteShift(Shift toBeDeleted) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " DELETE FROM shift WHERE ShiftID = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, toBeDeleted.getShiftID());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addShift(Shift shift) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String query = " INSERT INTO shift (Start_Time, End_Time, OvertimeHours, Username, ShiftID)"
                    + " VALUES (?, ?, ?, ?, ?)";
            Statement highestValueStmt = con.createStatement();
            ResultSet rs = highestValueStmt.executeQuery("SELECT max(ShiftID) FROM shift");
            int highestValue = 0;
            if (rs.first()) {
                highestValue = rs.getInt(1);
            }
            rs.close();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setFloat(1, shift.getStartTime());
            preparedStmt.setFloat(2, shift.getEndTime());
            preparedStmt.setInt(3, shift.getOvertimeHours());
            //  preparedStmt.setDate   (4, event.getDate());
            preparedStmt.setString(4, shift.getUsername());
            preparedStmt.setInt(5, highestValue + 1);

            // execute the preparedstatement
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Shift> getAllShifts() {
        ArrayList<Shift> shifts = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shift");

            while (rs.next()) {
                Shift shift = new Shift();
                shift.setShiftID(rs.getInt(1));
                shift.setStartTime(rs.getFloat(2));
                shift.setEndTime(rs.getFloat(3));
                shift.setDay(rs.getInt(4));
                shift.setWeek(rs.getInt(5));
                shift.setYear(rs.getInt(6));
                shift.setOvertimeHours(rs.getInt(7));
                shift.setUsername(rs.getString(8));

                shifts.add(shift);
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return shifts;
    }

    public ArrayList<Shift> getShiftByName(String filter) {
        ArrayList<Shift> shifts = new ArrayList<>();

        try {
            filter = "%" + filter + "%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM shift WHERE Username LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Shift shift = new Shift();
                shift.setShiftID(rs.getInt(1));
                shift.setStartTime(rs.getFloat(2));
                shift.setEndTime(rs.getFloat(3));
                shift.setDay(rs.getInt(4));
                shift.setWeek(rs.getInt(5));
                shift.setYear(rs.getInt(6));
                shift.setOvertimeHours(rs.getInt(7));
                shift.setUsername(rs.getString(8));

                shifts.add(shift);


            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return shifts;
    }


    public float[] calculateWages(String username, int weekNo) {
        float[] wages = new float[4];
        float totalWages = 0;
        float overtimeWages = 0;
        float hours = 0;
        float totalHours = 0;
        int overtimeHours = 0;
        float salaryPh = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            String querySalary = "SELECT * FROM employee WHERE username=?";
            PreparedStatement preparedStmtSalary = con.prepareStatement(querySalary);
            preparedStmtSalary.setString(1, username);
            ResultSet rsSalary = preparedStmtSalary.executeQuery();

            if (rsSalary.first()) {
                salaryPh = rsSalary.getFloat(6);
            }

            String query = "SELECT * FROM shift WHERE Username=? AND Week =?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setInt(2, weekNo);
            ResultSet rs = preparedStmt.executeQuery();

            //Calculate Total Wages including Overtime
            while (rs.next()) {
                overtimeHours = rs.getInt(7);
                hours = (rs.getFloat(3) - rs.getFloat(2));
                overtimeWages = overtimeHours * salaryPh;
                totalWages = totalWages + (hours * salaryPh) + overtimeWages;
                totalHours = totalHours + hours;
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        wages[0] = totalWages;
        wages[1] = overtimeWages;
        wages[2] = totalHours;
        wages[3] = overtimeHours;
        return wages;
    }
}
