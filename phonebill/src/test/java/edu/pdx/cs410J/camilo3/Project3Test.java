package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */

class Project3Test {

    /**
     * Just runs the current standard assumed command line inputs into the
     * project 3 and makes sure no bells or whistles sound off
     * maybe it is kind of an integration test in a way...
     */
  @Test
  void testEverythingWorksWonderfully() {
      Project3 proj = new Project3();
      String[] args = new String[] {"Steven", "867-867-5309", "503-222-2222",
                    "03/17/2022", "11:11", "am", "03/17/2022", "12:01", "pm"};
      proj.main(args);

  }

    /**
     * one error still handled within project 3.
     */
  @Test
  void checkNamesMatchErrorThrown() {
    Project3 proj = new Project3();
    Exception exception = assertThrows(Project3.NamesDontMatch.class, () -> {
      proj.checkNamesMatch("hello", "goodbye");
    });
    assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOUR NAMES DON'T MATCH."));
  }

    @Test
    void checkNamesMatchNothingThrown() {
        Project3 proj = new Project3();
        try {
            proj.checkNamesMatch("aloha.txt", "aloha.txt");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void checkAnExtraArgumentThrowsAnError() {
        Project3 proj = new Project3();
        ArrayList sa = new ArrayList(Arrays.asList("-extra-option", "831-479-4859", "861-227-1838", "03/7/2022", "01:42", "am", "03/07/2022", "001:47", "pm"));
        Exception exception = assertThrows(Project3.TooManyOptions.class, () -> {
            proj.checkOutOfOptions(sa);
        });
        assertTrue(exception.getMessage().contains("UNRECOGNIZED OPTIONS!"));
    }
}
