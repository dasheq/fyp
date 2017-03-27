package entities;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by damo k on 01/02/2017.
 */
public class Transaction implements Serializable{
    private int saleID;
    private Timestamp timeOfSale;
    private String username;
    private String paymentMethod;
    private float totalPrice;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


}
