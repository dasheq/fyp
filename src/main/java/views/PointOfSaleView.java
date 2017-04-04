package views;

import accessobjects.ProductDAO;
import accessobjects.TransactionDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import controllers.DataController;
import entities.Product;
import entities.Transaction;
import misc.MyUI;

import java.util.ArrayList;

/**
 * Created by damo k on 08/03/2017.
 */
public class PointOfSaleView extends CssLayout implements View{
    final VerticalLayout mainView = new VerticalLayout();
    final VerticalLayout sideView = new VerticalLayout();
    final CssLayout navLayout = new CssLayout();

    DataController dataController = new DataController();

    //ProductDAO productDAO = new ProductDAO();
    ArrayList<Product> products = dataController.getAllProducts();
    ArrayList<Product> saleProducts = new ArrayList<>();
    String screenWidth = "100%";
    float totalPrice = 0;
    Label totalPriceLabel = new Label("Total Price: " + totalPrice);
    NativeSelect paymentMethod = new NativeSelect("Payment Method");
    TextField subTotal = new TextField("Subtotal");
    Label change = new Label("Change");
    Button completeSaleButton = new Button("Complete Transaction");
    Button goBack = new Button("Back");
    Button logout = new Button("Logout");
    boolean showSaleComponents = false;

    Button showProductsList = new Button("Show products list");

    ArrayList<String> productTypes= dataController.getTypes();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setupComponents();
        getUI().setContent(this);
        Button.ClickListener checkIfPressed = (Button.ClickListener) event -> {
           // saleProducts.clear();
            if (!showSaleComponents){
                showSaleComponents = true;
                showSaleComponents();
            }

            updateSaleList((Product) event.getButton());
        };


        for(int i = 0; i < products.size(); i++ ) {
            products.get(i).addClickListener(checkIfPressed);
        }

    }

    public void showSaleComponents() {
        sideView.addComponents(paymentMethod, subTotal, change, completeSaleButton, showProductsList);

        BeanItemContainer<String> paymentMethodContainer = new BeanItemContainer<>(String.class);
        paymentMethodContainer.addItem("Cash");
        paymentMethodContainer.addItem("Card");

        paymentMethod.setContainerDataSource(paymentMethodContainer);

        paymentMethod.addValueChangeListener(e -> {
            if(paymentMethod.getValue().equals("Card")) {
                change.setValue("Cashback");
            }
            else {
                change.setValue("Change");
            }
        });


        completeSaleButton.addClickListener(e -> {
            float changeAmount = Float.valueOf(subTotal.getValue()) - totalPrice;
            change.setValue(change.getValue() + ": " +String.valueOf(changeAmount));
            for(int i = 0; i < saleProducts.size(); i++){
                dataController.updateProductQuantity(saleProducts.get(i));
            }
            Notification.show(String.valueOf(new java.sql.Timestamp(System.currentTimeMillis())) + " " +change.getValue() + ": " + change.getValue());
            //TransactionDAO transactionDAO = new TransactionDAO();
            Transaction transaction = new Transaction();
            transaction.setUsername(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user").toString());
            transaction.setTotalPrice(totalPrice);
            transaction.setPaymentMethod(paymentMethod.getValue().toString());
            transaction.setTimeOfSale(new java.sql.Timestamp(System.currentTimeMillis()));
            dataController.addSale(transaction);
        });

        showProductsList.addClickListener(e -> {
            for(int i = 0; i < saleProducts.size(); i++) {
                System.out.println(i + " " + saleProducts.get(i).getName());
            }
        });
    }

    public void updateSaleList(Product product) {
        CheckBox saleItem = new CheckBox(product.getName() + " " + product.getPrice());
        saleItem.setValue(true);
        totalPrice += product.getPrice();
        totalPriceLabel.setValue("Total Price: " + totalPrice);
        saleProducts.add(product);
        System.out.println("Adding" + product.getName() +"\n");

        saleItem.addValueChangeListener(valueChangeEvent -> {
            if (saleItem.getValue()) {
                totalPrice += product.getPrice();
                saleProducts.add(product);
                System.out.println("Adding" + product.getName());
            }
            else if(!saleItem.getValue()){
                totalPrice -= product.getPrice();
                saleProducts.remove(product);
                System.out.println("Removing" + product.getName());

            }
            totalPriceLabel.setValue("Total Price: " + totalPrice);

        });
        sideView.addComponent(saleItem);
    }


    public void setupComponents() {
        this.setWidth(screenWidth);
        mainView.setWidth("75%");
        sideView.setWidth("25%");
        Page.getCurrent().setTitle("Point of Transaction");
        Label label = new Label("Point of Transaction for " + VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"));
        label.setStyleName(ValoTheme.LABEL_H2);
        navLayout.addComponents(goBack, logout);
        mainView.addComponents(navLayout, label);

        Label label2 = new Label("Transaction");
        label2.setStyleName(ValoTheme.LABEL_H2);
        sideView.addComponents(label2, totalPriceLabel);

        for(int i = 0; i< productTypes.size(); i++) {
            Label productTypeLabel = new Label(productTypes.get(i));
            productTypeLabel.setStyleName(ValoTheme.LABEL_H4);
            mainView.addComponent(productTypeLabel);
            CssLayout productTypeLayout = new CssLayout();

            for(int j = 0; j < products.size(); j++) {
                if (products.get(j).getType().equals(productTypes.get(i))) {
                    products.get(j).setCaption(products.get(j).getName() + " €"+ products.get(j).getPrice() + " " + products.get(j).getQuantity() + " left");
                    productTypeLayout.addComponent(products.get(j));
                }
            }
            productTypeLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
            mainView.addComponent(productTypeLayout);
        }

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

        mainView.setMargin(true);
        mainView.setSpacing(true);
        sideView.setMargin(true);
        sideView.setSpacing(true);

        addComponent(mainView);
        addComponent(sideView);

    }
}
