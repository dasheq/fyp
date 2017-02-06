package entities;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by damo k on 01/02/2017.
 */
public class Sale implements Serializable{
    private int saleID;
    private Timestamp timeOfSale;
    private int quantity;
    private int productID;
    private String username;

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public Timestamp getTimeOfSale() {
        return timeOfSale;
    }

    public void setTimeOfSale(Timestamp timeOfSale) {
        this.timeOfSale = timeOfSale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
