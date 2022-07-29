package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A unit test for code in the <code>PhoneCallChecker</code> class.
 */

public class PhoneCallCheckerTest {
    /**
     * test the two paths for isValidPhoneNumber() function.
     */
    @Test
    void test01IsValidPhoneNumberTrue() {
        assertTrue(PhoneCallChecker.isValidPhoneNumber("503-867-5309"));
    }

    @Test
    void test02IsValidPhoneNumberFalse() {
        assertFalse(PhoneCallChecker.isValidPhoneNumber("50t-867-5309"));
    }

    /**
     * test the two paths for isValidDate() function.
     */
    @Test
    void test03IsValidDateTrue() {
        assertTrue(PhoneCallChecker.isValidDate("3/15/2022"));
        assertTrue(PhoneCallChecker.isValidDate("03/2/2022"));
    }

    @Test
    void test04IsValidDateFalse() {
        assertFalse(PhoneCallChecker.isValidDate("003/02/1901"));
        assertFalse(PhoneCallChecker.isValidDate("3/2/901"));
    }

    /**
     * test the two paths for isValidTime() function.
     */
    @Test
    void test05IsValidTimeTrue() {
        assertTrue(PhoneCallChecker.isValidTime("11:13 pm"));
        assertTrue(PhoneCallChecker.isValidTime("09:59 am"));
        assertTrue(PhoneCallChecker.isValidTime("11:13 PM"));
        assertTrue(PhoneCallChecker.isValidTime("09:59 AM"));
    }

    @Test
    void test06IsValidTimeFalse() {
        assertFalse(PhoneCallChecker.isValidTime("003/02/1901"));
        assertFalse(PhoneCallChecker.isValidTime("45:99"));
        assertFalse(PhoneCallChecker.isValidTime("5:19 pM"));
    }

    /**
     *  new boolean is ArrayZero(args)
     */
    @Test
    void test07CheckFormattingTrueOnZero() {
        assertTrue(PhoneCallChecker.isArrayZero(new ArrayList<>()));
    }

    @Test
    void test08CheckFormattingFalseOnOne() {
        ArrayList<String> sa = new ArrayList<>(List.of(new String[]{"hello"}));
        assertFalse(PhoneCallChecker.isArrayZero(sa));
    }

    /**
     *  These are just all the ways that formatting can fail!!!
     */
    @Test
    void test09CheckFormattingFailsOnFewerThanSix() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "831-444-4859",
                "date1", "time1", "time2"));
        Exception exception = assertThrows(PhoneCallChecker.MissingCommandLineArguments.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("TOO FEW ARGUMENTS"));
    }

    @Test
    void test10CheckFormattingFailsOn9Args() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "831-444-4859", "extras",
                "date1", "time1", "am", "date2", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ExtraneousCommandLineArguments.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("TOO MANY ARGUMENTS"));
    }

    @Test
    void test11CheckFormattingFailsOnBadFirstPhoneNumber() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("8k1-227-1838", "831-479-4859", "date1", "time1", "am", "date2", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperPhoneNumber.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF PHONE NUMBERS"));
    }

    @Test
    void test12CheckFormattingFailsOnBadSecondPhoneNumber() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "8k1-227-1838", "date1", "time1", "am", "date2", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperPhoneNumber.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF PHONE NUMBERS"));
    }

    @Test
    void test13CheckFormattingFailsOnBadFirstDate() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "861-227-1838", "003/7/2022", "time1", "am", "03/7/2022", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperDate.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
    }

    @Test
    void test14CheckFormattingFailsOnBadSecondDate() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "861-227-1838", "03/7/2022", "time1", "am", "03/007/2022", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperDate.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
    }

    @Test
    void test15CheckFormattingFailsOnBadFirstTime() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "861-227-1838", "03/7/2022", "33:42", "am", "03/7/2022", "01:47", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperTime.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
    }

    @Test
    void test16CheckFormattingFailsOnBadSecondTime() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "861-227-1838", "03/7/2022", "01:42", "am", "03/07/2022", "001:47", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperTime.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
    }

    @Test
    void test17CheckEndTimeBeforeStartTimeThrowsAnError() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("831-479-4859", "861-227-1838", "03/8/2022", "01:42", "am", "03/07/2022", "1:47", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.EndIsBeforeStart.class, () -> PhoneCallChecker.checkForImproperFormatting(sa));
        assertTrue(exception.getMessage().contains("END TIME IS BEFORE START!"));
    }
    @Test
    void test18DateTimeCheckFormattingFailsOnBadFirstDate() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("003/7/2022", "time1", "am", "03/7/2022", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperDate.class, () -> PhoneCallChecker.checkADateTime(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
    }

    @Test
    void test19DateTimeCheckFormattingFailsOnBadSecondDate() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("03/7/2022", "time1", "am", "03/007/2022", "time2", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperDate.class, () -> PhoneCallChecker.checkADateTime(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
    }

    @Test
    void test20DateTimeCheckFormattingFailsOnBadFirstTime() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("03/7/2022", "33:42", "am", "03/7/2022", "01:47", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperTime.class, () -> PhoneCallChecker.checkADateTime(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
    }

    @Test
    void test21DateTimeCheckFormattingFailsOnBadSecondTime() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("03/7/2022", "01:42", "am", "03/07/2022", "001:47", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperTime.class, () -> PhoneCallChecker.checkADateTime(sa));
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
    }

    @Test
    void test22DateTimeCheckEndTimeBeforeStartTimeThrowsAnError() {
        ArrayList<String> sa = new ArrayList<>(Arrays.asList("03/8/2022", "01:42", "am", "03/07/2022", "1:47", "pm"));
        Exception exception = assertThrows(PhoneCallChecker.EndIsBeforeStart.class, () -> PhoneCallChecker.checkADateTime(sa));
        assertTrue(exception.getMessage().contains("END TIME IS BEFORE START!"));
    }
}
