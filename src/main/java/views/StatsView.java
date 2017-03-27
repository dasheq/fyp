package views;

import accessobjects.TransactionDAO;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.NoChartTypeException;
import at.downdrown.vaadinaddons.highchartsapi.model.Axis;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.data.PieChartData;
import at.downdrown.vaadinaddons.highchartsapi.model.series.PieChartSeries;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.*;
import at.downdrown.vaadinaddons.highchartsapi.*;
import entities.PieChartDataTransaction;
import entities.Transaction;

import java.util.ArrayList;


/**
 * Created by damo k on 21/03/2017.
 */
public class StatsView extends VerticalLayout  {
    final VerticalLayout chartView = new VerticalLayout();
    HighChart pieChart;
    TransactionDAO transactionDAO = new TransactionDAO();
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayList<PieChartDataTransaction> usernames = new ArrayList<>();
    ArrayList<String> usernamesStrings = new ArrayList<>();

    CssLayout displayModeLayout = new CssLayout();
    NativeSelect displayMode = new NativeSelect("Display Mode");
    NativeSelect period = new NativeSelect("Period");
    NativeSelect options = new NativeSelect("Options");
    int filterType = 0;

    public void run() {
        BeanItemContainer<String> displayModeContainer = new BeanItemContainer<>(String.class);
        displayModeContainer.addItem("All Time");
        displayModeContainer.addItem("Custom");
        displayMode.setContainerDataSource(displayModeContainer);
        displayModeLayout.addComponents(displayMode);
        addComponent(displayModeLayout);
        displayMode.addValueChangeListener(e -> {
            if(displayMode.getValue().equals("Custom")) {
                displayModeLayout.addComponent(period);
            }
            else {
                queryDatabaseAll();
            }
        });

        BeanItemContainer<String> periodContainer = new BeanItemContainer<>(String.class);
        periodContainer.addItem("Month");
        periodContainer.addItem("Year");
        period.setContainerDataSource(periodContainer);

        BeanItemContainer<String> optionsContainer = new BeanItemContainer<>(String.class);
        period.addValueChangeListener(e -> {
            if(period.getValue().equals("Month")) {
                optionsContainer.removeAllItems();
                filterType = 1;
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                for(int i =0; i<months.length; i++) {
                    optionsContainer.addItem(months[i]);
                }
            }
            else {
                filterType = 2;
                optionsContainer.removeAllItems();
                optionsContainer.addItem("2017");
                optionsContainer.addItem("2018");
            }
            options.setContainerDataSource(optionsContainer);
            displayModeLayout.addComponent(options);
        });

        options.addValueChangeListener(e -> {
            int filter = 0;

            if(filterType == 1) {
                switch (options.getValue().toString()) {
                    case "January":
                        filter = 1;
                        break;
                    case "February":
                        filter = 2;
                        break;
                    case "March":
                        filter = 3;
                        break;
                    case "April":
                        filter = 4;
                        break;
                    case "May":
                        filter = 5;
                        break;
                    case "June":
                        filter = 6;
                        break;
                    case "July":
                        filter = 7;
                        break;
                    case "August":
                        filter = 8;
                        break;
                    case "September":
                        filter = 9;
                        break;
                    case "October":
                        filter = 10;
                        break;
                    case "November":
                        filter = 11;
                        break;
                    case "December":
                        filter = 12;
                        break;
                }
            }
            if(filterType == 2) {
                switch (options.getValue().toString()) {
                    case "2017":
                        filter = 2017;
                        break;
                    case "2018":
                        filter = 2018;
                        break;
                }
            }
            queryDatabaseFilter(filterType, filter);
        });



        setSpacing(true);
        setMargin(true);

    }

    public void queryDatabaseAll() {
        transactions = null;
        transactions = transactionDAO.getAllSales();
        showPieChart();
    }

    public void queryDatabaseFilter(int filterType, int filter) {
        transactions = null;
        transactions = transactionDAO.getSalesByFilter(filterType, filter);
        if(transactions.size() != 0) {
            showPieChart();
        }
        else {
            Notification.show("No Results Found");
        }
    }


    public void showPieChart() {
        usernamesStrings = null;
        usernamesStrings = transactionDAO.getAllUsernames();
        //<PieChartData> usernamesPC = new ArrayList<>();
        PieChartSeries pieRevenue = new PieChartSeries("Total Revenue in €");

        ChartConfiguration pieConfiguration = new ChartConfiguration();


        int noOfUsers = transactionDAO.getAllUsernames().size();
        for(int i=0; i<noOfUsers; i++) {
            usernames.add(new PieChartDataTransaction(usernamesStrings.get(i)));
        }

        for(int i =0; i< usernames.size(); i++) {
            for(int j =0 ; j<transactions.size(); j++) {
                if (usernames.get(i).getUsername().equals(transactions.get(j).getUsername())) {
                    usernames.get(i).setTotalSales(usernames.get(i).getTotalSales() + transactions.get(j).getTotalPrice());
                }
            }
            PieChartData pieChartData = new PieChartData(usernames.get(i).getUsername(), usernames.get(i).getTotalSales());
            pieRevenue.getData().add(pieChartData);
        }
        pieConfiguration.setTitle("Total Value of Sales By Employee");
        pieConfiguration.setChartType(ChartType.PIE);
        pieConfiguration.setBackgroundColor(new Color(Color.HSLtoRGB(210, 0, 96)));

        pieConfiguration.getSeriesList().add(pieRevenue);

        try
        {
            pieChart = HighChartFactory.renderChart(pieConfiguration);
            pieChart.setHeight(80, Unit.PERCENTAGE);
            pieChart.setWidth(80, Unit.PERCENTAGE);
            System.out.println("PieChart Script : " + pieConfiguration.getHighChartValue());
            chartView.removeAllComponents();
            chartView.addComponent(pieChart);
            addComponent(chartView);
            chartView.setComponentAlignment(pieChart, Alignment.MIDDLE_CENTER);
        } catch (NoChartTypeException e) {
            e.printStackTrace();
        }
        catch (HighChartsException e) {
            e.printStackTrace();
        }
    }



}

