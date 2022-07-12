package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * PhoneBill class extends AbstractPhoneBill
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  private List<PhoneCall> phoneCalls;

/**
 * Creates a new <code>PhoneBill</code>
 *
 * @param customer
 *        The customer's number as a String.
 *        Format "whatever string" where the string is unicode? i think...
 *        the phone call list will automatically be made with 1 slot
 *        and will grow as necessary.
 */

public PhoneBill(String customer) {
    this.customer = customer;
    this.phoneCalls = new ArrayList<>();
  }

  /**
   * super simple getCustomer() returns the string customer name
   * overrides the abstract phone bill's abstract getCustomer method
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  /**
   * addPhoneCall
   * adds a phone call to a growable array.  array starts as single element and
   * grows by double each time it expands beyond limits
   * adds 1 to the number of calls
   * @param call
   *        the call to be added to the phoneCalls list.
   */
  @Override
  public void addPhoneCall(PhoneCall call) {
    phoneCalls.add(call);
  }

  /**
   * returns the array as a copy from the range of # of phone calls and then
   * turns that into a list and returns that, there is probably a simpler way.
   * but this seems to work.
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return this.phoneCalls;
  }
}
