package views;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.*;
import accessobjects.*;
import misc.MyUI;
import misc.TableButton;

import java.util.ArrayList;

/**
 * Created by damo k on 15/02/2017.
 */
@DesignRoot
public class MainView extends VerticalLayout implements View {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    EventDAO eventDAO = new EventDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    ReservationsDAO reservationsDAO = new ReservationsDAO();
    ShiftDAO shiftDAO = new ShiftDAO();
    SupplierDAO supplierDAO = new SupplierDAO();
    TablesDAO tablesDAO = new TablesDAO();

    private String screenWidth = "100%";
    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    private int table = 0;
    private Label showTitle = new Label();
    CssLayout windows = new CssLayout();
    InsertView insertWindow = null;
    WagesView wagesView = null;
    TableMapView tableMapView = null;
    CssLayout tablesMap = new CssLayout();
    VerticalLayout tableWindow = new VerticalLayout();
    VerticalLayout shiftDivHolder = new VerticalLayout();
    CssLayout shiftDiv1 = new CssLayout();
    CssLayout shiftDiv2 = new CssLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Page.getCurrent().setTitle("Main Page");

        //final CssLayout windows = new CssLayout();
        final CssLayout topHorizontalDiv = new CssLayout();
        final CssLayout filtering = new CssLayout();

        windows.setWidth("100%");
        tableWindow.setWidth("75%");
        showTitle.setStyleName(ValoTheme.LABEL_H2);

        //screenWidth = String.valueOf(windows.getWidth()) + "%";

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
        Grid.MultiSelectionModel selection =
                (Grid.MultiSelectionModel) grid.getSelectionModel();

        Button delSelected = new Button("Delete Selected");
        delSelected.addClickListener(e -> {

            /* TODO: Confirmation Dialog */


            // Delete all selected data items
            for (Object itemId : selection.getSelectedRows()) {
                grid.getContainerDataSource().removeItem(itemId);
                deleteRecords(table, itemId);
            }

            grid.getSelectionModel().reset();

        });


        Button clearFilterButton = new Button("Clear Filter");
        clearFilterButton.addClickListener(e -> {
            filterText.clear();
            updateGrid(table);
        });

        updateGrid(table);


        MenuBar.Command menuCalcWages = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                table = 1;
                updateGrid(table);

                if(insertWindow != null)
                    windows.removeComponent(insertWindow);

