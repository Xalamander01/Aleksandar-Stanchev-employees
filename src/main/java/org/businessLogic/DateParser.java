package org.businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateParser {
    //an array of all date formats that we are able to process
    private final String[] dateFormats = {
            "yyyy-MM-dd", "dd/MM/yyyy",
            "dd-MMM-yyyy", "MM dd yyyy",
            "E MMM dd yyyy"
    } ;

    public String parseDateString(String dateString) {
        /*
         * for each date format listed above
         * try parsing the date string argument with each format
         * if successful, the date format will be returned and the loop will be terminated
         * otherwise, we will move to the catch clause and continue iterating through the array
         */
        for ( String dateFormat : dateFormats ) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            try {
                simpleDateFormat.parse(dateString);
                return dateFormat;
            } catch (ParseException e) {
            }
        }
        return dateString;
    }
}
