package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
    static final String NAME_PARAMETER = "name";
    static final String CALLER_PARAMETER = "caller";
    static final String CALLEE_PARAMETER = "callee";
    static final String BEGIN_PARAMETER = "begin";
    static final String END_PARAMETER = "end";

    static final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");

//    static final String DEFINITION_PARAMETER = "definition";

    // changed from <String, String>
    private final Map<String, PhoneBill> dictionary = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter( NAME_PARAMETER, request );
        if (name != null) {
            writeWholeBill(name, response);

        } else {
//            writeAllDictionaryEntries(response);
        }
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
        if ( caller == null) {
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

        // replaced this with stuff to change into a phonebill and add that.
//        this.dictionary.put(word, definition);
        // this ONLY adds a new phoneBill phonecall and looks it up.
        PhoneBill newBill = new PhoneBill(name);
        PhoneCall newCall = null;
        try {
            newCall = new PhoneCall(caller, callee, sdf.parse(begin), sdf.parse(end));
        } catch (Exception e) {
            missingRequiredParameter(response, "Something screwed up!");
        }
        newBill.addPhoneCall(newCall);
        this.dictionary.put(name, newBill);


        PrintWriter pw = response.getWriter();
        pw.println(Messages.addedPhoneCall(name, newCall));
        pw.flush();

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

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeWholeBill(String name, HttpServletResponse response) throws IOException {
        PhoneBill aBill = this.dictionary.get(name);

        // if we couldn't find a bill by that name
        if (aBill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter pw = response.getWriter();

//            Map<String, String> wordDefinition = Map.of(word, definition);
            TextDumper dumper = new TextDumper(pw);
            // changed to aBill from wordDefinition since dumper no longer
            // works that way I decided...
            dumper.dump(aBill);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    // changed text dumper and I don't think we need this...
//    /**
//     * Writes all of the dictionary entries to the HTTP response.
//     *
//     * The text of the message is formatted with {@link TextDumper}
//     */
//    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
//    {
//        PrintWriter pw = response.getWriter();
//        TextDumper dumper = new TextDumper(pw);
//        dumper.dump(dictionary);
//
//        response.setStatus( HttpServletResponse.SC_OK );
//    }

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

//    @VisibleForTesting
//    String getDefinition(String word) {
//        return this.dictionary.get(word);
//    }

}
