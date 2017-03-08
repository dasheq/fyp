package misc;

import com.vaadin.ui.MenuBar;

/**
 * Created by damo k on 22/02/2017.
 */
public class NavigationBar extends MenuBar {
    MenuItem staffMenu;
    MenuItem bookingsMenu;
    MenuItem stockMenu;
    MenuItem salesMenu;

    public void run () {
        setAutoOpen(true);
        setHeight("40");
        /*
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
        staffMenu = addItem("Staff", null);
        staffMenu.addItem("Add Employee", menuAddEmployee);
        staffMenu.addItem("Calculate Wages", menuCalcWages);
        staffMenu.addItem("Manage Roster", menuAddShift);
        bookingsMenu = addItem("Bookings", null);
        bookingsMenu.addItem("Add Event", menuAddEvent);
        //TODO: Working Reservations

        bookingsMenu.addItem("Add Reservations", menuAddReservation);
        bookingsMenu.addItem("Add Tables", menuAddTables);
        stockMenu = addItem("Stock", null);
        stockMenu.addItem("Add Invoice", menuAddInvoice);
        stockMenu.addItem("Add Supplier", menuAddSupplier);

        //TODO: Sales sales sales
        salesMenu = addItem("Sales", null);
        salesMenu.addItem("Sales Stats", null);
        salesMenu.addItem("Transactions", null);
        */
    }
}
