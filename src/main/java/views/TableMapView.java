package views;

import accessobjects.TablesDAO;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import entities.Tables;

import java.util.ArrayList;

/**
 * Created by damo k on 06/03/2017.
 */
public class TableMapView extends VerticalLayout {
    TablesDAO tablesDAO = new TablesDAO();
    ArrayList<Tables> tablesList = tablesDAO.getAllTables();
    int noOfTables = tablesList.size();
    int noOfAreas = tablesDAO.getNoOfAreas();
    ArrayList<String> areas = tablesDAO.getAreas();

    public void run() {
        Label label = new Label("Tables Map");
        addComponent(label);
        for(int i = 0; i< noOfAreas; i++) {
            Label areaLabel = new Label(areas.get(i));
            addComponent(areaLabel);
            System.out.println("noOfTables" + noOfTables + " noOfAreas " + noOfAreas);
            CssLayout areaTableLayout = new CssLayout();
            for(int j = 0; j < noOfTables; i++) {
                System.out.println(areas.get(i));
                System.out.println("Table: " + tablesList.get(j).getTableID() + " Area: " + tablesList.get(j).getArea());
                /*
                if (tablesList.get(j).getArea().equals(areas.get(i))) {
                    areaTableLayout.addComponent(tablesList.get(j));
                }*/
            }
            addComponent(areaTableLayout);
        }
    }
/*
    public void checkAvailability(String reservationDate, String reservationTime) {
        for (int i = 0; i < noOfTables; i++) {
            if (String.valueOf(tablesList.get(i).getReservationDate()).equals(reservationDate)) {
                if ((tablesList.get(i).getReservationTime() - Float.valueOf(reservationTime)) <= -2 &&
                        (tablesList.get(i).getReservationTime() - Float.valueOf(reservationTime)) >= 2) {
                    System.out.println((tablesList.get(i).getReservationTime() - Float.valueOf(reservationTime)));
                    //tablesList.get(i).setStyleName(ValoTheme.BUTTON_PRIMARY);
                    System.out.println("howya");
                    tableMap.removeComponent(tablesList.get(i));
                }

            }
        }
    }
*/
}
