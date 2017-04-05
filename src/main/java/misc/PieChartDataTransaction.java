package misc;

/**
 * Created by damo k on 24/03/2017.
 */
public class PieChartDataTransaction {

    private String username;
    private float totalSales;

    public PieChartDataTransaction(String username) {
        this.username = username;
        this.totalSales = 0;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
