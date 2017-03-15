package views;

import accessobjects.ShiftDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.Shift;

import java.util.ArrayList;

/**
 * Created by damo k on 15/03/2017.
 */
public class RosterView extends CssLayout implements View {
    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();
    //final CssLayout mainInsideView = new CssLayout();

    ShiftDAO shiftDAO = new ShiftDAO();
    ArrayList<Shift> shifts;
    ArrayList<String> employeesNames;
    String screenWidth = "100%";

    Button checkRosterButton = new Button("View Roster");
    NativeSelect employees = new NativeSelect("Employee");
    NativeSelect weeks = new NativeSelect("Week");


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setupComponents();
        getUI().setContent(this);
    }

    public void setupComponents() {
        this.setWidth("100%");
        mainView.setWidth("75%");
        sideView.setWidth("25%");
        Page.getCurrent().setTitle("Roster");

        Label label = new Label("Roster");
        label.setStyleName(ValoTheme.LABEL_H3);
        mainView.addComponent(label);

        employees.setWidth(screenWidth);
        weeks.setWidth(screenWidth);

        BeanItemContainer<String> employeeContainer =
                new BeanItemContainer<>(String.class);

        BeanItemContainer<String> weeksContainer =
                new BeanItemContainer<>(String.class);

        employeesNames = shiftDAO.getNameFromUsername();

        for(int i =0 ;i< employeesNames.size(); i++) {
            employeeContainer.addItem(employeesNames.get(i));
        }

        employees.setContainerDataSource(employeeContainer);

        for(int i = 1; i < 53; i++ ) {
            weeksContainer.addItem(String.valueOf(i));
        }

        weeks.setContainerDataSource(weeksContainer);
        sideView.addComponents(employees, weeks, checkRosterButton);

        addComponents(mainView,sideView);

        checkRosterButton.addClickListener(e -> displayRoster());
    }

    public void displayRoster() {
        shifts = shiftDAO.getShiftsByNameAndWeek(employees.getValue().toString(), Integer.valueOf(weeks.getValue().toString()));
        ArrayList<Label> dayLabels = new ArrayList<>();
        ArrayList<Label> shiftLabels = new ArrayList<>();
        ArrayList<Integer> daysWorking = new ArrayList<>();

        for(int j = 0; j <shifts.size() ; j++) {
            daysWorking.add(shifts.get(j).getDay());
        }

        for(int i =0; i<7; i++) {
            Label dayLabel = new Label();
            Label shiftLabel = new Label("n/a");
            switch (i) {
                case 0:
                    dayLabel.setValue("Sunday");
                    if(daysWorking.contains(i)) {

                    break;
                case 1:
                    dayLabel.setValue("Monday");
                    break;
                case 2:
                    dayLabel.setValue("Tuesday");
                    break;
                case 3:
                    dayLabel.setValue("Wednesday");
                    break;
                case 4:
                    dayLabel.setValue("Thursday");
                    break;
                case 5:
                    dayLabel.setValue("Friday");
                    break;
                case 6:
                    dayLabel.setValue("Saturday");
                    break;
            }
            dayLabels.add(dayLabel);
        }



            Label shiftLabel = new Label("n/a");
            switch(shifts.get(j).getDay()) {
                case 1:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
                case 2:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
                case 3:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
                case 4:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
                case 5:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
                case 6:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
                case 7:
                    shiftLabel.setValue(shifts.get(j).getStartTime()+ " - " + shifts.get(j).getEndTime());
                    break;
            }

        for(int i = 0; i< 7; i++ ){
            VerticalLayout dayLayout = new VerticalLayout();
            //dayLayout.setWidth(String.valueOf(this.getWidth()/7));
            Label dayLabel = new Label();
            Label shiftLabel = new Label("n/a");



            }
            dayLayout.addComponent(dayLabel);
            //.setCaption(shifts.get(i).getStartTime() + "-" + shifts.get(i).getEndTime());
            dayLayout.addComponent(shiftLabel);
            mainView.addComponent(dayLayout);
        }

        //mainView.addComponent(mainInsideView);
    }
}
