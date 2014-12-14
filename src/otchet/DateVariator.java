package otchet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateVariator {
    
    String date(String date) throws ParseException{
                SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy", Locale.ENGLISH);
                Date currentdate;
                currentdate=sdf.parse(date);
                SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
                return (sdf2.format(currentdate));            
   }
        String prevMonthLastDayData() throws ParseException  {
        String s = null;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        s = lastDateOfPreviousMonth.toString();
        return (DateVariator.this.date(s));
    }
        String prevPrevMonthLastDayData() throws ParseException  {
        String s = null;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -2);
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        s = lastDateOfPreviousMonth.toString();
        return (DateVariator.this.date(s));
    }        
    String currMonthLastDayData() throws ParseException {
        String s = DateVariator.this.date((new java.util.Date().toString()));
        return s;
    }
}