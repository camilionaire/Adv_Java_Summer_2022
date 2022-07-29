package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient {

    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
      this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

  /**
   * creates the rest client with the request helper.
   * @param http sets the HttpRequestHelper
   */
  @VisibleForTesting
  PhoneBillRestClient(HttpRequestHelper http) {
    this.http = http;
  }

  /**
   * Returns the pretty print version of the phonebill
   */
  public PhoneBill getPhoneBill(String customer) throws IOException, ParserException {
    Response response = http.get(Map.of("customer", customer));
    throwExceptionIfNotOkayHttpStatus(response);
    TextParser ps = new TextParser(new StringReader(response.getContent()));
    return ps.parse();
  }

  /**
   * Returns the pretty print version of the phonebill within the parameters.
   */
  public PhoneBill getPartialPhoneBill(String customer, String begin, String end) throws IOException, ParserException {
    Response response = http.get(Map.of("customer", customer, "begin", begin, "end", end));
    throwExceptionIfNotOkayHttpStatus(response);
    TextParser ps = new TextParser(new StringReader(response.getContent()));
    return ps.parse();
    }

  /**
   * Makes a post to add a phonecall to a phonebill.  if no phonebill already
   * exists, it creates a new phone bill.
   * @param customer customers name: any string
   * @param caller caller number string: nnn-nnn-nnnn
   * @param callee callee number string: nnn-nnn-nnnn
   * @param begin call begin time string: MM/dd/yyyy hh:mm am
   * @param end call end time string: MM/dd/yyyy hh:mm am
   * @throws IOException
   */
    public void addPhoneCallEntry(
          String customer, String caller, String callee, String begin, String end)
          throws IOException {
    Response response = http.post(
            Map.of("customer", customer, "callerNumber", caller, "calleeNumber", callee,
                    "begin", begin, "end", end));
      throwExceptionIfNotOkayHttpStatus(response);
    }

  /**
   * deletes the entire library of phonebills.  left over from
   * the project creation, but I don't think it hurts anyone.
   * @throws IOException if the response is bad...
   */
  public void removeAllDictionaryEntries() throws IOException {
      Response response = http.delete(Map.of());
      throwExceptionIfNotOkayHttpStatus(response);
    }

  /**
   * this takes in the http response and sends out a message
   * depending on what the error is.
   * @param response an http response.
   */
  private void throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getHttpStatusCode();
      if (code == HTTP_NO_CONTENT) {
        String message = "We couldn't find anyone by that name.  Sorry.";
        throw new RestException(code, message);
      } else if (code != HTTP_OK) {
        String message = response.getContent();
        throw new RestException(code, message);
      }
    }

}
