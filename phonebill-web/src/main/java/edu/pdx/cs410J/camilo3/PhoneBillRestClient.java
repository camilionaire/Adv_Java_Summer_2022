package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
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
   * Returns the pretty print version of the phonebill
   */
  public PhoneBill getPartialPhoneBill(String customer, String begin, String end) throws IOException, ParserException {
    Response response = http.get(Map.of("customer", customer, "begin", begin, "end", end));
    throwExceptionIfNotOkayHttpStatus(response);
    TextParser ps = new TextParser(new StringReader(response.getContent()));
    return ps.parse();
  }

  public void addPhoneCallEntry(
          String customer, String caller, String callee, String begin, String end)
          throws IOException {
    Response response = http.post(
            Map.of("customer", customer, "callerNumber", caller, "calleeNumber", callee,
                    "begin", begin, "end", end));
      throwExceptionIfNotOkayHttpStatus(response);
  }

  public void removeAllDictionaryEntries() throws IOException {
      Response response = http.delete(Map.of());
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private void throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getHttpStatusCode();
      if (code != HTTP_OK) {
        String message = response.getContent();
        throw new RestException(code, message);
      }
    }

}
