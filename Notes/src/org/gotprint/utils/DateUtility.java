package org.gotprint.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtility {
     /*
      * Returns currrent Date
      */
	public Date getCurrentDate() {
		LocalDateTime currentTime = LocalDateTime.now();
		Date todayDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
		return todayDate;
	}

}
