package views;

import accessobjects.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.SystemError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import controllers.DataController;
import entities.Employee;
import entities.Tables;
import misc.DateConvertor;
import misc.MyUI;
//import java.sql.Date;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by damo k on 15/02/2017.
 */
public class InsertView extends VerticalLayout {


    DataController dataController = new DataController();
    //Misc Variables
    CssLayout insertButtons = new CssLayout();
    private String screenWidth = "100%";
    private Label insertLabel = new Label();
    Button addButton;
    Button clearButton;
    Button checkAvailabilityButton;
    private int table;

    //Access Objects
    /*
    EmployeeDAO employeeDAO = new EmployeeDAO();
    EventDAO eventDAO = new EventDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    ReservationsDAO reservationsDAO = new ReservationsDAO();
    ShiftDAO shiftDAO = new ShiftDAO();
    SupplierDAO supplierDAO = new SupplierDAO();
    TablesDAO tablesDAO = new TablesDAO();
    */

    //Variables for Insert Employee Window
    private TextField employeeName = new TextField("Name");
    private TextField employeeAddress = new TextField("Address");
    private NativeSelect employeeContractType = new NativeSelect("Contract Type");
    private PopupDateField employeeDoB = new PopupDateField("Date of Birth");
    private TextField employeePhone = new TextField("Phone");
    private TextField employeePosition = new TextField("Position");
    private TextField employeeSalaryPh = new TextField("Salary Per Hour");
    private TextField employeeUsername = new TextField("Username");
    private PasswordField employeePassword = new PasswordField("Password");
    private NativeSelect employeeAccessLevel = new NativeSelect("Access Level");

    //Variables for Insert Event Window
    private TextField eventActName = new TextField("Act Name");
    private TextField eventActType = new TextField("Act Type");
    private TextField eventPrice = new TextField("Price");
    private TextField eventStartingTime = new TextField("Starting Time");
    private TextField eventEndingTime = new TextField("Ending Time");
    private PopupDateField eventDate = new PopupDateField("Date");
    private TextField eventArea = new TextField("Area");

    //Variables for Insert Reservation Window
    private PopupDateField reservationDate = new PopupDateField("Date");
    private NativeSelect reservationStartingTimeHour = new NativeSelect("Hour");
    private NativeSelect reservationStartingTimeMin = new NativeSelect("Minute");

    //Variables for Insert Shift Window
    private TextField shiftUsername = new TextField("Username");
    private TextField shiftStartTime = new TextField("Starting Time");
    private TextField shiftEndTime = new TextField("Ending Time");
    private PopupDateField shiftDate = new PopupDateField("Date");
    private TextField shiftOvertimeHours = new TextField("Overtime Hours");

    //Variables for Insert Supplier Window
    private TextField supplierName = new TextField("Name");
    private TextField supplierAddress = new TextField("Address");
    private TextField supplierContactNo = new TextField("Contact Number");
    private TextField supplierEmail = new TextField("Email");
    private TextField supplierWebsite = new TextField("Website");

    //Variables for Insert Invoice Window
    private PopupDateField invoiceDate = new PopupDateField("Date");
    private TextField invoiceTotalValue = new TextField("Total Value");
    private TextField invoiceSupplierID = new TextField("Supplier ID");

    //Variables for Insert Table Window
    private TextField tableNoOfSeats = new TextField("Number of Seats");
    private TextField tableArea = new TextField("Area");

    //Variables for Insert Product Window
    private TextField productName = new TextField("Name");
    private TextField productPrice = new TextField("Price");
    private NativeSelect productType = new NativeSelect("Type");
    private TextField productQuantity = new TextField("Quantity");


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
                    try {
                        employee.setName(employeeName.getValue());
                        employee.setAddress(employeeAddress.getValue());
                        employee.setContractType(employeeContractType.getValue().toString());
                        employee.setDob(new java.sql.Date(employeeDoB.getValue().getTime()));
                        employee.setPhone(Integer.valueOf(employeePhone.getValue()));
                        employee.setPosition(employeePosition.getValue());
                        employee.setSalaryPh(Float.valueOf(employeeSalaryPh.getValue()));
                        employee.setUsername(employeeUsername.getValue());
                        employee.setPassword(employeePassword.getValue());
                        if(employeeAccessLevel.getValue().toString().equals("Manager"))
                            employee.setAccessLevel(1);
                        else
                            employee.setAccessLevel(0);

                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }

                    dataController.addEmployee(employee);
                    clearFields(table);
                    Notification.show("Added " + employee.getName());

                    break;


