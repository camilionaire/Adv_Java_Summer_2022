package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

  /**
   * This unit test makes sure something is created.
   */
  @Test
  void testToSeeThatSomethingIsCreated() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertNotNull(call);
  }

  /**
   * This unit test makes sure getCallerFunction works
   */
  @Test
  void testToSeeThatCallerIsSetAndGetCallerWorks() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertThat(call.getCaller(), containsString("503-867-5309"));
  }

  @Test
  void testToSeeThatCalleeIsSetAndGetCalleeWorks() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertThat(call.getCallee(), containsString("800-666-1234"));
  }

  @Test
  void testToSeeThatBeginTimeIsSetAndGetBeginTimeStringWorks() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertThat(call.getBeginTimeString(), containsString("03/2/2022 1:03"));
  }

  @Test
  void testToSeeThatEndTimeIsSetAndGetEndTimeStringWorks() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertThat(call.getEndTimeString(), containsString("3/15/2022 10:39"));
  }

  @Test
  void testToMakeSureToStringFunctionStillPrintsWhatItUsedTo() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");

    assertThat(call.toString(), containsString( "Phone call from 503-867-5309 to 800-666-1234 from 03/2/2022 1:03 to 3/15/2022 10:39"));
  }
  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertThat(call.getBeginTime(), is(nullValue()));
  }

  @Test
  void forProject1ItIsOkayIfGetEndTimeReturnsNull() {
    PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
    assertThat(call.getEndTime(), is(nullValue()));
  }
}
