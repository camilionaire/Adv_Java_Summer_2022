package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
    static final String NAME_PARAMETER = "customer";
    static final String CALLER_PARAMETER = "callerNumber";
    static final String CALLEE_PARAMETER = "calleeNumber";
    static final String BEGIN_PARAMETER = "begin";
    static final String END_PARAMETER = "end";
    static final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");

//    static final String DEFINITION_PARAMETER = "definition";

    // changed from <String, String>
    private final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {

        String name = getParameter( NAME_PARAMETER, request );
        String begin = getParameter( BEGIN_PARAMETER, request );
        String end = getParameter( END_PARAMETER, request );

        if (name == null) {
            missingRequiredParameter(response, "We need a name to search for!");
        } else {
            if (begin == null && end == null) {
                writeWholeBill(name, response);
            } else if (begin != null && end != null) {
                try {
                    Date begT = sdf.parse(begin);
                    Date endT = sdf.parse(end);
                    if (! PhoneCallChecker.isStartBeforeEnd(begT, endT)) {
                        throw new TimeError();
                    }
                } catch (Exception e) {
                    requiredParameterNotFormattedCorrectly(response,
                            "Dates");
                    return;
                }
                writeSomeBill(name, begin, end, response);
            } else {
            missingRequiredParameter(response, "We need a begin and end time or neither to search for");
            }
        }
    }

    /**
     * Writes an error message about not finding a phone bill.
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void noPhoneBillFound( HttpServletResponse response, String customer)
            throws IOException
    {
        String message = Messages.noPhoneBillFound(customer);
        response.sendError(HttpServletResponse.SC_NO_CONTENT, message);
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter(NAME_PARAMETER, request );
        if (name == null) {
            missingRequiredParameter(response, NAME_PARAMETER);
            return;
        }
        String caller = getParameter(CALLER_PARAMETER, request );
        if ( caller == null) {
            missingRequiredParameter( response, CALLER_PARAMETER );
            return;
        }
        String callee = getParameter(CALLEE_PARAMETER, request );
        if ( callee == null) {
            missingRequiredParameter( response, CALLEE_PARAMETER );
            return;
        }
        String begin = getParameter(BEGIN_PARAMETER, request );
        if (begin == null) {
            missingRequiredParameter(response, BEGIN_PARAMETER);
            return;
        }
        String end = getParameter(END_PARAMETER, request );
        if (end == null) {
            missingRequiredParameter(response, END_PARAMETER);
            return;
        }

        PhoneCall newCall = null;
        PhoneBill oldBill = this.phoneBills.get(name);
        if (oldBill == null) {
            oldBill = new PhoneBill(name);
        }
        try {
            String[] bg = begin.split(" ");
            String[] ed = end.split(" ");
            ArrayList<String> input = new ArrayList<String>(List.of(new String[]{caller, callee, bg[0], bg[1], bg[2], ed[0], ed[1], ed[2]}));

            PhoneCallChecker.checkForImproperFormatting(input);

            newCall = new PhoneCall(caller, callee, sdf.parse(begin), sdf.parse(end));
        } catch (Exception e) {
            requiredParameterNotFormattedCorrectly(response, ("Something screwed up!\n" + e.getMessage()));
        }
        oldBill.addPhoneCall(newCall);
        this.phoneBills.put(name, oldBill);

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.phoneBills.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }


    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void requiredParameterNotFormattedCorrectly( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.requiredParameterNotFormattedCorrectly(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeWholeBill(String name, HttpServletResponse response) throws IOException {
        PhoneBill aBill = this.phoneBills.get(name);

        // if we couldn't find a bill by that name
        if (aBill == null) {
            noPhoneBillFound(response, name);
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setContentType( "text/plain" );
            PrintWriter pw = response.getWriter();

//            Map<String, String> wordDefinition = Map.of(word, definition);
            TextDumper dumper = new TextDumper(pw);
            // changed to aBill from wordDefinition since dumper no longer
            // works that way I decided...
            dumper.dump(aBill);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
    private void writeSomeBill(String name, String begin, String end, HttpServletResponse response) throws IOException {
        PhoneBill aBill = this.phoneBills.get(name);
        Date begDate;
        Date endDate;
        if (aBill == null) {
            noPhoneBillFound(response, name);
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setContentType( "text/plain" );
            SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
            PrintWriter pw = response.getWriter();
            TextDumper dumper = new TextDumper(pw);
            try {
                begDate = sdf.parse(begin);
                endDate = sdf.parse(end);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
            PhoneBill retBill = new PhoneBill(name);
            Collection<PhoneCall> calls = aBill.getPhoneCalls();

            for (PhoneCall call : calls) {
                if (time1IsBeforeTime2(begDate, call.getBeginTime()) && time1IsBeforeTime2(call.getBeginTime(), endDate)) {
                    retBill.addPhoneCall(call);
                }
            }

            dumper.dump(retBill);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    /**
     * returns true if the start is before or equal to the end
     * false otherwise
     */
    @VisibleForTesting
    static boolean time1IsBeforeTime2(Date time1, Date time2) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(
                time2.getTime() - time1.getTime());
        return seconds >= 0L;
    }

    static class TimeError extends Exception {
        public TimeError() {
            super("Time appears to be moving backwards!!!...");
        }
    }
}
