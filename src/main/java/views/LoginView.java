package views;

import accessobjects.EmployeeDAO;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.Employee;
import controllers.DataController;


/**
 * Created by damo k on 14/02/2017.
 */
@DesignRoot
public class LoginView extends CssLayout implements View{
    DataController dataController = new DataController();

    CssLayout horizontalLayout = new CssLayout();
    VerticalLayout spacing = new VerticalLayout();

    Label title = new Label("Bar ERP");
    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    Button loginButton = new Button("Login");
    Employee employeeDetails;

    public LoginView() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //Setup the layout of the view and its components
        setupLayout();
        setupComponents();
        title.setStyleName(ValoTheme.LABEL_HUGE);
        loginButton.addClickListener((Button.ClickEvent e) -> {

            //Login authentication and session attributes
                employeeDetails = dataController.login(username.getValue(), password.getValue());
            if (employeeDetails != null) {
                Notification.show("Welcome back " + username.getValue());
                VaadinService.getCurrentRequest().getWrappedSession()
                        .setAttribute("user", employeeDetails.getUsername());
                VaadinService.getCurrentRequest().getWrappedSession()
                        .setAttribute("access", employeeDetails.getAccessLevel());
                getUI().getNavigator().navigateTo("home");
            }
            else {
                Notification.show("Incorrect Login Details");
            }
        });



     }

    public void setupComponents() {
        username.setWidth("40%");
        password.setWidth("40%");
        loginButton.setWidth("40%");
        loginButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        username.setRequired(true);
        password.setRequired(true);
    }

    public void setupLayout() {
        Page.getCurrent().setTitle("Login Page");
        horizontalLayout.setWidth("40%");
        spacing.setWidth("40%");
        setStyleName(ValoTheme.LAYOUT_WELL);
        //super.setHeight("100%");
        super.setWidth("100%");
        spacing.setSpacing(true);
        spacing.setMargin(true);
        spacing.addComponents(title, username, password, loginButton);
        addComponents(horizontalLayout, spacing);
    }




}
