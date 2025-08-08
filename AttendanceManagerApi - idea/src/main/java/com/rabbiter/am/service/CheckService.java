package com.rabbiter.am.service;

import com.rabbiter.am.dao.CheckDao;
import com.rabbiter.am.entity.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CheckService {

    @Autowired
    private CheckDao checkDao;

    @Value("${attendance.on-time}")
    private String onTime;

    @Value("${attendance.off-time}")
    private String offTime;


    public int deleteById(String id) {
        return checkDao.deleteById(id);
    }

    public int insert(Check check) {
        return checkDao.insert(check);
    }

    public int checkOn(Check check) throws ParseException {

        if(ObjectUtils.isEmpty(check.getEmployeeID())) {
            return 0;
        }
        check.setId(UUID.randomUUID().toString());
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 =new SimpleDateFormat("HH:mm:ss");
        String date1 = sdf1.format(check.getCheckOnTime());
        check.setDate(date1);
        String date2 = sdf2.format(check.getCheckOnTime());
        Date time = sdf2.parse(date2);
        String time1 = onTime + ":00";
        Date time2;
        try {
            time2 = sdf2.parse(time1);
        } catch (ParseException e) {
            e.printStackTrace();
            time2 = sdf2.parse("08:30:00");
        }
        if(time.before(time2)){
            check.setCheckOnStatus("正常");
        }else {
            check.setCheckOnStatus("迟到");
        }
        checkDao.insert(check);
        return 0;
    }

    public int checkOff(Check check) throws ParseException {
        if(ObjectUtils.isEmpty(check.getEmployeeID())) {
            return 0;
        }
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 =new SimpleDateFormat("HH:mm:ss");
        String date1 = sdf1.format(check.getCheckOffTime());
        Check check1 = new Check();
        check1.setEmployeeID(check.getEmployeeID());
        check1.setDate(date1);
        Check check2 = findByNumberAndDate(check1);
        String date2 = sdf2.format(check.getCheckOffTime());
        Date time = sdf2.parse(date2);
        String time1 = offTime + ":00";
        Date time2 = null;
        try {
            time2 = sdf2.parse(time1);
        } catch (ParseException e) {
            time2 = sdf2.parse("17:30:00");
        }
        if(time.after(time2)){
            check2.setCheckOffStatus("正常");
        }else {
            check2.setCheckOffStatus("早退");
        }
        check2.setCheckOffTime(check.getCheckOffTime());
        checkDao.update(check2);
        return 0;
    }

    public int getCheckOn(Check check){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(check.getCheckOnTime());
        check.setDate(date);
        Check check1 = findByNumberAndDate(check);
        if(check1 != null){
            if(check1.getCheckOnTime() != null){
                return 1;
            }else if(check1.getRemarks() != null){
                return 2;
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    public int getCheckOff(Check check){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(check.getCheckOffTime());
        check.setDate(date);
        Check check1 = findByNumberAndDate(check);
        if(check1 != null){
            if(check1.getCheckOffTime() != null){
                return 1;
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    public Check selectById(String id) {
        return checkDao.selectById(id);
    }

    public int update(Check check) {
        return checkDao.update(check);
    }

    public List<Check> findByNumber(String number) {
        return checkDao.findByNumber(number);
    }

    public List<Check> findByMonth(String month) {
        return checkDao.findByMonth(month);
    }

    public List<Check> findByNumberAndMonth(Check check) {
        return checkDao.findByNumberAndMonth(check);
    }

    public Check findByNumberAndDate(Check check) {
        return checkDao.findByNumberAndDate(check);
    }

    public int getWorkDay(String date) {
        String y = date.substring(0,4);
        String m = date.substring(5,7);
        int year = Integer.parseInt(y);
        int month = Integer.parseInt(m);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        // 月份是从0开始计算，所以需要减1
        c.set(Calendar.MONTH, month - 1);

        // 当月最后一天的日期
        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 开始日期为1号
        int start = 1;
        // 计数
        int count = 0;
        while (start <= max) {
            c.set(Calendar.DAY_OF_MONTH, start);
            if (isWorkDay(c)) {
                count++;
            }
            start++;
        }
        return count;
    }

    public boolean isWorkDay(Calendar c) {
        // 获取星期,1~7,其中1代表星期日，2代表星期一 ... 7代表星期六
        int week = c.get(Calendar.DAY_OF_WEEK);
        // 不是周六和周日的都认为是工作日
        return week != Calendar.SUNDAY && week != Calendar.SATURDAY;
    }

    public List<Check> getCheckDay(Check check){
        check.setDate(check.getDate().substring(0,7));
        return checkDao.getCheckDay(check);
    }

    public List<Check> getLateDay(Check check) {
        check.setDate(check.getDate().substring(0,7));
        return checkDao.getLateDay(check);
    }

    public List<Check> getLeaveEarlyDay(Check check) {
        check.setDate(check.getDate().substring(0,7));
        return checkDao.getLeaveEarlyDay(check);
    }

    public int getCheckDayNumber(Check check){
        List<Check> checkList = getCheckDay(check);
        return checkList.size();
    }

    public int getLateDayNumber(Check check){
        List<Check> checkList = getLateDay(check);
        return checkList.size();
    }

    public int getLeaveEarlyDayNumber(Check check){
        List<Check> checkList = getLeaveEarlyDay(check);
        return checkList.size();
    }

    public List<Check> getLeaveDay(Check check){
        return checkDao.getLeaveDay(check);
    }

    public int getLeaveDayNumber(Check check,String leaveType){
        int days = 0;
        List<Check> checkList = getLeaveDay(check);
        for(Check item : checkList){
            if(item.getRemarks() != null){
                if(leaveType.equals(item.getRemarks())){
                    days = item.getDays();
                }
            }
        }
        return days;
    }

    public int getAllLeaveDay(Check check){
        int days = 0;
        List<Check> checkList = getLeaveDay(check);
        for(Check item : checkList){
            if(item.getRemarks() != null){
                days += item.getDays();
            }
        }
        return days;
    }
}
