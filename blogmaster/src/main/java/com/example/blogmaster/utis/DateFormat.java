package com.example.blogmaster.utis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ivyevy
 * @version 1.0
 */
public class DateFormat {

    private DateFormat(){};

    private static volatile SimpleDateFormat dateFormat = null;

    public static String getDateFormatInstance(Long timeStamp) throws ParseException {
        if(dateFormat == null){
            synchronized (DateFormat.class){
                if(dateFormat == null){
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                }
            }
        }
        return dateFormat.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
    }
}
