package views;

import accessobjects.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.SystemError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.Employee;
//import java.sql.Date;

import java.util.Date;

/**
 * Created by damo k on 15/02/2017.
 */
public class InsertView extends VerticalLayout {

    //Misc Variables
    CssLayout insertButtons = new CssLayout();
    private String screenWidth = "100%";
    private Label insertLabel = new Label();
    Button addButton;
    Button clearButton;
    private int table;

    //Access Objects
    EmployeeDAO employeeDAO = new EmployeeDAO();
    EventDAO eventDAO = new EventDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    ReservationsDAO reservationsDAO = new ReservationsDAO();
    ShiftDAO shiftDAO = new ShiftDAO();
    SupplierDAO supplierDAO = new SupplierDAO();
    TablesDAO tablesDAO = new TablesDAO();


    //Variables for Insert Employee Window
    private TextField employeeName = new TextField();
    private TextField employeeAddress = new TextField();
    private NativeSelect employeeContractType = new NativeSelect("Contract Type");
    private PopupDateField employeeDoB = new PopupDateField("Date of Birth");
    private TextField employeePhone = new TextField();
    private TextField employeePosition = new TextField();
    private TextField employeeSalaryPh = new TextField();
    private TextField employeeUsername = new TextField();
    private PasswordField employeePassword = new PasswordField("Password");

    private Label employeeNameLabel = new Label("Name");
    private Label employeeAddressLabel = new Label("Address");
    private Label employeePhoneLabel = new Label("Phone");
    private Label employeePositionLabel = new Label("Position");
    private Label employeeSalaryPhLabel = new Label("Salary Per Hour");
    private Label employeeUsernameLabel = new Label("Username");

    //Variables for Insert Event Window
    private TextField eventActName = new TextField();
    private TextField eventActType = new TextField();
    private TextField eventPrice = new TextField();
    private TextField eventStartingTime = new TextField();
    private TextField eventEndingTime = new TextField();
    private PopupDateField eventDate = new PopupDateField("Date");
    private TextField eventArea = new TextField();
    private Label eventActNameLabel = new Label("Act Name");
    private Label eventActTypeLabel = new Label("Act Type");
    private Label eventPriceLabel = new Label("Price");
    private Label eventStartingTimeLabel = new Label("Starting Time");
    private Label eventEndingTimeLabel = new Label("Ending Time");
    private Label eventAreaLabel = new Label("Area");

    //Variables for Insert Shift Window
    private TextField shiftUsername = new TextField();
    private TextField shiftStartTime = new TextField();
    private TextField shiftEndTime = new TextField();
    private PopupDateField shiftDate = new PopupDateField("Date");
    private TextField shiftOvertimeHours = new TextField();
    private Label shiftUsernameLabel = new Label("Username");
    private Label shiftStartTimeLabel = new Label("Starting Time");
    private Label shiftEndTimeLabel = new Label("Ending Time");
    private Label shiftOvertimeHoursLabel = new Label("Overtime Hours");

    //Variables for Insert Supplier Window
    private TextField supplierName = new TextField();
    private TextField supplierAddress = new TextField();
    private TextField supplierContactNo = new TextField();
    private TextField supplierEmail = new TextField();
    private TextField supplierWebsite = new TextField();
    private Label supplierNameLabel = new Label("Name");
    private Label supplierAddressLabel = new Label("Address");
    private Label supplierContactNoLabel = new Label("Contact Number");
    private Label supplierEmailLabel = new Label("Email");
    private Label supplierWebsiteLabel = new Label("Website");

    //Variables for Insert Invoice Window
    private PopupDateField invoiceDate = new PopupDateField("Date");
    private TextField invoiceTotalValue = new TextField();
    private TextField invoiceSupplierID = new TextField();
    private Label invoiceTotalValueLabel = new Label("Total Value");
    private Label invoiceSupplierIDLabel = new Label("Supplier ID");


    //Variables for Insert Table Window
    private TextField tableNoOfSeats = new TextField();
    private Label tableNoOfSeatsLabel = new Label("Number of Seats");


    public InsertView(int tableNo) {
        this.table = tableNo;
    }

