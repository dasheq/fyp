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
*/  public void addEmployee(Employee employee) {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
           String query = " insert into employee (name, Address, ContractType, DoB, Username, Position, Phone, SalaryPh, Password, AccessLevel)"
                   + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

           // create the mysql insert preparedstatement
           PreparedStatement preparedStmt = con.prepareStatement(query);
           preparedStmt.setString (1, employee.getName());
           preparedStmt.setString (2, employee.getAddress());
           preparedStmt.setString(3, employee.getContractType());
           preparedStmt.setDate(4, employee.getDob());
           preparedStmt.setString(5, employee.getUsername());
           preparedStmt.setString(6, employee.getPosition());
           preparedStmt.setInt    (7, employee.getPhone());
           preparedStmt.setFloat(8, employee.getSalaryPh());
           preparedStmt.setString(9, employee.getPassword());
           preparedStmt.setInt(10, employee.getAccessLevel());
           // execute the preparedstatement
           preparedStmt.execute();
           System.out.println(preparedStmt);
           con.close();
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    public void deleteEmployee(Employee toBeDeleted) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = " DELETE from employee WHERE Username = ?";

            // create the mysql delete preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, toBeDeleted.getUsername());
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Employee login(String username, String password) {
        Employee employeeValidated = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            String query = "SELECT * FROM employee WHERE username=? AND Password=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            ResultSet rs = preparedStmt.executeQuery();


            if (rs.first()) {
                employeeValidated = new Employee();
                employeeValidated.setAccessLevel(rs.getInt(10));
                employeeValidated.setUsername(rs.getString(1));
            }

            con.close();

        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Here");
        }

        return employeeValidated;
    }

    public ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
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
                emp.setPassword("*******");
                emp.setAccessLevel(rs.getInt(10));

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
                    "jdbc:mysql://localhost:3306/bar_mgmt?serverTimezone=GMT", "root", "password");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM employee WHERE name LIKE ? or Username LIKE ?" +
                    "OR Address LIKE ? or Position LIKE ?");
            stmt.setString(1, filter);
            stmt.setString(2, filter);
            stmt.setString(3, filter);
            stmt.setString(4, filter);
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
                emp.setPassword("*******");
                emp.setAccessLevel(rs.getInt(10));

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
