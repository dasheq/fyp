package views;

import accessobjects.ShiftDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.Shift;
import misc.MyUI;

import javax.swing.text.html.CSS;
import java.util.ArrayList;

/**
 * Created by damo k on 15/03/2017.
 */
public class RosterView extends CssLayout implements View {
    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();
    final CssLayout navLayout = new CssLayout();
    final CssLayout mainInsideView = new CssLayout();
    private int accessLevel;
    private String employeeName;

    ShiftDAO shiftDAO = new ShiftDAO();
    ArrayList<Shift> shifts;
    ArrayList<String> employeesNames;
    String screenWidth = "100%";

    Button checkRosterButton = new Button("View Roster");
    Button goBack = new Button("Back");
    Button logout = new Button("Logout");
    NativeSelect employees = new NativeSelect("Employee");
    NativeSelect weeks = new NativeSelect("Week");


    public RosterView(int accessLevel) {
        this.accessLevel = accessLevel;
    }

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
        label.setStyleName(ValoTheme.LABEL_H2);
        navLayout.addComponents(goBack, logout);
        mainView.addComponents(navLayout, label);

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
        if(accessLevel == 1){
            sideView.addComponent(employees);
        }
        else {
            employeeName = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user").toString();
        }
        sideView.addComponents(weeks, checkRosterButton);
        sideView.setSpacing(true);
        sideView.setMargin(true);
        mainView.setSpacing(true);
        mainView.setMargin(true);

        addComponents(mainView,sideView);

        checkRosterButton.addClickListener(e -> displayRoster());

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
    }

    public void displayRoster() {
        if(accessLevel == 1) {
            employeeName = employees.getValue().toString();
        }
        shifts = shiftDAO.getShiftsByNameAndWeek(employeeName, Integer.valueOf(weeks.getValue().toString()));
        ArrayList<Label> dayLabels = new ArrayList<>();
        ArrayList<Label> shiftLabels = new ArrayList<>();
        Label rosterLabel = new Label();
        rosterLabel.setValue("Employee: "+employeeName + " Week: "+ weeks.getValue().toString());
        mainInsideView.addComponent(rosterLabel);


        dayLabels.add(0, new Label("Sunday"));
        dayLabels.add(1, new Label("Monday"));
        dayLabels.add(2, new Label("Tuesday"));
        dayLabels.add(3, new Label("Wednesday"));
        dayLabels.add(4, new Label("Thursday"));
        dayLabels.add(5, new Label("Friday"));
        dayLabels.add(6, new Label("Saturday"));

        for(int i = 0; i<7; i++) {
            Label shiftLabel = new Label("n/a");
            shiftLabels.add(shiftLabel);
            dayLabels.get(i).setStyleName(ValoTheme.LABEL_BOLD);
        }


            for(int i =0; i<shifts.size(); i++) {
                switch (shifts.get(i).getDay()) {
                    case 1:
                        shiftLabels.set(0, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                    case 2:
                        shiftLabels.set(1, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                    case 3:
                        shiftLabels.set(2, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                    case 4:
                        shiftLabels.set(3, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                    case 5:
                        shiftLabels.set(4, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                    case 6:
                        shiftLabels.set(5, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                    case 7:
                        shiftLabels.set(6, new Label(shifts.get(i).getStartTime() + "0 - " + shifts.get(i).getEndTime() + "0"));
                        break;
                }
            }


        for(int i = 0; i< 7; i++ ){
            VerticalLayout dayLayout = new VerticalLayout();
            dayLayout.setWidth("120");
            dayLayout.addComponent(dayLabels.get(i));
            dayLayout.addComponent(shiftLabels.get(i));
            mainInsideView.addComponent(dayLayout);
        }
        mainView.addComponent(mainInsideView);

    }
}
