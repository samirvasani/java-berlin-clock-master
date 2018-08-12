package com.ubs.opsit.interviews;

import java.util.stream.Stream;

public class TimeConverterImplementation implements TimeConverter{

    private static String TOP_ROW1_PATTERN = "OOOO";
    private static String TOP_ROW2_PATTERN = "OOOO";
    private static String BOTTOM_ROW1_PATTERN = "OOOOOOOOOOO";
    private static String BOTTOM_ROW2_PATTERN = "OOOO";
    private static String MINUTE = "minute";
    private static String HOUR = "hour";

    private static char RED_LAMP = 'R';
    private static char YELLOW_LAMP = 'Y';
    private static char OFF = 'O';

    @Override
    public String convertTime(String aTime) {
	
	if(aTime == null) throw new IllegalArgumentException("No time provided error.");
	
	
	
	int[] parts = Stream.of(aTime.split(":")).mapToInt(Integer::parseInt).toArray();
	
	if(parts.length != 3) throw new IllegalArgumentException("Invalid time format error.");
	
	if (parts[0] < 0 || parts[0] > 23) throw new IllegalArgumentException("Provided hour does not come within the range.");
        if (parts[1] < 0 || parts[1] > 59) throw new IllegalArgumentException("Provided minute does not come within the range.");
        if (parts[2] < 0 || parts[2] > 59) throw new IllegalArgumentException("Provided second does not come within the range.");
	
	String clock = getSeconds(parts[2]).concat("\r\n").concat(getHour(parts[0])).concat("\r\n").concat(getMinute(parts[1]));
	return clock;
    }
    
    /**
     * THis method indicates blinking of lamp for the provided hour
     * @param minute  a part of time
     * @return String
     */
    protected String getMinute(Integer minute) {
	return getLampStatus(minute, MINUTE);
    }
    
    /**
     * THis method indicates blinking of lamp for the provided hour
     * @param hour minute  a part of time
     * @return String
     */
    protected String getHour(Integer hour) {
	return getLampStatus(hour,HOUR);
    }
    
    /**
     * THis method indicates blinking of lamp for the provided second
     * @param second minute  a part of time
     * @return String
     */
    protected  String getSeconds(Integer second) {
	char firstRow ;
	if(second % 2 == 0) {
	    firstRow = YELLOW_LAMP;
	}else {
	    firstRow = OFF;
	}
	return Character.toString(firstRow);
    }
    
    /**
     * Based on provided input this method forms the pattern
     * @param time it could be either hour or minute
     * @param timeComponent String format of minute or hour
     * @return String
     */
    protected String getLampStatus(Integer time ,String timeComponent) {
	
	int row1 = time / 5;
	int row2 = time % 5;
	
	char[] row1PatternChars = null ;
	char[] row2PatternChars = null ; 
	boolean isTimeComponentMinute = false;

	if(MINUTE.equals(timeComponent)) {
	    row1PatternChars = BOTTOM_ROW1_PATTERN.toCharArray();
	    row2PatternChars = BOTTOM_ROW2_PATTERN.toCharArray();
	    isTimeComponentMinute = true;
	}
	else if(HOUR.equals(timeComponent)) {
	    row1PatternChars = TOP_ROW1_PATTERN.toCharArray();
	    row2PatternChars = TOP_ROW2_PATTERN.toCharArray();
	}
	
	for(int i = 0 ; i< row1 ; i++ ) {

	    if(isTimeComponentMinute) {
		if(2 == i || 5 == i || 8 == i ) {
		    row1PatternChars[i] = RED_LAMP;
		}else {
		    row1PatternChars[i] = YELLOW_LAMP;
		}
	    }else {
		row1PatternChars[i] = RED_LAMP;
	    }
	}
	
	for(int j = 0 ; j< row2 ; j++ ) {
	    if(isTimeComponentMinute) {
		row2PatternChars[j] = YELLOW_LAMP;
	    }else {
		row2PatternChars[j] = RED_LAMP ;
	    }
	}
	return String.valueOf(row1PatternChars).concat("\r\n").concat(String.valueOf(row2PatternChars));
    }
}

