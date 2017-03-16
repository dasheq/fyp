package views;

import accessobjects.EmployeeDAO;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entities.Employee;


/**
 * Created by damo k on 14/02/2017.
 */
@DesignRoot
public class LoginView extends CssLayout implements View{
    CssLayout horizontalLayout = new CssLayout();
    VerticalLayout spacing = new VerticalLayout();
    public static final String NAME = "LoginView";
    Label title = new Label("Bar ERP");
    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    Button loginButton = new Button("Login");
    EmployeeDAO employeeDAO = new EmployeeDAO();
    private boolean correctDetails = false;

    public LoginView() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setupLayout();
        setupComponents();
        title.setStyleName(ValoTheme.LABEL_HUGE);
        username.setValue("dasheq");
        password.setValue("password");
        loginButton.addClickListener(e -> {
                correctDetails = employeeDAO.login(username.getValue(), password.getValue());
            if (correctDetails) {
                Notification.show("Welcome back " + username.getValue());
                getUI().getNavigator().navigateTo("home");

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