                case 2:
                    entities.Event event = new entities.Event();
                    try {
                        event.setActName(eventActName.getValue());
                        event.setActType(eventActType.getValue());
                        event.setDate(new java.sql.Date(eventDate.getValue().getTime()));
                        event.setArea(eventArea.getValue());
                        event.setEndTime(Float.valueOf(eventEndingTime.getValue()));
                        event.setPrice(Float.valueOf(eventPrice.getValue()));
                        event.setStartingTime(Float.valueOf(eventStartingTime.getValue()));
                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }
                    dataController.addEvent(event);
                    clearFields(table);
                    break;
                case 3:
                    //reservationsDAO.addReservation(reservation);
                    clearFields(table);
                    break;
                case 4:
                    entities.Shift shift = new entities.Shift();
                    try {
                        shift.setStartTime(Float.valueOf(shiftStartTime.getValue()));
                        shift.setEndTime(Float.valueOf(shiftEndTime.getValue()));
                        shift.setOvertimeHours(Integer.valueOf(shiftOvertimeHours.getValue()));
                        shift.setUsername(shiftUsername.getValue());

                        java.sql.Date date = new java.sql.Date(shiftDate.getValue().getTime());
                        String dateString = date.toString();
                        System.out.println(dateString);
                        //Convert date to integers


                        DateConvertor dateConvertor = new DateConvertor(dateString);
                        int year = Integer.valueOf(shiftDate.getValue().toString().substring(0, 4));
                        int week = dateConvertor.getWeekFromDate();
                        int day = dateConvertor.getDayFromDate();


                        shift.setDay(day);
                        shift.setWeek(week);
                        shift.setYear(year);
                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }

                    dataController.addShift(shift);
                    clearFields(table);
                    break;
                case 5:
                    entities.Supplier supplier = new entities.Supplier();
                    try {
                        supplier.setName(supplierName.getValue());
                        supplier.setAddress(supplierAddress.getValue());
                        supplier.setContactNumber(Integer.valueOf(supplierContactNo.getValue()));
                        supplier.setEmail(supplierEmail.getValue());
                        supplier.setWebsite(supplierWebsite.getValue());
                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }

                    dataController.addSupplier(supplier);
                    clearFields(table);
                    break;
                case 6:
                    entities.Invoice invoice = new entities.Invoice();
                    try {
                        invoice.setDate(new java.sql.Date(invoiceDate.getValue().getTime()));
                        invoice.setSupplierID(Integer.valueOf(invoiceSupplierID.getValue()));
                        invoice.setTotalValue(Float.valueOf(invoiceTotalValue.getValue()));
                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }

