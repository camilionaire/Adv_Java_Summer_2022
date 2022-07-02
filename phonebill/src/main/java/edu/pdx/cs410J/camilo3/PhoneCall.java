package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * The class represents a phone call.
 */
public class PhoneCall extends AbstractPhoneCall {
  private String callerNumber;
  private String calleeNumber;
  private String beginTimeString;
  private String endTimeString;
  /**
   * Creates a new <code>Student</code>
   *
   * @param caller
   *        The caller's number as a String.
   *        Format "nnn-nnn-nnnn" where n is a 0-9 character.
   * @param callee
   *        The callee's number as a String.
   *        Format "nnn-nnn-nnnn" where n is a 0-9 character.
   * @param begin
   *        The time the phone call was placed.
   * @param end
   *        The time the phone call was finished.
   */

  public PhoneCall(String caller, String callee, String begin, String end) {
    this.callerNumber = caller;
    this.calleeNumber = callee;
    this.beginTimeString = begin;
    this.endTimeString = end;
  }
  /**
   * getCaller() returns the callerNumber string.
   */
  @Override
  public String getCaller() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.callerNumber;
  }

  /**
   * getCallee() returns the calleeNumber string.
   */
  @Override
  public String getCallee() {
//    return "This method is not implemented yet";
    return this.calleeNumber;
  }

  /**
   * getBeginTimeString() returns the beginTimeString string..
   */
  @Override
  public String getBeginTimeString() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.beginTimeString;
  }

  /**
   * getEndTimeString() returns the endTimeString string..
   */
  @Override
  public String getEndTimeString() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.endTimeString;
  }
}
