package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

  SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");
  /**
   * This unit test makes sure something is created.
   */
  @Test
  void testToSeeThatSomethingIsCreated() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertNotNull(call);
  }

  /**
   * This unit test makes sure getCallerFunction works
   */
  @Test
  void testToSeeThatCallerIsSetAndGetCallerWorks() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertThat(call.getCaller(), containsString("503-867-5309"));
  }

  @Test
  void testToSeeThatCalleeIsSetAndGetCalleeWorks() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertThat(call.getCallee(), containsString("800-666-1234"));
  }

  @Test
  void testToSeeThatBeginTimeIsSetAndGetBeginTimeStringWorks() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertThat(call.getBeginTimeString(), containsString("3/2/22"));
  }

  @Test
  void testToSeeThatEndTimeIsSetAndGetEndTimeStringWorks() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertThat(call.getEndTimeString(), containsString("3/15/22"));
  }

  @Test
  void testToMakeSureToStringFunctionStillPrintsWhatItUsedTo() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));

    assertThat(call.toString(), containsString(
            "Phone call from 503-867-5309 to 800-666-1234 from 3/2/22, 1:03 AM to 3/15/22, 10:39 AM"));
  }
  @Test
  void forProject1NowGetBeginTimeReturnsStuff() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertTrue(call.getBeginTime().equals(sdf.parse("03/02/2022 1:03 am")));
  }

  @Test
  void forProject1NowGetEndTimeReturnsStuff() throws ParseException {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    assertEquals(call.getEndTime(), sdf.parse("3/15/2022 10:39 am"));
  }

  @Test
  void compareToPutsOutANegativeTest() throws ParseException {
    PhoneCall call1 = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 1:03 am"), sdf.parse("3/15/2022 10:39 am"));
    PhoneCall call2 = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/2/2022 12:03 am"), sdf.parse("3/02/2022 10:39 am"));

    assertEquals(call2.compareTo(call1), -1);
  }

  @Test
  void compareToPutsOutAPositiveTest() throws ParseException {
    // 9 in phone number comes after 8 in phone number
    PhoneCall call1 = new PhoneCall("503-867-5308", "800-666-1234",
            sdf.parse("03/1/2022 1:03 am"), sdf.parse("3/2/2022 10:37 am"));
    PhoneCall call2 = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/1/2022 1:03 am"), sdf.parse("3/02/2022 10:39 am"));
    assertEquals(call2.compareTo(call1), 1);
  }

  @Test
  void compareToPutsOutAZeroTest() throws ParseException {
    PhoneCall call1 = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/1/2022 1:03 am"), sdf.parse("3/2/2022 10:37 am"));
    PhoneCall call2 = new PhoneCall("503-867-5309", "800-666-1234",
            sdf.parse("03/1/2022 1:03 am"), sdf.parse("3/02/2022 10:39 am"));
    assertEquals(call2.compareTo(call1), 0);
  }
}
