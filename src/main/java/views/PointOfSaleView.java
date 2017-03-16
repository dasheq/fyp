package views;

import accessobjects.ProductDAO;
import com.vaadin.ui.VerticalLayout;
import entities.Product;

import java.util.ArrayList;

/**
 * Created by damo k on 08/03/2017.
 */
public class PointOfSaleView extends VerticalLayout{
    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();

    ProductDAO productDAO = new ProductDAO();
    ArrayList<Product> products = productDAO.getAllProducts();



}
