package misc;

import javax.servlet.annotation.WebServlet;

import accessobjects.*;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import views.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */

@PreserveOnRefresh
@Theme("mytheme")
public class MyUI extends UI {
    Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Label label = new Label("No");
        navigator = new Navigator(this,this);
        final CssLayout mainWindow = new CssLayout();
        mainWindow.addComponent(label);
        getNavigator().addView("login", new LoginView());
        getNavigator().addView("home", new MainView());
        getNavigator().addView("reservations", new TableMapView());
        getNavigator().addView("sale", new PointOfSaleView());
        getNavigator().navigateTo("login");
        mainWindow.setStyleName(ValoTheme.LAYOUT_WELL);

    }

        @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
        }
}
