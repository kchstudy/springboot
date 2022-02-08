package blog.benggri.springboot.comm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static final int nextDay = 1;

    public static String getDateForPattern(String pattern) {        
        return getDateForPattern(new Date(), pattern);
    }

    public static String getDateForPattern(Date date, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    public static Date getDateForCalc(int calcType, Date day, int incDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day);
        calendar.add(calcType, incDay);
        return calendar.getTime();
    }

    public static String getSimpleDate() {
        return getDateForPattern("yyyyMMdd");
    }

    public static String getSimpleTime() {
        return getDateForPattern("HH:mm:ss");
    }

    public static String getFullTime() {
        return getDateForPattern("yyyyMMddHHmmss");
    }

    public static String AddDate(String strDate, int day) throws ParseException { 
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd"); 
        Calendar cal = Calendar.getInstance(); 
        Date dt = dtFormat.parse(strDate); 
        cal.setTime(dt); 

        cal.add(Calendar.DATE, day); 

        return dtFormat.format(cal.getTime()); 
    }

    public static boolean isHoliday(String date) {
        try {
            return isHolidayAlternate(date) || isWeekend(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //토요일, 일요일
    private static boolean isWeekend(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    //대체공휴일
    private static boolean isHolidayAlternate(String date) {
        
        String[] altHoliday = new String[] {"20210816", "20211004", "20211011"}; 
        
        return Arrays.asList(altHoliday).contains(date);
    }

    //적용일자 = 업로드 날짜 + 2영업일
    public static String getApplyDt() throws ParseException {
        String today = DateUtil.getSimpleDate();
        int workDay = 0;        
        int cnt = 0;
        String nexter = "";
        do {
            if(cnt > 0) {
                today = DateUtil.AddDate(today, nextDay);
            }           
            nexter = DateUtil.AddDate(today, nextDay);              
            workDay = getWorking(nexter, workDay);
            cnt ++;
            
        }while(workDay != 2);
        
        return nexter;
    }

    public static int getWorking(String starter, int workDay){  
        if(!isHoliday(starter)) {
            workDay ++;         
        }
        return workDay;
    }

    public static Date toDate(String strDate, String strDatePattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(strDatePattern);
        Date date = new Date(sdf.parse(strDate).getTime());
        return date;
    }
}
