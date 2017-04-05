package entities;

import com.vaadin.ui.Button;

import java.io.Serializable;

/**
 * Created by damo k on 01/02/2017.
 */
public class ProductButton extends Button implements Serializable{
    private int productID;
    private String name;
    private float price;
    private String type;
    private int quantity;
    private int refNumber;
    private boolean clicked;

    public ProductButton() {
        clicked = false;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(int refNumber) {
        this.refNumber = refNumber;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isClicked() { return clicked; }

    public void setClicked(boolean clicked) {this.clicked = clicked;}

}
