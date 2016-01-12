package sklcc.ws.publicer;

/**
 * Created by sukai on 12/25/15.
 */
import sklcc.ws.util.TimeUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerManager {

    //时间间隔(一天)
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    private PublicerService publicerService;

    public TimerManager() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间小于当前的时间
        //此时要在第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            //date = TimeUtil.addDay(date, 1);
        }
        Timer timer = new Timer();
        this.publicerService = new PublicerService();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(this.publicerService, date, PERIOD_DAY);
    }

    // 增加或减少天数
    /*public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }*/

    public static void main(String[] args) {
        new TimerManager();
    }

}

