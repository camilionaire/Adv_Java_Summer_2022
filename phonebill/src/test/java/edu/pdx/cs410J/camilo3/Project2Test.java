package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */

class Project2Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("Project 1+: Designing a Phone Bill Application"));
    }
  }

  /**
   * These 3 tests are for the ReadMe test on the arg strings.
   */
  @Test
  void stringOfArgsWithOptionOfReadmeTrueSingleOption() {
    String[] test = {"-README"};
    Project2 proj = new Project2();

    assertEquals(proj.checkForReadme(test), true);
  }
  @Test
  void stringOfArgsWithOptionOfReadmeTrueMultipleOptions() {
    String[] test = {"-print", "-hello-world", "-README", "more things"};
    Project2 proj = new Project2();

    assertEquals(proj.checkForReadme(test), true);
  }

  @Test
  void stringOfArgsWithOptionOfReadmeFalseMultipleOptions() {
    String[] test = {"-print", "-hello-world", "Robert Paulson", "-README"};
    Project2 proj = new Project2();

    assertEquals(proj.checkForReadme(test), false);
  }

  /**
   * test the two paths for isValidPhoneNumber() function.
   */
  @Test
  void isValidPhoneNumberTrue() {
    Project2 proj = new Project2();

    assertEquals(proj.isValidPhoneNumber("503-867-5309"), true);
  }

  @Test
  void isValidPhoneNumberFalse() {
    Project2 proj = new Project2();

    assertEquals(proj.isValidPhoneNumber("50t-867-5309"), false);
  }

  /**
   * test the two paths for isValidDate() function.
   */
  @Test
  void isValidDateTrue() {
    Project2 proj = new Project2();

    assertEquals(proj.isValidDate("3/15/2022"), true);
    assertEquals(proj.isValidDate("03/2/2022"), true);
  }

  @Test
  void isValidDateFalse() {
    Project2 proj = new Project2();

    assertEquals(proj.isValidDate("003/02/1901"), false);
    assertEquals(proj.isValidDate("3/2/901"), false);
  }

  /**
   * test the two paths for isValidTime() function.
   */
  @Test
  void isValidTimeTrue() {
    Project2 proj = new Project2();

    assertEquals(proj.isValidTime("11:13"), true);
    assertEquals(proj.isValidTime("09:59"), true);
  }

  @Test
  void isValidTimeFalse() {
    Project2 proj = new Project2();

    assertEquals(proj.isValidTime("003/02/1901"), false);
    assertEquals(proj.isValidTime("45:99"), false);
  }

  /**
   * These next few check the checkForPrint paths
   */
  @Test
  void checkForPrintTrue() {
    Project2 proj = new Project2();

    assertEquals(proj.checkForPrint(new String[] {"-print", "-first"}), true);
  }

  @Test
  void checkForPrintFalse() {
    Project2 proj = new Project2();

    assertEquals(proj.checkForPrint(new String[] {"-first", "-print"}), false);
  }

  @Test
  void checkForPrintFalseZero() {
    Project2 proj = new Project2();

    assertEquals(proj.checkForPrint(new String[0]), false);
  }

  /**
   *  These are just all the ways that formatting can fail!!!
   */
  @Test
  void checkFormattingFailsOnZero() {
    Project2 proj = new Project2();
    String[] testArgs = new String[0];
    Exception exception = assertThrows(Project2.MissingCommandLineArguments.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT USE OF COMMAND LINE ARGUMENTS"));
  }

  @Test
  void checkFormattingFailsOnBadFirstPhoneNumber() {
    Project2 proj = new Project2();
    String[] testArgs = new String[] {"dave", "8k1-227-1838", "831-479-4859", "date1", "time1", "date2", "time2"};
    Exception exception = assertThrows(Project2.ImproperPhoneNumber.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF PHONE NUMBERS"));
  }

  @Test
  void checkFormattingFailsOnBadSecondPhoneNumber() {
    Project2 proj = new Project2();
    String[] testArgs = new String[] {"dave", "831-479-4859", "8k1-227-1838", "date1", "time1", "date2", "time2"};
    Exception exception = assertThrows(Project2.ImproperPhoneNumber.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF PHONE NUMBERS"));
  }

  @Test
  void checkFormattingFailsOnBadFirstDate() {
    Project2 proj = new Project2();
    String[] testArgs = new String[] {"dave", "831-479-4859", "861-227-1838", "003/7/2022", "time1", "03/7/2022", "time2"};
    Exception exception = assertThrows(Project2.ImproperDate.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
  }

  @Test
  void checkFormattingFailsOnBadSecondDate() {
    Project2 proj = new Project2();
    String[] testArgs = new String[] {"dave", "831-479-4859", "861-227-1838", "03/7/2022", "time1", "03/007/2022", "time2"};
    Exception exception = assertThrows(Project2.ImproperDate.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF DATES"));
  }

  @Test
  void checkFormattingFailsOnBadFirstTime() {
    Project2 proj = new Project2();
    String[] testArgs = new String[] {"dave", "831-479-4859", "861-227-1838", "03/7/2022", "33:42", "03/7/2022", "01:47"};
    Exception exception = assertThrows(Project2.ImproperTime.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
  }

  @Test
  void checkFormattingFailsOnBadSecondTime() {
    Project2 proj = new Project2();
    String[] testArgs = new String[] {"dave", "831-479-4859", "861-227-1838", "03/7/2022", "01:42", "03/07/2022", "001:47"};
    Exception exception = assertThrows(Project2.ImproperTime.class, () -> {
      proj.checkForImproperFormatting(testArgs);
    });
    assertTrue(exception.getMessage().contains("INCORRECT FORMATTING OF TIMES"));
  }

}
