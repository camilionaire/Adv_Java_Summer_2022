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
    void test0RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getTextWrittenToStandardError(), containsString("While parsing the command line, there were irregularities\n"));
    }

    @Test
    void testForPortNotParsable() {
        MainMethodResult result = invokeMain(Project4.class, "-port", "Camilo", "-host", "localhost");
        assertThat(result.getTextWrittenToStandardError(), containsString("While attempting to carry out your request\n"));
        assertThat(result.getTextWrittenToStandardError(), containsString("THE PORT HAS GOTTA BE AN INTEGER DUDE!"));
    }

    @Test
    void test1ToMakeSureCanAddAPhoneBillAndItPrints() {
        MainMethodResult result = invokeMain(
                Project4.class, "-print", "Camilo", "831-666-7777", "831-777-6666",
                "3/3/2003", "11:11", "am", "3/3/2003", "12:12", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString(
                "Phone call from 831-666-7777 to 831-777-6666 from 3/3/03, 11:11 AM to 3/3/03, 12:12 PM" +
                " was added to \nbill for: Camilo"
        ));
    }

    @Test
    void testToSeeIfPortHostErrorIsThrown() {
        MainMethodResult result = invokeMain(Project4.class, "-port", "8080", "Steven", "867-867-5309", "503-222-2222",
                "03/17/2022", "11:11", "am", "03/17/2022", "12:01", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "While attempting to carry out your request\n" +
                        "we received the following error message:\n"));
        assertThat(result.getTextWrittenToStandardError(), containsString("MISSING HOST OR PORT!\n" +
                "You need to have a host and a port,\n"));
    }

    @Test
    void test2ToSeeIfTooManyOptionsErrorIsThrown() {
        MainMethodResult result = invokeMain(
                Project4.class, "-Iron-Man", "Camilo", "831-666-7777", "831-777-6666",
                "3/3/2003", "11:11", "am", "3/3/2003", "12:12", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("UNRECOGNIZED OPTIONS!\n" +
                        "Only options currently available are -print,\n"));
    }

//    @Test
//    void test3ToSeeIfMissingCommandLineArgumentsIsThrown() {
//        MainMethodResult result = invokeMain(
//                Project4.class, "-Iron-Man", "Camilo", "831-666-7777", "831-777-6666",
//                "3/3/2003", "11:11", "am", "3/3/2003", "12:12", "pm");
//        assertThat(result.getTextWrittenToStandardError(), containsString("UNRECOGNIZED OPTIONS!\n" +
//                "Only options currently available are -print,\n"));
//    }

//    @Test
//    void test2EmptyServer() {
//        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
//        String out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(PrettyPrinter.formatWordCount(0)));
//    }
//
//    @Test
//    void test3NoDefinitionsThrowsAppointmentBookRestException() {
//        String word = "WORD";
//        try {
//            invokeMain(Project4.class, HOSTNAME, PORT, word);
//            fail("Expected a RestException to be thrown");
//
//        } catch (UncaughtExceptionInMain ex) {
//            RestException cause = (RestException) ex.getCause();
//            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
//        }
//    }

//    @Test
//    void test4AddDefinition() {
//        String word = "WORD";
//        String definition = "DEFINITION";
//
//        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, word, definition );
//        String out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));
//
//        result = invokeMain( Project4.class, HOSTNAME, PORT, word );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));
//
//        result = invokeMain( Project4.class, HOSTNAME, PORT );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));
//    }
}