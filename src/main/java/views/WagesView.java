package views;

import accessobjects.EmployeeDAO;
import accessobjects.ShiftDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import controllers.DataController;
import entities.Employee;
import misc.MyUI;

import java.util.ArrayList;

/**
 * Created by damo k on 15/02/2017.
 */
public class WagesView extends VerticalLayout{
    private NativeSelect employeeSelect = new NativeSelect("Select Employee");
    private NativeSelect weekSelect = new NativeSelect("Select Calendar Week");
    private String employeeName;
    private Label wagesTotalLabel = new Label();
    private Label wagesLabel = new Label("Calculate Wages");
    private Label hoursLabel = new Label();
    private Label overtimeHoursLabel = new Label();
    private Label overtimeWagesLabel = new Label();
    private Label employeeNameLabel = new Label();
    private Label calendarWeekLabel = new Label();
    private String screenWidth = "100%";
    private Button calculateButton = new Button("Calculate");
    private float[] wagesDetails = new float[4];
    private int accessLevel;

    private VerticalLayout labelsLayout = new VerticalLayout();

    DataController dataController = new DataController();

    //ShiftDAO shiftDAO = new ShiftDAO();
    //EmployeeDAO employeeDAO = new EmployeeDAO();

    public WagesView(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void run() {
        wagesLabel.setHeight("50");
        wagesLabel.setStyleName(ValoTheme.LABEL_H2);
        wagesTotalLabel.setHeight("50");

        super.setSpacing(true);

        employeeSelect.setWidth(screenWidth);
        weekSelect.setWidth(screenWidth);

        BeanItemContainer<String> containerEmployees =
                new BeanItemContainer<>(String.class);
        ArrayList<Employee> employees = dataController.getAllEmployees();


        for (Employee employee : employees) {
            containerEmployees.addItem(employee.getUsername());
        }

        BeanItemContainer<String> containerWeeks =
                new BeanItemContainer<>(String.class);

        for (int i = 1 ; i < 53 ; i++ ) {
            containerWeeks.addItem(String.valueOf(i));
        }

        employeeSelect.setContainerDataSource(containerEmployees);
        weekSelect.setContainerDataSource(containerWeeks);

        addComponent(wagesLabel);

        if(accessLevel == 0) {
            employeeName = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user").toString();
        }
        else {
            addComponent(employeeSelect);
        }
        addComponents(weekSelect, calculateButton);

        calculateButton.addClickListener(e -> {
            if(accessLevel == 1) {
                employeeName = employeeSelect.getValue().toString();
            }
           wagesDetails = dataController.calculateWages(
                   employeeName, Integer.valueOf(weekSelect.getValue().toString()));
           updateWages(wagesDetails);
        });
    }

    public void updateWages(float[] wagesDetails) {
        if (!wagesTotalLabel.getValue().isEmpty()) {
            labelsLayout.removeAllComponents();
        }

        employeeNameLabel.setValue("Employee username: " + employeeName);
        calendarWeekLabel.setValue("Calendar week: " + weekSelect.getValue().toString());
        wagesTotalLabel.setValue("Total earnings: €"+ String.valueOf(wagesDetails[0]));
        hoursLabel.setValue("Hours worked: "+String.valueOf(wagesDetails[2]));
        labelsLayout.addComponents(employeeNameLabel, calendarWeekLabel, wagesTotalLabel, hoursLabel);
        if(wagesDetails[1] > 0) {
            overtimeWagesLabel.setValue("Overtime earnings: €" + String.valueOf(wagesDetails[1]));
            overtimeHoursLabel.setValue("Overtime hours worked: " + String.valueOf(wagesDetails[3]));
            labelsLayout.addComponents(overtimeWagesLabel, overtimeHoursLabel);
        }
        labelsLayout.setSpacing(false);
        addComponent(labelsLayout);
    }
}
