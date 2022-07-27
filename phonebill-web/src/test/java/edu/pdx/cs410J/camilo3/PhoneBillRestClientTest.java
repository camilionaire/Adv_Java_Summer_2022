package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        assertTrue(custBill.getCustomer().equals(customer));
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) custBill.getPhoneCalls();
        assertTrue(calls.get(0).getCaller().equals(callerNumber));
        assertTrue(calls.get(0).getCallee().equals(calleeNumber));
        assertTrue(calls.get(0).getBeginTime().getTime() == begin.getTime());
        assertTrue(calls.get(0).getEndTime().getTime() == end.getTime());
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

        assertTrue(custBill.getCustomer().equals(customer));
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) custBill.getPhoneCalls();
        assertTrue(calls.get(0).getCaller().equals(callerNumber));
        assertTrue(calls.get(0).getCallee().equals(calleeNumber));
        assertTrue(calls.get(0).getBeginTime().getTime() == begin2.getTime());
        assertTrue(calls.get(0).getEndTime().getTime() == end2.getTime());
    }

//    @Test
//    void putAPhoneBillOntoTheServlet() {
//        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
//        String customer = "Camilo";
//        String callerNumber = "831-227-1838";
//        String calleeNumber = "831-227-1234";
//        String begin = "3/3/2022 10:29 am";
//        String end = "03/03/2022 12:29 pm";
//
//        HttpRequestHelper http = mock(HttpRequestHelper.class);
//        when(http.post(eq(Map.of("customer", customer, "callerNumber", callerNumber, "calleeNumber", calleeNumber, begin, "end", end)))).thenReturn(phoneBillAsText(aBill));
//    }

  private HttpRequestHelper.Response phoneBillAsText(PhoneBill aBill) {
      StringWriter sw = new StringWriter();
      new TextDumper(sw).dump(aBill);
      return new HttpRequestHelper.Response(sw.toString());
  }
}
