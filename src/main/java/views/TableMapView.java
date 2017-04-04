package views;

import accessobjects.ReservationsDAO;
import accessobjects.TablesDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import controllers.DataController;
import entities.Reservations;
import entities.Tables;
import entities.TablesCheckBox;
import misc.MyUI;

import java.util.ArrayList;

/**
 * Created by damo k on 06/03/2017.
 */
public class TableMapView extends CssLayout implements View {

    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();
    final CssLayout navLayout = new CssLayout();
    CssLayout insertButtons = new CssLayout();

    Button checkAvailabilityButton = new Button("Check Availability");
    Label chosenTablesLabel = new Label();
    Label noOfSeatsLabel = new Label();
    Button goBack = new Button("Back");
    Button logout = new Button("Logout");

    DataController dataController = new DataController();

   // ReservationsDAO reservationsDAO = new ReservationsDAO();
   // TablesDAO tablesDAO = new TablesDAO();
    ArrayList<TablesCheckBox> tablesList = dataController.getAllTablesCb();
    ArrayList<Reservations> reservationsList = dataController.getAllReservations();
    ArrayList<String> areas = dataController.getAreas();
    ArrayList<Integer> tableIDs = new ArrayList<>();
    int noOfSeats;
    int noOfTables = tablesList.size();
    int noOfAreas = dataController.getNoOfAreas();
    String screenWidth = "100%";

    //Variables for Insert Reservation Window
    boolean tablesAvailable = false;
    boolean continueReservation = false;
    private Label insertLabel = new Label("Add Reservations");
    private PopupDateField reservationDate = new PopupDateField("Date");
    private NativeSelect reservationStartingTimeHour = new NativeSelect("Hour");
    private NativeSelect reservationStartingTimeMin = new NativeSelect("Minute");

    public TableMapView() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setupComponents();
        getUI().setContent(this);

