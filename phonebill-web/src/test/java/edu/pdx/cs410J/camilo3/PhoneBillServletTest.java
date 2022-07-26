package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class PhoneBillServletTest {

  // this doesn't quite work the same way with the phonebill...
//  @Test
//  void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
//    PhoneBillServlet servlet = new PhoneBillServlet();
//
//    HttpServletRequest request = mock(HttpServletRequest.class);
//    HttpServletResponse response = mock(HttpServletResponse.class);
//    PrintWriter pw = mock(PrintWriter.class);
//
//    when(response.getWriter()).thenReturn(pw);
//
//    servlet.doGet(request, response);
//
//    // Nothing is written to the response's PrintWriter
//    verify(pw, never()).println(anyString());
//    verify(response).setStatus(HttpServletResponse.SC_OK);
//  }

  // doesn't work right now due to changing of messages and changing to phonebill
  @Test
  void addOnePhoneBillToDictionary() throws IOException, ParseException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Camilo Schaser-Hughes";
    String callerNumber = "831-227-1838";
    String calleeNumber = "831-222-1234";
    String begin = "3/3/2022 11:11 am";
    String end = "03/03/2022 12:12 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customer);
    when(request.getParameter("callerNumber")).thenReturn(callerNumber);
    when(request.getParameter("calleeNumber")).thenReturn(calleeNumber);
    when(request.getParameter("begin")).thenReturn(begin);
    when(request.getParameter("end")).thenReturn(end);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
    PhoneCall fakeCall = new PhoneCall(callerNumber, calleeNumber, sdf.parse(begin), sdf.parse(end));

    assertThat(stringWriter.toString(), containsString(Messages.addedPhoneCall(customer, fakeCall)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    // not sure if need this or if it's really all that important, it would be get phonebill
    // and then assert the textdumper version of it...
//    assertThat(servlet.getDefinition(word), equalTo(definition));
  }

}
