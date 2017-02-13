package ui;

import javax.servlet.annotation.WebServlet;
import javax.swing.text.html.CSS;

import accessobjects.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    EventDAO eventDAO = new EventDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    ReservationsDAO reservationsDAO = new ReservationsDAO();
    ShiftDAO shiftDAO = new ShiftDAO();
    SupplierDAO supplierDAO = new SupplierDAO();
    TablesDAO tablesDAO = new TablesDAO();

    private String screenWidth;
    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    private Button button1 = new Button("Employee");
    private Button button2 = new Button("Event");
    private Button button3 = new Button("Reservations");
    private Button button4 = new Button("Shift");
    private Button button5 = new Button("Supplier");
    private Button button6 = new Button("Invoice");
    private Button button7 = new Button("Tables");
    private int table = 0;
    private Label showTitle = new Label();
    private Label insertLabel = new Label();
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
    private TextField actName = new TextField();
    private TextField actType = new TextField();
    private TextField price = new TextField();
    private TextField startingTime = new TextField();
    private TextField endingTime = new TextField();
    private PopupDateField dateOfEvent = new PopupDateField("Date");
    private TextField areaOfEvent = new TextField();
    private Label actNameLabel = new Label("Act Name");
    private Label actTypeLabel = new Label("Act Type");
    private Label priceLabel = new Label("Price");
    private Label startingTimeLabel = new Label("Starting Time");
    private Label endingTimeLabel = new Label("Ending Time");
    private Label areaOfEventLabel = new Label("Area");


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout tableWindow = new VerticalLayout();
        final VerticalLayout insertWindow = new VerticalLayout();
        final CssLayout windows = new CssLayout();
        final CssLayout tables = new CssLayout();
        final CssLayout filtering = new CssLayout();
        final CssLayout insertButtons = new CssLayout();

        windows.setWidth("100%");
        tableWindow.setWidth("75%");
        insertWindow.setWidth("25%");
        showTitle.setStyleName(ValoTheme.LABEL_H2);

        screenWidth = String.valueOf(windows.getWidth()) + "%";

        //
        //
        // Grid
        //
        //

        filterText.setInputPrompt("Filter by name...");
        filterText.addTextChangeListener(e -> {
            switch (table) {
                case 0:
                    break;
                case 1:
                    grid.setContainerDataSource(new BeanItemContainer<>(entities.Employee.class, employeeDAO.getEmployeesByName(e.getText())));
                    break;
                case 2:
                    grid.setContainerDataSource(new BeanItemContainer<>(entities.Event.class, eventDAO.getEventsByName(e.getText())));
                    break;
                case 3:
                    grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, reservationsDAO.getReservationsByName(e.getText())));
                    break;
                case 4:
                    grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, shiftDAO.getShiftByName(e.getText())));
                    break;
                case 5:
                    grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, supplierDAO.getSupplierByName(e.getText())));
                    break;
                case 6:
                    grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, invoiceDAO.getInvoicesByName(e.getText())));
                    break;
                case 7:
                    grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, tablesDAO.getTableByName(e.getText())));
                    break;
            }

            if (e.getText().equals(""))
                updateGrid(table);
        });

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        // Pre-select some items
        Grid.MultiSelectionModel selection =
                (Grid.MultiSelectionModel) grid.getSelectionModel();

        Button delSelected = new Button("Delete Selected", e -> {
            // Delete all selected data items
            for (Object itemId: selection.getSelectedRows()) {
                grid.getContainerDataSource().removeItem(itemId);
                deleteRecords(table, itemId);
            }

            // Otherwise out of sync with container
            grid.getSelectionModel().reset();

            updateGrid(table);
            // Disable after deleting
           // e.getButton().setEnabled(false);
        });
        /*
        if(selection.getSelectedRows().isEmpty()) {
            delSelected.setEnabled(false);
        }
        else {
            delSelected.setEnabled(true);
        }
        */
        //
        //
        //Insert Area
        //
        //




        insertLabel.setHeight("50");
        insertLabel.setStyleName(ValoTheme.LABEL_H2);
        tables.addComponents(button1, button2, button3, button4, button5, button6, button7);
        tables.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        Button clearFilterButton = new Button("Clear Filter");
        clearFilterButton.addClickListener(e -> {
            filterText.clear();
            updateGrid(table);
        });

        updateGrid(table);

        Button addButton = new Button("Add", e-> {
            switch (table) {
                case 1:
                    Employee employee = new Employee();
                    employee.setName(employeeName.getValue());
                    employee.setAddress(employeeAddress.getValue());
                    employee.setContractType(employeeContractType.getValue().toString());
                   // employee.setDob(employeeDoB.getValue());
                    employee.setPhone(Integer.valueOf(employeePhone.getValue()));
                    employee.setPosition(employeePosition.getValue());
                    employee.setSalaryPh(Float.valueOf(employeeSalaryPh.getValue()));
                    employee.setUsername(employeeUsername.getValue());
                    employee.setPassword(employeePassword.getValue());
                    employeeDAO.addEmployee(employee);
                    updateGrid(table);
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
                    entities.Event event = new entities.Event();
                    event.setActName(actName.getValue());
                    event.setActType(actType.getValue());
                    event.setArea(areaOfEvent.getValue());
                    event.setEndTime(Float.valueOf(endingTime.getValue()));
                    event.setPrice(Float.valueOf(price.getValue()));
                    event.setStartingTime(Float.valueOf(startingTime.getValue()));
                    eventDAO.addEvent(event);
                    updateGrid(table);
                    actName.clear();
                    actType.clear();
                    areaOfEvent.clear();
                    dateOfEvent.clear();
                    startingTime.clear();
                    endingTime.clear();
                    price.clear();
                    break;
                case 3:
                    //reservationsDAO.addReservation(reservation);
                    break;
                case 4:
                    //shiftDAO.addShift(shiftDAO);
                    break;
                case 5:
                    //supplierDAO.addSuplier(supplier);
                    break;
                case 6:
                    //invoiceDAO.addInvoice(invoice);
                    break;
                case 7:
                    //tablesDAO.addTable(table);
                    break;
            }
        });
        Button clearButton = new Button("Clear", e-> {
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
                    actName.clear();
                    actType.clear();
                    areaOfEvent.clear();
                    dateOfEvent.clear();
                    startingTime.clear();
                    endingTime.clear();
                    price.clear();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        });

        button1.addClickListener(e -> {
            table = 1;
            updateGrid(table);
            updateInsertArea(table);
            insertWindow.removeAllComponents();
            insertWindow.addComponents(insertLabel, employeeNameLabel, employeeName);
            insertWindow.addComponents(employeeUsernameLabel, employeeUsername, employeePassword);
            insertWindow.addComponents(employeeAddressLabel,employeeAddress, employeeContractType, employeeDoB);
            //insertWindow.addComponent(employeeDoB);
            insertWindow.addComponents(employeePhoneLabel, employeePhone, employeePositionLabel, employeePosition);
            insertWindow.addComponents(employeeSalaryPhLabel, employeeSalaryPh);
            insertButtons.addComponents(addButton, clearButton);
            insertWindow.addComponents(insertButtons);
        });
        button2.addClickListener(e -> {
            table = 2;
            updateGrid(table);
            updateInsertArea(table);
            insertWindow.removeAllComponents();
            insertWindow.addComponents(insertLabel, actNameLabel, actName, actTypeLabel, actType);
            insertWindow.addComponents(priceLabel, price, startingTimeLabel, startingTime, endingTimeLabel, endingTime);
            insertWindow.addComponents(dateOfEvent, areaOfEventLabel, areaOfEvent);
            insertButtons.addComponents(addButton, clearButton);
            insertWindow.addComponents(insertButtons);
        });
        button3.addClickListener(e -> {
            table = 3;
            updateGrid(table);
        });
        button4.addClickListener(e -> {
            table = 4;
            updateGrid(table);
        });
        button5.addClickListener(e -> {
            table = 5;
            updateGrid(table);
        });
        button6.addClickListener(e -> {
            table = 6;
            updateGrid(table);
        });
        button7.addClickListener(e -> {
            table = 7;
            updateGrid(table);
        });




        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filterText, clearFilterButton, delSelected);
        tableWindow.addComponents(showTitle, tables, filtering, grid);
        windows.addComponents(tableWindow, insertWindow);

        grid.setWidth(screenWidth);
        tableWindow.setMargin(true);
        tableWindow.setSpacing(true);
        insertWindow.setMargin(true);

        //insertWindow.setSpacing(true);

        windows.setStyleName(ValoTheme.LAYOUT_WELL);
        setContent(windows);


    }

    public void updateGrid(int tableNo) {
        switch (tableNo) {
            case 0:
                showTitle.setValue("Manage Records");
                grid.setEnabled(false);
                break;
            case 1:
                showTitle.setValue("Manage Employees");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(entities.Employee.class, employeeDAO.getAllEmployees()));
                break;

            case 2:
                showTitle.setValue("Manage Events");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(entities.Event.class, eventDAO.getAllEvents()));
                break;

            case 3:
                showTitle.setValue("Manage Reservations");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, reservationsDAO.getAllReservations()));
                break;

            case 4:
                showTitle.setValue("Manage Rosters");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, shiftDAO.getAllShifts()));
                break;

            case 5:
                showTitle.setValue("Manage Suppliers");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, supplierDAO.getAllSuppliers()));
                break;

            case 6:
                showTitle.setValue("Manage Invoices");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, invoiceDAO.getAllInvoices()));
                break;

            case 7:
                showTitle.setValue("Manage Tables");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, tablesDAO.getAllTables()));
                break;
        }
    }

    public void deleteRecords(int tableNo, Object toBeDeleted) {
        switch (tableNo) {
            case 1:
                employeeDAO.deleteEmployee((entities.Employee)toBeDeleted);
        }
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

                //employeeDoB.setValue(new Date().toLocalDate());
                employeeDoB.setIcon(FontAwesome.USER);
                employeeDoB.setRequired(true);
                break;
            case 2:
                insertLabel.setValue("Add Event");

                actName.setWidth(screenWidth);
                actType.setWidth(screenWidth);
                price.setWidth(screenWidth);
                startingTime.setWidth(screenWidth);
                endingTime.setWidth(screenWidth);

                dateOfEvent.setCaption("Date");
                //dateOfEvent.setValue(new Date());
                //dateOfEvent.setRequired(true);
                startingTime.setRequired(true);
                endingTime.setRequired(true);
                areaOfEvent.setRequired(true);
                actName.setRequired(true);
                actType.setRequired(true);
                price.setRequired(true);


                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }


        //List<Employee> employees = employeeDAO.getAllEmployees();


        @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
        }
    }