                wagesView = new WagesView();
                wagesView.setWidth("25%");
                wagesView.run();
                windows.addComponent(wagesView);
            }
        };

        MenuBar.Command menuAddEmployee = (MenuBar.Command) menuItem -> {
            table = 1;
            updateGrid(table);
            refreshInsertView(table);
        };

        MenuBar.Command menuAddEvent = (MenuBar.Command) menuItem -> {
            table = 2;
            updateGrid(table);
            refreshInsertView(table);
        };
        MenuBar.Command menuAddReservation = (MenuBar.Command) menuItem -> {
            table = 3;
            updateGrid(table);
            refreshInsertView(table);

        };
        MenuBar.Command menuAddShift = (MenuBar.Command) menuItem -> {
            table = 4;
            updateGrid(table);
            refreshInsertView(table);
        };
        MenuBar.Command menuAddSupplier = (MenuBar.Command) menuItem -> {
            table = 5;
            updateGrid(table);
            refreshInsertView(table);
        };
        MenuBar.Command menuAddInvoice = (MenuBar.Command) menuItem -> {
            table = 6;
            updateGrid(table);
            refreshInsertView(table);
        };
        MenuBar.Command menuAddTables = (MenuBar.Command) menuItem -> {
            table = 7;
            updateGrid(table);
            refreshInsertView(table);
        };
        MenuBar.Command menuShowTableMap = (MenuBar.Command) menuItem -> {
            if(grid != null)
                tableWindow.removeComponent(grid);
            /*
            if(tableMapView != null)
                tableWindow.removeComponent(tableMapView);
*/
            MyUI.getCurrent().getUI().getNavigator().navigateTo("reservations");
            //MyUI.getUI().getNavigator().navigateTo("reservations");
            /*
            tableMapView = new TableMapView();
            tableMapView.enter();
            tableWindow.addComponent(tableMapView);
            table = 3;
            updateGrid(table);
            refreshInsertView(table);
            */
        };


       // NavigationBar menuBar = new NavigationBar();

        MenuBar menuBar = new MenuBar();
        menuBar.setAutoOpen(true);
        menuBar.setHeight("40");
        MenuBar.MenuItem staffMenu = menuBar.addItem("Staff", null);
        staffMenu.addItem("Add Employee", menuAddEmployee);
        staffMenu.addItem("Calculate Wages", menuCalcWages);
        staffMenu.addItem("Manage Roster", menuAddShift);
        MenuBar.MenuItem bookingsMenu = menuBar.addItem("Bookings", null);
        bookingsMenu.addItem("Add Event", menuAddEvent);
        //TODO: Working Reservations
        bookingsMenu.addItem("Add Reservations", menuShowTableMap   );
        bookingsMenu.addItem("Add Tables", menuAddTables);
        MenuBar.MenuItem stockMenu = menuBar.addItem("Stock", null);
        stockMenu.addItem("Add Invoice", menuAddInvoice);
        stockMenu.addItem("Add Supplier", menuAddSupplier);

        //TODO: Sales sales sales
        MenuBar.MenuItem salesMenu = menuBar.addItem("Sales", null);
        salesMenu.addItem("Sales Stats", null);
        salesMenu.addItem("Transactions", null);



        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filterText, clearFilterButton, delSelected);
        topHorizontalDiv.addComponents(showTitle, menuBar);
        tableWindow.addComponents(topHorizontalDiv, filtering, grid);
        windows.addComponents(tableWindow);

        grid.setWidth(screenWidth);
        tableWindow.setMargin(true);
        tableWindow.setSpacing(true);

        windows.setStyleName(ValoTheme.LAYOUT_WELL);
        getUI().setContent(windows);


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
                /*
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, shiftDAO.getAllShifts())); */
                if(grid != null)
                    tableWindow.removeComponent(grid);



                Label dayLabel1 = new Label();
                Label dayLabel2= new Label();
                Label dayLabel3= new Label();
                Label dayLabel4= new Label();
                Label dayLabel5= new Label();
                Label dayLabel6= new Label();
                Label dayLabel7= new Label();

                ArrayList<String> shiftsAL; // = new ArrayList<>();

                shiftsAL = shiftDAO.getShiftsByNameAndWeek();
                for(int i = 0 ; i < 7 ; i ++ ) {
                   switch (i) {
                       case 0:
                           dayLabel1.setCaption("  Sunday");
                           dayLabel1.setWidth("80");
                           break;
                       case 1:
                           dayLabel2.setCaption(" Monday");
                           dayLabel2.setWidth("80");
                           break;
                       case 2:
                           dayLabel3.setCaption("Tuesday");
                           dayLabel3.setWidth("60");
                           break;
                       case 3:
                           dayLabel4.setCaption("Wednesday");
                           dayLabel4.setWidth("50");
                           break;
                       case 4:
                           dayLabel5.setCaption("Thursday");
                           dayLabel5.setWidth("50");
                           break;
                       case 5:
                           dayLabel6.setCaption("Friday");
                           dayLabel6.setWidth("65");
                           break;
                       case 6:
                           dayLabel7.setCaption("Saturday");
                           dayLabel7.setWidth("65");
                           break;

                   }
                }
                shiftDiv1.addComponents(dayLabel1, dayLabel2, dayLabel3, dayLabel4, dayLabel5, dayLabel6, dayLabel7);
                shiftDiv1.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
                for(int i = 0; i <  shiftsAL.size(); i++ ) {
                    Label shiftTimeLabel = new Label(shiftsAL.get(i));
                    shiftTimeLabel.setWidth("125");
                    shiftDiv2.addComponent(shiftTimeLabel);
                }
                shiftDiv2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

                shiftDivHolder.addComponents(shiftDiv1, shiftDiv2);
                tableWindow.addComponent(shiftDivHolder);
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
                employeeDAO.deleteEmployee((entities.Employee) toBeDeleted);
                break;
            case 2:
                eventDAO.deleteEvent((entities.Event) toBeDeleted);
                break;
            case 3:
                reservationsDAO.deleteReservation((Reservations) toBeDeleted);
                break;
            case 4:
                shiftDAO.deleteShift((Shift) toBeDeleted);
                break;
            case 5:
                supplierDAO.deleteSupplier((Supplier) toBeDeleted);
                break;
            case 6:
                invoiceDAO.deleteInvoice((Invoice) toBeDeleted);
                break;
            case 7:
                tablesDAO.deleteTable((Tables) toBeDeleted);
                break;
        }
        updateGrid(table);
    }

    public void refreshInsertView(int tableNo) {
        if(insertWindow != null)
            windows.removeComponent(insertWindow);
        if(wagesView != null)
            windows.removeComponent(wagesView);

        insertWindow = new InsertView(tableNo);
        insertWindow.setWidth("25%");
        insertWindow.run();
        windows.addComponent(insertWindow);
    }

    public void checkTableAvailability(java.sql.Date date, String startingTime) {
        int noOfTables = tablesDAO.getNoOfTables();
        Notification.show(" dsad " + noOfTables);
        for(int i =0 ; i <noOfTables ; i++ ) {
            Tables tableButton = new Tables("Table " + i);
            /*
            if (tableButton.getReservationDate() == date)
                if(tableButton.getReservationTime() >= Float.valueOf(startingTime)-2 ||
                    tableButton.getReservationTime() <= Float.valueOf(startingTime)+2)
                    tableButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
*/
            if(grid != null)
                tableWindow.removeComponent(grid);

            tablesMap.addComponent(tableButton);
        }
        tableWindow.addComponent(tablesMap);
    }

}

