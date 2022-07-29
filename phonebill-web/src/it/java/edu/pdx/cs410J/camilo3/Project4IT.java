package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test01RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    void test02NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getTextWrittenToStandardError(), containsString("While parsing the command line, there were irregularities\n"));
    }

    @Test
    void test03ForPortNotParsable() {
        MainMethodResult result = invokeMain(Project4.class, "-port", "Camilo", "-host", "localhost");
        assertThat(result.getTextWrittenToStandardError(), containsString("While attempting to carry out your request\n"));
        assertThat(result.getTextWrittenToStandardError(), containsString("THE PORT HAS GOTTA BE AN INTEGER DUDE!"));
    }


    @Test
    void test04ToSeeIfPortHostErrorIsThrown() {
        MainMethodResult result = invokeMain(Project4.class, "-port", "8080", "Steven", "867-867-5309", "503-222-2222",
                "03/17/2022", "11:11", "am", "03/17/2022", "12:01", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "While attempting to carry out your request\n" +
                        "we received the following error message:\n"));
        assertThat(result.getTextWrittenToStandardError(), containsString("MISSING HOST OR PORT!\n" +
                "You need to have a host and a port,\n"));
    }

    @Test
    void test05ToSeeIfTooManyOptionsErrorIsThrown() {
        MainMethodResult result = invokeMain(
                Project4.class, "-Iron-Man", "Camilo", "831-666-7777", "831-777-6666",
                "3/3/2003", "11:11", "am", "3/3/2003", "12:12", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("UNRECOGNIZED OPTIONS!\n" +
                        "Only options currently available are -print,\n"));
    }

    @Test
    void test06ToMakeSureCanAddAPhoneBillAndItPrints() {
        MainMethodResult result = invokeMain(
                Project4.class, "-print", "Camilo", "831-666-7777", "831-777-6666",
                "3/3/2003", "11:11", "am", "3/3/2003", "12:12", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 831-666-7777 to 831-777-6666 from 3/3/03, 11:11 AM to 3/3/03, 12:12 PM\n" +
                        "was added to bill for: Camilo"
        ));
    }

    @Test
    void test07RequestPhoneBillReadItBackOldPersists() {
        // adds a phone bill and doesn't do anything...
        invokeMain(
                Project4.class, "Camilo", "831-666-7777", "831-777-6666",
                "3/6/2003", "12:31", "pm", "3/6/2003", "12:57", "pm");
        MainMethodResult result = invokeMain(
                Project4.class, "Camilo");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Customer name:\nCamilo"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Caller:       Callee:       Call Begins:          Call Ends:            Time:"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("831-666-7777  831-777-6666  Mar 03, 03  11:11 AM  Mar 03, 03  12:12 PM  61 mins"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("831-666-7777  831-777-6666  Mar 06, 03  12:31 PM  Mar 06, 03  12:57 PM  26 mins"));
    }
    @Test
    void test08RequestPartialPhoneBillReadItBackOldPersists() {
        // adds a phone bill and doesn't do anything...
        invokeMain(
                Project4.class, "Camilo", "831-666-7777", "831-777-6666",
                "3/9/2003", "11:47", "am", "3/9/2003", "12:59", "pm");
        MainMethodResult result = invokeMain(
                Project4.class, "-search",  "Camilo", "3/5/2003", "12:00", "am", "3/10/2003", "12:00", "am");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Customer name:\nCamilo"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Caller:       Callee:       Call Begins:          Call Ends:            Time:"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("831-666-7777  831-777-6666  Mar 06, 03  12:31 PM  Mar 06, 03  12:57 PM  26 mins"));
        assertThat(result.getTextWrittenToStandardOut(), containsString("831-666-7777  831-777-6666  Mar 09, 03  11:47 AM  Mar 09, 03  12:59 PM  72 mins"));
    }

}