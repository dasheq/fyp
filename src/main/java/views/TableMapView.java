package views;

import accessobjects.ReservationsDAO;
import accessobjects.TablesDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.Reservations;
import entities.Tables;

import java.util.ArrayList;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

/**
 * Created by damo k on 06/03/2017.
 */
public class TableMapView extends CssLayout implements View {

    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();
    CssLayout insertButtons = new CssLayout();

    Button checkAvailabilityButton = new Button("Check Availability");

    ReservationsDAO reservationsDAO = new ReservationsDAO();
    TablesDAO tablesDAO = new TablesDAO();
    ArrayList<Tables> tablesList = tablesDAO.getAllTables();
    ArrayList<Reservations> reservationsList = reservationsDAO.getAllReservations();
    ArrayList<String> areas = tablesDAO.getAreas();
    int noOfTables = tablesList.size();
    int noOfAreas = tablesDAO.getNoOfAreas();

    //Variables for Insert Reservation Window
    private Label insertLabel = new Label("Add Reservations");
    private PopupDateField reservationDate = new PopupDateField("Date");
    private NativeSelect reservationStartingTimeHour = new NativeSelect("Hour");
    private NativeSelect reservationStartingTimeMin = new NativeSelect("Minute");

    public TableMapView() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setWidth("100%");
        mainView.setWidth("75%");
        sideView.setWidth("25%");
        String screenWidth = String.valueOf(this.getWidth());
        System.out.println(screenWidth);
        Page.getCurrent().setTitle("Reservations");



        Label label = new Label("Tables Map");
        label.setStyleName(ValoTheme.LABEL_H3);
        mainView.addComponent(label);
        for(int i = 0; i< noOfAreas; i++) {
            Label areaLabel = new Label(areas.get(i));
            areaLabel.setStyleName(ValoTheme.LABEL_H4);
            mainView.addComponent(areaLabel);
            CssLayout areaTableLayout = new CssLayout();
            for(int j = 0; j < noOfTables; j++) {
                if (tablesList.get(j).getArea().equals(areas.get(i))) {
                    areaTableLayout.addComponent(tablesList.get(j));
                }
            }
            mainView.addComponent(areaTableLayout);
        }

        reservationDate.setWidth("100%");
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

        checkAvailabilityButton.addClickListener(e -> {
            String reservationTime = reservationStartingTimeHour.getValue().toString() + "." + reservationStartingTimeMin.getValue().toString();
            java.sql.Date date = new java.sql.Date(reservationDate.getValue().getTime());
            System.out.println(reservationDate.getValue().toString().getClass());
            checkAvailability(date.toString(), reservationTime);
        });

        reservationDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
        reservationStartingTimeHour.setContainerDataSource(hourContainer);
        reservationStartingTimeHour.setWidth("100%");
        reservationStartingTimeMin.setWidth("100%");
        reservationStartingTimeMin.setContainerDataSource(minContainer);
        selectTimeDiv.addComponents(reservationStartingTimeHour,reservationStartingTimeMin);
        sideView.addComponents(insertLabel, reservationDate, selectTimeDiv);
        insertButtons.addComponents(checkAvailabilityButton);
        sideView.addComponent(insertButtons);
        addComponents(mainView,sideView);
        getUI().setContent(this);
    }


    public void checkAvailability(String reservationDate, String reservationTime) {
        for (int i = 0; i < reservationsList.size(); i++) {
            System.out.println(reservationDate + reservationsList.get(i).getDate());
            if (String.valueOf(reservationsList.get(i).getDate()).equals(reservationDate)) {
                System.out.println(" ss " + reservationsList.get(i).getStartingTime() + reservationTime);
                if ((reservationsList.get(i).getStartingTime() - Float.valueOf(reservationTime)) <= -2 &&
                        (reservationsList.get(i).getStartingTime() - Float.valueOf(reservationTime)) >= 2) {
                    //System.out.println(reservationsList.get(i).getStartingTime() + reservationTime);
                    tablesList.get(i).setStyleName(ValoTheme.BUTTON_PRIMARY);
                    System.out.println("howya");
                    //tableMap.removeComponent(tablesList.get(i));
                }

            }
        }
    }

}
