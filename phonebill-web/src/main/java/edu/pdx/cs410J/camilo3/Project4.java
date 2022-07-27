package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        boolean readme, printOption, searchOption;
        PhoneCall aCall;
        ArrayList<String> argList = new ArrayList<String>(Arrays.asList(args));

        OptionsChecker optChecker = new OptionsChecker();

        // runs through options and checks for a -README
        readme = optChecker.checkForReadme(argList);

        if (!readme) {
            printOption = optChecker.checkForPrint(argList);
            searchOption = optChecker.checkForSearch(argList);

            try {
                String host = optChecker.checkForHost(argList);
                String portString = optChecker.checkForPort(argList);

                String[] hpResult = optChecker.hostAndPortOrNeither(host, portString);
                host = hpResult[0];
                portString = hpResult[1];

                int port;
                try {
                    port = Integer.parseInt(portString);
                } catch (NumberFormatException ex) {
                    throw new PortIsNotAnInteger();
                }

                PhoneBillRestClient client = new PhoneBillRestClient(host, port);
                PhoneCallChecker checker = new PhoneCallChecker();
                SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");

                String message;

                if (checker.isArrayZero(argList)) {
                    throw new CommandLineException("There are no arguments.");
                }

                checkOutOfOptions(argList);
                String customer = argList.get(0);
                argList.remove(0);

                if (checker.isArrayZero(argList)) {
                    message = client.getPhoneBill(customer);
                } else if (argList.size() == 8) {
                    checker.checkForImproperFormatting(argList);
                    String beg = argList.get(2) + " " + argList.get(3) + " " + argList.get(4);
                    String end = argList.get(5) + " " + argList.get(6) + " " + argList.get(7);
                    String caller = argList.get(0);
                    String callee = argList.get(1);
                    client.addPhoneCallEntry(customer, caller, callee, beg, end);
                    aCall = new PhoneCall(caller, callee, sdf.parse(beg), sdf.parse(end));
                    message = Messages.addedPhoneCall(customer, aCall);
                } else if (searchOption && argList.size() == 6) {
                    // will need to add in some error checking here
                    String beg = argList.get(0) + " " + argList.get(1) + " " + argList.get(2);
                    String end = argList.get(3) + " " + argList.get(4) + " " + argList.get(5);
                    message = client.getPartialPhoneBill(customer, beg, end);
                } else {
                    // message just here to prevent the error of 'possibly' being null.
                    message = "something went horribly wrong";
                    throw new CommandLineException("Incorrect number/formatting of args.");
                }

                if (argList.size() != 8 || printOption) {
                    System.out.println(message);
                }

            } catch (Exception e) {
                System.err.println("While attempting to carry out your request\n" +
                        "we received the following error message:\n" + e.getMessage());
            }

        } // end of the if readMe section

//        try {
//            String beg = bd + " " + bt + " " + ba;
//            String end = ed + " " + et + " " + ea;
//            client.addPhoneCallEntry(name, caller, callee, beg, end);
//            PhoneCall newCall = new PhoneCall(caller, callee, sdf.parse(beg), sdf.parse(end));
//
//            message = Messages.addedPhoneCall(name, newCall);

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

        // THIS I'LL PROBABLY ADD BACK IN!!!
//        } catch (IOException | ParseException ex) {
//            error("While contacting server: " + ex);
//            return;
//        }
//
//        System.out.println(message);
    }

    @VisibleForTesting
    /**
     * wrapper function for throwing error if there are too many options
     */
    static void checkOutOfOptions(ArrayList<String> args) throws TooManyOptions {
        if (args.get(0).startsWith("-")) {
            throw new TooManyOptions();
        }
    }

    /**
     * throws an error if the port isn't an int, will probs be refactored out
     */
    static class PortIsNotAnInteger extends Exception {
        public PortIsNotAnInteger() {
            super("THE PORT HAS GOTTA BE AN INTEGER DUDE!");
        }
    }

    /**
     * exception that is thrown when there are too many options
     */
    static class TooManyOptions extends Exception {
        public TooManyOptions() {
            super( "UNRECOGNIZED OPTIONS!\n" +
                    "Only options currently available are -print,\n" +
                    "-README, -search, -host hostname, and -port port");
        }
    }

//    /**
//     * exception that is thrown when there are too few arguments
//     */
//    @VisibleForTesting
//    static class MissingCommandLineArguments extends Exception {
//        public MissingCommandLineArguments() {
//            super("TOO FEW ARGUMENTS");
//        }
//    }

    /**
     * exception that is thrown when the names don't match.
     */
    static class CommandLineException extends Exception {
        public CommandLineException(String msg) {
            super("While parsing the command line, there were irregularities\n" +
                    "usage: java -jar target/phonebill-client.jar [options] <args>\n" +
                    "Run with the '-README' flag enabled for proper usage.\n" +
                    "Error message shown was:\n" + msg);
        }
    }

    // THIS I MIGHT NEED LATER ON... I DON'T REALLY KNOW THOUGH.
//    /**
//     * Makes sure that the give response has the expected HTTP status code
//     *
//     * @param code     The expected status code
//     * @param response The response from the server
//     */
//    private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
//        if (response.getHttpStatusCode() != code) {
//            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
//                    response.getHttpStatusCode(), response.getContent()));
//        }
//    }
//
//    private static void error(String message) {
//        PrintStream err = System.err;
//        err.println("** " + message);
//    }

    // might need this as well, not sure what PrintStream is or if I should use it.
//    /**
//     * Prints usage information for this program and exits
//     *
//     * @param message An error message to print
//     */
//    private static void usage(String message) {
//        PrintStream err = System.err;
//        err.println("** " + message);
//        err.println();
//        err.println("usage: java Project4 host port [word] [definition]");
//        err.println("  host         Host of web server");
//        err.println("  port         Port of web server");
//        err.println("  word         Word in dictionary");
//        err.println("  definition   Definition of word");
//        err.println();
//        err.println("This simple program posts words and their definitions");
//        err.println("to the server.");
//        err.println("If no definition is specified, then the word's definition");
//        err.println("is printed.");
//        err.println("If no word is specified, all dictionary entries are printed");
//        err.println();
//    }

} // end of the Project4 class