        for(int i = 0; i< tablesList.size(); i++ ) {
            tablesList.get(i).addValueChangeListener(e -> {
                if(tablesAvailable) {
                    tableIDs.clear();
                    noOfSeats = 0;
                    checkTablesChosen();
                    chosenTablesLabel.setValue("Chosen Tables: " + tableIDs.toString());
                    noOfSeatsLabel.setValue("Seats :" + noOfSeats);
                    if(!continueReservation) {
                        setupMoreComponents();
                        continueReservation = true;
                    }
                }
            });
        }
    }

    public void checkTablesChosen() {
        for(int i = 0 ; i< tablesList.size(); i++) {
            if(tablesList.get(i).getValue()) {
                if(!tableIDs.contains(tablesList.get(i).getTableID())) {
                    noOfSeats += tablesList.get(i).getNoOfSeats();
                    tableIDs.add(tablesList.get(i).getTableID());
                }
            }
        }
    }


    public void checkAvailability(String reservationDate, String reservationTime) {
        for(int k = 0; k < tablesList.size(); k++) {
            tablesList.get(k).setEnabled(true);
        }
        tablesAvailable = true;

        for (int i = 0; i < reservationsList.size(); i++) {
            if (String.valueOf(reservationsList.get(i).getDate()).equals(reservationDate)) {
                for(int j = 0; j < tablesList.size() ; j++) {
                    if (reservationsList.get(i).getTableID() == tablesList.get(j).getTableID()) {
                        if ((reservationsList.get(i).getStartingTime() - Float.valueOf(reservationTime)) >= -2 &&
                                (reservationsList.get(i).getStartingTime() - Float.valueOf(reservationTime)) <= 2) {
                            tablesList.get(j).setEnabled(false);
                            //tablesList.get(j).setReserved(true);
                        } else {
                            tablesList.get(j).setEnabled(true);
                        }
                    }
                }

            }
        }
    }

    public void setupComponents() {
        this.setWidth("100%");
        mainView.setWidth("75%");
        sideView.setWidth("25%");
        Page.getCurrent().setTitle("Reservations");

        Label label = new Label("Tables Map");
        label.setStyleName(ValoTheme.LABEL_H2);
        navLayout.addComponents(goBack, logout);
        mainView.addComponents(navLayout, label);
        for(int i = 0; i< noOfAreas; i++) {
            Label areaLabel = new Label(areas.get(i));
            areaLabel.setStyleName(ValoTheme.LABEL_H4);
            mainView.addComponent(areaLabel);
            CssLayout areaTableLayout = new CssLayout();
            for(int j = 0; j < noOfTables; j++) {
                if (tablesList.get(j).getArea().equals(areas.get(i))) {
                    tablesList.get(j).setEnabled(false);
                    areaTableLayout.addComponent(tablesList.get(j));
                    tablesList.get(j).setStyleName(ValoTheme.CHECKBOX_LARGE);
                }
            }
            areaTableLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
            mainView.addComponent(areaTableLayout);
        }

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

        checkAvailabilityButton.addClickListener(e -> {
            String reservationTime = reservationStartingTimeHour.getValue().toString() + "." + reservationStartingTimeMin.getValue().toString();
            java.sql.Date date = new java.sql.Date(reservationDate.getValue().getTime());
            checkAvailability(date.toString(), reservationTime);
        });

        goBack.addClickListener(e -> {
            getUI().getNavigator().removeView("home");
            getUI().getNavigator().addView("home", new MainView());
            getUI().getNavigator().navigateTo("home");
        });

        logout.addClickListener(e -> {
            VaadinService.getCurrentRequest().getWrappedSession().invalidate();
            getUI().getPage().setLocation("/*");


            MyUI.getCurrent().getUI().getNavigator().removeView("login");
            MyUI.getCurrent().getUI().getNavigator().addView("login", new LoginView());
            MyUI.getCurrent().getUI().getNavigator().navigateTo("login");
        });

        reservationDate.setValue(new java.sql.Date(new java.util.Date().getTime()));
        reservationStartingTimeHour.setContainerDataSource(hourContainer);
        reservationStartingTimeHour.setWidth(screenWidth);
        reservationStartingTimeMin.setWidth(screenWidth);
        reservationStartingTimeMin.setContainerDataSource(minContainer);
        selectTimeDiv.addComponents(reservationStartingTimeHour,reservationStartingTimeMin);
        sideView.addComponents(insertLabel, reservationDate, selectTimeDiv);
        insertButtons.addComponents(checkAvailabilityButton);
        sideView.addComponent(insertButtons);
        mainView.setMargin(true);
        mainView.setSpacing(true);
        sideView.setMargin(true);
        sideView.setSpacing(true);


        sideView.addComponent(chosenTablesLabel);
        sideView.addComponent(noOfSeatsLabel);
        addComponents(mainView,sideView);
    }

    public void setupMoreComponents() {
        NativeSelect reservationEndingTime = new NativeSelect("Ending Time");
        TextField reservationName = new TextField("Name");
        TextField reservationNumber = new TextField("Number");
        TextField reservationNumberOFPeople = new TextField("Number of People");
        TextField reservationDescription = new TextField("Description");
        TextField reservationEmail = new TextField("Email");
        Button submitReservationButton = new Button("Submit Reservation");

        reservationEndingTime.setWidth(screenWidth);
        reservationName.setWidth(screenWidth);
        reservationNumber.setWidth(screenWidth);
        reservationNumberOFPeople.setWidth(screenWidth);
        reservationDescription.setWidth(screenWidth);
        reservationEmail.setWidth(screenWidth);
        submitReservationButton.setWidth(screenWidth);

        BeanItemContainer<String> hourContainer =
                new BeanItemContainer<>(String.class);

        for(int i = (Integer.valueOf(reservationStartingTimeHour.getValue().toString()) + 1); i < 24; i++) {
            hourContainer.addItem(String.valueOf(i));
        }

        reservationEndingTime.setContainerDataSource(hourContainer);

        sideView.addComponents(reservationEndingTime, reservationName, reservationNumber,
                reservationEmail, reservationNumberOFPeople, reservationDescription);
        sideView.addComponent(submitReservationButton);

        submitReservationButton.addClickListener(e -> {
            //ReservationsDAO reservationsDAO = new ReservationsDAO();
            for(int i = 0; i< tableIDs.size() ; i++) {
                Reservations reservation = new Reservations();
                reservation.setTableID(tableIDs.get(i));
                reservation.setDate(new java.sql.Date(reservationDate.getValue().getTime()));
                reservation.setDescription(reservationDescription.getValue());
                reservation.setEmail(reservationEmail.getValue());
                reservation.setName(reservationName.getValue());
                reservation.setNumber(Integer.valueOf(reservationNumber.getValue()));
                reservation.setNumberOfPeople(Integer.valueOf(reservationNumberOFPeople.getValue()));
                reservation.setStartingTime(Float.valueOf(reservationStartingTimeHour.getValue().toString() + "." + reservationStartingTimeMin.getValue().toString()));
                reservation.setEndingTime(Float.valueOf(reservationEndingTime.getValue().toString()));
                dataController.addReservation(reservation);
            }
        });

    }


}
