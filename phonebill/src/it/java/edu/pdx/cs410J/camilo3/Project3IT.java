package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
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
                "-print", "Steven", "867-867-5309", "503-222-2222",
                "03/17/2022", "11:11", "pm", "03/17/2022", "11:27", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 3/17/22, 11:11 PM to 3/17/22, 11:27 PM"));
    }

    /**
     * Tests that invoking the main method with correct arguments
     * and no print option, and it's alright.
     */
    @Test
    void testEverythingWorksWonderfully() throws Exception {
        MainMethodResult result = invokeMain(
                "Steven", "867-867-5309", "503-222-2222",
                "03/17/2022", "11:11", "pm", "03/17/2022", "11:27", "pm");
    }

    /**
     * Tests that invoking the main method with correct arguments
     * and print option and pretty option, and -textFile prints
     * and reads everything, and it's alright.
     */
    @Test
    void testEverythingReadsAndPrintsWonderfully() throws IOException, ParserException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");
        File tempFile = new File("src/it/resources/edu/pdx/cs410J/camilo3/COPYTODELETE.txt");

        PhoneBill aBill = new PhoneBill("Camilo");

        PhoneCall call1 = new PhoneCall("867-867-5309", "503-222-2222",
                sdf.parse("03/17/2022 11:11 pm"), sdf.parse("03/17/2022 11:27 pm"));
        PhoneCall call2 = new PhoneCall("867-867-5309", "503-222-2222",
                sdf.parse("03/17/2022 11:35 pm"), sdf.parse("03/17/2022 11:47 pm"));

        aBill.addPhoneCall(call1);
        aBill.addPhoneCall(call2);

        FileWriter fw = new FileWriter(tempFile);
        PrintWriter pw = new PrintWriter(fw);
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(aBill);

        MainMethodResult result = invokeMain(
                "-print", "-textFile",
                "src/it/resources/edu/pdx/cs410J/camilo3/COPYTODELETE.txt",
                "-pretty", "-",
                "Camilo", "867-867-5309", "503-222-2222",
                "03/16/2022", "3:11", "pm", "03/16/2022", "3:27", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 3/16/22, 3:11 PM to 3/16/22, 3:27 PM"));

        tempFile.delete();
    }

    /**
     * Another Test that invoking the main method with correct arguments
     * and print option and pretty option to file this time,
     * and -textFile prints, and reads everything, and it's alright.
     */
    @Test
    void testEverythingReadsAndPrintsWonderfullyPrettyToFile() throws IOException, ParserException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");
        File tempFile = new File("src/it/resources/edu/pdx/cs410J/camilo3/COPYTODELETE.txt");
        File prettyFile = new File("src/it/resources/edu/pdx/cs410J/camilo3/PRETTYTODELETE.txt");
        File prettyComp = new File("src/it/resources/edu/pdx/cs410J/camilo3/pretty-to-compare.txt");

        PhoneBill aBill = new PhoneBill("Camilo");

        PhoneCall call1 = new PhoneCall("867-867-5309", "503-222-2222",
                sdf.parse("03/17/2022 11:11 pm"), sdf.parse("03/17/2022 11:27 pm"));
        PhoneCall call2 = new PhoneCall("867-867-5309", "503-222-2222",
                sdf.parse("03/17/2022 11:35 pm"), sdf.parse("03/17/2022 11:47 pm"));

        aBill.addPhoneCall(call1);
        aBill.addPhoneCall(call2);

        FileWriter fw = new FileWriter(tempFile);
        PrintWriter pw = new PrintWriter(fw);
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(aBill);

        MainMethodResult result = invokeMain(
                "-print", "-textFile",
                "src/it/resources/edu/pdx/cs410J/camilo3/COPYTODELETE.txt",
                "-pretty", "src/it/resources/edu/pdx/cs410J/camilo3/PRETTYTODELETE.txt",
                "Camilo", "867-867-5309", "503-222-2222",
                "03/16/2022", "3:11", "pm", "03/16/2022", "3:27", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 3/16/22, 3:11 PM to 3/16/22, 3:27 PM"));

        BufferedReader bf1 = new BufferedReader(new FileReader(prettyFile));
        BufferedReader bf2 = new BufferedReader(new FileReader(prettyComp));

        String currLine;
        while ((currLine = bf1.readLine()) != null) {
            assertTrue(currLine.equals(bf2.readLine()));
        }
        bf1.close();
        bf2.close();


        tempFile.delete();
        prettyFile.delete();
    }

        /**
     * Tests that invoking the main method with correct arguments
     * and print option, and -textFile prints and reads everything
     * and it's alright when the file name doesn't exist yet.
     */
    @Test
    void testEverythingReadsAndPrintsWonderfullyFileDoesNotExist() {
        MainMethodResult result = invokeMain(
                "-print", "-textFile", "SHOULDBEDELETED.txt", "Camilo", "867-867-5309", "503-222-2222",
                "03/17/2022", "11:11", "pm", "03/17/2022", "11:27", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 867-867-5309 to 503-222-2222 from 3/17/22, 11:11 PM to 3/17/22, 11:27 PM"));

        File deleteFile = new File("SHOULDBEDELETED.txt");
        deleteFile.delete();
    }
}