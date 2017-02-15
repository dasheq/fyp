package views;

import accessobjects.EmployeeDAO;
import accessobjects.ShiftDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import entities.Employee;

import java.util.ArrayList;

/**
 * Created by damo k on 15/02/2017.
 */
public class WagesView extends VerticalLayout{
    private NativeSelect employeeSelect = new NativeSelect("Select Employee");
    private NativeSelect weekSelect = new NativeSelect("Select Calendar Week");
    private Label wagesTotalLabel = new Label();
    private Label wagesLabel = new Label("Calculate Wages");
    private String screenWidth = "100%";
    private Button calculateButton = new Button("Calculate");
    private float wagesTotal;

    ShiftDAO shiftDAO = new ShiftDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();

    public void run() {
        wagesLabel.setHeight("50");
        wagesLabel.setStyleName(ValoTheme.LABEL_H2);

        employeeSelect.setWidth(screenWidth);
        weekSelect.setWidth(screenWidth);

        BeanItemContainer<String> containerEmployees =
                new BeanItemContainer<>(String.class);
        ArrayList<Employee> employees = employeeDAO.getAllEmployees();

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

        addComponents(wagesLabel, employeeSelect, weekSelect, calculateButton);

        calculateButton.addClickListener(e -> {
           wagesTotal = shiftDAO.calculateWages(
                   employeeSelect.getValue().toString(), Integer.valueOf(weekSelect.getValue().toString()));

           wagesTotalLabel.setValue(String.valueOf(wagesTotal));
           addComponent(wagesTotalLabel);
        });
    }
}
