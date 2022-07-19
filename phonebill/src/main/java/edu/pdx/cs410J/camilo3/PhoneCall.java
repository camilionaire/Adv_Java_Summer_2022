package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The class represents a phone call. It is made up of a
 * callerNumber, calleeNumber, beginTime, and endTime
 */
public class PhoneCall extends AbstractPhoneCall {

  private DateFormat df;
  private String callerNumber;
  private String calleeNumber;
  private Date beginTime;
  private Date endTime;
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

  public PhoneCall(String caller, String callee, Date begin, Date end) {
    this.df = DateFormat.getDateInstance(DateFormat.SHORT);
    this.callerNumber = caller;
    this.calleeNumber = callee;
    this.beginTime = begin;
    this.endTime = end;
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
   * getBeginTimeString() returns the beginTime as a String.
   */
  @Override
  public String getBeginTimeString() {
    return df.format(this.beginTime);
  }

  /**
   * getEndTimeString() returns the endTime as a String.
   */
  @Override
  public String getEndTimeString() {
    return df.format(this.endTime);
  }

  /**
   * getBeginTime() returns the beginTime as a Date.
   */
  @Override
  public Date getBeginTime() {
    return this.beginTime;
  }

  /**
   * getEndTime() returns the endTime as a Date.
   */
  @Override
  public Date getEndTime() {
    return this.endTime;
  }
}
