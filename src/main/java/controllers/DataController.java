package controllers;

import accessobjects.*;
import entities.Employee;
import entities.Event;

import java.util.ArrayList;

/**
 * Created by damo k on 04/04/2017.
 */
public class DataController {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    EventDAO eventDAO = new EventDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    ReservationsDAO reservationsDAO = new ReservationsDAO();
    ShiftDAO shiftDAO = new ShiftDAO();
    SupplierDAO supplierDAO = new SupplierDAO();
    TablesDAO tablesDAO = new TablesDAO();
    TransactionDAO transactionDAO = new TransactionDAO();

    // Employee DAO Handlers
    public Employee login(String username, String password) {
        return employeeDAO.login(username, password);
    }

    public void addEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }

    public void deleteEmployee(Employee toBeDeleted) {
        employeeDAO.deleteEmployee(toBeDeleted);
    }

    public ArrayList<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public ArrayList<Employee> getEmployeesByName(String filter) {
        return employeeDAO.getAllEmployees();
    }

    //Event Handlers
    public void addEvent(Event event) {
        eventDAO.addEvent(event);
    }

    //Invoice Handlers
    public void deleteEvent(Event toBeDeleted) {
        eventDAO.deleteEvent(toBeDeleted);
    }

    public ArrayList<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    //Reservation Handlers

    //Shift Handlers

    //Supplier Handlers

    //Tables Handlers

    //Transaction Handlers

}
