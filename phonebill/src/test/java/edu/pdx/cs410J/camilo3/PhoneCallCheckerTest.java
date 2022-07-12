package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>PhoneCallChecker</code> class.
 */

public class PhoneCallCheckerTest {

    /**
     * test the two paths for isValidPhoneNumber() function.
     */
    @Test
    void isValidPhoneNumberTrue() {
        PhoneCallChecker checker = new PhoneCallChecker();

        assertEquals(checker.isValidPhoneNumber("503-867-5309"), true);
    }

    @Test
    void isValidPhoneNumberFalse() {
        PhoneCallChecker checker = new PhoneCallChecker();

        assertEquals(checker.isValidPhoneNumber("50t-867-5309"), false);
    }

    /**
     * test the two paths for isValidDate() function.
     */
    @Test
    void isValidDateTrue() {
        PhoneCallChecker checker = new PhoneCallChecker();

        assertEquals(checker.isValidDate("3/15/2022"), true);
        assertEquals(checker.isValidDate("03/2/2022"), true);
    }

    @Test
    void isValidDateFalse() {
        PhoneCallChecker checker = new PhoneCallChecker();

        assertEquals(checker.isValidDate("003/02/1901"), false);
        assertEquals(checker.isValidDate("3/2/901"), false);
    }

    /**
     * test the two paths for isValidTime() function.
     */
    @Test
    void isValidTimeTrue() {
        PhoneCallChecker checker = new PhoneCallChecker();

        assertEquals(checker.isValidTime("11:13"), true);
        assertEquals(checker.isValidTime("09:59"), true);
    }

    @Test
    void isValidTimeFalse() {
        PhoneCallChecker checker = new PhoneCallChecker();

        assertEquals(checker.isValidTime("003/02/1901"), false);
        assertEquals(checker.isValidTime("45:99"), false);
    }

    /**
     *  These are just all the ways that formatting can fail!!!
     */
    @Test
    void checkFormattingFailsOnZero() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList();
        Exception exception = assertThrows(PhoneCallChecker.MissingCommandLineArguments.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("TOO FEW COMMAND LINE ARGUMENTS"));
    }

    @Test
    void checkFormattingFailsOn7Args() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList<>(Arrays.asList("831-479-4859", "831-444-4859", "extras",
                "date1", "time1", "date2", "time2"));
        Exception exception = assertThrows(PhoneCallChecker.ExtraneousCommandLineArguments.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("TOO MANY COMMAND LINE ARGUMENTS"));
    }

    @Test
    void checkFormattingFailsOnBadFirstPhoneNumber() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList(Arrays.asList("8k1-227-1838", "831-479-4859", "date1", "time1", "date2", "time2"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperPhoneNumber.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF PHONE NUMBERS"));
    }

    @Test
    void checkFormattingFailsOnBadSecondPhoneNumber() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList(Arrays.asList("831-479-4859", "8k1-227-1838", "date1", "time1", "date2", "time2"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperPhoneNumber.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF PHONE NUMBERS"));
    }

    @Test
    void checkFormattingFailsOnBadFirstDate() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList(Arrays.asList("831-479-4859", "861-227-1838", "003/7/2022", "time1", "03/7/2022", "time2"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperDate.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
    }

    @Test
    void checkFormattingFailsOnBadSecondDate() {
        PhoneCallChecker checker = new PhoneCallChecker();
        String[] testArgs = new String[] {};
        ArrayList sa = new ArrayList(Arrays.asList("831-479-4859", "861-227-1838", "03/7/2022", "time1", "03/007/2022", "time2"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperDate.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
    }

    @Test
    void checkFormattingFailsOnBadFirstTime() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList(Arrays.asList("831-479-4859", "861-227-1838", "03/7/2022", "33:42", "03/7/2022", "01:47"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperTime.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
    }

    @Test
    void checkFormattingFailsOnBadSecondTime() {
        PhoneCallChecker checker = new PhoneCallChecker();
        ArrayList sa = new ArrayList(Arrays.asList("831-479-4859", "861-227-1838", "03/7/2022", "01:42", "03/07/2022", "001:47"));
        Exception exception = assertThrows(PhoneCallChecker.ImproperTime.class, () -> {
            checker.checkForImproperFormatting(sa);
        });
        assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
    }
}
