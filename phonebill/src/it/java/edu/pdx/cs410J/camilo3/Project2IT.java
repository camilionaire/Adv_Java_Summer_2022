package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("TOO FEW COMMAND LINE ARGUMENTS"));
  }

    /**
     * Tests that invoking the main method with correct arguments
     * and print option, prints everything and it's alright.
     */
    @Test
    void testReadmePrints() {
        MainMethodResult result = invokeMain(new String[] {"-README"});
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Project 1+: Designing a Phone Bill Application"));
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
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 03/17/2022 23:11 to 03/17/2022 23:27"));
    }

    // NOT SURE ABOUT THIS, WILL HAVE TO ASK IN OFFICE HOURS
    /**
     * Tests that invoking the main method with correct arguments
     * and no print option, and it's alright.
     */
    @Test
    void testEverythingWorksWonderfully() throws Exception {
        MainMethodResult result = invokeMain(
                new String[]
                        {"Steven", "867-867-5309", "503-222-2222",
                                "03/17/2022", "23:11", "03/17/2022", "23:27"});
    }

    /**
     * Tests that invoking the main method with correct arguments
     * and print option, and -textFile prints and reads everything
     * and it's alright.
     */
    @Test
    void testEverythingReadsAndPrintsWonderfully() {
        MainMethodResult result = invokeMain(
                new String[]
                        {"-print", "-textFile", "src/it/java/edu/pdx/cs410J/camilo3/FORINTEGRATIONTESTING.txt", "Camilo", "867-867-5309", "503-222-2222",
                                "03/17/2022", "23:11", "03/17/2022", "23:27"});
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 03/17/2022 23:11 to 03/17/2022 23:27"));
    }

    /**
     * Tests that invoking the main method with correct arguments
     * and print option, and -textFile prints and reads everything
     * and it's alright when the file name doesn't exist yet.
     */
    @Test
    void testEverythingReadsAndPrintsWonderfullyFileDoesNotExist() {
        MainMethodResult result = invokeMain(
                new String[]
                        {"-print", "-textFile", "SHOULDBEDELETED.txt", "Camilo", "867-867-5309", "503-222-2222",
                                "03/17/2022", "23:11", "03/17/2022", "23:27"});
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 03/17/2022 23:11 to 03/17/2022 23:27"));

        File deleteFile = new File("SHOULDBEDELETED.txt");
        deleteFile.delete();
    }
}