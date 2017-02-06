package accessobjects;

import entities.Employee;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damo k on 22/11/2016.
 */
public class EmployeeDAO {
   /* private static EmployeeDAO ourInstance = new EmployeeDAO();

   public static EmployeeDAO getInstance() {
        return ourInstance;
   }

    private EmployeeDAO() {
    }
*/

    public ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setUsername(rs.getString(1));
                emp.setName(rs.getString(2));
                emp.setAddress(rs.getString(3));
                emp.setDob(rs.getDate(4));
                emp.setPhone(rs.getInt(5));
                emp.setSalaryPh(rs.getFloat(6));
                emp.setContractType(rs.getString(7));
                emp.setPosition(rs.getString(8));

                employees.add(emp);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByName(String filter){
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            filter = "%"+filter+"%";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM employee WHERE name LIKE ?");
            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setUsername(rs.getString(1));
                emp.setName(rs.getString(2));
                emp.setAddress(rs.getString(3));
                emp.setDob(rs.getDate(4));
                emp.setPhone(rs.getInt(5));
                emp.setSalaryPh(rs.getFloat(6));
                emp.setContractType(rs.getString(7));
                emp.setPosition(rs.getString(8));

                employees.add(emp);

            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return employees;
    }

}
