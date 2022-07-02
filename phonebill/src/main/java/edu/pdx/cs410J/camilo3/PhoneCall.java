package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

  private String callerNumber;
  private String calleeNumber;
  private String beginTimeString;
  private String endTimeString;

  public PhoneCall(String caller, String callee, String begin, String end) {
    this.callerNumber = caller;
    this.calleeNumber = callee;
    this.beginTimeString = begin;
    this.endTimeString = end;
  }
  @Override
  public String getCaller() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.callerNumber;
  }

  @Override
  public String getCallee() {
//    return "This method is not implemented yet";
    return this.calleeNumber;
  }

  @Override
  public String getBeginTimeString() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.beginTimeString;
  }

  @Override
  public String getEndTimeString() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.endTimeString;
  }
}
