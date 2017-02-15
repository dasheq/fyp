package views;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.*;
import accessobjects.*;

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
    private Button button1 = new Button("Employee");
    private Button button2 = new Button("Event");
    private Button button3 = new Button("Reservations");
    private Button button4 = new Button("Shift");
    private Button button5 = new Button("Supplier");
    private Button button6 = new Button("Invoice");
    private Button button7 = new Button("Tables");
    private int table = 0;
    private Label showTitle = new Label();
    CssLayout windows = new CssLayout();
    InsertView insertWindow;

    private Button calcWagesButton = new Button("Calculate Wages");


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Page.getCurrent().setTitle("Main Page");
        final VerticalLayout tableWindow = new VerticalLayout();
        //final CssLayout windows = new CssLayout();
        final CssLayout tables = new CssLayout();
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
            // Delete all selected data items
            for (Object itemId : selection.getSelectedRows()) {
                grid.getContainerDataSource().removeItem(itemId);
                deleteRecords(table, itemId);
            }

            grid.getSelectionModel().reset();

        });




        tables.addComponents(button1, button2, button3, button4, button5, button6, button7);
        tables.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        Button clearFilterButton = new Button("Clear Filter");
        clearFilterButton.addClickListener(e -> {
            filterText.clear();
            updateGrid(table);
        });

        updateGrid(table);

        calcWagesButton.addClickListener(e -> {
            if(insertWindow != null)
                windows.removeComponent(insertWindow);

            WagesView wagesView = new WagesView();
            wagesView.setWidth("25%");
            wagesView.run();
            windows.addComponent(wagesView);
        });

        button1.addClickListener(e -> {
            table = 1;
            filtering.addComponent(calcWagesButton);
            updateGrid(table);
            refreshInsertView(table);

        });
        button2.addClickListener(e -> {
            table = 2;
            updateGrid(table);
            refreshInsertView(table);
        });
        button3.addClickListener(e -> {
            table = 3;
            updateGrid(table);
            refreshInsertView(table);
        });
        button4.addClickListener(e -> {
            table = 4;
            updateGrid(table);
            refreshInsertView(table);
        });
        button5.addClickListener(e -> {
            table = 5;
            updateGrid(table);
            refreshInsertView(table);
        });
        button6.addClickListener(e -> {
            table = 6;
            updateGrid(table);
            refreshInsertView(table);
        });
        button7.addClickListener(e -> {
            table = 7;
            updateGrid(table);
            refreshInsertView(table);

        });


        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filterText, clearFilterButton, delSelected);
        tableWindow.addComponents(showTitle, tables, filtering, grid);
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

        insertWindow = new InsertView(tableNo);
        insertWindow.setWidth("25%");
        insertWindow.run();
        windows.addComponent(insertWindow);
    }

}

