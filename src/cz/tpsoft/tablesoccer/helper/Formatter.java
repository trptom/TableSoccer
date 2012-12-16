/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.tpsoft.tablesoccer.helper;

import java.util.Calendar;

/**
 *
 * @author tomas.praslicak
 */
public class Formatter {
    public static String dateToStr(Calendar date) {
        if (date == null) {
            return null;
        }
        
        StringBuilder ret = new StringBuilder();
        ret.append(date.get(Calendar.YEAR) + 1900);
        ret.append(date.get(Calendar.MONTH) < 9 ? "0" : "").append(date.get(Calendar.MONTH) + 1);
        ret.append(date.get(Calendar.DATE) < 10 ? "0" : "").append(date.get(Calendar.DATE));
        ret.append(" ");
        ret.append(date.get(Calendar.HOUR_OF_DAY));
        ret.append(date.get(Calendar.MINUTE) < 10 ? "0" : "").append(date.get(Calendar.MINUTE));
        ret.append(date.get(Calendar.SECOND) < 10 ? "0" : "").append(date.get(Calendar.SECOND));
        
        return ret.toString();
    }
    
    public static Calendar strToDate(String str) {
        if (str == null) {
            return null;
        }
        
        String[] tmp = str.split(" ");
        
        Calendar ret = Calendar.getInstance();
        
        if (tmp.length > 0) {
            String[] date = tmp[0].split("-");
            ret.set(Calendar.YEAR, Integer.parseInt(date[0]));
            ret.set(Calendar.MONTH, Integer.parseInt(date[1]));
            ret.set(Calendar.DATE, Integer.parseInt(date[2]));
        }
        
        if (tmp.length > 1) {
            String[] time = tmp[0].split(":");
            ret.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            ret.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            if (time.length > 2) {
                ret.set(Calendar.SECOND, Integer.parseInt(time[2]));
            }
        }
        
        return ret;
    }
}
