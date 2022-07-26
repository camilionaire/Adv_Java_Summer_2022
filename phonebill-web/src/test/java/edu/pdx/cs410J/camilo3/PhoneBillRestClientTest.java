package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneBillRestClientTest {

    // this one I failed to get working...
    @Test
    void getCustomerCamiloEntryPrettyIThink() throws ParseException, IOException, ParserException {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
        String customer = "Camilo";
        String callerNumber = "831-227-1838";
        String calleeNumber = "831-227-1234";
        Date begin = sdf.parse("3/3/2022 10:29 am");
        Date end = sdf.parse("03/03/2022 12:29 pm");
        PhoneBill aBill = new PhoneBill(customer);
        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
        aBill.addPhoneCall(call);

        HttpRequestHelper http = mock(HttpRequestHelper.class);
        when(http.get(eq(Map.of("customer", customer)))).thenReturn(phoneBillAsText(aBill));

        PhoneBillRestClient client = new PhoneBillRestClient(http);

        String res = client.getPhoneBill(customer);
        StringWriter sw = new StringWriter();

        assertThat(res, containsString("Customer name:\n" + customer));
        assertThat(res, containsString(
                "Caller:       Callee:       Call Begins:          Call Ends:            Time:"));
        assertThat(res, containsString(
                "------------  ------------  --------------------  --------------------  --------"));
        assertThat(res, containsString(
                 callerNumber + "  " + calleeNumber + "  Mar 03, 22  10:29 AM  Mar 03, 22  12:29 PM  120 mins"));
    }
  // this all got broke when I changed the textdumper class...
//  @Test
//  void getAllDictionaryEntriesPerformsHttpGetWithNoParameters() throws ParserException, IOException {
//    Map<String, String> dictionary = Map.of("One", "1", "Two", "2");
//
//    HttpRequestHelper http = mock(HttpRequestHelper.class);
//    when(http.get(eq(Map.of()))).thenReturn(dictionaryAsText(dictionary));
//
//    PhoneBillRestClient client = new PhoneBillRestClient(http);
//
//    assertThat(client.getAllDictionaryEntries(), equalTo(dictionary));
//  }

  private HttpRequestHelper.Response phoneBillAsText(PhoneBill aBill) {
      StringWriter sw = new StringWriter();
      new TextDumper(sw).dump(aBill);
      return new HttpRequestHelper.Response(sw.toString());
  }
//  private HttpRequestHelper.Response dictionaryAsText(Map<String, String> dictionary) {
//    StringWriter writer = new StringWriter();
//    new TextDumper(writer).dump(dictionary);
//
//    return new HttpRequestHelper.Response(writer.toString());
//  }
}
