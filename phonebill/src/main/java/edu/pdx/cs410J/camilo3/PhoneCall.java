package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * The class represents a phone call. It is made up of a
 * callerNumber, calleeNumber, beginTimeString, and endTimeString
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
   *        The time the phone call was placed as a String.
   * @param end
   *        The time the phone call was finished as a String.
   */

  public PhoneCall(String caller, String callee, String begin, String end) {
    this.callerNumber = caller;
    this.calleeNumber = callee;
    this.beginTimeString = begin;
    this.endTimeString = end;
  }
  /**
   * getCaller() returns the callerNumber String.
   */
  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  /**
   * getCallee() returns the calleeNumber String.
   */
  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  /**
   * getBeginTimeString() returns the beginTimeString as a String.
   */
  @Override
  public String getBeginTimeString() {
    return this.beginTimeString;
  }

  /**
   * getEndTimeString() returns the endTimeString as a String.
   */
  @Override
  public String getEndTimeString() {
    return this.endTimeString;
  }
}
