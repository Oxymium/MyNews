package com.raspberyl.mynews;

import android.annotation.SuppressLint;

import org.junit.Test;
import com.raspberyl.mynews.utils.SharedPreferencesUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateConvertTest {

    @Test
    public void test_getPublished_date_converted() throws Exception {

        String leapYearDateInput = "2024-02-29";
        String leapYearDateOutput = "29/02/24";

        String veryFarFutureDateInput = "2900-10-08";
        String veryFarFutureDateOutput = "08/10/00";

        String futureDateInput = "2150-01-01";
        String futureDateOutput = "01/01/50";

        String presentDateInput = "2018-07-30";
        String presentDateOutput = "30/07/18";

        String pastDateInput = "1995-12-25";
        String pastDateOutput = "25/12/95";

        String veryFarPastDateInput = "1970-05-05";
        String veryFarPastDateOutput = "05/05/70";

        String endOfTimesInput = "9999-12-31";
        String endOfTimesOutput = "31/12/99";

        String firstDayInput = "0001-01-01";
        String firstDayOutput = "01/01/01";

        String convertedDate1 = "";
        String convertedDate2 = "";
        String convertedDate3 = "";
        String convertedDate4 = "";
        String convertedDate5 = "";
        String convertedDate6 = "";
        String convertedDate7 = "";
        String convertedDate8 = "";

        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd"+"/"+"MM"+"/"+"yy");

        try {
            convertedDate1 = outputFormat.format(inputFormat.parse(leapYearDateInput));
            convertedDate2 = outputFormat.format(inputFormat.parse(veryFarFutureDateInput));
            convertedDate3 = outputFormat.format(inputFormat.parse(futureDateInput));
            convertedDate4 = outputFormat.format(inputFormat.parse(presentDateInput));
            convertedDate5 = outputFormat.format(inputFormat.parse(pastDateInput));
            convertedDate6 = outputFormat.format(inputFormat.parse(veryFarPastDateInput));
            convertedDate7 = outputFormat.format(inputFormat.parse(endOfTimesInput));
            convertedDate8 = outputFormat.format(inputFormat.parse(firstDayInput));

            assertEquals(convertedDate1, leapYearDateOutput);
            assertEquals(convertedDate2, veryFarFutureDateOutput);
            assertEquals(convertedDate3, futureDateOutput);
            assertEquals(convertedDate4, presentDateOutput);
            assertEquals(convertedDate5, pastDateOutput);
            assertEquals(convertedDate6, veryFarPastDateOutput);
            assertEquals(convertedDate7, endOfTimesOutput);
            assertEquals(convertedDate8, firstDayOutput);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}