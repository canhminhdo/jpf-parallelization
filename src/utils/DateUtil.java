package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date Utility
 * 
 * @author OgataLab
 *
 */
public class DateUtil {
	
	/**
	 * Get time from now
	 * 
	 * @return {@link Long}
	 */
	public static long getTime() {
		Date date= new Date();
		return date.getTime();
	}
	
	/**
	 * Get current date time as string
	 * 
	 * @return {@link String}
	 */
	public static String getDateTimeString() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mm a");
		Date date = new Date();
		return formatter.format(date);
	}
}
