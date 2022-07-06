package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("INCORRECT USE OF COMMAND LINE ARGUMENTS"));
  }

    /**
     * Tests that invoking the main method with correct arguments
     * and print option, prints everything and it's alright.
     */
    @Test
    void testEverythingPrintsWonderfully() {
        MainMethodResult result = invokeMain(
                new String[]
                        {"-print", "Steven", "867-867-5309", "503-222-2222",
                                "03/17/2022", "23:11", "03/17/2022", "23:27"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("Steven's phone bill with 1 phone calls"));
    }
}