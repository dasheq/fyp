package entities;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by damo k on 01/02/2017.
 */
public class Invoice implements Serializable {
    private int refNumber;
    private Date date;
    private float totalValue;
    private int supplierID;

    public int getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(int refNumber) {
        this.refNumber = refNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }




}
