package org.test;

import javax.servlet.annotation.WebServlet;

import accessobjects.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.*;

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

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        final CssLayout tables = new CssLayout();
        final CssLayout filtering = new CssLayout();

/*
        filterText.setInputPrompt("Filter by name...");
        filterText.addTextChangeListener(e -> {
            switch (table) {
                case '0':
                    break;
                case '1':
                    grid.setContainerDataSource(new BeanItemContainer<>(Employee.class, employeeDAO.getEmployeesByName(e.getText())));
                    break;
                case '2':
                    //grid.setContainerDataSource(new BeanItemContainer<>(Event.class, eventDAO.getEventsByName(e.getText())));
                    break;
                case '3':
                    grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, reservationsDAO.getReservationsByName(e.getText())));
                    break;
                case '4':
                    grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, shiftDAO.getShiftByName(e.getText())));
                    break;
                case '5':
                    grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, supplierDAO.getSupplierByName(e.getText())));
                    break;
                case '6':
                    grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, invoiceDAO.getInvoicesByName(e.getText())));
                    break;
                case '7':
                    grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, tablesDAO.getTableByName(e.getText())));
                    break;
            }

            if (e.getText().equals(""))
                updateList(1);
        });


*/

        tables.addComponents(button1, button2, button3, button4, button5, button6, button7);
        Button clearFilterButton = new Button("Clear Filter");
        clearFilterButton.addClickListener(e -> {
            filterText.clear();
            updateList(table);
        });

       // grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, reservationsDAO.getAllReservations()));
        //updateList(3);

        button1.addClickListener(e -> {
            grid.removeAllColumns();
            table = 1;
       //     grid.setContainerDataSource(new BeanItemContainer<>(Employee.class, employeeDAO.getAllEmployees()));
        });
        button2.addClickListener(e -> {
            grid.removeAllColumns();
            table = 2;
        //    grid.setContainerDataSource(new BeanItemContainer<>(Employee.class, employeeDAO.getAllEmployees()));
        });
        button3.addClickListener(e -> {
            grid.removeAllColumns();
            table = 3;
            grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, reservationsDAO.getAllReservations()));
        });
        button4.addClickListener(e -> {
            grid.removeAllColumns();
            table = 4;
            grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, shiftDAO.getAllShifts()));
        });
        button5.addClickListener(e -> {
            grid.removeAllColumns();
            table = 5;
            grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, supplierDAO.getAllSuppliers()));
        });
        button6.addClickListener(e -> {
            grid.removeAllColumns();
            table = 6;
            grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, invoiceDAO.getAllInvoices()));
        });
        button7.addClickListener(e -> {
            grid.removeAllColumns();
            table = 7;
            grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, tablesDAO.getAllTables()));
        });

        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filterText, clearFilterButton);

        layout.addComponents(tables, filtering, grid);

        grid.setWidth("800");
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    public void updateList(int table) {
        switch (table) {
            case '0':
                break;
            case '1': {
        //        grid.setContainerDataSource(new BeanItemContainer<>(Employee.class, employeeDAO.getAllEmployees()));
                break;
            }
            case '2': {
          //      grid.setContainerDataSource(new BeanItemContainer<>(Event.class, eventDAO.getAllEvents()));
                break;
            }
            case '3': {
                grid.setContainerDataSource(new BeanItemContainer<>(Reservations.class, reservationsDAO.getAllReservations()));
                break;
            }
            case '4': {
                grid.setContainerDataSource(new BeanItemContainer<>(Shift.class, shiftDAO.getAllShifts()));
                break;
            }
            case '5': {
                grid.setContainerDataSource(new BeanItemContainer<>(Supplier.class, supplierDAO.getAllSuppliers()));
                break;
            }
            case '6': {
                grid.setContainerDataSource(new BeanItemContainer<>(Invoice.class, invoiceDAO.getAllInvoices()));
                break;
            }
            case '7': {
                grid.setContainerDataSource(new BeanItemContainer<>(Tables.class, tablesDAO.getAllTables()));
                break;
            }
        }
    }

        //List<Employee> employees = employeeDAO.getAllEmployees();


        @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
        }
    }