    public void run() {
        insertLabel.setHeight("50");
        insertLabel.setStyleName(ValoTheme.LABEL_H2);

        addButton = new Button("Add", e -> {
            switch (table) {
                case 1:
                    java.sql.Date testDate = new java.sql.Date(0);
                    Employee employee = new Employee();
                    employee.setName(employeeName.getValue());
                    employee.setAddress(employeeAddress.getValue());
                    employee.setContractType(employeeContractType.getValue().toString());
                    employee.setDob(new java.sql.Date( employeeDoB.getValue().getTime()));
                    employee.setPhone(Integer.valueOf(employeePhone.getValue()));
                    employee.setPosition(employeePosition.getValue());
                    employee.setSalaryPh(Float.valueOf(employeeSalaryPh.getValue()));
                    employee.setUsername(employeeUsername.getValue());
                    employee.setPassword(employeePassword.getValue());
                    employeeDAO.addEmployee(employee);
                    clearFields(table);
                    Notification.show("Added " + employee.getName());
                    break;
                case 2:
                    entities.Event event = new entities.Event();
                    event.setActName(eventActName.getValue());
                    event.setActType(eventActType.getValue());
                    event.setDate(new java.sql.Date(eventDate.getValue().getTime()));
                    event.setArea(eventArea.getValue());
                    event.setEndTime(Float.valueOf(eventEndingTime.getValue()));
                    event.setPrice(Float.valueOf(eventPrice.getValue()));
                    event.setStartingTime(Float.valueOf(eventStartingTime.getValue()));
                    eventDAO.addEvent(event);
                    clearFields(table);
                    break;
                case 3:
                    //reservationsDAO.addReservation(reservation);
                    clearFields(table);
                    break;
                case 4:
                    entities.Shift shift = new entities.Shift();
                    shift.setStartTime(Float.valueOf(shiftStartTime.getValue()));
                    shift.setEndTime(Float.valueOf(shiftEndTime.getValue()));
                    shift.setOvertimeHours(Integer.valueOf(shiftOvertimeHours.getValue()));
                    shift.setUsername(shiftUsername.getValue());

                    //Convert date to integers
                    int day = Integer.valueOf(shiftDate.getValue().toString().substring(8,10));
                    int month = Integer.valueOf(shiftDate.getValue().toString().substring(5,7));
                    int year = Integer.valueOf(shiftDate.getValue().toString().substring(0,4));

                    //Convert day to 1 to 7
                    if ( day >= 8 && day <= 14) {
                        day = day - 7;
                    }
                    else if (day >= 15 && day <= 21) {
                        day = day - 14;
                    }
                    else if(day >= 22 && day <= 28) {
                        day = day - 21;
                    }
                    else if(day >= 29) {
                        day = day - 28;
                    }

                    //Convert week to 1 to 52


                    shift.setDay(day);
                    shift.setWeek(month);
                    shift.setYear(year);

                    shiftDAO.addShift(shift);
                    clearFields(table);
                    break;
                case 5:
                    entities.Supplier supplier = new entities.Supplier();
                    supplier.setName(supplierName.getValue());
                    supplier.setAddress(supplierAddress.getValue());
                    supplier.setContactNumber(Integer.valueOf(supplierContactNo.getValue()));
                    supplier.setEmail(supplierEmail.getValue());
                    supplier.setWebsite(supplierWebsite.getValue());
                    supplierDAO.addSupplier(supplier);
                    clearFields(table);
                    break;
                case 6:
                    entities.Invoice invoice = new entities.Invoice();
                    invoice.setDate(new java.sql.Date( invoiceDate.getValue().getTime()));
                    invoice.setSupplierID(Integer.valueOf(invoiceSupplierID.getValue()));
                    invoice.setTotalValue(Float.valueOf(invoiceTotalValue.getValue()));
                    invoiceDAO.addInvoice(invoice);
                    clearFields(table);
                    break;
                case 7:
                    entities.Tables tables = new entities.Tables();
                    tables.setNoOfSeats(Integer.valueOf(tableNoOfSeats.getValue()));
                    tablesDAO.addTable(tables);
                    clearFields(table);
                    break;
            }
        });
        clearButton = new Button("Clear", e -> {
            clearFields(table);
        });

        updateInsertArea(table);
    }

