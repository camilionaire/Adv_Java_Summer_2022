package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

// might use later...
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneBillRestClientTest {

    // this one I failed to get working...
    @Test
    void getFullPhoneBillCamiloEntryReturnsPhoneBill() throws ParseException, IOException, ParserException {
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

        PhoneBill custBill = client.getPhoneBill(customer);

        assertEquals(custBill.getCustomer(), customer);
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) custBill.getPhoneCalls();
        assertEquals(calls.get(0).getCaller(), callerNumber);
        assertEquals(calls.get(0).getCallee(), calleeNumber);
        assertEquals(calls.get(0).getBeginTime().getTime(), begin.getTime());
        assertEquals(calls.get(0).getEndTime().getTime(), end.getTime());
    }

    @Test
    void getPartialPhoneBillCamiloEntryReturnsPhoneBill() throws ParseException, IOException, ParserException {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
        String customer = "Camilo";
        String callerNumber = "831-227-1838";
        String calleeNumber = "831-227-1234";
        String begin = "3/4/2022 10:29 am";
        String end = "03/06/2022 12:29 pm";
        String beg2String = "3/5/2022 10:29 am";
        String end2String = "03/05/2022 12:29 pm";
        Date begin2 = sdf.parse(beg2String);
        Date end2 = sdf.parse(end2String);
        PhoneBill aBill = new PhoneBill(customer);
//        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
        PhoneCall call2 = new PhoneCall(callerNumber, calleeNumber, begin2, end2);
//        aBill.addPhoneCall(call);
        aBill.addPhoneCall(call2);

        HttpRequestHelper http = mock(HttpRequestHelper.class);
        when(http.get(eq(Map.of("customer", customer, "begin", begin, "end", end)))).thenReturn(phoneBillAsText(aBill));

        PhoneBillRestClient client = new PhoneBillRestClient(http);

        PhoneBill custBill = client.getPartialPhoneBill(customer, begin, end);
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) custBill.getPhoneCalls();

        assertEquals(custBill.getCustomer(), customer);
        assertEquals(calls.get(0).getCaller(), callerNumber);
        assertEquals(calls.get(0).getCallee(), calleeNumber);
        assertEquals(calls.get(0).getBeginTime().getTime(), begin2.getTime());
        assertEquals(calls.get(0).getEndTime().getTime(), end2.getTime());
    }

    @Test
    void putAPhoneBillOntoTheServlet() throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
        String customer = "Camilo";
        String callerNumber = "831-227-1838";
        String calleeNumber = "831-227-1234";
        String beginS = "3/3/2022 10:29 am";
        String endS = "03/03/2022 12:29 pm";
        Date begin = sdf.parse(beginS);
        Date end = sdf.parse(endS);
        PhoneBill aBill = new PhoneBill(customer);
        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
        aBill.addPhoneCall(call);

        HttpRequestHelper.Response fakeResponse = mock(HttpRequestHelper.Response.class);
        when(fakeResponse.getHttpStatusCode()).thenReturn(404);

        HttpRequestHelper http = mock(HttpRequestHelper.class);
        when(http.post(eq(Map.of("customer", customer,
                "callerNumber", callerNumber, "calleeNumber", calleeNumber,
                "begin", beginS, "end", endS)))).thenReturn(fakeResponse);

        PhoneBillRestClient client = new PhoneBillRestClient(http);

        Exception exception = assertThrows(HttpRequestHelper.RestException.class, () ->
                client.addPhoneCallEntry(customer, callerNumber, calleeNumber, beginS, endS));

    }

//    private HttpRequestHelper.Response fakeResponseNotFound() {
//
//        HttpRequestHelper.Response response = mock(HttpRequestHelper.Response.class);
//        when(response.getHttpStatusCode()).thenReturn(404);
//
//        return response;
//    }

  private HttpRequestHelper.Response phoneBillAsText(PhoneBill aBill) {
      StringWriter sw = new StringWriter();
      new TextDumper(sw).dump(aBill);
      HttpRequestHelper.Response response = new HttpRequestHelper.Response(sw.toString());
      return response;
  }
}
