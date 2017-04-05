package controllers;

import accessobjects.*;
import entities.*;

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
    ProductDAO productDAO = new ProductDAO();

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

    public void deleteEvent(Event toBeDeleted) {
        eventDAO.deleteEvent(toBeDeleted);
    }

    public ArrayList<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public ArrayList<Event> getEventsByName(String filter) {
        return eventDAO.getEventsByName(filter);
    }


    //Invoice Handlers
    public ArrayList<Invoice> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    public void deleteInvoice(Invoice toBeDeleted) {
        invoiceDAO.deleteInvoice(toBeDeleted);
    }

    public void addInvoice(Invoice invoice) {
        invoiceDAO.addInvoice(invoice);
    }

    public ArrayList<Invoice> getInvoicesByName(String filter) {
        return invoiceDAO.getInvoicesByName(filter);
    }


    //Reservation Handlers
    public ArrayList<Reservations> getAllReservations() {
        return reservationsDAO.getAllReservations();
    }

    public void deleteReservation(Reservations toBeDeleted) {
        reservationsDAO.deleteReservation(toBeDeleted);
    }

    public void addReservation(Reservations toBeAdded) {
        reservationsDAO.addReservation(toBeAdded);
    }

    public ArrayList<Reservations> getReservationsByName(String filter) {
        return reservationsDAO.getReservationsByName(filter);
    }

    //Shift Handlers
    public void deleteShift(Shift toBeDeleted) {
        shiftDAO.deleteShift(toBeDeleted);
    }

    public void addShift(Shift shift) {
        shiftDAO.addShift(shift);
    }

    public ArrayList<Shift> getAllShifts() {
        return shiftDAO.getAllShifts();
    }

    public ArrayList<Shift> getShiftByName(String filter) {
        return shiftDAO.getShiftByName(filter);
    }

    public float[] calculateWages(String username, int weekNo) {
        return shiftDAO.calculateWages(username, weekNo);
    }

    public ArrayList<Shift> getShiftsByNameAndWeek(String employeeName, int weekNo) {
        return shiftDAO.getShiftsByNameAndWeek(employeeName, weekNo);
    }

    public ArrayList<String> getNameFromUsername() {
        return shiftDAO.getNameFromUsername();
    }

    //Supplier Handlers
    public void deleteSupplier(Supplier toBeDeleted) {
        supplierDAO.deleteSupplier(toBeDeleted);
    }

    public void addSupplier(Supplier supplier) {
        supplierDAO.addSupplier(supplier);
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    public ArrayList<Supplier> getSupplierByName(String filter) {
        return supplierDAO.getSupplierByName(filter);
    }

    //Tables Handlers
    public void deleteTable(Tables toBeDeleted) {
        tablesDAO.deleteTable(toBeDeleted);
    }

    public ArrayList<String> getAreas() {
        return tablesDAO.getAreas();
    }

    public int getNoOfAreas() {
        return tablesDAO.getNoOfAreas();
    }

    public void addTable(Tables table) {
        tablesDAO.addTable(table);
    }

    public ArrayList<Tables> getAllTables() {
        return tablesDAO.getAllTables();
    }

    public ArrayList<TablesCheckBox> getAllTablesCb() {
        return tablesDAO.getAllTablesCb();
    }

    public ArrayList<Tables> getTableByName(String filter) {
        return tablesDAO.getTableByName(filter);
    }

    //Transaction Handlers
    public void addSale(Transaction transaction) {
        transactionDAO.addSale(transaction);
    }

    public void deleteSale(Transaction toBeDeleted) {
        transactionDAO.deleteSale(toBeDeleted);
    }

    public ArrayList<String> getAllUsernames() {
        return transactionDAO.getAllUsernames();
    }

    public ArrayList<Transaction> getAllSales() {
        return transactionDAO.getAllSales();
    }

    public ArrayList<Transaction> getSalesByFilter(int filterType, int filter) {
        return transactionDAO.getSalesByFilter(filterType, filter);
    }

    public ArrayList<Transaction> getTransactionByName(String filter) {
        return transactionDAO.getTransactionByName(filter);
    }

    //Product Handlers
    public ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public ArrayList<ProductButton> getAllProductsBtn() {
        return productDAO.getAllProductsBtn();
    }

    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    public void updateProductQuantityButton(ProductButton toBeUpdated) {
        productDAO.updateProductQuantityButton(toBeUpdated);
    }

    public void deleteProduct(Product toBeDeleted) {
        productDAO.deleteProduct(toBeDeleted);
    }

    public ArrayList<String> getTypes() {
        return productDAO.getTypes();
    }

    public ArrayList<Product> getProductByName(String filter) {
        return productDAO.getProductByName(filter);
    }
}

