package views;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Not;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import controllers.DataController;
import entities.*;
import accessobjects.*;
import misc.MyUI;

/**
 * Created by damo k on 15/02/2017.
 */
@DesignRoot
public class MainView extends VerticalLayout implements View {
    DataController dataController = new DataController();

    int accessLevel;

    private String screenWidth = "100%";
    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    private int table = 0;
    private Label showTitle = new Label();
    MenuBar menuBar = new MenuBar();
    CssLayout windows = new CssLayout();
    InsertView insertWindow = null;
    WagesView wagesView = null;
    StatsView statsView = null;
    VerticalLayout tableWindow = new VerticalLayout();

    Grid.MultiSelectionModel selection;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Page.getCurrent().setTitle("Main Page");

        accessLevel = Integer.valueOf(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("access").toString());

        final CssLayout topHorizontalDiv = new CssLayout();
        final CssLayout filtering = new CssLayout();

        windows.setWidth("100%");
        tableWindow.setWidth("75%");
        showTitle.setStyleName(ValoTheme.LABEL_H2);


        filterText.setInputPrompt("Filter Search");
        filterText.addTextChangeListener(e -> {
            switch (table) {
                case 0:
                    break;
                case 1:
                    grid.setContainerDataSource(new BeanItemContainer<>(entities.Employee.class, dataController.getEmployeesByName(e.getText())));
                    break;
                case 2:
                    grid.setContainerDataSource(new BeanItemContainer<>(entities.Event.class, dataController.getEventsByName(e.getText())));
                    break;
                case 3:
                    grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, dataController.getReservationsByName(e.getText())));
                    break;
                case 4:
                    grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, dataController.getShiftByName(e.getText())));
                    break;
                case 5:
                    grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, dataController.getSupplierByName(e.getText())));
                    break;
                case 6:
                    grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, dataController.getInvoicesByName(e.getText())));
                    break;
                case 7:
                    grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, dataController.getTableByName(e.getText())));
                    break;
                case 8:
                    grid.setContainerDataSource(new BeanItemContainer<>(Transaction.class, dataController.getTransactionByName(e.getText())));
                    break;
                case 9:
                    grid.setContainerDataSource(new BeanItemContainer<>(Product.class, dataController.getProductByName(e.getText())));
            }

            if (e.getText().equals(""))
                updateGrid(table);
        });

        if(accessLevel == 1) {
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            selection =
                    (Grid.MultiSelectionModel) grid.getSelectionModel();
        }


        Button delSelected = new Button("Delete Selected");
        if(accessLevel == 0) {
            delSelected.setEnabled(false);
        }
        delSelected.addClickListener(e -> {

            Notification.show("Deleted");


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


        MenuBar.Command menuCalcWages = (MenuBar.Command) menuItem -> {
            if(accessLevel == 1) {
                table = 1;
                updateGrid(table);
            }
            else {
                grid.removeAllColumns();
                grid.setEnabled(true);
                grid.setContainerDataSource(new BeanItemContainer<>(Employee.class, dataController.getEmployeesByName(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user").toString())));
            }

            if(insertWindow != null)
                windows.removeComponent(insertWindow);

            wagesView = new WagesView(accessLevel);
            wagesView.setWidth("25%");
            wagesView.run();
            windows.addComponent(wagesView);
        };

        MenuBar.Command menuAddEmployee = (MenuBar.Command) menuItem -> {
            if(accessLevel == 1) {
                table = 1;
                updateGrid(table);
                refreshInsertView(table);
            }
            else {
                Notification.show("Access Only For Managers");
            }
        };

        MenuBar.Command menuAddEvent = (MenuBar.Command) menuItem -> {
            table = 2;
            updateGrid(table);
            if(accessLevel == 1) {
                refreshInsertView(table);
            }
        };
        MenuBar.Command menuAddShift = (MenuBar.Command) menuItem -> {
            if(grid != null)
                tableWindow.removeComponent(grid);

            //MyUI.getCurrent().getUI().getNavigator().removeView("roster");
            MyUI.getCurrent().getUI().getNavigator().addView("roster", new RosterView(accessLevel));
            MyUI.getCurrent().getUI().getNavigator().navigateTo("roster");
        };
        MenuBar.Command menuCheckProductAvailability = (MenuBar.Command) menuItem -> {
            table = 9;
            updateGrid(table);

            if(accessLevel == 1) {
                refreshInsertView(table);
            }
        };
        MenuBar.Command menuAddSupplier = (MenuBar.Command) menuItem -> {
            table = 5;
            updateGrid(table);
            if(accessLevel == 1) {
                refreshInsertView(table);
            }
        };
        MenuBar.Command menuAddInvoice = (MenuBar.Command) menuItem -> {
            table = 6;
            updateGrid(table);
            if(accessLevel == 1) {
                refreshInsertView(table);
            }
        };
        MenuBar.Command menuAddTables = (MenuBar.Command) menuItem -> {
            table = 7;
            updateGrid(table);
            if(accessLevel == 1) {
                refreshInsertView(table);
            }
        };
        MenuBar.Command menuShowReservations = (MenuBar.Command) menuItem -> {
            if(insertWindow != null)
                windows.removeComponent(insertWindow);

            table = 3;
            updateGrid(table);
            //refreshInsertView(table);
        };
        MenuBar.Command menuShowTableMap = (MenuBar.Command) menuItem -> {
            if(grid != null)
                tableWindow.removeComponent(grid);

            MyUI.getCurrent().getUI().getNavigator().removeView("reservations");
            MyUI.getCurrent().getUI().getNavigator().addView("reservations", new TableMapView());
            MyUI.getCurrent().getUI().getNavigator().navigateTo("reservations");
        };
        MenuBar.Command menuSaleTransactions = (MenuBar.Command) menuItem -> {
            table = 8;
            if(accessLevel == 1)
                updateGrid(table);
            else
                grid.removeAllColumns();
                grid.setEnabled(true);
                grid.setContainerDataSource(new BeanItemContainer<>(Transaction.class, dataController.getTransactionByName(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user").toString())));

        };
        MenuBar.Command menuShowSaleStats = (MenuBar.Command) menuItem -> {
            if(accessLevel == 1){
                if(grid != null)
                    tableWindow.removeComponent(grid);

                if(insertWindow != null)
                    removeComponent(insertWindow);

                if(statsView == null) {
                    statsView = new StatsView();
                    statsView.run();
                    tableWindow.addComponent(statsView);
                }
            }
            else {
                Notification.show("Access Only For Managers");
            }


        };
        MenuBar.Command menuShowPointOfSale = (MenuBar.Command) menuItem -> {
            if(grid != null)
                tableWindow.removeComponent(grid);

            MyUI.getCurrent().getUI().getNavigator().removeView("sale");
            MyUI.getCurrent().getUI().getNavigator().addView("sale", new PointOfSaleView());
            MyUI.getCurrent().getUI().getNavigator().navigateTo("sale");
        };
        MenuBar.Command menuShowHome = (MenuBar.Command) menuItem -> {
            MyUI.getCurrent().getUI().getNavigator().removeView("home");
            MyUI.getCurrent().getUI().getNavigator().addView("home", new MainView());
            MyUI.getCurrent().getUI().getNavigator().navigateTo("home");
        };
        MenuBar.Command menuLogout = (MenuBar.Command) menuItem -> {
           // getUI().getSession().close();
            VaadinService.getCurrentRequest().getWrappedSession().invalidate();
            MyUI.getCurrent().getUI().getPage().setLocation("/*");


            MyUI.getCurrent().getUI().getNavigator().removeView("login");
            MyUI.getCurrent().getUI().getNavigator().addView("login", new LoginView());
            MyUI.getCurrent().getUI().getNavigator().navigateTo("login");
        };


       // NavigationBar menuBar = new NavigationBar();

        menuBar.setAutoOpen(true);
        menuBar.setHeight("40");
        MenuBar.MenuItem staffMenu = menuBar.addItem("Staff", null);
        staffMenu.addItem("Add Employee", menuAddEmployee);
        staffMenu.addItem("Calculate Wages", menuCalcWages);
        staffMenu.addItem("Manage Roster", menuAddShift);
        MenuBar.MenuItem bookingsMenu = menuBar.addItem("Bookings", null);
        bookingsMenu.addItem("Events", menuAddEvent);
        bookingsMenu.addItem("See Reservations", menuShowReservations);
        bookingsMenu.addItem("Make a Reservation", menuShowTableMap   );
        bookingsMenu.addItem("Add Tables", menuAddTables);
        MenuBar.MenuItem stockMenu = menuBar.addItem("Stock", null);
        stockMenu.addItem("Products", menuCheckProductAvailability);
        stockMenu.addItem("Add Invoice", menuAddInvoice);
        stockMenu.addItem("Add Supplier", menuAddSupplier);
        MenuBar.MenuItem salesMenu = menuBar.addItem("Sales", null);
        salesMenu.addItem("Point of Transaction", menuShowPointOfSale);
        salesMenu.addItem("Transactions", menuSaleTransactions);
        salesMenu.addItem("Sales Statistics", menuShowSaleStats);
        MenuBar.MenuItem userMenu = menuBar.addItem("Navigate", null);
        userMenu.addItem("Home", menuShowHome);
        userMenu.addItem("Logout", menuLogout);




        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filterText, clearFilterButton, delSelected);

        if(topHorizontalDiv.getComponentCount() == 0) {
            topHorizontalDiv.addComponents(showTitle, menuBar);
        }
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
                grid.setContainerDataSource(new BeanItemContainer<>(entities.Employee.class, dataController.getAllEmployees()));
                break;

            case 2:
                showTitle.setValue("Manage Events");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(entities.Event.class, dataController.getAllEvents()));
                break;

            case 3:
                showTitle.setValue("Manage Reservations");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, dataController.getAllReservations()));
                break;

            case 4:

                break;
            case 5:
                showTitle.setValue("Manage Suppliers");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, dataController.getAllSuppliers()));
                break;

            case 6:
                showTitle.setValue("Manage Invoices");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, dataController.getAllInvoices()));
                break;

            case 7:
                showTitle.setValue("Manage Tables");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, dataController.getAllTables()));
                break;

            case 8:
                showTitle.setValue("Manage Sales");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(Transaction.class, dataController.getAllSales()));
                break;
            case 9:
                showTitle.setValue("Manage Products");
                grid.setEnabled(true);
                grid.removeAllColumns();
                grid.setContainerDataSource(new BeanItemContainer<>(entities.Product.class, dataController.getAllProducts()));
                break;
        }
    }

    public void deleteRecords(int tableNo, Object toBeDeleted) {
        switch (tableNo) {
            case 1:
                dataController.deleteEmployee((entities.Employee) toBeDeleted);
                break;
            case 2:
                dataController.deleteEvent((entities.Event) toBeDeleted);
                break;
            case 3:
                dataController.deleteReservation((Reservations) toBeDeleted);
                break;
            case 4:
                dataController.deleteShift((Shift) toBeDeleted);
                break;
            case 5:
                dataController.deleteSupplier((Supplier) toBeDeleted);
                break;
            case 6:
                dataController.deleteInvoice((Invoice) toBeDeleted);
                break;
            case 7:
                dataController.deleteTable((Tables) toBeDeleted);
                break;
            case 8:
                dataController.deleteSale((Transaction) toBeDeleted);
                break;
            case 9:
                dataController.deleteProduct((Product) toBeDeleted);
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

}

