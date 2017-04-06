package misc;


/**
 * Created by damo k on 22/02/2017.
 */
public class DateConvertor {
    private String date;
    private int week;

    private int extraDays = 0;
    private int daysUpdated = 0;
    private int day;
    private int month;


    public DateConvertor(String date) {
        this.date = date;
        this.day = Integer.valueOf(date.substring(8, 10));
        this.month = Integer.valueOf(date.substring(5, 7));
    }

    public int getWeekFromDate() {
        switch(this.month) {
            case 1:
                            /* No extra days in January */
                week = (day % 7) + 1;
                break;
            case 2:
                            /* 31 extra days for January */
                extraDays = 31;
               // week = ((extraDays + day) /7) + 1;
                break;
            case 3:
                            /* 31 + 28 extra days for Jan + Feb */
                extraDays = 31 + 28;
              //  week = ((extraDays + day) / 7) + 1;
                break;
            case 4:
                            /* 31 + 28 + 31 extra days for Jan + Feb + Mar */
                extraDays = 31 + 28 + 31;
              //  week = ((extraDays + day) / 7) + 1;
                break;
            case 5:
                            /* Jan + Feb + Mar + Apr */
                extraDays = 31 + 28 + 28 + 31;
              //  week = ((extraDays + day) / 7) + 1;
                break;
            case 6:
                            /* Jan + Feb + Mar + Apr + May */
                extraDays = 31 + 28 + 31 + 30 + 31;
             //   week = ((extraDays + day) / 7) + 1;
                break;
            case 7:
                            /* Jan + Feb + Mar + Apr + May + Jun */
                extraDays = 31 + 28 + 31 + 30 + 31 + 30;
              //  week = ((extraDays + day) / 7) + 1;
                break;
            case 8:
                            /* Jan + Feb + Mar + Apr + May + Jun + Jul */
                extraDays = 31 + 28 + 31 + 30 + 31 + 30 + 31;
              //  week = ((extraDays + day) / 7) + 1;
                break;
            case 9:
                            /* Jan + Feb + Mar + Apr + May + Jun + Jul + Aug */
                extraDays = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31;
             //   week = ((extraDays + day) / 7) + 1;
                break;
            case 10:
                            /* Jan + Feb + Mar + Apr + May + Jun + Jul + Aug + Sep */
                extraDays = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30;
             //   week = ((extraDays + day) / 7) + 1;
                break;
            case 11:
                            /* Jan + Feb + Mar + Apr + May + Jun + Jul + Aug + Sep + Oct */
                extraDays = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31;
             //   week = ((extraDays + day) / 7) + 1;
                break;
            case 12:
                            /* Jan + Feb + Mar + Apr + May + Jun + Jul + Aug + Sep + Oct + Nov*/
                extraDays = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30;
            //    week = ((extraDays + day) / 7) + 1;
                break;
        }

        daysUpdated = extraDays + day;
        week = (daysUpdated / 7) + 1;

        return week;
    }

    public int getDayFromDate() {
        int seven = 7;
        //daysUpdated = extra days from previous months + actual day of the month
        for(int i = 1; i<52; i++) {
            if(daysUpdated >= ((seven * i) + 1) && daysUpdated <= (seven * (i +1)))
                daysUpdated = daysUpdated - (seven * i);
        }

        return daysUpdated;
    }

}
