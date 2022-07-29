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

                int port = optChecker.parsedPort(portString);

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
                    PhoneBill custBill = client.getPhoneBill(customer);
                    StringWriter sw = new StringWriter();
                    PrettyPrinter pp = new PrettyPrinter(sw);
                    pp.dump(custBill);
                    message = sw.toString();
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
                    checker.checkADateTime(argList); // throws errors if things are wrong
                    // before the servlet that will also throw errors.
                    PhoneBill custPartBill = client.getPartialPhoneBill(customer, beg, end);
                    StringWriter sw = new StringWriter();
                    PrettyPrinter pp = new PrettyPrinter(sw);
                    pp.dump(custPartBill);
                    message = sw.toString();

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

        // THIS I'LL PROBABLY ADD BACK IN!!!
//        } catch (IOException | ParseException ex) {
//            error("While contacting server: " + ex);
//            return;
//        }
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
     * exception that is thrown when there are too many options
     */
    static class TooManyOptions extends Exception {
        public TooManyOptions() {
            super( "UNRECOGNIZED OPTIONS!\n" +
                    "Only options currently available are -print,\n" +
                    "-README, -search, -host hostname, and -port port");
        }
    }

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
    /**
     * Makes sure that the give response has the expected HTTP status code
     *
     * @param code     The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
        if (response.getHttpStatusCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getHttpStatusCode(), response.getContent()));
        }
    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
    }

} // end of the Project4 class