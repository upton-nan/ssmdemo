package com.app.utils.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: TimestampUtils
 * @Description: TimeStamp(时间戳)工具类，提供TimeStamp与String、Date的转换
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class TimestampUtils {

	/**
	 * 
	 * @Title: string2Timestamp
	 * @Description: String转换为TimeStamp
	 * @param value 待转换的String，格式必须为 yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错
	 * @return java.sql.Timestamp
	 */
    public static Timestamp string2Timestamp(String value){
        if(value == null && !"".equals(value.trim())){
            return null;
        }
    	Timestamp ts = new Timestamp(System.currentTimeMillis());
    	ts = Timestamp.valueOf(value);
    	return ts;
    }

    /**
     * 
     * @Title: timestamp2String
     * @Description: 将Timestamp 转换为String类型，format为null则使用默认格式 yyyy-MM-dd HH:mm:ss
     * @param value 待转换的Timestamp
     * @param format String的格式
     * @return java.lang.String
     */
    public static String timestamp2String(Timestamp value,String format){
    	if(null == value){
    		return "";
    	}
    	SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
    	
    	return sdf.format(value);
    }

    /**
     * 
     * @Title: date2Timestamp
     * @Description: Date转换为Timestamp
     * @param date 待转换的Date
     * @return java.sql.Timestamp
     */
    public static Timestamp date2Timestamp(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }

    
    /**
     * 
     * @Title: timestamp2Date
     * @Description: Timestamp转换为Date
     * @param time 待转换的Timestamp
     * @return java.util.Date
     */
    public static Date timestamp2Date(Timestamp time){
        return time == null ? null : time;
    }
}
