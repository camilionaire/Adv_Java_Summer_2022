package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String name = null;
        String caller = null;
        String callee = null;
        String bd = null;
        String bt = null;
        String ba = null;
        String ed = null;
        String et = null;
        String ea = null;

        for (String arg : args) {
            if (hostName == null) {
                hostName = arg;
            } else if ( portString == null) {
                portString = arg;
            } else if (name == null) {
                name = arg;
            } else if (caller == null) {
                caller = arg;
            } else if (callee == null) {
                callee = arg;
            } else if (bd == null) {
                bd = arg;
            } else if (bt == null) {
                bt = arg;
            } else if (ba == null) {
                ba = arg;
            } else if (ed == null) {
                ed = arg;
            } else if (et == null) {
                et = arg;
            } else if (ea == null) {
                ea = arg;
            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );
            
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");

        String message;
        try {
            String beg = bd + " " + bt + " " + ba;
            String end = ed + " " + et + " " + ea;
            client.addPhoneCallEntry(name, caller, callee, beg, end);
            PhoneCall newCall = new PhoneCall(caller, callee, sdf.parse(beg), sdf.parse(end));

            message = Messages.addedPhoneCall(name, newCall);

//            if (word == null) {
//                // Print all word/definition pairs
//                Map<String, String> dictionary = client.getAllDictionaryEntries();
//                StringWriter sw = new StringWriter();
//                PrettyPrinter pretty = new PrettyPrinter(sw);
//                pretty.dump(dictionary);
//                message = sw.toString();
//
//            } else if (definition == null) {
//                // Print all dictionary entries
//                message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));
//
//            } else {
//                // Post the word/definition pair
//                client.addDictionaryEntry(word, definition);
//                message = Messages.definedWordAs(word, definition);
//            }

//        } catch (IOException | ParserException ex ) {
        } catch (IOException | ParseException ex) {
            error("While contacting server: " + ex);
            return;
        }

        System.out.println(message);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getHttpStatusCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getHttpStatusCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }
}