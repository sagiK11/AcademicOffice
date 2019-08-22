package AcademicOffice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatHandler {
	private String dateAsString;
	private int dayAsInt, monthAsInt, yearAsInt;


	DateFormatHandler(String string) {
		final int beginDayIndex = 0, endDayIndex = 2, beginMonthIndex = 3, endMonthIndex = 5,
			beginYearIndex = 6, endYearIndex = 10;

		dateAsString = string;
		dayAsInt = Integer.parseInt( dateAsString.substring( beginDayIndex , endDayIndex ) );
		monthAsInt = Integer.parseInt( dateAsString.substring( beginMonthIndex , endMonthIndex ) );
		yearAsInt = Integer.parseInt( dateAsString.substring( beginYearIndex , endYearIndex ) );
	}

	static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat( );
		Date date = new Date( System.currentTimeMillis( ) );

		String currentDate = dateFormat.format( date );
		currentDate = currentDate.replace( "." , "/" );

		int endIndex = currentDate.indexOf( "," );
		currentDate = currentDate.substring( 0 , endIndex );

		//padding with leading zeros
		String[] tmp = currentDate.split( "/" );
		final int SEPARATORS_NAM = 2;

		for ( int i = 0 ; i < SEPARATORS_NAM ; i++ ) {
			if( tmp[ i ].length( ) == 1 )
				tmp[ i ] = "0" + tmp[ i ];
		}
		String day = tmp[ 0 ], month = tmp[ 1 ], year = tmp[ 2 ];
		return day + "/" + month + "/" + year;
	}

	static String convertToLocalDateFormat(String date) {
		String[] tmp = date.split( "-" );
		final int dayIndex = 2, monthIndex = 1, yearIndex = 0;
		return tmp[ dayIndex ] + "/" + tmp[ monthIndex ] + "/" + tmp[ yearIndex ];
	}

	boolean isAfter(DateFormatHandler other) {
		if( yearAsInt > other.yearAsInt )
			return true;
		else if( yearAsInt >= other.yearAsInt && monthAsInt > other.monthAsInt )
			return true;
		else
			return yearAsInt >= other.yearAsInt && monthAsInt >= other.monthAsInt
				&& dayAsInt > other.dayAsInt;
	}

	boolean isBefore(DateFormatHandler other) {
		if( yearAsInt < other.yearAsInt )
			return true;
		else if( yearAsInt <= other.yearAsInt && monthAsInt < other.monthAsInt )
			return true;
		else
			return yearAsInt <= other.yearAsInt && monthAsInt <= other.monthAsInt
				&& dayAsInt < other.dayAsInt;
	}

	String getDateAsString() {
		return dateAsString;
	}


}