                    dataController.addInvoice(invoice);
                    clearFields(table);
                    break;
                case 7:
                    entities.Tables tables = new entities.Tables("");
                    try {
                        tables.setNoOfSeats(Integer.valueOf(tableNoOfSeats.getValue()));
                        tables.setArea(tableArea.getValue());
                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }
                    dataController.addTable(tables);
                    clearFields(table);
                    break;
                case 9:
                    entities.Product product = new entities.Product();
                    try {
                        product.setName(productName.getValue());
                        product.setPrice(Float.valueOf(productPrice.getValue()));
                        product.setQuantity(Integer.valueOf(productQuantity.getValue()));
                        product.setType(productType.getValue().toString());
                    }
                    catch (NullPointerException npe) {
                        Notification.show("Fill in Required Fields!");
                        break;
                    }
                    catch (NumberFormatException nfe) {
                        Notification.show("Input Error. Check Your Input Fields!");
                        break;
                    }
                    dataController.addProduct(product);
                    clearFields(table);
                    break;
            }
        });
        clearButton = new Button("Clear", e -> clearFields(table));

        checkAvailabilityButton = new Button("Check Availability", e-> {
            String reservationTime = reservationStartingTimeHour.getValue().toString() + "." + reservationStartingTimeMin.getValue().toString();
            TableMapView tableMapView = new TableMapView();

            //tableMapView.checkAvailability(String.valueOf(reservationDate.getValue()), reservationTime);
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
                employeeAccessLevel.setWidth(screenWidth);

                employeeName.setRequired(true);
                employeeAddress.setRequired(true);
                employeeUsername.setRequired(true);
                employeePassword.setRequired(true);
                employeePosition.setRequired(true);
                employeeSalaryPh.setRequired(true);
                employeeAccessLevel.setRequired(true);


                BeanItemContainer<String> container =
                        new BeanItemContainer<>(String.class);

                // Put some data into list
                container.addItem("PT");
                container.addItem("FT");

                employeeContractType.setContainerDataSource(container);

                employeeContractType.setWidth("200");
                employeeContractType.setRequired(true);

                BeanItemContainer<String> accessLevelContainer = new BeanItemContainer<>(String.class);

                accessLevelContainer.addItem("Manager");
                accessLevelContainer.addItem("Basic");

                employeeAccessLevel.setContainerDataSource(accessLevelContainer);

                employeeDoB.setValue(new java.sql.Date(new java.util.Date().getTime()));
                employeeDoB.setIcon(FontAwesome.USER);
                employeeDoB.setRequired(true);
                addComponents(insertLabel, employeeName);
                addComponents(employeeUsername, employeePassword);
                addComponents(employeeAddress, employeeContractType, employeeDoB);
                addComponents(employeePhone, employeePosition);
                addComponents(employeeSalaryPh, employeeAccessLevel);
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
                eventDate.setRequired(true);
                //eventDate.setRequired(true);
                eventStartingTime.setRequired(true);
                eventEndingTime.setRequired(true);
                eventActName.setRequired(true);
                eventActType.setRequired(true);
                eventPrice.setRequired(true);

                addComponents(insertLabel, eventActName, eventActType);
                addComponents( eventPrice,  eventStartingTime,  eventEndingTime);
                addComponents(eventDate, eventArea);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 3:
                /* insertLabel.setValue("Add Reservation");
                reservationDate.setWidth(screenWidth);
                CssLayout selectTimeDiv = new CssLayout();
                BeanItemContainer<String> hourContainer =
                        new BeanItemContainer<>(String.class);

                BeanItemContainer<String> minContainer =
                        new BeanItemContainer<>(String.class);

                for(int i = 12; i < 23; i++) {
                    hourContainer.addItem("" + i );
                }
                for(int i = 0; i < 4; i++) {
                    if(i == 0)
                        minContainer.addItem("00");
                    else
                        minContainer.addItem("" + (i * 15));
                }

                reservationDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
                reservationStartingTimeHour.setContainerDataSource(hourContainer);
                reservationStartingTimeHour.setWidth("100");
                reservationStartingTimeMin.setWidth("100");
                reservationStartingTimeMin.setContainerDataSource(minContainer);
                selectTimeDiv.addComponents(reservationStartingTimeHour,reservationStartingTimeMin);
                addComponents(insertLabel, reservationDate, selectTimeDiv);
                insertButtons.addComponents(checkAvailabilityButton);
                addComponent(insertButtons);
                setSpacing(true);
                break; */
            case 4:
                insertLabel.setValue("Add Shift");
                shiftDate.setWidth(screenWidth);
                shiftEndTime.setWidth(screenWidth);
                shiftOvertimeHours.setWidth(screenWidth);
                shiftStartTime.setWidth(screenWidth);
                shiftUsername.setWidth(screenWidth);
                shiftDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
                addComponents(insertLabel,  shiftUsername,  shiftStartTime);
                addComponents( shiftEndTime, shiftDate, shiftOvertimeHours);
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

                supplierAddress.setRequired(true);
                supplierContactNo.setRequired(true);
                supplierEmail.setRequired(true);
                supplierName.setRequired(true);
                supplierWebsite.setRequired(true);

                addComponents(insertLabel, supplierName, supplierAddress);
                addComponents(supplierContactNo, supplierEmail);
                addComponents( supplierWebsite);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 6:
                insertLabel.setValue("Add Invoice");
                invoiceDate.setWidth(screenWidth);
                invoiceSupplierID.setWidth(screenWidth);
                invoiceTotalValue.setWidth(screenWidth);
                invoiceDate.setValue(new java.sql.Date(new java.util.Date().getTime()));

                invoiceDate.setRequired(true);
                invoiceSupplierID.setRequired(true);
                invoiceTotalValue.setRequired(true);
                addComponents(insertLabel, invoiceDate, invoiceTotalValue, invoiceSupplierID);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 7:
                insertLabel.setValue("Add Table");
                tableNoOfSeats.setWidth(screenWidth);
                tableArea.setWidth(screenWidth);
                tableNoOfSeats.setRequired(true);
                tableArea.setRequired(true);
                addComponents(insertLabel, tableNoOfSeats, tableArea);
                insertButtons.addComponents(addButton, clearButton);
                addComponents(insertButtons);
                break;
            case 9:
                insertLabel.setValue("Add Product");
                productName.setWidth(screenWidth);
                productPrice.setWidth(screenWidth);
                productQuantity.setWidth(screenWidth);
                productType.setWidth(screenWidth);

                productName.setRequired(true);
                productPrice.setRequired(true);
                productQuantity.setRequired(true);
                productType.setRequired(true);

                BeanItemContainer<String> productTypeContainer = new BeanItemContainer<>(String.class);
                ArrayList<String> types = dataController.getTypes();
                for(int i =0 ; i < dataController.getTypes().size(); i++) {
                    productTypeContainer.addItem(types.get(i));
                }

                employeeAccessLevel.setContainerDataSource(productTypeContainer);
                addComponents(insertLabel, productName, productPrice, productType, productQuantity);
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
                tableArea.clear();
                break;
            case 9:
                productName.clear();
                productType.clear();
                productQuantity.clear();
                productPrice.clear();
                break;
        }
    }
}