    public void updateInsertArea(int tableNo) {
        switch (tableNo) {
            case 0:
                break;
            case 1:
                insertLabel.setValue("Add Employee");

                employeeName.setWidth(screenWidth);
                employeeAddress.setWidth(screenWidth);
                employeeContractType.setWidth(screenWidth);
                employeeDoB.setWidth(screenWidth);
                employeeUsername.setWidth(screenWidth);
                employeePassword.setWidth(screenWidth);
                employeePosition.setWidth(screenWidth);
                employeeSalaryPh.setWidth(screenWidth);

                employeeName.setRequired(true);
                employeeAddress.setRequired(true);
                employeeUsername.setRequired(true);
                employeePassword.setRequired(true);
                employeePosition.setRequired(true);
                employeeSalaryPh.setRequired(true);
                employeeContractType.setRequired(true);


                BeanItemContainer<String> container =
                        new BeanItemContainer<>(String.class);

                // Put some data into list
                container.addItem("PT");
                container.addItem("FT");

                employeeContractType.setContainerDataSource(container);

                employeeContractType.setWidth("200");
                employeeContractType.setRequired(true);

                employeeDoB.setValue(new java.sql.Date(new java.util.Date().getTime()));
                employeeDoB.setIcon(FontAwesome.USER);
                employeeDoB.setRequired(true);
                addComponents(insertLabel, employeeNameLabel, employeeName);
                addComponents(employeeUsernameLabel, employeeUsername, employeePassword);
                addComponents(employeeAddressLabel, employeeAddress, employeeContractType, employeeDoB);
                //insertWindow.addComponent(employeeDoB);
                addComponents(employeePhoneLabel, employeePhone, employeePositionLabel, employeePosition);
                addComponents(employeeSalaryPhLabel, employeeSalaryPh);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 2:
                insertLabel.setValue("Add Event");

                eventActName.setWidth(screenWidth);
                eventActType.setWidth(screenWidth);
                eventPrice.setWidth(screenWidth);
                eventStartingTime.setWidth(screenWidth);
                eventEndingTime.setWidth(screenWidth);

                eventDate.setCaption("Date");
                eventDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
                //eventDate.setRequired(true);
                eventStartingTime.setRequired(true);
                eventEndingTime.setRequired(true);
                eventArea.setRequired(true);
                eventActName.setRequired(true);
                eventActType.setRequired(true);
                eventPrice.setRequired(true);

                addComponents(insertLabel, eventActNameLabel, eventActName, eventActTypeLabel, eventActType);
                addComponents(eventPriceLabel, eventPrice, eventStartingTimeLabel, eventStartingTime, eventEndingTimeLabel, eventEndingTime);
                addComponents(eventDate, eventAreaLabel, eventArea);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 3:
                insertLabel.setValue("Add Reservation");
                break;
            case 4:
                insertLabel.setValue("Add Shift");
                shiftDate.setWidth(screenWidth);
                shiftEndTime.setWidth(screenWidth);
                shiftOvertimeHours.setWidth(screenWidth);
                shiftStartTime.setWidth(screenWidth);
                shiftUsername.setWidth(screenWidth);
                shiftDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
                addComponents(insertLabel, shiftUsernameLabel, shiftUsername, shiftStartTimeLabel, shiftStartTime);
                addComponents(shiftEndTimeLabel, shiftEndTime, shiftDate, shiftOvertimeHoursLabel, shiftOvertimeHours);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 5:
                insertLabel.setValue("Add Supplier");
                supplierAddress.setWidth(screenWidth);
                supplierContactNo.setWidth(screenWidth);
                supplierEmail.setWidth(screenWidth);
                supplierName.setWidth(screenWidth);
                supplierWebsite.setWidth(screenWidth);

                addComponents(insertLabel, supplierNameLabel, supplierName, supplierAddressLabel, supplierAddress);
                addComponents(supplierContactNoLabel, supplierContactNo, supplierEmailLabel, supplierEmail);
                addComponents(supplierWebsiteLabel, supplierWebsite);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 6:
                insertLabel.setValue("Add Invoice");
                invoiceDate.setWidth(screenWidth);
                invoiceSupplierID.setWidth(screenWidth);
                invoiceTotalValue.setWidth(screenWidth);
                invoiceDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
                addComponents(insertLabel, invoiceDate, invoiceTotalValueLabel, invoiceTotalValue, invoiceSupplierIDLabel, invoiceSupplierID);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 7:
                insertLabel.setValue("Add Table");
                tableNoOfSeats.setWidth(screenWidth);

                addComponents(insertLabel, tableNoOfSeatsLabel, tableNoOfSeats);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
        }
    }

    public void clearFields(int tableNo) {
        switch (table) {
            case 1:
                employeeName.clear();
                employeeAddress.clear();
                employeeContractType.clear();
                employeeDoB.clear();
                employeeSalaryPh.clear();
                employeePosition.clear();
                employeePassword.clear();
                employeeUsername.clear();
                break;
            case 2:
                eventActName.clear();
                eventActType.clear();
                eventArea.clear();
                eventDate.clear();
                eventStartingTime.clear();
                eventEndingTime.clear();
                eventPrice.clear();
                break;
            case 3:
                break;
            case 4:
                shiftUsername.clear();
                shiftStartTime.clear();
                shiftEndTime.clear();
                shiftDate.clear();
                shiftOvertimeHours.clear();
                break;
            case 5:
                supplierName.clear();
                supplierAddress.clear();
                supplierContactNo.clear();
                supplierEmail.clear();
                supplierWebsite.clear();
                break;
            case 6:
                invoiceDate.clear();
                invoiceTotalValue.clear();
                invoiceSupplierID.clear();
                break;
            case 7:
                tableNoOfSeats.clear();
                break;
        }
    }
}
