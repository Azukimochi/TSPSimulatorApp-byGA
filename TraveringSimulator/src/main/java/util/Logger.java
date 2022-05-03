package util;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

	static boolean logging = false;
	public static void isEnabled(boolean b) {
		logging = b;
	}
	public static void Log(Object str) {

		if(logging) {
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
			System.out.println(sdf.format(cl.getTime()) + str);
		}
	}

}
