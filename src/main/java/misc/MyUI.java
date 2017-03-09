package misc;

import javax.servlet.annotation.WebServlet;

import accessobjects.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import views.LoginView;
import views.MainView;
import views.TableMapView;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */


@Theme("mytheme")
public class MyUI extends UI {
    Navigator navigator;
    LoginView loginView;
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this,this);
        final CssLayout mainWindow = new CssLayout();
        getNavigator().addView("login", new LoginView());
        getNavigator().addView("home", new MainView());
        getNavigator().addView("reservations", new TableMapView());
        getNavigator().navigateTo("login");
        mainWindow.setStyleName(ValoTheme.LAYOUT_WELL);






    }
    //List<Employee> employees = employeeDAO.getAllEmployees();


        @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
        }
}
