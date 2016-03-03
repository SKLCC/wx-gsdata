package sklcc.ws.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sukai on 15/11/11.
 */
public class TimeUtil {

    private static Logger logger = LogManager.getLogger(TimeUtil.class.getSimpleName());

    /*
        返回startStr和endStr两者日期间的所有日期
     */
    public static List<Date> dateSplit(String startStr, String endStr) {
        List<Date> dateList = new ArrayList<Date>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startStr);
            Date endDate = sdf.parse(endStr);
            if (startDate.before(endDate)) {
                Long spi = endDate.getTime() - startDate.getTime();
                Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
                dateList.add(endDate);
                for (int i = 1; i <= step; i++) {
                    // 比上一天减一
                    dateList.add(new Date(dateList.get(i - 1).getTime() - (24 * 60 * 60 * 1000)));
                }
            } else if(startDate.equals(endDate)) {
                dateList.add(startDate);
            }
        } catch (Exception e) {
            logger.error("dateSplit Error." + e.getMessage());
        }
        return dateList;
    }

    public static int getCurWeekDayByStr(Date curday) {
        try {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(curday);
            return rightNow.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            logger.info("getCurWeekDayByStr Error: " + e.getMessage());
            return 1;
        }
    }

    /*
        返回两个日期间，所有的周六
     */
    public static List<Date> GetSaturDays(String startStr, String endStr) {
        List<Date> saturdays = new ArrayList<Date>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startStr);
            Date endDate = sdf.parse(endStr);
            if (startDate.before(endDate)) {
                Long spi = endDate.getTime() - startDate.getTime();
                Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
                Date firstSar = null;
                for (int i = 1; i <= step; i++) {
                    firstSar = new Date(startDate.getTime() + (24 * 60 * 60 * 1000 * i));
                    if (getCurWeekDayByStr(firstSar) == 7) {
                        break;
                    }
                }
                Date curSat = firstSar;
                saturdays.add(curSat);
                while (true) {
                    Date newSar = new Date(curSat.getTime() + (24 * 60 * 60 * 1000 * 7));
                    if (newSar.after(endDate)) {
                        break;
                    }
                    curSat = newSar;
                    saturdays.add(newSar);
                }
            }
        } catch (Exception e) {
            logger.error("dateSplit Error." + e.getMessage());
        }
        return saturdays;
    }

    public static Date getCurrentDate() {
        Date currentDate = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return currentDate;
    }

    // 增加或减少天数
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    /*
        给定周六, 返回这周所有日期
     */
    public static List<Date> getWeekDates(Date saturDay) {
        Date monDay = addDay(saturDay, -6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return dateSplit(sdf.format(monDay), sdf.format(saturDay));
    }

    public static void main(String[] args) throws Exception {

        /*
        List<Date> dates = dateSplit("2015-01-12", "2015-01-12");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<dates.size();i++) {
            System.out.println(sdf.format(dates.get(i)));
        }
        */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2015-02-21");
        System.out.println(getCurWeekDayByStr(date));
        /*List<Date> saturs = GetSaturDays("2015-01-01", "2015-02-28");
        System.out.println(saturs.size());
        for (int i=0;i<saturs.size();i++) {
            System.out.println(saturs.get(i));
        }
        */

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(addDay(getCurrentDate(), -1)));
        */

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2016-01-09");
        getWeekDates(date);
        */
    }
}

