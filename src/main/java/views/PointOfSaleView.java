package views;

import accessobjects.ProductDAO;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import entities.Product;

import java.util.ArrayList;

/**
 * Created by damo k on 08/03/2017.
 */
public class PointOfSaleView extends VerticalLayout implements View{
    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();

    ProductDAO productDAO = new ProductDAO();
    ArrayList<Product> products = productDAO.getAllProducts();
    String screenWidth = "100%";

    ArrayList<String> productTypes= productDAO.getTypes();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setupComponents();
        getUI().setContent(this);
    }

    public void setupComponents() {
        this.setWidth(screenWidth);
        mainView.setWidth("75%");
        sideView.setWidth("75%");
        Page.getCurrent().setTitle("Point of Sale");
        Label label = new Label("Point of Sale for " + VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"));
        label.setStyleName(ValoTheme.LABEL_H2);
        mainView.addComponent(label);

        for(int i = 0; i< productTypes.size(); i++) {
            Label productTypeLabel = new Label(productTypes.get(i));
            productTypeLabel.setStyleName(ValoTheme.LABEL_H4);
            mainView.addComponent(productTypeLabel);
            CssLayout productTypeLayout = new CssLayout();

            for(int j = 0; j < products.size(); j++) {
                if (products.get(j).getType().equals(productTypes.get(i))) {
                    products.get(j).setCaption(products.get(j).getName() + " $"+ products.get(j).getPrice());
                    productTypeLayout.addComponent(products.get(j));
                }
            }
            productTypeLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
            mainView.addComponent(productTypeLayout);
        }

        mainView.setMargin(true);
        mainView.setSpacing(true);
        sideView.setMargin(true);
        sideView.setSpacing(true);

        addComponents(mainView,sideView);

    }
}
