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

  // redone so now it works i think...
  @Test
  void addOnePhoneBillToDictionary() throws IOException, ParseException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Camilo Schaser-Hughes";
    String callerNumber = "831-227-1838";
    String calleeNumber = "831-222-1234";
    // keep day it looks like double digits leading zero for test to work
    // it is not hardcoded and
    String begin = "3/03/2022 11:11 am";
    String end = "3/03/2022 12:12 pm";

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

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    HttpServletRequest request2 = mock(HttpServletRequest.class);
    when(request2.getParameter("customer")).thenReturn(customer);
    when(request2.getParameter("begin")).thenReturn(null);
    when(request2.getParameter("end")).thenReturn(null);
    HttpServletResponse response2 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter2 = new StringWriter();
    PrintWriter pw2 = new PrintWriter(stringWriter2, true);

    when(response2.getWriter()).thenReturn(pw2);

    servlet.doGet(request2, response2);

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode2 = ArgumentCaptor.forClass(Integer.class);
    verify(response2).setStatus(statusCode2.capture());

    assertThat(statusCode2.getValue(), equalTo(HttpServletResponse.SC_OK));
    assertThat(stringWriter2.toString(), containsString(customer));
    assertThat(stringWriter2.toString(), containsString(callerNumber + " "  + calleeNumber +
            " " + begin + " " + end));
  }

  @Test
  void testToCheckIfNoBillsSomethingHappens() throws IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Camilo Schaser-Hughes";
    String begin = "3/03/2022 11:11 am";
    String end = "3/03/2022 12:12 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customer);
    when(request.getParameter("begin")).thenReturn(begin);
    when(request.getParameter("end")).thenReturn(end);
    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    // don't know if I need this for this test
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> errMsg = ArgumentCaptor.forClass(String.class);
    verify(response).sendError(statusCode.capture(), errMsg.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_NO_CONTENT));
    assertThat(errMsg.getValue(), containsString("We could not locate any phone bill's by the\n" +
            "name " + customer + ".  Sorry."));

  }
  @Test
  void testToCheckIfTimesBackwardsSomethingHappens() throws IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Camilo Schaser-Hughes";
    String end = "3/03/2022 11:11 am";
    String begin = "3/03/2022 12:12 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customer);
    when(request.getParameter("begin")).thenReturn(begin);
    when(request.getParameter("end")).thenReturn(end);
    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    // don't know if I need this for this test
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> errMsg = ArgumentCaptor.forClass(String.class);
    verify(response).sendError(statusCode.capture(), errMsg.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_PRECONDITION_FAILED));
    assertThat(errMsg.getValue(), containsString("The required parameter \"Dates\" is is incorrectly formed."));
    assertThat(errMsg.getValue(), containsString("\nBegin date must be after End Date as well."));

  }

}
